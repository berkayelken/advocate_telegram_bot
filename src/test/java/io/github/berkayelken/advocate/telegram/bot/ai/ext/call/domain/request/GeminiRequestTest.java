package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeminiRequestTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new GeminiRequest(TEST_STR));
	}

	@Test
	public void testAccessor() {
		GeminiRequest request = new GeminiRequest(TEST_STR);
		Assertions.assertNotNull(request.getContents());
	}

	@Test
	public void testMutator() {
		GeminiRequest request = new GeminiRequest(TEST_STR);
		request.setContents(null);
		Assertions.assertNull(request.getContents());
	}
}
