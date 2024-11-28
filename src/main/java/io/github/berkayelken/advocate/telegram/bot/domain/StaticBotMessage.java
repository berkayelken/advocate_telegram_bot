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
	private MessageAction type;
	private String userMessage;
	private boolean prefix;
	private String message;
	private String failMessage;
	private List<String> fixedKeyboard;

	public boolean isCommandMessage(String text) {
		if(prefix) {
			return text.startsWith(userMessage);
		}

		return text.equalsIgnoreCase(userMessage);
	}

	public boolean isRelatedUserMessage() {
		return type != null && !type.isMessageTemplate();
	}

	public boolean isAnswerMessage() {
		return MessageAction.ANSWER == type;
	}

	public boolean isMailApprove() {
		return MessageAction.EMAIL_APPROVE_MAIL == type;
	}

	public boolean isAccountDeletion() {
		return MessageAction.ACCOUNT_DELETION_APPROVE_MAIL == type;
	}
}
