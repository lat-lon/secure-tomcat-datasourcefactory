package com.github.ncredinburgh.tomcat;

import java.io.IOException;
import java.util.Properties;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Path.of;

/**
 * A {@link KeyLocator} that reads the secret key from a file.
 */
public class KeyFile implements KeyLocator {

	/**
	 * The property name holding the path of the key file.
	 */
	public static final String PROP_KEY_FILENAME = "keyFilename";

	private String keyFilename;

	/**
	 * Creates a new, unconfigured key file locator. Call {@link #configure(Properties)}
	 * before {@link #locateKey()}.
	 */
	public KeyFile() {
	}

	@Override
	public void configure(Properties properties) throws DecryptionException {
		keyFilename = properties.getProperty(PROP_KEY_FILENAME);
	}

	@Override
	public byte[] locateKey() throws DecryptionException {
		try {
			validate();

			return readAllBytes(of(keyFilename));
		}
		catch (IOException e) {
			throw new DecryptionException(e);
		}
	}

	private void validate() throws DecryptionException {
		if (keyFilename == null) {
			throw new DecryptionException("Property '" + PROP_KEY_FILENAME + "' not specified");
		}
	}

}
