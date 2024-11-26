package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document
public class CustomerQuestion {
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
}
