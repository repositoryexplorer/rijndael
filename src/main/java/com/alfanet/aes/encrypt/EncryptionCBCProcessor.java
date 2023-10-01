package com.alfanet.aes.encrypt;

import com.alfanet.aes.AesUtils;
import com.alfanet.utils.InputLengthException;

public class EncryptionCBCProcessor implements EncryptionProcessor {

    private byte[] key;
    private byte[] lastOutput;

    public EncryptionCBCProcessor(byte[] key, byte[] initVector) {
        this.key = key;
        this.lastOutput = initVector;
    }

    @Override
    public byte[] encryptData(byte[] data) throws InputLengthException {
        AesUtils aes = new AesUtils();
        byte[] xoredData = aes.xorArrays(lastOutput, data);
        lastOutput = aes.encrypt(key, xoredData);
        return lastOutput;
    }
}
