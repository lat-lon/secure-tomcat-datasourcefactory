package com.github.ncredinburgh.tomcat;

/**
 * Thrown when a password cannot be decrypted or when a required configuration property is
 * missing.
 *
 * @since 0.1
 */
@SuppressWarnings("serial")
public class DecryptionException extends Exception {

	/**
	 * Creates a new exception with the given cause.
	 * @param t the cause
	 */
	public DecryptionException(Throwable t) {
		super(t);
	}

	/**
	 * Creates a new exception with the given message.
	 * @param message the message
	 */
	public DecryptionException(String message) {
		super(message);
	}

}
