package com.alfanet.aes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public class AESInput {
    private String inputFilePath;
    private String outputFilePath;
    private byte[] key;
    private CipherOperation operation;
    private AESMode mode;
    private byte[] initVector;
    private boolean verbose;

    @Override
    public String toString() {
        return "AESInput{" +
                "inputFilePath='" + inputFilePath + '\'' +
                ", outputFilePath='" + outputFilePath + '\'' +
                ", key=" + Arrays.toString(key) +
                ", operation=" + operation +
                ", mode=" + mode +
                ", initVector=" + Arrays.toString(initVector) +
                ", verbose mode=" + verbose +
                '}';
    }
}
