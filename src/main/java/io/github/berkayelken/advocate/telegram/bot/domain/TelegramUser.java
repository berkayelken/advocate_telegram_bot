package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TelegramUser {
	@Id
	@MongoId(targetType = FieldType.INT64)
	private long chatId;
	private String email;
	private String phone;
	private long consentTime;

	public TelegramUser(long chatId) {
		this.chatId = chatId;
		this.consentTime = Instant.now().toEpochMilli();
	}
}
