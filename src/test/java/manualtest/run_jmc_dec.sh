#!/bin/bash
rm -f /home/przemek/Downloads/manual_cbc_32_decrypted_LARGE
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=/home/przemek/Downloads/manual_cbc_32_encrypted_LARGE  --o=/home/przemek/Downloads/manual_cbc_32_decrypted_LARGE --m=cbc --d --iv=000102030405060708090a0b0c0d0e0f