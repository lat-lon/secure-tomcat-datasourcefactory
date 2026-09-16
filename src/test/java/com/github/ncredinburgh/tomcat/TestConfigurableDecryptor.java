package com.github.ncredinburgh.tomcat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Properties;

import static jakarta.xml.bind.DatatypeConverter.parseBase64Binary;
import static org.assertj.core.api.Assertions.assertThat;

public class TestConfigurableDecryptor {

	private static final String RESOURCE_DIR = "src/test/resources/com/github/ncredinburgh/tomcat/";

	private Decryptor testee;

	private Properties decryptorProperties;

	@BeforeEach
	public void setUp() {
		testee = new ConfigurableDecryptor();
		decryptorProperties = new Properties();
	}

	@Test
	public void shouldDecryptEncryptedPasswordUsingKeyFile() throws Exception {
		File keyFile = new File(RESOURCE_DIR + "Example-AES-128.key");
		String encryptedPassword = "C0iZc6o+6xqr0NggmuTo9gRtfowg0kyM8fqNQEJwAZE=";
		String expectedPassword = "Sup3rS3cr3tP455w0rd";

		byte[] passwordBytes = parseBase64Binary(encryptedPassword);

		decryptorProperties.put("algorithm", "AES");
		decryptorProperties.put("mode", "ECB");
		decryptorProperties.put("padding", "PKCS5PADDING");
		decryptorProperties.put("keyFilename", keyFile.getCanonicalPath());
		testee.configure(decryptorProperties);

		byte[] clearBytes = testee.decrypt(passwordBytes);

		assertThat(expectedPassword).isEqualTo(new String(clearBytes));
	}

	@Test
	public void shouldDecryptEncryptedPasswordUsingCompiledKey() throws Exception {
		String encryptedPassword = "9feb2+wj47QYKChmbeEnY28a1krO2H7k";
		String expectedPassword = "Sup3rS3cr3tP455w0rd";
		byte[] passwordBytes = parseBase64Binary(encryptedPassword);

		decryptorProperties.put("algorithm", "DES");
		decryptorProperties.put("mode", "ECB");
		decryptorProperties.put("padding", "PKCS5PADDING");
		decryptorProperties.put("keyLocator", "com.github.ncredinburgh.tomcat.DES56CompiledKey");

		testee.configure(decryptorProperties);
		byte[] clearBytes = testee.decrypt(passwordBytes);

		assertThat(expectedPassword).isEqualTo(new String(clearBytes));
	}

}
