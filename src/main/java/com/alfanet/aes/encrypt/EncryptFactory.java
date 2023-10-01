package com.alfanet.aes.encrypt;

import com.alfanet.aes.AESMode;

public class EncryptFactory {
    public static EncryptionProcessor createEncryptionProcessor(AESMode mode, byte[] key, byte[] initVector) {
        switch (mode) {
            case CBC -> {
                return new EncryptionCBCProcessor(key, initVector);
            }
            case ECB -> {
                return new EncryptionECBProcessor(key);
            }
        }
        return null;
    }
}
