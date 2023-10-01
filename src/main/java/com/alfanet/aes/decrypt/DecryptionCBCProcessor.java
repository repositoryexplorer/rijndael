package com.alfanet.aes.decrypt;

import com.alfanet.aes.AesUtils;
import com.alfanet.utils.InputLengthException;

import static com.alfanet.aes.AesUtils.AES_BLOCK_SIZE;

public class DecryptionCBCProcessor implements DecryptionProcessor {

    private byte[] key;
    private byte[] lastData = new byte[AES_BLOCK_SIZE];

    public DecryptionCBCProcessor(byte[] key, byte[] initVector) {
        this.key = key;
        this.lastData = initVector;
    }

    @Override
    public byte[] decryptData(byte[] data) throws InputLengthException {
        AesUtils aes = new AesUtils();
        byte[] decryptedData = aes.decrypt(key, data);
        byte[] result = aes.xorArrays(decryptedData, lastData);
        System.arraycopy(data, 0, lastData, 0, AES_BLOCK_SIZE);
        return result;
    }
}
