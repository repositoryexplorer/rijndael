#!/bin/bash
rm -f /home/przemek/Downloads/manual_cbc_32_encrypted_LARGE
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=/home/przemek/Downloads/jdk-21_linux-x64_bin.rpm --m=cbc --o=/home/przemek/Downloads/manual_cbc_32_encrypted_LARGE --iv=000102030405060708090a0b0c0d0e0f  -XX:+UnlockCommercialFeatures -XX:+FlightRecorder