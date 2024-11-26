package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.GeminiContent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class GeminiCandidate {
	private GeminiContent content;

	String findAndGetText() {
		if (content == null) {
			return GeminiResponse.NO_RESPONSE;
		}

		return content.findAndGetText();
	}
}
