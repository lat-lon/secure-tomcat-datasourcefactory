package com.github.ncredinburgh.tomcat;

import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPropertyParser {

	@Test
	public void shouldParseOneProperty() throws Exception {
		String propertyString = "prop1=value1";

		Properties result = PropertyParser.parseProperties(propertyString);

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.containsKey("prop1")).isEqualTo(true);
		assertThat(result.getProperty("prop1")).isEqualTo("value1");
	}

	@Test
	public void shouldParseTwoProperties() throws Exception {
		String propertyString = "prop1=value1;prop2=value2";

		Properties result = PropertyParser.parseProperties(propertyString);

		assertThat(result.size()).isEqualTo(2);
		assertThat(result.containsKey("prop1")).isEqualTo(true);
		assertThat(result.getProperty("prop1")).isEqualTo("value1");
		assertThat(result.containsKey("prop2")).isEqualTo(true);
		assertThat(result.getProperty("prop2")).isEqualTo("value2");
	}

	@Test
	public void shouldIgnoreTrailingSemicolon() throws Exception {
		String propertyString = "prop1=value1;";

		Properties result = PropertyParser.parseProperties(propertyString);

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.containsKey("prop1")).isEqualTo(true);
		assertThat(result.getProperty("prop1")).isEqualTo("value1");
	}

}
