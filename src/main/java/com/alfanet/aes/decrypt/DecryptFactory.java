package com.alfanet.aes.decrypt;

import com.alfanet.aes.AESMode;

public class DecryptFactory {
    public static DecryptionProcessor createDecryptionProcessor(AESMode mode, byte[] key, byte[] initVector) {
        switch (mode) {
            case CBC -> {
                return new DecryptionCBCProcessor(key, initVector);
            }
            case ECB -> {
                return new DecryptionECBProcessor(key);
            }
        }
        return null;
    }
}
