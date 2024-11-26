package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.GeminiContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeminiCandidateTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(GeminiCandidate::new);
	}

	@Test
	public void testAccessor() {
		GeminiCandidate candidate = new GeminiCandidate();
		Assertions.assertNull(candidate.getContent());
	}

	@Test
	public void testMutator() {
		GeminiCandidate candidate = new GeminiCandidate();
		candidate.setContent(new GeminiContent(TEST_STR));
		Assertions.assertNotNull(candidate.getContent());
	}

	@Test
	public void testFindAndGetTextWithNoContent() {
		GeminiCandidate candidate = new GeminiCandidate();
		Assertions.assertEquals(GeminiResponse.NO_RESPONSE, candidate.findAndGetText());
	}

	@Test
	public void testFindAndGetTextWithContent() {
		GeminiCandidate candidate = new GeminiCandidate();
		candidate.setContent(new GeminiContent(TEST_STR));
		Assertions.assertNotEquals(GeminiResponse.NO_RESPONSE, candidate.findAndGetText());
	}
}
