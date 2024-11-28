package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageAction {
	START(false),
	AGREE,
	DISAGREE(false),
	ENABLE_AI,
	DISABLE_AI,
	EMAIL,
	PHONE,
	EMAIL_APPROVE,
	GET_ALL_QUESTIONS,
	GET_QUESTION,
	DELETE_QUESTION,
	DELETE_ACCOUNT,
	DELETE_ACCOUNT_APPROVE,
	DELETE_ALL_QUESTIONS,
	HELP(false),
	ANSWER(true, true),
	EMAIL_APPROVE_MAIL(true, true),
	ACCOUNT_DELETION_APPROVE_MAIL(true, true);

	private final boolean needAction;
	private final boolean messageTemplate;

	MessageAction() {
		needAction = true;
		messageTemplate = false;
	}

	MessageAction(boolean needAction) {
		this.needAction = needAction;
		messageTemplate = false;
	}

}
