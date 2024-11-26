package io.github.berkayelken.advocate.telegram.bot.ai.ext.call;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.request.GeminiRequest;
import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response.GeminiResponse;
import io.github.berkayelken.advocate.telegram.bot.properties.GeminiProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GeminiClientTest {
	private static final String TEST_STR = "TEST";
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
		Mockito.when(properties.getApiKey()).thenReturn(TEST_STR);
		Mockito.when(feignClient.callAI(ArgumentMatchers.anyString(), ArgumentMatchers.any(GeminiRequest.class))).thenReturn(new GeminiResponse());
		Assertions.assertEquals(GeminiResponse.NO_RESPONSE, client.askAI(TEST_STR));
	}

	@Test
	public void testAskWithNoResponse() {
		GeminiClient client = getClient();
		Assertions.assertEquals(GeminiResponse.NO_RESPONSE, client.askAI(TEST_STR));
	}

}
