import com.alfanet.fileutils.Padding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaddingTest {

    @Test
    public void testPadding() {
        byte[] test = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        byte[] expected = {0, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14};
        byte[] result = Padding.addPadding(test, 2);
        Assertions.assertArrayEquals(expected, result);
        Assertions.assertEquals(14, result[result.length - 1]);
    }

    @Test
    public void testPadding_16bytes() {
        byte[] test = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16};
        byte[] result = Padding.addPadding(test, 16);
        Assertions.assertArrayEquals(expected, result);
        Assertions.assertEquals(16, result[result.length - 1]);
    }

    @Test
    public void testPadding_remove() {
        byte[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] test = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16};
        byte[] result = Padding.removePadding(test);
        Assertions.assertArrayEquals(expected, result);
    }
}
