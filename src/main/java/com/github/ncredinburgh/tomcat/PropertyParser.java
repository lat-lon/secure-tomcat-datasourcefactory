package com.github.ncredinburgh.tomcat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

/**
 * Parses a string of semicolon separated properties.
 *
 * @since 0.1
 */
public class PropertyParser {

	private PropertyParser() {
	}

	/**
	 * Parses the given property string into a {@link java.util.Properties} object.
	 * @param propertyString the semicolon separated properties to parse
	 * @return the parsed properties
	 */
	public static Properties parseProperties(String propertyString) {
		Properties properties = new Properties();
		try {
			Reader propertyReader = new StringReader(propertyString.replace(";", "\n"));
			properties.load(propertyReader);
		}
		catch (IOException e) {
			// Can't happen with StringReader
		}
		return properties;
	}

}
