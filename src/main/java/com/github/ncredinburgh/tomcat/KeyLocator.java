package com.github.ncredinburgh.tomcat;

import java.util.Properties;

/**
 * A component that locates the secret key used to decrypt an encrypted password.
 *
 * @since 0.1
 */
public interface KeyLocator {

	/**
	 * Configures this locator using the given properties.
	 * @param properties the configuration properties
	 * @throws com.github.ncredinburgh.tomcat.DecryptionException if the configuration is
	 * invalid
	 */
	void configure(Properties properties) throws DecryptionException;

	/**
	 * Locates the secret key.
	 * @return the secret key bytes
	 * @throws com.github.ncredinburgh.tomcat.DecryptionException if the key cannot be
	 * located
	 */
	byte[] locateKey() throws DecryptionException;

}
