package com.alfanet.aes.dataprocessor;

import com.alfanet.aes.AESInput;

public class AESDataProcessorFactory {
    public static AESDataProcessor createDataProcessor(AESInput aesInput) {
        switch (aesInput.getOperation()) {
            case ENCRYPTION -> {
                return new EncryptionDataProcessor(aesInput.getMode(), aesInput.getKey(), aesInput.getInitVector());
            }
            case DECRYPTION -> {
                return new DecryptionDataProcessor(aesInput.getMode(), aesInput.getKey(), aesInput.getInitVector());
            }
        }
        return null;
    }
}
