package com.github.ncredinburgh.tomcat;

import java.util.Properties;

public interface Decryptor {

    void configure(Properties properties) throws DecryptionException;

    byte[] decrypt(byte[] cipherBytes) throws DecryptionException;
}
