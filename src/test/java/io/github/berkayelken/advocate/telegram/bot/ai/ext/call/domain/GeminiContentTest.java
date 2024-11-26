package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response.GeminiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class GeminiContentTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new GeminiContent());
	}

	@Test
	public void testArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new GeminiContent(TEST_STR));
	}

	@Test
	public void testAccessor() {
		GeminiContent content = new GeminiContent();

		Assertions.assertNull(content.getParts());
	}

	@Test
	public void testMutators() {
		GeminiContent content = new GeminiContent();

		content.setParts(Collections.singletonList(new GeminiPart(TEST_STR)));
		Assertions.assertNotNull(content.getParts());
	}

	@Test
	public void testFindAndGetTextWithNoText() {
		GeminiContent content = new GeminiContent();

		Assertions.assertEquals(GeminiResponse.NO_RESPONSE, content.findAndGetText());
	}

	@Test
	public void testFindAndGetTextWithText() {
		GeminiContent content = new GeminiContent(TEST_STR);

		Assertions.assertNotEquals(GeminiResponse.NO_RESPONSE, content.findAndGetText());
	}
}
