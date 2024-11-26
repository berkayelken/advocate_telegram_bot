package io.github.berkayelken.advocate.telegram.bot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("gemini-connection")
public class GeminiProperties {
	private String apiKey;
}
