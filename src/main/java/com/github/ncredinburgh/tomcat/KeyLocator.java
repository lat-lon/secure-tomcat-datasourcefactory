package com.github.ncredinburgh.tomcat;

import java.util.Properties;

public interface KeyLocator {

    void configure(Properties properties) throws DecryptionException;

    byte[] locateKey() throws DecryptionException;
}
