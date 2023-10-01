import com.alfanet.aes.KeyExpansion;
import com.alfanet.utils.InputLengthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.alfanet.aes.KeyExpansion.allKeysToRoundKeysByteArray;
import static org.springframework.test.util.AssertionErrors.fail;

public class KeyExpansionTests {

    private byte[] test128Bit = {(byte) 0x2b, (byte) 0x7e, (byte) 0x15, (byte) 0x16, (byte) 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6, (byte) 0xab, (byte) 0xf7, (byte) 0x15, (byte) 0x88, (byte) 0x09, (byte) 0xcf, (byte) 0x4f, (byte) 0x3c};
    private int[] keySet128 = {0x2b7e1516, 0x28aed2a6, 0xabf71588, 0x09cf4f3c, 0xa0fafe17, 0x88542cb1, 0x23a33939, 0x2a6c7605, 0xf2c295f2, 0x7a96b943, 0x5935807a, 0x7359f67f, 0x3d80477d, 0x4716fe3e, 0x1e237e44, 0x6d7a883b, 0xef44a541, 0xa8525b7f, 0xb671253b, 0xdb0bad00, 0xd4d1c6f8, 0x7c839d87, 0xcaf2b8bc, 0x11f915bc, 0x6d88a37a, 0x110b3efd, 0xdbf98641, 0xca0093fd, 0x4e54f70e, 0x5f5fc9f3, 0x84a64fb2, 0x4ea6dc4f, 0xead27321, 0xb58dbad2, 0x312bf560, 0x7f8d292f, 0xac7766f3, 0x19fadc21, 0x28d12941, 0x575c006e, 0xd014f9a8, 0xc9ee2589, 0xe13f0cc8, 0xb6630ca6};

    private byte[] test192Bit = {(byte) 0x8e, (byte) 0x73, (byte) 0xb0, (byte) 0xf7, (byte) 0xda, (byte) 0x0e, (byte) 0x64, (byte) 0x52, (byte) 0xc8, (byte) 0x10, (byte) 0xf3, (byte) 0x2b, (byte) 0x80, (byte) 0x90, (byte) 0x79, (byte) 0xe5, (byte) 0x62, (byte) 0xf8, (byte) 0xea, (byte) 0xd2, (byte) 0x52, (byte) 0x2c, (byte) 0x6b, (byte) 0x7b};
    private int[] keySet192 = {0x8e73b0f7, 0xda0e6452, 0xc810f32b, 0x809079e5, 0x62f8ead2, 0x522c6b7b, 0xfe0c91f7, 0x2402f5a5, 0xec12068e, 0x6c827f6b, 0x0e7a95b9, 0x5c56fec2, 0x4db7b4bd, 0x69b54118, 0x85a74796, 0xe92538fd, 0xe75fad44, 0xbb095386, 0x485af057, 0x21efb14f, 0xa448f6d9, 0x4d6dce24, 0xaa326360, 0x113b30e6, 0xa25e7ed5, 0x83b1cf9a, 0x27f93943, 0x6a94f767, 0xc0a69407, 0xd19da4e1, 0xec1786eb, 0x6fa64971, 0x485f7032, 0x22cb8755, 0xe26d1352, 0x33f0b7b3, 0x40beeb28, 0x2f18a259, 0x6747d26b, 0x458c553e, 0xa7e1466c, 0x9411f1df, 0x821f750a, 0xad07d753, 0xca400538, 0x8fcc5006, 0x282d166a, 0xbc3ce7b5, 0xe98ba06f, 0x448c773c, 0x8ecc7204, 0x01002202};

