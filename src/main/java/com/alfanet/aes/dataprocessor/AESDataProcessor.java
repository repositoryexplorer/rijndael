package com.alfanet.aes.dataprocessor;

import com.alfanet.utils.InputLengthException;

public interface AESDataProcessor {
    byte[] processData(byte[] data) throws InputLengthException;
}
