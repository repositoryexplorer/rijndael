package com.alfanet.aes;

import static com.alfanet.aes.AesUtils.StateWords;

public class AddRoundKey {

    public static byte[][] addRoundKey(byte[][] state, byte[][] key) {
        byte[][] result = new byte[4][StateWords];
        for (int col = 0; col < StateWords; col++) {
            for (int row = 0; row < 4; row++) {
                result[row][col] = (byte) (state[row][col] ^ key[row][col]);
            }
        }
        return result;
    }
}
