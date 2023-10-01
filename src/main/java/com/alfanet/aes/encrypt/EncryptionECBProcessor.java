package com.alfanet.aes.encrypt;

import com.alfanet.aes.AesUtils;
import com.alfanet.utils.InputLengthException;

public class EncryptionECBProcessor implements EncryptionProcessor {

    private byte[] key;

    public EncryptionECBProcessor(byte[] key) {
        this.key = key;
    }

    @Override
    public byte[] encryptData(byte[] data) throws InputLengthException {
        AesUtils aes = new AesUtils();
        return aes.encrypt(key, data);
    }
}
