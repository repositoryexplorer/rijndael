import com.alfanet.aes.ShiftRows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.alfanet.aes.AesUtils.StateWords;

public class ShiftRowsTest {

    private final byte[] row0 = {0, 1, 2, 3};
    private final byte[] row1 = {5, 6, 7, 4};
    private final byte[] row2 = {10, 11, 8, 9};
    private final byte[] row3 = {15, 12, 13, 14};


    @Test
    public void shiftRowsTest() {
        byte[][] state = new byte[4][StateWords];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < StateWords; j++) {
                state[i][j] = (byte) (i * StateWords + j);
            }
        }
        ShiftRows.shiftRows(state);

        Assertions.assertArrayEquals(row0, state[0]);
        Assertions.assertArrayEquals(row1, state[1]);
        Assertions.assertArrayEquals(row2, state[2]);
        Assertions.assertArrayEquals(row3, state[3]);
    }
}