    private byte[] test256Bit = {(byte) 0x60, (byte) 0x3d, (byte) 0xeb, (byte) 0x10, (byte) 0x15, (byte) 0xca, (byte) 0x71, (byte) 0xbe, (byte) 0x2b, (byte) 0x73, (byte) 0xae, (byte) 0xf0, (byte) 0x85, (byte) 0x7d, (byte) 0x77, (byte) 0x81, (byte) 0x1f, (byte) 0x35, (byte) 0x2c, (byte) 0x07, (byte) 0x3b, (byte) 0x61, (byte) 0x08, (byte) 0xd7, (byte) 0x2d, (byte) 0x98, (byte) 0x10, (byte) 0xa3, (byte) 0x09, (byte) 0x14, (byte) 0xdf, (byte) 0xf4};
    private int[] keySet256 = {0x603deb10, 0x15ca71be, 0x2b73aef0, 0x857d7781, 0x1f352c07, 0x3b6108d7, 0x2d9810a3, 0x0914dff4, 0x9ba35411, 0x8e6925af, 0xa51a8b5f, 0x2067fcde, 0xa8b09c1a, 0x93d194cd, 0xbe49846e, 0xb75d5b9a, 0xd59aecb8, 0x5bf3c917, 0xfee94248, 0xde8ebe96, 0xb5a9328a, 0x2678a647, 0x98312229, 0x2f6c79b3, 0x812c81ad, 0xdadf48ba, 0x24360af2, 0xfab8b464, 0x98c5bfc9, 0xbebd198e, 0x268c3ba7, 0x09e04214, 0x68007bac, 0xb2df3316, 0x96e939e4, 0x6c518d80, 0xc814e204, 0x76a9fb8a, 0x5025c02d, 0x59c58239, 0xde136967, 0x6ccc5a71, 0xfa256395, 0x9674ee15, 0x5886ca5d, 0x2e2f31d7, 0x7e0af1fa, 0x27cf73c3, 0x749c47ab, 0x18501dda, 0xe2757e4f, 0x7401905a, 0xcafaaae3, 0xe4d59b34, 0x9adf6ace, 0xbd10190d, 0xfe4890d1, 0xe6188d0b, 0x046df344, 0x706c631e};

