package com.github.ncredinburgh.tomcat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Path.of;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static jakarta.xml.bind.DatatypeConverter.printBase64Binary;

/**
 * Command line entry point for generating encryption keys and encrypting passwords.
 *
 * @since 0.2
 */
public class Main {

	/**
	 * Creates a new {@code Main}. Use {@link #main(String[])} to run the command line
	 * tool.
	 */
	public Main() {
	}

	/**
	 * Executes the command given on the command line.
	 * @param args the command and its options
	 * @throws Exception if the command cannot be executed
	 */
	public static void main(String[] args) throws Exception {
		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
		try {
			String command = arguments.remove();
			switch (command) {
				case "listKeyGenerators" -> listKeyGenerators(arguments);
				case "listCiphers" -> listCiphers(arguments);
				case "generateKey" -> generateKey(arguments);
				case "encryptPassword" -> encryptPassword(arguments);
				default -> printUsage();
			}
		}
		catch (NoSuchElementException e) {
			printUsage();
		}
	}

	private static void listKeyGenerators(Queue<String> arguments) {
		for (Provider provider : Security.getProviders()) {
			for (Provider.Service service : provider.getServices()) {
				if (service.getType().equals("KeyGenerator")) {
					System.out.println("Provider: " + provider.getName() + "  Algorithm: " + service.getAlgorithm());
				}
			}
		}
	}

	private static void listCiphers(Queue<String> arguments) {
		for (Provider provider : Security.getProviders()) {
			for (Provider.Service service : provider.getServices()) {
				if (service.getType().equals("Cipher")) {
					System.out.println("Provider: " + provider.getName() + "  Algorithm: " + service.getAlgorithm());
				}
			}
		}
	}

	private static void generateKey(Queue<String> arguments) throws GeneralSecurityException, IOException {
		try {
			String algorithm = arguments.remove();
			int keySize = Integer.parseInt(arguments.remove());
			String keyFilename = arguments.remove();

			byte[] keyBytes = generateKey(algorithm, keySize);
			File keyFile = new File(keyFilename);
			Files.write(keyFile.toPath(), keyBytes);
			System.out.println("New key written to file: " + keyFilename);
		}
		catch (NoSuchElementException e) {
			printUsage();
		}
	}

	private static byte[] generateKey(String algorithm, int keySize) throws GeneralSecurityException {
		KeyGenerator generator = KeyGenerator.getInstance(algorithm);
		generator.init(keySize);
		SecretKey key = generator.generateKey();
		return key.getEncoded();
	}

	private static void encryptPassword(Queue<String> arguments) throws GeneralSecurityException, IOException {
		try {
			String password = arguments.remove();
			String algorithm = arguments.remove();
			String mode = arguments.remove();
			String padding = arguments.remove();
			String keyFilename = arguments.remove();

			byte[] key = readAllBytes(of(keyFilename));
			System.out.println("Encrypted password: "
					+ printBase64Binary(encryptPassword(password, algorithm, mode, padding, key)));
		}
		catch (NoSuchElementException e) {
			printUsage();
		}
	}

	/**
	 * Encrypts the given password using the specified algorithm, mode, padding and key.
	 * @param password the plain text password to encrypt
	 * @param algorithm the encryption algorithm
	 * @param mode the cipher mode
	 * @param padding the padding scheme
	 * @param key the encryption key
	 * @return the encrypted password bytes
	 * @throws GeneralSecurityException if the password cannot be encrypted
	 */
	public static byte[] encryptPassword(String password, String algorithm, String mode, String padding, byte[] key)
			throws GeneralSecurityException {
		SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(format("%s/%s/%s", algorithm, mode, padding));
		cipher.init(ENCRYPT_MODE, keySpec);
		return cipher.doFinal(password.getBytes());
	}

	private static void printUsage() {
		System.out.println("Usage: secure-tomcat-datasourcefactory <command> <options>");
		System.out.println("Commands:");
		System.out.println("    listKeyGenerators");
		System.out.println("    listCiphers");
		System.out.println("    generateKey <algorithm> <keySize> <keyFilename>");
		System.out.println("    encryptPassword <password> <algorithm> <mode> <padding> <keyFilename>");
	}

}
