package io.github.berkayelken.advocate.telegram.bot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document
public class StandaloneCustomerQuestion extends CustomerQuestion {
	@Id
	@MongoId
	private String id;
	private String email;
	private String phone;
}
