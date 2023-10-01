import com.alfanet.aes.AESInput;
import com.alfanet.aes.AESMode;
import com.alfanet.aes.CipherOperation;
import com.alfanet.parameters.ParametersParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.ApplicationArguments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CmdParametersTest {

    private ApplicationArguments args;

    @BeforeEach
    public void beforeEach() {
        args = mock(ApplicationArguments.class);
    }

    @Test
    public void test_encrypt_ecb() {
        List<String> listOfNonOpts = new ArrayList<>();
        Set<String> setOfOpts = new HashSet<>();
        setOfOpts.add("o");
        setOfOpts.add("i");
        setOfOpts.add("k");
        setOfOpts.add("m");

        when(args.getNonOptionArgs()).then(a -> listOfNonOpts);
        when(args.getOptionNames()).then(a -> setOfOpts);
        when(args.getOptionValues(anyString())).then(new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                String paramName = invocation.getArgument(0);
                List<String> result = new ArrayList<>();
                switch (paramName) {

                    case "i":
                        result.add("./src/test/resources/inputtest");
                        break;
                    case "o":
                        result.add("outputfile");
                        break;
                    case "k":
                        result.add("./src/test/resources/testkey_16");
                        break;
                    case "m":
                        result.add("ecb");
                        break;
                }
                return result;
            }
        });
        ParametersParser parametersParser = new ParametersParser();
        parametersParser.parseArguments(args);

        AESInput input = parametersParser.toAESInput();

        assertEquals(AESMode.ECB, input.getMode());
        assertEquals(CipherOperation.ENCRYPTION, input.getOperation());
    }

    @Test
    public void test_encrypt_cbc() {
        List<String> listOfNonOpts = new ArrayList<>();
        Set<String> setOfOpts = new HashSet<>();
        setOfOpts.add("o");
        setOfOpts.add("i");
        setOfOpts.add("k");
        setOfOpts.add("m");

        when(args.getNonOptionArgs()).then(a -> listOfNonOpts);
        when(args.getOptionNames()).then(a -> setOfOpts);
        when(args.getOptionValues(anyString())).then(new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                String paramName = invocation.getArgument(0);
                List<String> result = new ArrayList<>();
                switch (paramName) {

                    case "i":
                        result.add("./src/test/resources/inputtest");
                        break;
                    case "o":
                        result.add("outputfile");
                        break;
                    case "k":
                        result.add("./src/test/resources/testkey_16");
                        break;
                    case "m":
                        result.add("cbc");
                        break;
                    case "iv":
                        result.add("000102030405060708090a0b0c0d0e0f");
                        break;
                }
                return result;
            }
        });
        ParametersParser parametersParser = new ParametersParser();
        parametersParser.parseArguments(args);

        AESInput input = parametersParser.toAESInput();

        assertEquals(AESMode.CBC, input.getMode());
        assertEquals(CipherOperation.ENCRYPTION, input.getOperation());
        byte[] iv = {(byte) 0x0, (byte) 0x1, (byte) 0x2, (byte) 0x3, (byte) 0x4, (byte) 0x5, (byte) 0x6, (byte) 0x7, (byte) 0x8, (byte) 0x9, (byte) 0xa, (byte) 0xb, (byte) 0xc, (byte) 0xd, (byte) 0xe, (byte) 0xf};
        assertArrayEquals(iv, parametersParser.getInitVector());
    }
}
