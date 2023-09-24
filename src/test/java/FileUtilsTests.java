import com.alfanet.fileutils.FileUtils;
import com.alfanet.fileutils.KeyLengthException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FileUtilsTests {

    public static final byte[] key16 = {(byte) 0x0f, (byte) 0x03, (byte) 0x23, (byte) 0x67, (byte) 0x18, (byte) 0xab, (byte) 0x02, (byte) 0xcd, (byte) 0xe0, (byte) 0xfa, (byte) 0xbd, (byte) 0x43, (byte) 0x12, (byte) 0x81, (byte) 0x99, (byte) 0xaa};
    public static final byte[] key24 = {(byte) 0x0F, (byte) 0x03, (byte) 0x23, (byte) 0x67, (byte) 0x18, (byte) 0xAB, (byte) 0x02, (byte) 0xCD, (byte) 0xE0, (byte) 0xFA, (byte) 0xBD, (byte) 0x43, (byte) 0x12, (byte) 0x81, (byte) 0x99, (byte) 0xAA, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07};
    public static final byte[] key32 = {(byte) 0x0F, (byte) 0x03, (byte) 0x23, (byte) 0x67, (byte) 0x18, (byte) 0xAB, (byte) 0x02, (byte) 0xCD, (byte) 0xE0, (byte) 0xFA, (byte) 0xBD, (byte) 0x43, (byte) 0x12, (byte) 0x81, (byte) 0x99, (byte) 0xAA, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA3, (byte) 0xA4, (byte) 0xA5, (byte) 0xA6, (byte) 0xA7};

    @Test
    public void testReadKeyFile16() {
        try {
            byte[] keybytes = FileUtils.readKey(Paths.get(".").toFile().getAbsolutePath().replace(".", "") + "/src/test/resources/testkey_16");
            assertThat(keybytes.length).isEqualTo(16);
            assertArrayEquals(keybytes, key16);
        } catch (IOException e) {
            fail("IOException " + e.getMessage());
        } catch (KeyLengthException e) {
            fail("Key length exception " + e.getMessage());
        }
    }

    @Test
    public void testReadKeyFile24() {
        try {
            byte[] keybytes = FileUtils.readKey(Paths.get(".").toFile().getAbsolutePath().replace(".", "") + "/src/test/resources/testkey_24");
            assertThat(keybytes.length).isEqualTo(24);
            assertArrayEquals(keybytes, key24);
        } catch (IOException e) {
            fail("IOException " + e.getMessage());
        } catch (KeyLengthException e) {
            fail("Key length exception " + e.getMessage());
        }
    }

    @Test
    public void testReadKeyFile32() {
        try {
            byte[] keybytes = FileUtils.readKey(Paths.get(".").toFile().getAbsolutePath().replace(".", "") + "/src/test/resources/testkey_32");
            assertThat(keybytes.length).isEqualTo(32);
            assertArrayEquals(keybytes, key32);
        } catch (IOException e) {
            fail("IOException " + e.getMessage());
        } catch (KeyLengthException e) {
            fail("Key length exception " + e.getMessage());
        }
    }
}
