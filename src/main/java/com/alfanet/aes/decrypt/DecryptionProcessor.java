package com.alfanet.aes.decrypt;

import com.alfanet.utils.InputLengthException;

public interface DecryptionProcessor {
    byte[] decryptData(byte[] data) throws InputLengthException;
}
