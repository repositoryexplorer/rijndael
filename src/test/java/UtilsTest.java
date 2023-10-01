import com.alfanet.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void asciiHexBytesToByteArrayTest() {
        // 0, f, 1, e, 2, 0
        byte[] input = {(byte) 0x30, (byte) 0x66, (byte) 0x31, (byte) 0x65, (byte) 0x32, (byte) 0x30};
        byte[] expected = {(byte) 0x0f, (byte) 0x1E, (byte) 0x20};
        byte[] result = Utils.asciiHexBytesToByteArray(input);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void asciiHexStringToByteArrayTest() {
        String input = "0f1e20";
        byte[] expected = {(byte) 0x0f, (byte) 0x1E, (byte) 0x20};
        byte[] result = Utils.asciiHexBytesToByteArray(input);
        Assertions.assertArrayEquals(expected, result);
    }
}
