package com.alfanet.parameters;

import com.alfanet.aes.AESInput;
import com.alfanet.aes.AESMode;
import com.alfanet.aes.CipherOperation;
import com.alfanet.fileutils.FileUtils;
import com.alfanet.utils.Utils;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;

import java.util.List;
import java.util.Optional;

@Getter
public class ParametersParser {

    private String keyFilePath;
    private String inputFilePath;
    private String outputFilePath;

    private CipherOperation operation;
    private byte[] keyBytes;
    private AESMode aesMode;
    private byte[] initVector;
    private boolean verbose;

    public boolean parseArguments(ApplicationArguments params) {
        Optional<String> keyFileParam = getFileValue(params.getOptionValues("k"));
        Optional<String> inputFileParam = getFileValue(params.getOptionValues("i"));
        Optional<String> outputFileParam = getFileValue(params.getOptionValues("o"));
        if (keyFileParam.isPresent() && inputFileParam.isPresent() && outputFileParam.isPresent()) {
            keyFilePath = keyFileParam.get();
            try {
                keyBytes = FileUtils.readKey(keyFilePath);
            } catch (Exception e) {
                System.out.println("Error while reading key bytes: " + e.getMessage());
                printHelp();
                System.exit(0);
            }
            inputFilePath = inputFileParam.get();
            operation = getOperationParam(params.containsOption("d"));
            outputFilePath = outputFileParam.get();
            aesMode = getModeParam(params.getOptionValues("m"));
            verbose = params.containsOption("v");
            try {
                initVector = getInitVectorParam(getStringValue(params.getOptionValues("iv")), aesMode);
            } catch (Exception e) {
                System.out.println("Initialization vector failed: " + e.getMessage());
                printHelp();
                return false;
            }
        } else {
            System.out.println("Input file, key file or output file is empty");
            printHelp();
            return false;
        }
        return true;
    }

    private byte[] getInitVectorParam(Optional<String> initVectorParam, AESMode aesMode) throws InitializationVectorSizeException, InitializationVectorMissingException {
        if (aesMode != AESMode.CBC) return new byte[0];

        if (initVectorParam.isPresent()) {
            String tmp = initVectorParam.get();
            if (tmp.length() != 32) {
                throw new InitializationVectorSizeException("Wrong IV size, expected: 16, actual: " + tmp.length());
            }
            return Utils.asciiHexBytesToByteArray(initVectorParam.get());
        } else throw new InitializationVectorMissingException();
    }

    private AESMode getModeParam(List<String> mode) {
        Optional<String> modeOpt = getStringValue(mode);
        return modeOpt.map(s -> AESMode.valueOf(s.toUpperCase())).orElse(AESMode.CBC);
    }

    private CipherOperation getOperationParam(boolean isDecryptOption) {
        return isDecryptOption ? CipherOperation.DECRYPTION : CipherOperation.ENCRYPTION;
    }

    private Optional<String> getStringValue(List<String> paramValues) {
        if (paramValues != null && !paramValues.isEmpty()) {
            return Optional.of(paramValues.get(0));
        } else return Optional.empty();
    }

    private Optional<String> getFileValue(List<String> paramValues) {
        return getStringValue(paramValues);
    }

    private void printHelp() {
        System.out.println("Usage:");
        System.out.println(" java -jar rijndael.jar [options]");
        System.out.println("Encrypts/decrypts input file using Rijndael(AES) algorithm.");
        System.out.println("It uses PKCS#7 padding for blocks smaller than 16 bytes");
        System.out.println("Options");
        System.out.println(" --k      file with private secret key (Hexadecimal digits only allowed. Supported key lengths are: 128, 192, 256)");
        System.out.println(" --i      input file to be encrypted/decrypted");
        System.out.println(" --d      decrypt file content, if omit the default behaviour is encrypt");
        System.out.println(" --m      mode, ECB or CBC");
        System.out.println(" --o      output file");
        System.out.println(" --iv     16 bytes length initialization vector (only for CBC mode). This must be string containing only hexadecimal values. ");
        System.out.println("\n");
        System.out.println("Example usage");
        System.out.println(" java -jar rijndael.jar --k=key --i=inputfile --m=ecb --o=outputfile");
        System.out.println(" java -jar rijndael.jar --k=key --i=encryptedfile --m=cbc --d --o=outputfile --iv=000102030405060708090a0b0c0d0e0f");
    }

    public AESInput toAESInput() {
        return new AESInput(inputFilePath, outputFilePath, keyBytes, operation, aesMode, initVector, verbose);
    }
}
