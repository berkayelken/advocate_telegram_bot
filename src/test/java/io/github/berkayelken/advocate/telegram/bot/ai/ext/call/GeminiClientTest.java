package io.github.berkayelken.advocate.telegram.bot.ai.ext.call;

import io.github.berkayelken.advocate.telegram.bot.properties.GeminiProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GeminiClientTest {
	@Mock
	private GeminiProperties properties;
	@Mock
	private GeminiFeignClient feignClient;

	private GeminiClient getClient() {
		return new GeminiClient(properties, feignClient);
	}

	@Test
	public void testAsk() {
		GeminiClient client = getClient();
		Assertions.assertDoesNotThrow(() -> client.askAI("TEST"));
	}

}
