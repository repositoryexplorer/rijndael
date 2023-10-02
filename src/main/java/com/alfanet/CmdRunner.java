package com.alfanet;

import com.alfanet.aes.CipherOperation;
import com.alfanet.fileutils.FileUtils;
import com.alfanet.parameters.ParametersParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CmdRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ParametersParser params = new ParametersParser();

        if (!params.parseArguments(args)) {
            System.exit(0);
        } else {
            if (params.getOperation() == CipherOperation.ENCRYPTION) {
                FileUtils.processFileEncrypt(params.toAESInput());
            } else {
                FileUtils.processFileDecrypt(params.toAESInput());
            }
        }
    }
}
