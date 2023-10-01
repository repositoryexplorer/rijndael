package com.alfanet.aes;

import com.alfanet.utils.InputBlockUtils;
import com.alfanet.utils.InputLengthException;

import java.util.Collections;
import java.util.List;

public class AesUtils {
    public static final int StateWords = 4;
    public static final int AES_BLOCK_SIZE = 16;

    /**
     * Encrypts plainText using Rijndael(AES) algorithm
     *
     * @param key       - key used to encrypt. Allowed key length: 16 (128bit). 24 bytes (192bit), 32 bytes (256bit).
     * @param plainText - plain text to encrypt. Always 16 bytes (128 bit) length
     * @return encrypted text
     */
    public byte[] encrypt(byte[] key, byte[] plainText) throws InputLengthException {
        byte[][] state = new byte[4][StateWords];
        int roundNum = getRoundCountForKeyLen(key.length);
        List<byte[][]> keys = getKeys(key, roundNum);

        InputBlockUtils.inputToState(plainText, state);
        return encrypt(keys, roundNum, state);
    }

    public byte[] decrypt(byte[] key, byte[] encryptedText) throws InputLengthException {
        byte[][] state = new byte[4][StateWords];
        int roundNum = getRoundCountForKeyLen(key.length);
        List<byte[][]> keys = getKeys(key, roundNum);

        InputBlockUtils.inputToState(encryptedText, state);
        return decrypt(keys, roundNum, state);
    }

    private byte[] decrypt(List<byte[][]> keys, int roundNum, byte[][] state) {
        Collections.reverse(keys);
        byte[][] k = keys.get(0);
        state = AddRoundKey.addRoundKey(state, keys.get(0));
        for (int i = 0; i < roundNum; i++) {
            state = ShiftRows.invShiftRows(state);
            state = SBox.invSubStateBytes(state);
            state = AddRoundKey.addRoundKey(state, keys.get(i + 1));
            if (i != roundNum - 1) {
                state = MixColumns.invMixColumns(state); // in last round we don't mix columns
            }
        }
        return InputBlockUtils.stateToOutput(state);
    }


    private byte[] encrypt(List<byte[][]> keys, int roundNum, byte[][] state) {
        byte[][] k = keys.get(0);
        state = AddRoundKey.addRoundKey(state, keys.get(0));
        for (int i = 0; i < roundNum; i++) {
            state = SBox.subStateBytes(state);
            state = ShiftRows.shiftRows(state);
            if (i != roundNum - 1) {
                state = MixColumns.mixColumns(state); // in last round we don't mix columns
            }
            state = AddRoundKey.addRoundKey(state, keys.get(i + 1));
        }
        return InputBlockUtils.stateToOutput(state);
    }

    private List<byte[][]> getKeys(byte[] key, int roundNum) throws InputLengthException {
        return KeyExpansion.allKeysToRoundKeysByteArray(KeyExpansion.expandKey(key, roundNum));
    }

    private int getRoundCountForKeyLen(int keyLen) {
        switch (keyLen) {
            case 32 -> {
                return 14;
            }
            case 24 -> {
                return 12;
            }
            default -> {
                return 10;
            }
        }
    }

    public byte[] xorArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length];
        for (int i = 0; i < array1.length; i++) {
            result[i] = (byte) (array1[i] ^ array2[i]);
        }
        return result;
    }
}