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
	private MessageAction action;
	private String userMessage;
	private boolean prefix;
	private String message;
	private String failMessage;
	private List<String> fixedKeyboard;

	public boolean isRelatedUserMessage() {
		return action != null && !action.isMessageTemplate();
	}

	public boolean isAnswerMessage() {
		return MessageAction.ANSWER == action;
	}

	public boolean isMailApprove() {
		return MessageAction.EMAIL_APPROVE_MAIL == action;
	}

	public boolean isAccountDeletion() {
		return MessageAction.ACCOUNT_DELETION_APPROVE_MAIL == action;
	}
}
