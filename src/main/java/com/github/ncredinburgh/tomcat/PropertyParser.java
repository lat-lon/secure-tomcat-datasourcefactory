package com.github.ncredinburgh.tomcat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

public class PropertyParser {
    private PropertyParser() {
    }

    public static Properties parseProperties(String propertyString) {
        Properties properties = new Properties();
        try {
            Reader propertyReader = new StringReader(propertyString.replace(";", "\n"));
            properties.load(propertyReader);
        } catch (IOException e) {
            // Can't happen with StringReader
        }
        return properties;
    }
}
