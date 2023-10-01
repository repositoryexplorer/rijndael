package com.alfanet.aes.dataprocessor;

import com.alfanet.aes.AESMode;
import com.alfanet.aes.decrypt.DecryptFactory;
import com.alfanet.aes.decrypt.DecryptionProcessor;
import com.alfanet.utils.InputLengthException;

public class DecryptionDataProcessor implements AESDataProcessor {

    private final DecryptionProcessor processor;

    public DecryptionDataProcessor(AESMode mode, byte[] key, byte[] initVector) {
        processor = DecryptFactory.createDecryptionProcessor(mode, key, initVector);
    }

    @Override
    public byte[] processData(byte[] data) throws InputLengthException {
        return processor.decryptData(data);
    }
}