    @Test
    public void testKeyExp128() {
        try {
            int[] keys = KeyExpansion.expandKey(test128Bit, 10);
            Assertions.assertArrayEquals(keys, keySet128);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testKeyExp192() {
        try {
            int[] keys = KeyExpansion.expandKey(test192Bit, 12);
            Assertions.assertArrayEquals(keys, keySet192);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testKeyExp256() {
        try {
            int[] keys = KeyExpansion.expandKey(test256Bit, 14);
            Assertions.assertArrayEquals(keys, keySet256);
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testKey16() {
        byte[] key = {
                (byte) 0x2b, (byte) 0x7e, (byte) 0x15, (byte) 0x16,
                (byte) 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
                (byte) 0xab, (byte) 0xf7, (byte) 0x15, (byte) 0x88,
                (byte) 0x09, (byte) 0xcf, (byte) 0x4f, (byte) 0x3c
        };

        int[] reference = {
                0x2b7e1516, 0x28aed2a6, 0xabf71588, 0x9cf4f3c,
                0xa0fafe17, 0x88542cb1, 0x23a33939, 0x2a6c7605,
                0xf2c295f2, 0x7a96b943, 0x5935807a, 0x7359f67f,
                0x3d80477d, 0x4716fe3e, 0x1e237e44, 0x6d7a883b,
                0xef44a541, 0xa8525b7f, 0xb671253b, 0xdb0bad00, //4
                0xd4d1c6f8, 0x7c839d87, 0xcaf2b8bc, 0x11f915bc,
                0x6d88a37a, 0x110b3efd, 0xdbf98641, 0xca0093fd,
                0x4e54f70e, 0x5f5fc9f3, 0x84a64fb2, 0x4ea6dc4f,
                0xead27321, 0xb58dbad2, 0x312bf560, 0x7f8d292f,
                0xac7766f3, 0x19fadc21, 0x28d12941, 0x575c006e,
                0xd014f9a8, 0xc9ee2589, 0xe13f0cc8, 0xb6630ca6
        };

        byte[][] round10Key = {
                {(byte) 0xd0, (byte) 0xc9, (byte) 0xe1, (byte) 0xb6},
                {(byte) 0x14, (byte) 0xee, (byte) 0x3f, (byte) 0x63},
                {(byte) 0xf9, (byte) 0x25, (byte) 0x0c, (byte) 0x0c},
                {(byte) 0xa8, (byte) 0x89, (byte) 0xc8, (byte) 0xa6}
        };

        byte[][] round9Key = {
                {(byte) 0xac, (byte) 0x19, (byte) 0x28, (byte) 0x57},
                {(byte) 0x77, (byte) 0xfa, (byte) 0xd1, (byte) 0x5c},
                {(byte) 0x66, (byte) 0xdc, (byte) 0x29, (byte) 0x00},
                {(byte) 0xf3, (byte) 0x21, (byte) 0x41, (byte) 0x6e}
        };

        byte[][] round8Key = {
                {(byte) 0xea, (byte) 0xb5, (byte) 0x31, (byte) 0x7f},
                {(byte) 0xd2, (byte) 0x8d, (byte) 0x2b, (byte) 0x8d},
                {(byte) 0x73, (byte) 0xba, (byte) 0xf5, (byte) 0x29},
                {(byte) 0x21, (byte) 0xd2, (byte) 0x60, (byte) 0x2f}
        };

        byte[][] round7Key = {
                {(byte) 0x4e, (byte) 0x5f, (byte) 0x84, (byte) 0x4e},
                {(byte) 0x54, (byte) 0x5f, (byte) 0xa6, (byte) 0xa6},
                {(byte) 0xf7, (byte) 0xc9, (byte) 0x4f, (byte) 0xdc},
                {(byte) 0x0e, (byte) 0xf3, (byte) 0xb2, (byte) 0x4f}
        };

        byte[][] round6Key = {
                {(byte) 0x6d, (byte) 0x11, (byte) 0xdb, (byte) 0xca},
                {(byte) 0x88, (byte) 0x0b, (byte) 0xf9, (byte) 0x00},
                {(byte) 0xa3, (byte) 0x3e, (byte) 0x86, (byte) 0x93},
                {(byte) 0x7a, (byte) 0xfd, (byte) 0x41, (byte) 0xfd}
        };

        byte[][] round5Key = {
                {(byte) 0xd4, (byte) 0x7c, (byte) 0xca, (byte) 0x11},
                {(byte) 0xd1, (byte) 0x83, (byte) 0xf2, (byte) 0xf9},
                {(byte) 0xc6, (byte) 0x9d, (byte) 0xb8, (byte) 0x15},
                {(byte) 0xf8, (byte) 0x87, (byte) 0xbc, (byte) 0xbc}
        };

        byte[][] round4Key = {
                {(byte) 0xef, (byte) 0xa8, (byte) 0xb6, (byte) 0xdb},
                {(byte) 0x44, (byte) 0x52, (byte) 0x71, (byte) 0x0b},
                {(byte) 0xa5, (byte) 0x5b, (byte) 0x25, (byte) 0xad},
                {(byte) 0x41, (byte) 0x7f, (byte) 0x3b, (byte) 0x00}
        };

        byte[][] round3Key = {
                {(byte) 0x3d, (byte) 0x47, (byte) 0x1e, (byte) 0x6d},
                {(byte) 0x80, (byte) 0x16, (byte) 0x23, (byte) 0x7a},
                {(byte) 0x47, (byte) 0xfe, (byte) 0x7e, (byte) 0x88},
                {(byte) 0x7d, (byte) 0x3e, (byte) 0x44, (byte) 0x3b}
        };

        byte[][] round2Key = {
                {(byte) 0xf2, (byte) 0x7a, (byte) 0x59, (byte) 0x73},
                {(byte) 0xc2, (byte) 0x96, (byte) 0x35, (byte) 0x59},
                {(byte) 0x95, (byte) 0xb9, (byte) 0x80, (byte) 0xf6},
                {(byte) 0xf2, (byte) 0x43, (byte) 0x7a, (byte) 0x7f}
        };

        byte[][] round1Key = {
                {(byte) 0xa0, (byte) 0x88, (byte) 0x23, (byte) 0x2a},
                {(byte) 0xfa, (byte) 0x54, (byte) 0xa3, (byte) 0x6c},
                {(byte) 0xfe, (byte) 0x2c, (byte) 0x39, (byte) 0x76},
                {(byte) 0x17, (byte) 0xb1, (byte) 0x39, (byte) 0x05}
        };

        byte[][] round0Key = {
                {(byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09},
                {(byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf},
                {(byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f},
                {(byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c}
        };

        try {
            int[] keys = KeyExpansion.expandKey(key, 10);
            Assertions.assertArrayEquals(reference, keys);
            List<byte[][]> listOfArraysOfKeys = allKeysToRoundKeysByteArray(keys);
            Assertions.assertEquals(11, listOfArraysOfKeys.size());
            Assertions.assertArrayEquals(round0Key, listOfArraysOfKeys.get(0));
            Assertions.assertArrayEquals(round1Key, listOfArraysOfKeys.get(1));
            Assertions.assertArrayEquals(round2Key, listOfArraysOfKeys.get(2));
            Assertions.assertArrayEquals(round3Key, listOfArraysOfKeys.get(3));
            Assertions.assertArrayEquals(round4Key, listOfArraysOfKeys.get(4));
            Assertions.assertArrayEquals(round5Key, listOfArraysOfKeys.get(5));
            Assertions.assertArrayEquals(round6Key, listOfArraysOfKeys.get(6));
            Assertions.assertArrayEquals(round7Key, listOfArraysOfKeys.get(7));
            Assertions.assertArrayEquals(round8Key, listOfArraysOfKeys.get(8));
            Assertions.assertArrayEquals(round9Key, listOfArraysOfKeys.get(9));
            Assertions.assertArrayEquals(round10Key, listOfArraysOfKeys.get(10));
        } catch (InputLengthException e) {
            fail(e.getMessage());
        }
    }
}
