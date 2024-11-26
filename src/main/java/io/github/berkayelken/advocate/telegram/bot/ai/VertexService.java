package io.github.berkayelken.advocate.telegram.bot.ai;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.GeminiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VertexService {
	private final GeminiClient geminiClient;

	public String ask(String question) {
		return geminiClient.askAI(question);
	}
}
