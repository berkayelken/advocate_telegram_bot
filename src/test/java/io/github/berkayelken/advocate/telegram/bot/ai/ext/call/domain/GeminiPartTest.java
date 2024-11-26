package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeminiPartTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor(){
		Assertions.assertDoesNotThrow(() -> new GeminiPart());
	}

	@Test
	public void testArgsConstructor(){
		Assertions.assertDoesNotThrow(() -> new GeminiPart(TEST_STR));
	}

	@Test
	public void testAccessors() {
		GeminiPart part = new GeminiPart();

		Assertions.assertNull(part.getText());
	}

	@Test
	public void testMutators() {
		GeminiPart part = new GeminiPart();

		part.setText(TEST_STR);
		Assertions.assertEquals(TEST_STR, part.getText());
	}
}
