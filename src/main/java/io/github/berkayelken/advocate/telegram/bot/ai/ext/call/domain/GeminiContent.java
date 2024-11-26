package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response.GeminiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeminiContent {
	private List<GeminiPart> parts;

	public GeminiContent(String text) {
		parts = Collections.singletonList(new GeminiPart(text));
	}

	public String findAndGetText() {
		if (CollectionUtils.isEmpty(parts)) {
			return GeminiResponse.NO_RESPONSE;
		}

		return parts.stream().map(GeminiPart::getText).findFirst().orElse(GeminiResponse.NO_RESPONSE);
	}
}
