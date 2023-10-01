package com.alfanet.aes.encrypt;

import com.alfanet.utils.InputLengthException;

public interface EncryptionProcessor {
    byte[] encryptData(byte[] data) throws InputLengthException;
}
