package io.github.berkayelken.advocate.telegram.bot.domain;

public enum ApproveAction {
	ADD_EMAIL,
	REMOVE_ACCOUNT;

	public static ApproveAction findInstance(MessageAction action) {
		switch (action) {
			case EMAIL_APPROVE, EMAIL -> {
				return ADD_EMAIL;
			}
			case DELETE_ACCOUNT_APPROVE, DELETE_ACCOUNT -> {
				return REMOVE_ACCOUNT;
			}
			default -> {
				return null;
			}
		}
	}
}
