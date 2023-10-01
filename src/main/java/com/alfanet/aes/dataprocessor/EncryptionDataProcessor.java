package com.alfanet.aes.dataprocessor;

import com.alfanet.aes.AESMode;
import com.alfanet.aes.encrypt.EncryptFactory;
import com.alfanet.aes.encrypt.EncryptionProcessor;
import com.alfanet.utils.InputLengthException;

public class EncryptionDataProcessor implements AESDataProcessor {
    private final EncryptionProcessor processor;

    public EncryptionDataProcessor(AESMode mode, byte[] key, byte[] initVector) {
        processor = EncryptFactory.createEncryptionProcessor(mode, key, initVector);
    }

    @Override
    public byte[] processData(byte[] data) throws InputLengthException {
        return processor.encryptData(data);
    }
}
