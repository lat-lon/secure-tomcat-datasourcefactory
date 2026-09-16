package com.github.ncredinburgh.tomcat;

import java.util.Properties;

/**
 * A component that decrypts cipher bytes using the configuration held in a set of
 * properties.
 *
 * @since 0.1
 */
public interface Decryptor {

	/**
	 * Configures this decryptor using the given properties.
	 * @param properties the configuration properties
	 * @throws DecryptionException if the configuration is invalid
	 */
	void configure(Properties properties) throws DecryptionException;

	/**
	 * Decrypts the given cipher bytes.
	 * @param cipherBytes the bytes to decrypt
	 * @return the decrypted bytes
	 * @throws DecryptionException if the bytes cannot be decrypted
	 */
	byte[] decrypt(byte[] cipherBytes) throws DecryptionException;

}
