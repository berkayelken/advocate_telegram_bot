package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class GeminiResponse {
	public static final String NO_RESPONSE = "Cevap yok.";

	private List<GeminiCandidate> candidates;

	public String findAndGetAnswer() {
		if(CollectionUtils.isEmpty(candidates)) {
			return NO_RESPONSE;
		}

		return candidates.stream().map(GeminiCandidate::findAndGetText).findFirst().orElse(NO_RESPONSE);
	}
}
