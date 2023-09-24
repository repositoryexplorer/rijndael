package com.alfanet.parameters;

import com.alfanet.fileutils.FileUtils;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;

import java.util.List;
import java.util.Optional;

@Getter
public class ParametersParser {

    public enum Operation {ENCRYPT, DECRYPT}

    ;

    private String keyFilePath;
    private String inputFilePath;

    private Operation operation;
    private byte[] keyBytes;

    public boolean parseArguments(ApplicationArguments params) {
        Optional<String> keyFileParam = getFileValue(params.getOptionValues("k"));
        Optional<String> inputFileParam = getFileValue(params.getOptionValues("i"));
        if (keyFileParam.isPresent() && inputFileParam.isPresent()) {
            keyFilePath = keyFileParam.get();
            try {
                keyBytes = FileUtils.readKey(keyFilePath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                printHelp();
                System.exit(0);
            }
            inputFilePath = inputFileParam.get();
            operation = getOperationParam(params.containsOption("d"));
        } else {
            printHelp();
            return false;
        }
        return true;
    }

    private Operation getOperationParam(boolean isDecryptOption) {
        return isDecryptOption ? Operation.DECRYPT : Operation.ENCRYPT;
    }

    private Optional<String> getStringValue(List<String> paramValues) {
        if (paramValues != null && paramValues.size() > 0) {
            return Optional.of(paramValues.get(0));
        } else return Optional.empty();
    }

    private Optional<String> getFileValue(List<String> paramValues) {
        Optional<String> filePath = getStringValue(paramValues);
        if (filePath.isPresent()) {
            return filePath;
        } else return Optional.empty();
    }

    private void printHelp() {
        System.out.println("Usage:");
        System.out.println(" java -jar rijndael.jar [options]");
        System.out.println("Encrypts/decrypts input file using Rijndael(AES) algorithm.");
        System.out.println("Options");
        System.out.println(" --k      file with private secret key (Hexadecimal digits only allowed. Supported key lengths are: 128, 192, 256)");
        System.out.println(" --i      input file to be encrypted/decrypted");
        System.out.println(" --d      decrypt file content, if omit the default behaviour is encrypt");
        System.out.println("\n");
        System.out.println("Example usage");
        System.out.println(" java -jar rijndael.jar --k=key --i=inputfile");
        System.out.println(" java -jar rijndael.jar --k=key --k=encryptedfile --d");
    }
}
