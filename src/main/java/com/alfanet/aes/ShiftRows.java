package com.alfanet.aes;

import static com.alfanet.aes.AesUtils.StateWords;

public class ShiftRows {
    public static byte[][] shiftRows(byte[][] state) {
        for (int row = 1; row < 4; row++) {
            byte[] tmp = new byte[row];
            System.arraycopy(state[row], 0, tmp, 0, row);
            for (int col = row; col < StateWords; col++) {
                state[row][col - row] = state[row][col];
            }
            System.arraycopy(tmp, 0, state[row], StateWords - tmp.length, tmp.length);
        }
        return state;
    }
}
