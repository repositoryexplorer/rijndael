# rijndael
## Example implementation of AES algorithm in Java
Encrypts/decrypts input file using Rijndael(AES) algorithm.
It uses PKCS#7 padding for blocks smaller than 16 bytes
## Usage:
    java -jar rijndael.jar [options]
        
        Options
         --k      file with private secret key (Hexadecimal digits only allowed. Supported key lengths are: 128, 192, 256)
         --i      input file to be encrypted/decrypted
         --d      decrypt file content, if omit the default behaviour is encrypt
         --m      mode, ECB or CBC
         --o      output file
         --iv     16 bytes length initialization vector (only for CBC mode). This must be string containing only hexadecimal values. 
         

## Examples

java -jar rijndael.jar --k=key --i=inputfile --m=ecb --o=outputfile

java -jar rijndael.jar --k=key --i=encryptedfile --m=cbc --d --o=outputfile --iv=000102030405060708090a0b0c0d0e0f
   
## License

MIT
**Free Software, Hell Yeah!**
