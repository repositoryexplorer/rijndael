package com.alfanet.aes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.alfanet.aes.AesUtils.StateWords;

public class MixColumns {

    private static final List<Byte> irreduciblePoly = Arrays.asList(new Byte[]{4, 3, 1, 0});

    public static byte[][] mixColumns(byte[][] state) {
        byte[][] result = new byte[4][StateWords];
        for (int col = 0; col < StateWords; col++) {
            byte[] colBytes = {state[0][col], state[1][col], state[2][col], state[3][col]};
            result[0][col] = calculateRow0Value(colBytes);
            result[1][col] = calculateRow1Value(colBytes);
            result[2][col] = calculateRow2Value(colBytes);
            result[3][col] = calculateRow3Value(colBytes);
        }
        return result;
    }

    private static byte calculateRow3Value(byte[] colBytes) {
        byte[] poly3 = {1, 0};// {03}
        byte b1 = multiplePolyToByte(poly3, getPowersOf2((byte) colBytes[0]));
        byte b2 = colBytes[1];
        byte b3 = colBytes[2];
        byte[] poly2 = {1};// {02}
        byte b4 = multiplePolyToByte(poly2, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte calculateRow2Value(byte[] colBytes) {
        byte b1 = colBytes[0];
        byte b2 = colBytes[1];
        byte[] poly2 = {1};// {02}
        byte b3 = multiplePolyToByte(poly2, getPowersOf2((byte) colBytes[2]));
        byte[] poly3 = {1, 0};// {03}
        byte b4 = multiplePolyToByte(poly3, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte calculateRow1Value(byte[] colBytes) {
        byte b1 = colBytes[0];
        byte[] poly2 = {1};// {02}
        byte b2 = multiplePolyToByte(poly2, getPowersOf2((byte) colBytes[1]));
        byte[] poly3 = {1, 0};// {03}
        byte b3 = multiplePolyToByte(poly3, getPowersOf2((byte) colBytes[2]));
        byte b4 = colBytes[3];
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    public static byte calculateRow0Value(byte[] colBytes) {
        byte[] poly2 = {1}; // {02}
        byte b1 = multiplePolyToByte(poly2, getPowersOf2((byte) (0xFF & colBytes[0])));
        byte[] poly3 = {1, 0};// {03}
        byte b2 = multiplePolyToByte(poly3, getPowersOf2((byte) (0xff & colBytes[1])));
        byte b3 = colBytes[2];
        byte b4 = colBytes[3];
        byte result = (byte) (b1 ^ b2 ^ b3 ^ b4);
        return result;
    }

    public static byte multiplePolyToByte(byte[] poly1, byte[] poly2) {
        List<Byte> polynomial = multiplyPoly(toListOfByte(poly1), toListOfByte(poly2));
        return reducePoly(polynomial);
    }

    private static byte reducePoly(List<Byte> poly) {
        List<Byte> result = new ArrayList<>();
        List<Byte> sorted = poly.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int index = 0;
        while (index < sorted.size()) {
            byte current = sorted.get(index++);
            if (index < sorted.size()) {
                if (sorted.get(index) == current) { //next equals current
                    index++;
                } else {
                    result.add(current);
                }
            } else {
                result.add(current);
            }
        }
        return powOf2ToByte(result);
    }

    private static List<Byte> toListOfByte(byte[] poly2) {
        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < poly2.length; i++) {
            result.add(poly2[i]);
        }
        return result;
    }

    private static byte powOf2ToByte(List<Byte> powersOf2) {
        byte result = 0;
        for (int i = 0; i < powersOf2.size(); i++) {
            result += Math.pow(2, powersOf2.get(i));
        }
        return result;
    }

    private static List<Byte> multiplyPoly(List<Byte> poly1, List<Byte> poly2) {
        List<Byte> resultPoly = new ArrayList<>();
        if (poly1.size() != 0 && poly2.size() != 0) {
            for (int i = 0; i < poly1.size(); i++) {
                for (int j = 0; j < poly2.size(); j++) {
                    byte poly = (byte) (poly1.get(i) + poly2.get(j));
                    if (poly >= 8) {

                        List<Byte> polyRest = new ArrayList<>();
                        byte rest = (byte) (poly % 8);
                        polyRest.add((byte) (rest));
                        List<Byte> tmpPolyBytes;
                        if (rest > 0) {
                            tmpPolyBytes = multiplyPoly(irreduciblePoly, polyRest);
                        } else {
                            tmpPolyBytes = new ArrayList<>();
                            tmpPolyBytes.addAll(irreduciblePoly);
                        }

                        resultPoly.addAll(tmpPolyBytes);
                    } else {
                        resultPoly.add(poly);
                    }
                }
            }
        }

        return resultPoly;
    }

    public static void displayPoly(List<Byte> poly) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < poly.size(); i++) {
            Byte value = poly.get(i);
            String plusSign = builder.length() > 0 ? "+" : "";
            builder.append(plusSign + (value == 0 ? "1" : "x" + value));
        }
        System.out.println(builder.toString());
    }

    public static byte[] getPowersOf2(byte value) {
        List<Byte> powOf2 = new ArrayList<>();
        int tmp = 128;
        for (int i = 7; i >= 0; i--) {
            if ((value & tmp) == tmp) {
                powOf2.add((byte) i);
            }
            tmp >>= 1;
        }
        byte[] result = new byte[powOf2.size()];
        for (int i = powOf2.size() - 1; i >= 0; i--) {
            result[i] = powOf2.get(i);
        }
        return result;
    }

    public static byte[][] invMixColumns(byte[][] state) {
        byte[][] result = new byte[4][StateWords];
        for (int col = 0; col < StateWords; col++) {
            byte[] colBytes = {state[0][col], state[1][col], state[2][col], state[3][col]};
            result[0][col] = invCalculateRow0Value(colBytes);
            result[1][col] = invCalculateRow1Value(colBytes);
            result[2][col] = invCalculateRow2Value(colBytes);
            result[3][col] = invCalculateRow3Value(colBytes);
        }
        return result;
    }

    private static byte invCalculateRow3Value(byte[] colBytes) {
        byte[] poly11 = {3, 1, 0};// {11}
        byte b1 = multiplePolyToByte(poly11, getPowersOf2((byte) colBytes[0]));
        byte[] poly13 = {3, 2, 0}; //{13}
        byte b2 = multiplePolyToByte(poly13, getPowersOf2((byte) colBytes[1]));
        byte[] poly9 = {3, 0}; //{09}
        byte b3 = multiplePolyToByte(poly9, getPowersOf2((byte) colBytes[2]));
        byte[] poly14 = {3, 2, 1};// {14}
        byte b4 = multiplePolyToByte(poly14, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte invCalculateRow2Value(byte[] colBytes) {
        byte[] poly13 = {3, 2, 0}; //{13}
        byte b1 = multiplePolyToByte(poly13, getPowersOf2((byte) colBytes[0]));
        byte[] poly9 = {3, 0}; //{09}
        byte b2 = multiplePolyToByte(poly9, getPowersOf2((byte) colBytes[1]));
        byte[] poly14 = {3, 2, 1};// {14}
        byte b3 = multiplePolyToByte(poly14, getPowersOf2((byte) colBytes[2]));
        byte[] poly11 = {3, 1, 0};// {11}
        byte b4 = multiplePolyToByte(poly11, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte invCalculateRow1Value(byte[] colBytes) {
        byte[] poly9 = {3, 0}; //{09}
        byte b1 = multiplePolyToByte(poly9, getPowersOf2((byte) colBytes[0]));
        byte[] poly14 = {3, 2, 1};// {14}
        byte b2 = multiplePolyToByte(poly14, getPowersOf2((byte) colBytes[1]));
        byte[] poly11 = {3, 1, 0};// {11}
        byte b3 = multiplePolyToByte(poly11, getPowersOf2((byte) colBytes[2]));
        byte[] poly13 = {3, 2, 0}; //{13}
        byte b4 = multiplePolyToByte(poly13, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    public static byte invCalculateRow0Value(byte[] colBytes) {
        byte[] poly14 = {3, 2, 1};// {14}
        byte b1 = multiplePolyToByte(poly14, getPowersOf2((byte) colBytes[0]));
        byte[] poly11 = {3, 1, 0};// {11}
        byte b2 = multiplePolyToByte(poly11, getPowersOf2((byte) colBytes[1]));
        byte[] poly13 = {3, 2, 0}; //{13}
        byte b3 = multiplePolyToByte(poly13, getPowersOf2((byte) colBytes[2]));
        byte[] poly9 = {3, 0}; //{09}
        byte b4 = multiplePolyToByte(poly9, getPowersOf2((byte) colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }
}
