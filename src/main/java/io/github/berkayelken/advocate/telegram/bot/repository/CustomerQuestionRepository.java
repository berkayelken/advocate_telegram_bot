package io.github.berkayelken.advocate.telegram.bot.repository;

import io.github.berkayelken.advocate.telegram.bot.domain.CustomerQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerQuestionRepository extends CrudRepository<CustomerQuestion, String> {
	List<CustomerQuestion> findAllByEmail(String email);

	List<CustomerQuestion> findAllByEmailAndAskedToAIAndAskingTimeGreaterThanEqual(String email, boolean askedToAI,
			long askingTime);

	boolean deleteAllByEmail(String email);
}
