package com.alfanet.fileutils;

import com.alfanet.aes.AESInput;
import com.alfanet.aes.dataprocessor.AESDataProcessor;
import com.alfanet.aes.dataprocessor.AESDataProcessorFactory;
import com.alfanet.utils.Utils;
import lombok.SneakyThrows;

import java.io.*;

import static com.alfanet.aes.AesUtils.AES_BLOCK_SIZE;

public class FileUtils {

    private static final int BUFFER_SIZE = 64 * 1024;

    public static byte[] readKey(String keyFilePath) throws IOException, KeyLengthException {
        try (FileInputStream inStream = new FileInputStream(keyFilePath)) {
            return readKeyBytes(inStream);
        }
    }

    private static byte[] readKeyBytes(FileInputStream inStream) throws IOException, KeyLengthException {
        if (inStream.available() != 32 && inStream.available() != 48 && inStream.available() != 64) {
            throw new KeyLengthException("Unsupported key length. Allowed key lengths are: 16, 24 or 32 bytes.");
        }
        byte[] result = new byte[inStream.available() / 2];
        int index = 0;
        while (inStream.available() >= 2) {
            byte[] bytesRead = new byte[2];
            inStream.read(bytesRead);
            byte byteValue = (byte) (Character.digit(bytesRead[0], 16) * 16 + Character.digit(bytesRead[1], 16));
            result[index++] = byteValue;
        }
        return result;
    }

    @SneakyThrows
    public static void processFileEncrypt(AESInput aesInput) throws IOException {
        Utils.debugPrint(aesInput.isVerbose(), aesInput.toString());
        try (
                FileInputStream inputStream = new FileInputStream(aesInput.getInputFilePath());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
                FileOutputStream outputStream = new FileOutputStream(aesInput.getOutputFilePath());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE)
        ) {
            AESDataProcessor dataProcessor = AESDataProcessorFactory.createDataProcessor(aesInput);
            byte[] bytes = new byte[AES_BLOCK_SIZE];
            if (bufferedInputStream.available() == 0) throw new IOException("Input file is empty");
            int bytesRead;
            boolean extraRun = false; // true only if padding of last block is equal 16 bytes
            do {
                if (extraRun) {
                    extraRun = false;
                    System.arraycopy(bytes, AES_BLOCK_SIZE, bytes, 0, 16);
                } else {
                    bytesRead = bufferedInputStream.read(bytes);
                    //add padding to last block if length less than 16 and it wasn't added yet
                    if (bufferedInputStream.available() == 0) { // we want to add padding when last block is smaller than AES_BLOCK_SIZE or last block len is equal 16
                        bytes = Padding.addPadding(bytes, bytesRead);
                        if (bytes.length == AES_BLOCK_SIZE * 2) {
                            extraRun = true;
                        }
                    }
                }


                byte[] result = dataProcessor.processData(bytes);
                bufferedOutputStream.write(result);
            } while (bufferedInputStream.available() > 0 || extraRun);
        }
    }

    @SneakyThrows
    public static void processFileDecrypt(AESInput aesInput) throws IOException {
        Utils.debugPrint(aesInput.isVerbose(), aesInput.toString());
        try (
                FileInputStream inputStream = new FileInputStream(aesInput.getInputFilePath());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
                FileOutputStream outputStream = new FileOutputStream(aesInput.getOutputFilePath());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE)
        ) {
            AESDataProcessor dataProcessor = AESDataProcessorFactory.createDataProcessor(aesInput);
            byte[] bytes = new byte[AES_BLOCK_SIZE];
            if (bufferedInputStream.available() == 0) throw new IOException("Input file is empty");
            do {
                bufferedInputStream.read(bytes);

                byte[] result = dataProcessor.processData(bytes);
                if (bufferedInputStream.available() == 0) {
                    result = Padding.removePadding(result);
                }
                bufferedOutputStream.write(result);
            } while (bufferedInputStream.available() > 0);
        }
    }
}
