#!/bin/bash

checkResult() {
   result=`cmp $1 $2`
   if [ -n "$result" ];
   then
     echo "$result"
   fi;
}

rm -f manual_ecb_16_encrypted
rm -f manual_ecb_16_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_16 --i=../../resources/inputtest --m=ecb --o=manual_ecb_16_encrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_16 --i=manual_ecb_16_encrypted  --o=manual_ecb_16_decrypted --m=ecb --d
checkResult "../../resources/inputtest" "manual_ecb_16_decrypted"

rm -f manual_ecb_24_encrypted
rm -f manual_ecb_24_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_24 --i=../../resources/inputtest --m=ecb --o=manual_ecb_24_encrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_24 --i=manual_ecb_24_encrypted  --o=manual_ecb_24_decrypted --m=ecb --d
checkResult "../../resources/inputtest" "manual_ecb_24_decrypted"

rm -f manual_ecb_32_encrypted
rm -f manual_ecb_32_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=../../resources/inputtest --m=ecb --o=manual_ecb_32_encrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=manual_ecb_32_encrypted  --o=manual_ecb_32_decrypted --m=ecb --d
checkResult "../../resources/inputtest" "manual_ecb_32_decrypted"

rm -f manual_cbc_16_encrypted
rm -f manual_cbc_16_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_16 --i=../../resources/inputtest --m=cbc --o=manual_cbc_16_encrypted --iv=000102030405060708090a0b0c0d0e0f
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_16 --i=manual_cbc_16_encrypted  --o=manual_cbc_16_decrypted --m=cbc --d --iv=000102030405060708090a0b0c0d0e0f
checkResult "../../resources/inputtest" "manual_cbc_16_decrypted"

rm -f manual_cbc_24_encrypted
rm -f manual_cbc_24_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_24 --i=../../resources/inputtest --m=cbc --o=manual_cbc_24_encrypted --iv=000102030405060708090a0b0c0d0e0f
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_24 --i=manual_cbc_24_encrypted  --o=manual_cbc_24_decrypted --m=cbc --d --iv=000102030405060708090a0b0c0d0e0f
checkResult "../../resources/inputtest" "manual_cbc_24_decrypted"

rm -f manual_cbc_32_encrypted
rm -f manual_cbc_32_decrypted
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=../../resources/inputtest --m=cbc --o=manual_cbc_32_encrypted --iv=000102030405060708090a0b0c0d0e0f
java -jar ../../../../build/libs/rijndael.jar --k=../../resources/testkey_32 --i=manual_cbc_32_encrypted  --o=manual_cbc_32_decrypted --m=cbc --d --iv=000102030405060708090a0b0c0d0e0f
checkResult "../../resources/inputtest" "manual_cbc_32_decrypted"
