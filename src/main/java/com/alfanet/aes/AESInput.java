package com.alfanet.aes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AESInput {
    private String inputFilePath;
    private String outputFilePath;
    private byte[] key;
    private CipherOperation operation;
    private AESMode mode;
    private byte[] initVector;
}
