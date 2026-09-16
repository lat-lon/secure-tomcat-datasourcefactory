package com.github.ncredinburgh.tomcat;

/**
 * A holder for the decrypted password, shared between the datasource factory and its
 * configuration steps.
 *
 * @since 0.1
 */
public class PasswordHolder {

	private static String password;

	/**
	 * Creates a new {@code PasswordHolder}. The class is static and instances are not
	 * normally required.
	 */
	public PasswordHolder() {
	}

	/**
	 * Sets the password.
	 * @param password the password
	 */
	public static void setPassword(String password) {
		PasswordHolder.password = password;
	}

	/**
	 * Gets the password.
	 * @return the password, or {@code null} if not set
	 */
	public static String getPassword() {
		return password;
	}

}
