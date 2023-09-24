package com.alfanet.aes;

import com.alfanet.utils.InputLengthException;

import static com.alfanet.aes.AesUtils.StateWords;
import static com.alfanet.utils.Utils.bytesToInt;
import static com.alfanet.utils.Utils.intToBytes;

public class KeyExpansion {

    private static final int[] Rcon = {0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000, 0x1B000000, 0x36000000};

    public static int[] expandKey(byte[] key, int roundNum) throws InputLengthException {
        int keyWords = key.length / 4;
        int[] result = new int[StateWords * (roundNum + 1)];
        for (int i = 0; i < keyWords; i++) {
            int tmpKey = bytesToInt(new byte[]{key[i * 4], key[i * 4 + 1], key[i * 4 + 2], key[i * 4 + 3]});
            result[i] = tmpKey;
        }
        int tmpKey = 0;
        byte[] rw = {};
        int subbbb = 0;
        int rcon = 0;
        for (int i = keyWords; i < (StateWords * (roundNum + 1)); i++) {
            byte[] lastRoundKey = intToBytes(result[i - keyWords]);
            tmpKey = result[i - 1];
            if (i % keyWords == 0) {
                //tmpKey = SubWord(RotWord(intToBytes(tmpKey)) ^ Rcon[(i / keyWords)-1];
                rw = RotWord(intToBytes(tmpKey));
                subbbb = SubWord(rw);
                rcon = Rcon[(i / keyWords) - 1];
                tmpKey = subbbb ^ rcon;
            } else if (keyWords > 6 && i % keyWords == 4) {
                tmpKey = SubWord(intToBytes(tmpKey));
            }
            result[i] = result[i - keyWords] ^ tmpKey;

            //System.out.println("i= " + i + " temp: " + Integer.toHexString(tmpKey) + " rotword: " + bytesToString(rw) + " subword: " + Integer.toHexString(subbbb) +
            //        " Rcon: " + Integer.toHexString(rcon) + " After xor RCon: " + Integer.toHexString(tmpKey) + " w[i-1]: " + Integer.toHexString(result[i - keyWords]) + " w[i-1] XOR tmpKey: " + Integer.toHexString(result[i]));
        }
        String s = "";
        for (int i = 0; i < result.length; i++) {
            s += "0x" + String.format("%08x", result[i]) + ", ";
        }
        System.out.println(s);
        return result;
    }

//    i = Nk
//while (i < Nb * (Nr+1)]
//    temp = w[i-1]
//            if (i mod Nk = 0)
//    temp = SubWord(RotWord(temp)) xor Rcon[i/Nk]
//            else if (Nk > 6 and i mod Nk = 4)
//    temp = SubWord(temp)
//    end if
//      w[i] = w[i-Nk] xor temp
//      i = i + 1
//    end while


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
}