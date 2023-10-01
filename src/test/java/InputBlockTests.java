import com.alfanet.utils.InputBlockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.alfanet.aes.AesUtils.StateWords;

public class InputBlockTests {

    @Test
    public void testInputToState() {
        byte[] input = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};

        byte[][] state = new byte[4][StateWords];
        InputBlockUtils.inputToState(input, state);

        Assertions.assertEquals(0x32, 0xff & state[0][0]);
        Assertions.assertEquals(0x88, 0xff & state[0][1]);
        Assertions.assertEquals(0x31, 0xff & state[0][2]);
        Assertions.assertEquals(0xe0, 0xff & state[0][3]);

        Assertions.assertEquals(0x43, 0xff & state[1][0]);
        Assertions.assertEquals(0x5a, 0xff & state[1][1]);
        Assertions.assertEquals(0x31, 0xff & state[1][2]);
        Assertions.assertEquals(0x37, 0xff & state[1][3]);

        Assertions.assertEquals(0xF6, 0xff & state[2][0]);
        Assertions.assertEquals(0x30, 0xff & state[2][1]);
        Assertions.assertEquals(0x98, 0xff & state[2][2]);
        Assertions.assertEquals(0x07, 0xff & state[2][3]);

        Assertions.assertEquals(0xa8, 0xff & state[3][0]);
        Assertions.assertEquals(0x8d, 0xff & state[3][1]);
        Assertions.assertEquals(0xa2, 0xff & state[3][2]);
        Assertions.assertEquals(0x34, 0xff & state[3][3]);
    }

    @Test
    public void testStateToOutput() {
        byte[] outputExpected = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};
        byte[][] state = {{(byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0}, {(byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37}, {(byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07}, {(byte) 0xa8, (byte) 0x8d, (byte) 0xa2, (byte) 0x34}};
        byte[] output = InputBlockUtils.stateToOutput(state);

        Assertions.assertArrayEquals(outputExpected, output);
    }
}
