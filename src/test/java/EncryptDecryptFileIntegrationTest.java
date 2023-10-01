import com.alfanet.aes.AESInput;
import com.alfanet.aes.AESMode;
import com.alfanet.aes.CipherOperation;
import com.alfanet.fileutils.FileUtils;
import com.alfanet.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class EncryptDecryptFileIntegrationTest {

    byte[] initVector = {(byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62, (byte) 0x62};
    byte[] key128 = {(byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61};
    byte[] key192 = {(byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61};

    byte[] key256 = {(byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x63, (byte) 0x63, (byte) 0x63, (byte) 0x63, (byte) 0x63, (byte) 0x63, (byte) 0x63, (byte) 0x63};

    String outputFilePath = "./src/test/resources/outputtest";

    @BeforeEach
    private void beforeEach() {
        Paths.get(outputFilePath).toFile().delete();
    }

    @Test
    public void encryptFileTestECB_128() {

        AESInput input = new AESInput("./src/test/resources/inputtest", outputFilePath, key128,
                CipherOperation.ENCRYPTION, AESMode.ECB, new byte[0]);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            Assertions.assertTrue(data.length > 0);
            fs = new FileInputStream("./src/test/resources/ecb_16_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestECB_128() {

        AESInput input = new AESInput("./src/test/resources/ecb_16_encrypted", outputFilePath, key128,
                CipherOperation.DECRYPTION, AESMode.ECB, new byte[0]);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/inputtest");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void encryptFileTestCBC_128() {
        AESInput input = new AESInput("./src/test/resources/inputtest", outputFilePath, key128,
                CipherOperation.ENCRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            Assertions.assertTrue(data.length > 0);
            fs = new FileInputStream("./src/test/resources/cbc_16_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestCBC_128() {

        AESInput input = new AESInput("./src/test/resources/cbc_16_encrypted", outputFilePath, key128,
                CipherOperation.DECRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/inputtest");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void encryptFileTestCBC_LARGE_128() {
        AESInput input = new AESInput("./src/test/resources/input_large", outputFilePath, key128,
                CipherOperation.ENCRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            fs = new FileInputStream("./src/test/resources/large_cbc_16_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestCBC_LARGE_128() {

        AESInput input = new AESInput("./src/test/resources/large_cbc_16_encrypted", outputFilePath, key128,
                CipherOperation.DECRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/input_large");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void encryptFileTestECB_LARGE_128() {

        AESInput input = new AESInput("./src/test/resources/input_large", outputFilePath, key128,
                CipherOperation.ENCRYPTION, AESMode.ECB, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] outputData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(outputData);
            fs = new FileInputStream("./src/test/resources/large_ecb_16_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, outputData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestECB_LARGE_128() {

        AESInput input = new AESInput("./src/test/resources/large_ecb_16_encrypted", outputFilePath, key128,
                CipherOperation.DECRYPTION, AESMode.ECB, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/input_large");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    ////////////////////  192 bit key
    @Test
    public void encryptFileTestECB_192() {

        AESInput input = new AESInput("./src/test/resources/inputtest", outputFilePath, key192,
                CipherOperation.ENCRYPTION, AESMode.ECB, new byte[0]);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            Assertions.assertTrue(data.length > 0);
            fs = new FileInputStream("./src/test/resources/ecb_24_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestECB_192() {

        AESInput input = new AESInput("./src/test/resources/ecb_24_encrypted", outputFilePath, key192,
                CipherOperation.DECRYPTION, AESMode.ECB, new byte[0]);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/inputtest");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void encryptFileTestCBC_192() {
        AESInput input = new AESInput("./src/test/resources/inputtest", outputFilePath, key192,
                CipherOperation.ENCRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            Assertions.assertTrue(data.length > 0);
            fs = new FileInputStream("./src/test/resources/cbc_24_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestCBC_192() {

        AESInput input = new AESInput("./src/test/resources/cbc_24_encrypted", outputFilePath, key192,
                CipherOperation.DECRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/inputtest");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void encryptFileTestCBC_LARGE_192() {
        AESInput input = new AESInput("./src/test/resources/input_large", outputFilePath, key192,
                CipherOperation.ENCRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            fs = new FileInputStream("./src/test/resources/large_cbc_24_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestCBC_LARGE_192() {

        AESInput input = new AESInput("./src/test/resources/large_cbc_24_encrypted", outputFilePath, key192,
                CipherOperation.DECRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/input_large");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    //////////////////// 256 bit key
    @Test
    public void encryptFileTestCBC_LARGE_256() {
        AESInput input = new AESInput("./src/test/resources/input_large", outputFilePath, key256,
                CipherOperation.ENCRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileEncrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());
            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] data = fs.readAllBytes();
            fs.close();

            Assertions.assertNotNull(data);
            fs = new FileInputStream("./src/test/resources/large_cbc_32_encrypted_hex");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            byte[] expected = Utils.asciiHexBytesToByteArray(allBytes);
            Assertions.assertArrayEquals(expected, data);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void decryptFileTestCBC_LARGE_256() {

        AESInput input = new AESInput("./src/test/resources/large_cbc_32_encrypted", outputFilePath, key256,
                CipherOperation.DECRYPTION, AESMode.CBC, initVector);
        try {
            FileUtils.processFileDecrypt(input);
            Assertions.assertTrue(Paths.get(outputFilePath).toFile().exists());

            FileInputStream fs = new FileInputStream(outputFilePath);
            byte[] decryptedData = fs.readAllBytes();
            fs.close();
            Assertions.assertNotNull(decryptedData);
            Assertions.assertTrue(decryptedData.length > 0);

            fs = new FileInputStream("./src/test/resources/input_large");
            byte[] allBytes = fs.readAllBytes();
            fs.close();

            Assertions.assertArrayEquals(allBytes, decryptedData);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
