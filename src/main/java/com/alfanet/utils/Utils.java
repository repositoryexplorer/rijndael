package com.alfanet.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Utils {
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static int bytesToInt(byte[] bytes) throws InputLengthException {
        if (bytes.length != 4) {
            throw new InputLengthException("Wrong input length, expected: 4, current: " + bytes.length);
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    public static String bytesToString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            result += String.format("%02x", bytes[i]);
        }
        return result;
    }

    public static byte[] intToBytes(int input) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(input);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.array();
    }
}
