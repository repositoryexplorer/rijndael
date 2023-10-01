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

    public static byte[][] invShiftRows(byte[][] state) {
        for (int row = 1; row < 4; row++) {
            byte[] tmp = new byte[StateWords];
            System.arraycopy(state[row], 0, tmp, row, StateWords - row);
            for (int col = StateWords - row; col < StateWords; col++) {
                int idx = 0 + (col - (StateWords - row));
                tmp[idx] = state[row][col];
            }
            System.arraycopy(tmp, 0, state[row], 0, tmp.length);
        }
        return state;
    }
}
