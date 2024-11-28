package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document
public class CustomerQuestion {
	private static final String ABSTRACTION_TEMPLATE = "id: %s, soru: %s";
	private static final String DETAIL_ABSTRACTION_TEMPLATE_WITH_AI = "id: %s\n\nsoru: %s\n\nyapay zeka cevab\u0131: %s\n\navukat cevab\u0131: %s";
	private static final String DETAIL_ABSTRACTION_TEMPLATE = "id: %s\n\nsoru: %s\n\navukat cevab\u0131: %s";
	private static final String NOT_RESPONSE = "Sorunuz avukat\u0131m\u0131z taraf\u0131ndan hen\u00fcz g\u00f6r\u00fclmedi.";

	@Id
	@MongoId
	private String id;
	private String email;
	private String phone;
	private String question;
	private String category;
	private String issueType;
	private String answer;
	private String abstractAnswer;
	private String abstractQuestion;
	private String lawyerAnswer;
	private long askingTime;
	private boolean askedToAI;
	private boolean seenByCustomer;

	public void handleUserInfo(TelegramUser user) {
		id = UUID.randomUUID().toString();
		askingTime = Instant.now().toEpochMilli();
		updateUserContext(user);
	}

	public CustomerQuestion updateUserContext(TelegramUser user) {
		email = user.getEmail();
		phone = user.getPhone();
		return this;
	}

	public String convertAbstractInfo() {
		return String.format(ABSTRACTION_TEMPLATE, id, findAndGetAbstractQuestion());
	}

	public String convertDetailAbstract() {
		if (StringUtils.hasText(answer)) {
			return String.format(DETAIL_ABSTRACTION_TEMPLATE_WITH_AI, id, question, answer, getLawyerAnswerAsSafe());
		}

		return String.format(DETAIL_ABSTRACTION_TEMPLATE, id, question, getLawyerAnswerAsSafe());
	}

	private String findAndGetAbstractQuestion() {
		if (StringUtils.hasText(abstractQuestion)) {
			return abstractQuestion;
		}

		return question;
	}

	private String getLawyerAnswerAsSafe() {
		if (StringUtils.hasText(lawyerAnswer)) {
			return lawyerAnswer;
		}

		return NOT_RESPONSE;
	}
}
