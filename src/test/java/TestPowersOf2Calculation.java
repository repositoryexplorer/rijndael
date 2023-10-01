import com.alfanet.aes.MixColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPowersOf2Calculation {

    @Test
    public void testPowOf2_1() {
        byte input = (byte) 0xff;
        byte[] check = {7, 6, 5, 4, 3, 2, 1, 0};
        byte[] result = MixColumns.getPowersOf2(input);
        Assertions.assertArrayEquals(check, result);
    }

    @Test
    public void testPowOf2_2() {
        byte input2 = (byte) 0x80;
        byte[] check2 = {7};
        byte[] result2 = MixColumns.getPowersOf2(input2);
        Assertions.assertArrayEquals(check2, result2);
    }

    @Test
    public void testPowOf2_3() {
        byte input2 = (byte) 0x81;
        byte[] check2 = {7, 0};
        byte[] result2 = MixColumns.getPowersOf2(input2);
        Assertions.assertArrayEquals(check2, result2);
    }

    @Test
    public void testPowOf2_4() {
        byte input2 = (byte) 0x0;
        byte[] check2 = {};
        byte[] result2 = MixColumns.getPowersOf2(input2);
        Assertions.assertArrayEquals(check2, result2);
    }

    @Test
    public void testPowOf2_5() {
        byte input2 = (byte) 0xd4;
        byte[] check2 = {7, 6, 4, 2};
        byte[] result2 = MixColumns.getPowersOf2(input2);
        Assertions.assertArrayEquals(check2, result2);
    }
}
