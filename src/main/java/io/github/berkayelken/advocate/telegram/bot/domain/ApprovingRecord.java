package io.github.berkayelken.advocate.telegram.bot.domain;

import io.github.berkayelken.advocate.telegram.bot.util.IdGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@Document
public class ApprovingRecord {
	private static final long EXPIRES_MINUTES = 5L;
	private static final int ID_SIZE = 6;

	@Id
	@MongoId
	private String id;
	private String email;
	private String code;
	private ApproveAction type;
	private long expiresTime;

	public ApprovingRecord(String email, MessageAction action) {
		this.email = email;
		code = IdGenerator.getDigitId(ID_SIZE);
		type = ApproveAction.findInstance(action);
		expiresTime = ZonedDateTime.now().plusMinutes(EXPIRES_MINUTES).toInstant().toEpochMilli();
	}

	public boolean checkFreshness() {
		return Instant.now().isBefore(Instant.ofEpochMilli(expiresTime));
	}

	public void refreshCode() {
		code = IdGenerator.getDigitId(ID_SIZE);
	}
}
