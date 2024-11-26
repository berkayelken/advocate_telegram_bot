package io.github.berkayelken.advocate.telegram.bot.ai.ext.call;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.request.GeminiRequest;
import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response.GeminiResponse;
import io.github.berkayelken.advocate.telegram.bot.properties.GeminiProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeminiClient {
	private final GeminiProperties properties;
	private final GeminiFeignClient feignClient;

	public String askAI(String question) {
		GeminiResponse response = feignClient.callAI(properties.getApiKey(), new GeminiRequest(question));
		if (response == null) {
			return GeminiResponse.NO_RESPONSE;
		}

		return response.findAndGetAnswer();
	}
}
