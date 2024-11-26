package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerQuestion {
	private String id;
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
}
