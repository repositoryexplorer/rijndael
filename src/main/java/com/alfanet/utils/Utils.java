package com.alfanet.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Utils {
    public static byte[] asciiHexBytesToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len - 1; i += 2) {
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

    public static void printMultiBytes(byte[][] bytes) {
        String result = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += String.format("%02x", bytes[j][i]);
            }
        }
        System.out.println(result);
    }

    public static byte[] asciiHexBytesToByteArray(byte[] allBytes) {
        int len = allBytes.length / 2;
        byte[] data = new byte[len];
        for (int i = 0; i < allBytes.length - 1; i += 2) {
            int a = Character.digit(allBytes[i], 16) * 16;
            int b = Character.digit(allBytes[i + 1], 16);
            data[i / 2] = (byte) (a + b);
        }
        return data;
    }

    public static void debugPrint(boolean debugMode, String string) {
        if (debugMode) System.out.println(string);
    }
}
