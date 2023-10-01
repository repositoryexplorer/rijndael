package com.alfanet.fileutils;

import static com.alfanet.aes.AesUtils.AES_BLOCK_SIZE;

public class Padding {
    public static byte[] addPadding(byte[] bytes, int bytesRead) {
        byte[] result;
        int rest = bytesRead % AES_BLOCK_SIZE;
        int paddingValue = AES_BLOCK_SIZE - rest;
        if (bytesRead == AES_BLOCK_SIZE) {
            result = new byte[AES_BLOCK_SIZE * 2];
        } else {
            result = new byte[AES_BLOCK_SIZE];
        }
        System.arraycopy(bytes, 0, result, 0, bytesRead);
        for (int i = bytesRead; i < result.length; i++) {
            result[i] = (byte) paddingValue;
        }
        return result;
    }

    public static byte[] removePadding(byte[] data) {
        byte len = data[data.length - 1];
        byte[] result = new byte[data.length - len];
        System.arraycopy(data, 0, result, 0, result.length);
        return result;
    }
}
