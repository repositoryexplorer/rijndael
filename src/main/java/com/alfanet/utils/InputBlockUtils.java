package com.alfanet.utils;

import static com.alfanet.aes.AesUtils.StateWords;

public class InputBlockUtils {

    public static void inputToState(byte[] input, byte[][] state) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < StateWords; c++) {
                state[r][c] = input[r + 4 * c];
            }
        }
    }

    public static byte[] stateToOutput(byte[][] state) {
        byte[] output = new byte[4 * StateWords];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < StateWords; c++) {
                output[r + 4 * c] = state[r][c];
            }
        }
        return output;
    }
}
