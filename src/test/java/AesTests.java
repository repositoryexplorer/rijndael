import com.alfanet.aes.AesUtils;
import com.alfanet.utils.InputLengthException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AesTests {

    @Test
    public void testAESEncrypt_Key16() {
        byte[] plaintext = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f};
        byte[] expect = {(byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30, (byte) 0xd8, (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.encrypt(key, plaintext);
            assertArrayEquals(expect, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAESDecrypt_Key16() {
        byte[] expect = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] cipher = {(byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30, (byte) 0xd8, (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.decrypt(key, cipher);
            assertArrayEquals(expect, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAESEncrypt_Key24() {
        byte[] plaintext = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17};
        byte[] expect = {(byte) 0xdd, (byte) 0xa9, (byte) 0x7c, (byte) 0xa4, (byte) 0x86, (byte) 0x4c, (byte) 0xdf, (byte) 0xe0, (byte) 0x6e, (byte) 0xaf, (byte) 0x70, (byte) 0xa0, (byte) 0xec, (byte) 0x0d, (byte) 0x71, (byte) 0x91};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.encrypt(key, plaintext);
            assertArrayEquals(expect, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAESDecrypt_Key24() {
        byte[] plaintext = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17};
        byte[] cipher = {(byte) 0xdd, (byte) 0xa9, (byte) 0x7c, (byte) 0xa4, (byte) 0x86, (byte) 0x4c, (byte) 0xdf, (byte) 0xe0, (byte) 0x6e, (byte) 0xaf, (byte) 0x70, (byte) 0xa0, (byte) 0xec, (byte) 0x0d, (byte) 0x71, (byte) 0x91};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.decrypt(key, cipher);
            assertArrayEquals(plaintext, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAESEncrypt_Key32() {
        byte[] plaintext = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19, (byte) 0x1a, (byte) 0x1b, (byte) 0x1c, (byte) 0x1d, (byte) 0x1e, (byte) 0x1f};
        byte[] expect = {(byte) 0x8e, (byte) 0xa2, (byte) 0xb7, (byte) 0xca, (byte) 0x51, (byte) 0x67, (byte) 0x45, (byte) 0xbf, (byte) 0xea, (byte) 0xfc, (byte) 0x49, (byte) 0x90, (byte) 0x4b, (byte) 0x49, (byte) 0x60, (byte) 0x89};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.encrypt(key, plaintext);
            assertArrayEquals(expect, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAESDecrypt_Key32() {
        byte[] plaintext = {(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff};
        byte[] key = {(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19, (byte) 0x1a, (byte) 0x1b, (byte) 0x1c, (byte) 0x1d, (byte) 0x1e, (byte) 0x1f};
        byte[] cipher = {(byte) 0x8e, (byte) 0xa2, (byte) 0xb7, (byte) 0xca, (byte) 0x51, (byte) 0x67, (byte) 0x45, (byte) 0xbf, (byte) 0xea, (byte) 0xfc, (byte) 0x49, (byte) 0x90, (byte) 0x4b, (byte) 0x49, (byte) 0x60, (byte) 0x89};
        AesUtils aes = new AesUtils();
        try {
            byte[] result = aes.decrypt(key, cipher);
            assertArrayEquals(plaintext, result);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }
}
