import com.alfanet.aes.SBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SBoxTest {
    @Test
    public void SBoxTest() {
        Assertions.assertEquals(SBox.subBytes((byte) 15), (byte) 0x76);
        Assertions.assertEquals(SBox.subBytes((byte) 30), (byte) 0x72);
        Assertions.assertEquals(SBox.subBytes((byte) 0xFF), (byte) 0x16);

        Assertions.assertEquals(SBox.subBytes((byte) 0xCF), (byte) 0x8A);
        Assertions.assertEquals(SBox.subBytes((byte) 0x4F), (byte) 0x84);
        Assertions.assertEquals(SBox.subBytes((byte) 0x3C), (byte) 0xEB);
        Assertions.assertEquals(SBox.subBytes((byte) 0x09), (byte) 0x01);
    }

    @Test
    public void invSBoxTest() {
        Assertions.assertEquals(SBox.invSubBytes((byte) 15), (byte) 0xfb);
        Assertions.assertEquals(SBox.invSubBytes((byte) 30), (byte) 0xE9);
        Assertions.assertEquals(SBox.invSubBytes((byte) 0xFF), (byte) 0x7d);
    }
}
