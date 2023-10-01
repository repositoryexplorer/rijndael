package com.alfanet.aes;

import com.alfanet.utils.InputLengthException;
import com.alfanet.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.alfanet.aes.AesUtils.StateWords;
import static com.alfanet.utils.Utils.bytesToInt;
import static com.alfanet.utils.Utils.intToBytes;

public class KeyExpansion {

    private static final int[] roundConst = {0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000, 0x1B000000, 0x36000000};

    public static int[] expandKey(byte[] key, int roundNum) throws InputLengthException {
        int keyWords = key.length / 4;
        int[] result = new int[StateWords * (roundNum + 1)];
        for (int i = 0; i < keyWords; i++) {
            int tmpKey = bytesToInt(new byte[]{key[i * 4], key[i * 4 + 1], key[i * 4 + 2], key[i * 4 + 3]});
            result[i] = tmpKey;
        }
        for (int i = keyWords; i < (StateWords * (roundNum + 1)); i++) {
            int tmpKey = result[i - 1];
            if (i % keyWords == 0) {
                tmpKey = SubWord(RotWord(intToBytes(tmpKey))) ^ roundConst[(i / keyWords) - 1];
            } else if (keyWords > 6 && i % keyWords == 4) {
                tmpKey = SubWord(intToBytes(tmpKey));
            }
            result[i] = result[i - keyWords] ^ tmpKey;
        }
        return result;
    }

    private static int SubWord(byte[] input) throws InputLengthException {
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = SBox.subBytes(input[i]);
        }
        return bytesToInt(result);
    }

    private static byte[] RotWord(byte[] input) {
        byte[] result = new byte[input.length];
        byte tmp = input[0];
        for (int i = 1; i < input.length; i++) {
            result[i - 1] = input[i];
        }
        result[input.length - 1] = tmp;
        return result;
    }

    public static List<byte[][]> allKeysToRoundKeysByteArray(int[] keys) {
        List<byte[][]> result = new ArrayList<>();

        for (int i = 0; i < (keys.length / 4); i++) {
            int[] roundKeys = new int[4];

            for (int j = 0; j < StateWords; j++) {
                roundKeys[j] = keys[i * 4 + j];
            }
            byte[][] keyBytes = keysToByteArray(roundKeys);
            result.add(keyBytes);
        }
        return result;
    }

    private static byte[][] keysToByteArray(int[] roundKeys) { // roundKeys is always StateWords length (4bytes in current implementation)
        byte[][] result = new byte[4][StateWords];
        for (int i = 0; i < 4; i++) {
            byte[] keyBytes = Utils.intToBytes(roundKeys[i]);
            for (int j = 0; j < StateWords; j++) {
                result[j][i] = keyBytes[j];
            }
        }
        return result;
    }
}