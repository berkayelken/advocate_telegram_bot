package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.request;

import io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.GeminiContent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class GeminiRequest {
	private List<GeminiContent> contents;

	public GeminiRequest(String text) {
		contents = Collections.singletonList(new GeminiContent(text));
	}
}
