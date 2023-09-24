package com.alfanet.fileutils;

import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    public static byte[] readKey(String keyFilePath) throws IOException, KeyLengthException {
        try (FileInputStream inStream = new FileInputStream(keyFilePath)) {
            byte[] keyBytes = readKeyBytes(inStream);
            return keyBytes;
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
}
