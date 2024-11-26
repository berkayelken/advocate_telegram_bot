package io.github.berkayelken.advocate.telegram.bot.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeminiPropertiesTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(GeminiProperties::new);
	}

	@Test
	public void testAccessor() {
		GeminiProperties properties = new GeminiProperties();
		Assertions.assertNull(properties.getApiKey());
	}

	@Test
	public void testMutator() {
		GeminiProperties properties = new GeminiProperties();
		properties.setApiKey(TEST_STR);
		Assertions.assertEquals(TEST_STR, properties.getApiKey());
	}
}
