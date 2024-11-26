package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.GeminiContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class GeminiResponseTest {
	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(GeminiResponse::new);
	}

	@Test
	public void testAccessor() {
		GeminiResponse response = new GeminiResponse();
		Assertions.assertNull(response.getCandidates());
	}

	@Test
	public void testMutator() {
		GeminiResponse response = new GeminiResponse();
		response.setCandidates(Collections.singletonList(new GeminiCandidate()));
		Assertions.assertNotNull(response.getCandidates());
	}

	@Test
	public void testFindAndGetAnswerWithNoCandidate() {
		GeminiResponse response = new GeminiResponse();
		Assertions.assertEquals(GeminiResponse.NO_RESPONSE, response.findAndGetAnswer());
	}

	@Test
	public void testFindAndGetAnswerWithCandidateAndText() {
		GeminiResponse response = new GeminiResponse();
		GeminiCandidate candidate = new GeminiCandidate();
		candidate.setContent(new GeminiContent("TEST"));
		response.setCandidates(Collections.singletonList(candidate));
		Assertions.assertNotEquals(GeminiResponse.NO_RESPONSE, response.findAndGetAnswer());
	}
}
