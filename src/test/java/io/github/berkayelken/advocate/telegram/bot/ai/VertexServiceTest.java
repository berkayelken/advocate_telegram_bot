package io.github.berkayelken.advocate.telegram.bot.ai;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.GeminiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VertexServiceTest {
	@Mock
	private GeminiClient geminiClient;

	private VertexService getService() {
		MockitoAnnotations.openMocks(this);
		return new VertexService(geminiClient);
	}

	@Test
	public void testAsk() {
		VertexService service = getService();
		Assertions.assertDoesNotThrow(() -> service.ask(""));
	}
}
