package io.github.berkayelken.advocate.telegram.bot.ai.ext.call;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.request.GeminiRequest;
import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "${feign-client.gemini.name}", url = "${feign-client.gemini.url}")
public interface GeminiFeignClient {
	String APPLICATION_TYPE = "application/json";

	@PostMapping(consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE)
	GeminiResponse callAI(@RequestParam ("key") String apiKey, @RequestBody GeminiRequest request);
}
