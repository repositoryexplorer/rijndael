package com.alfanet.aes.decrypt;

import com.alfanet.aes.AesUtils;
import com.alfanet.utils.InputLengthException;

public class DecryptionECBProcessor implements DecryptionProcessor {

    private byte[] key;

    public DecryptionECBProcessor(byte[] key) {
        this.key = key;
    }

    @Override
    public byte[] decryptData(byte[] data) throws InputLengthException {
        AesUtils aes = new AesUtils();
        return aes.decrypt(key, data);
    }
}
