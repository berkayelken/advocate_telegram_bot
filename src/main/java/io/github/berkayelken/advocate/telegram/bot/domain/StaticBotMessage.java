package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@ToString
@Document
public class StaticBotMessage {
	@Id
	@MongoId
	private String id;
	private String userMessage;
	private String message;
	private List<String> fixedKeyboard;
}
