package com.alfanet.aes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alfanet.aes.AesUtils.StateWords;

public class MixColumns {

    private static final Map<Byte, byte[]> powersOf2Map = new HashMap<>();
    private static final byte[] poly14 = {0, 1, 1, 1};// {14}
    private static final byte[] poly13 = {1, 0, 1, 1}; //{13}
    private static final byte[] poly11 = {1, 1, 0, 1};// {11}
    private static final byte[] poly9 = {1, 0, 0, 1}; //{09}
    private static final byte[] poly3 = {1, 1};// {03}
    private static final byte[] poly2 = {0, 1}; // {02}

    private static final byte[] irreduciblePolyBA = {1, 1, 0, 1, 1};

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
        byte b1 = multiplyPolynomial(poly3, getPowersOf2(colBytes[0]));
        byte b2 = colBytes[1];
        byte b3 = colBytes[2];
        byte b4 = multiplyPolynomial(poly2, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte calculateRow2Value(byte[] colBytes) {
        byte b1 = colBytes[0];
        byte b2 = colBytes[1];
        byte b3 = multiplyPolynomial(poly2, getPowersOf2(colBytes[2]));
        byte b4 = multiplyPolynomial(poly3, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte calculateRow1Value(byte[] colBytes) {
        byte b1 = colBytes[0];
        byte b2 = multiplyPolynomial(poly2, getPowersOf2(colBytes[1]));
        byte b3 = multiplyPolynomial(poly3, getPowersOf2(colBytes[2]));
        byte b4 = colBytes[3];
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    public static byte calculateRow0Value(byte[] colBytes) {
        byte b1 = multiplyPolynomial(poly2, getPowersOf2((byte) (colBytes[0])));
        byte b2 = multiplyPolynomial(poly3, getPowersOf2((byte) (colBytes[1])));
        byte b3 = colBytes[2];
        byte b4 = colBytes[3];
        byte result = (byte) (b1 ^ b2 ^ b3 ^ b4);
        return result;
    }


    private static String getKey(byte[] a1, byte[] a2) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < a1.length; i++) {
            s.append(a1[i]);
        }
        for (int i = 0; i < a1.length; i++) {
            s.append(a2[i]);
        }
        return s.toString();
    }

    private static final Map<String, Byte> map = new HashMap<>();

    public static byte multiplyPolynomial(byte[] poly1, byte[] poly2) {
        String key = getKey(poly1, poly2);
        Byte fromCache = map.get(key);
        if (fromCache != null) return fromCache;

        byte result = 0;
        byte[] bytes = multiplyPoly(poly1, poly2);
        for (int i = 0; i < bytes.length; i++) {
            byte tmp = bytes[i];
            if (tmp > 0 && tmp % 2 > 0) {
                result += 1 << i;
            }
        }
        map.put(key, result);
        return result;
    }

    private static byte[] multiplyPoly(byte[] poly1, byte[] poly2) {
        byte[] resultPoly = new byte[20];
        if (poly1.length != 0 && poly2.length != 0) {
            for (int i = 0; i < poly1.length; i++) {
                for (int j = 0; j < poly2.length; j++) {
                    if (poly1[i] == 0 || poly2[j] == 0) {
                        continue;
                    }
                    byte poly = (byte) (i + j);// (byte) (poly1[i] + poly2[j]);
                    if (poly >= 8) {

                        byte[] polyRest = new byte[20];
                        byte rest = (byte) (poly % 8);
                        polyRest[rest] = 1;
                        byte[] tmpPolyBytes;
                        if (rest > 0) {
                            tmpPolyBytes = multiplyPoly(irreduciblePolyBA, polyRest);
                        } else {
                            tmpPolyBytes = new byte[5];
                            System.arraycopy(irreduciblePolyBA, 0, tmpPolyBytes, 0, 5);
                        }
                        for (int k = 0; k < tmpPolyBytes.length; k++) {
                            resultPoly[k] += tmpPolyBytes[k];
                        }
                    } else {
                        resultPoly[i + j] += 1;
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
        byte[] result = powersOf2Map.get(value);
        if (result == null) {
            result = new byte[8];
            int tmp = 128;
            for (int i = 7; i >= 0; i--) {
                if ((value & tmp) == tmp) {
                    result[i] = 1;
                }
                tmp >>= 1;
            }
            powersOf2Map.put(value, result);
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
        byte b1 = multiplyPolynomial(poly11, getPowersOf2(colBytes[0]));
        byte b2 = multiplyPolynomial(poly13, getPowersOf2(colBytes[1]));
        byte b3 = multiplyPolynomial(poly9, getPowersOf2(colBytes[2]));
        byte b4 = multiplyPolynomial(poly14, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte invCalculateRow2Value(byte[] colBytes) {
        byte b1 = multiplyPolynomial(poly13, getPowersOf2(colBytes[0]));
        byte b2 = multiplyPolynomial(poly9, getPowersOf2(colBytes[1]));
        byte b3 = multiplyPolynomial(poly14, getPowersOf2(colBytes[2]));
        byte b4 = multiplyPolynomial(poly11, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    private static byte invCalculateRow1Value(byte[] colBytes) {
        byte b1 = multiplyPolynomial(poly9, getPowersOf2(colBytes[0]));
        byte b2 = multiplyPolynomial(poly14, getPowersOf2(colBytes[1]));
        byte b3 = multiplyPolynomial(poly11, getPowersOf2(colBytes[2]));
        byte b4 = multiplyPolynomial(poly13, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }

    public static byte invCalculateRow0Value(byte[] colBytes) {
        byte b1 = multiplyPolynomial(poly14, getPowersOf2(colBytes[0]));
        byte b2 = multiplyPolynomial(poly11, getPowersOf2(colBytes[1]));
        byte b3 = multiplyPolynomial(poly13, getPowersOf2(colBytes[2]));
        byte b4 = multiplyPolynomial(poly9, getPowersOf2(colBytes[3]));
        return (byte) (b1 ^ b2 ^ b3 ^ b4);
    }
}
