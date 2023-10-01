import com.alfanet.aes.MixColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MixColumnsTest {

    @Test
    public void testPolynomialsMultiplication1() {
        byte[] poly1 = {3, 2, 0}; // 13 = 8 + 4 + 1
        byte[] poly2 = {1, 0}; // 3 = 2 + 1
        byte result = MixColumns.multiplePolyToByte(poly1, poly2);
        Assertions.assertEquals(23, result);
    }

    @Test
    public void testPolynomialsMultiplication2() {
        byte[] poly1 = {7, 2, 0}; // 133 = 128 + 4 + 1
        byte[] poly2 = {2, 1, 0}; // 7 = 4 + 2 + 1
        byte result = MixColumns.multiplePolyToByte(poly1, poly2);
        Assertions.assertEquals(182, result & 0xFF);
    }

    @Test
    public void testPolynomialsMultiplication3() {
        byte[] poly1 = {3, 2, 0}; // 13 = 4 + 1
        byte[] poly2 = {3, 2, 0}; // 13 = 4 + 1
        byte result = MixColumns.multiplePolyToByte(poly1, poly2);
        Assertions.assertEquals(81, result & 0xFF);
    }

    @Test
    public void testMix1() {
        byte[][] state = {
                {(byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e},
                {(byte) 0xbf, (byte) 0xb4, (byte) 0x41, (byte) 0x27},
                {(byte) 0x5d, (byte) 0x52, (byte) 0x11, (byte) 0x98},
                {(byte) 0x30, (byte) 0xae, (byte) 0xf1, (byte) 0xe5}};
        byte[][] expected = {
                {(byte) 0x04, (byte) 0xe0, (byte) 0x48, (byte) 0x28},
                {(byte) 0x66, (byte) 0xcb, (byte) 0xf8, (byte) 0x06},
                {(byte) 0x81, (byte) 0x19, (byte) 0xd3, (byte) 0x26},
                {(byte) 0xe5, (byte) 0x9a, (byte) 0x7a, (byte) 0x4c}};

        byte[][] result = MixColumns.mixColumns(state);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testMix2() {
        byte[][] state = {
                {(byte) 0x49, (byte) 0x45, (byte) 0x7f, (byte) 0x77},
                {(byte) 0xdb, (byte) 0x39, (byte) 0x02, (byte) 0xde},
                {(byte) 0x87, (byte) 0x53, (byte) 0xd2, (byte) 0x96},
                {(byte) 0x3b, (byte) 0x89, (byte) 0xf1, (byte) 0x1a}};
        byte[][] expected = {
                {(byte) 0x58, (byte) 0x1b, (byte) 0xdb, (byte) 0x1b},
                {(byte) 0x4d, (byte) 0x4b, (byte) 0xe7, (byte) 0x6b},
                {(byte) 0xca, (byte) 0x5a, (byte) 0xca, (byte) 0xb0},
                {(byte) 0xf1, (byte) 0xac, (byte) 0xa8, (byte) 0xe5}};

        byte[][] result = MixColumns.mixColumns(state);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testMix3() {
        byte[][] state = {
                {(byte) 0x87, (byte) 0xf2, (byte) 0x4d, (byte) 0x97},
                {(byte) 0x6e, (byte) 0x4c, (byte) 0x90, (byte) 0xec},
                {(byte) 0x46, (byte) 0xe7, (byte) 0x4a, (byte) 0xc3},
                {(byte) 0xa6, (byte) 0x8c, (byte) 0xd8, (byte) 0x95}};
        byte[][] expected = {
                {(byte) 0x47, (byte) 0x40, (byte) 0xa3, (byte) 0x4c},
                {(byte) 0x37, (byte) 0xd4, (byte) 0x70, (byte) 0x9f},
                {(byte) 0x94, (byte) 0xe4, (byte) 0x3a, (byte) 0x42},
                {(byte) 0xed, (byte) 0xa5, (byte) 0xa6, (byte) 0xbc}};

        byte[][] result = MixColumns.mixColumns(state);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testInvMix1() {
        byte[][] state = {
                {(byte) 0xe9, (byte) 0x02, (byte) 0x1b, (byte) 0x35},
                {(byte) 0xf7, (byte) 0x30, (byte) 0xf2, (byte) 0x3c},
                {(byte) 0x4e, (byte) 0x20, (byte) 0xcc, (byte) 0x21},
                {(byte) 0xec, (byte) 0xf6, (byte) 0xf2, (byte) 0xc7}};
        byte[][] expected = {
                {(byte) 0x54, (byte) 0x6b, (byte) 0x96, (byte) 0xa1},
                {(byte) 0xd9, (byte) 0xa0, (byte) 0xbb, (byte) 0x11},
                {(byte) 0x90, (byte) 0x9a, (byte) 0xf4, (byte) 0x70},
                {(byte) 0xa1, (byte) 0xb5, (byte) 0x0e, (byte) 0x2f}};

        byte[][] result = MixColumns.invMixColumns(state);
        Assertions.assertArrayEquals(expected, result);
    }

//    54d990a1 6ba09ab5 96bbf40e a111702f
    //e9f74eec023020f61bf2ccf2353c21c7
    //round[ 2].istart 54d990a16ba09ab596bbf40ea111702f
}
