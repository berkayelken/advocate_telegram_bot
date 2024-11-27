package io.github.berkayelken.advocate.telegram.bot.repository;

import io.github.berkayelken.advocate.telegram.bot.domain.CustomerQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerQuestionRepository extends MongoRepository<CustomerQuestion, String> {
	List<CustomerQuestion> findAllByEmail(String email);

	List<CustomerQuestion> findAllByEmailAndAskedToAIAndAskingTimeGreaterThanEqual(String email, boolean askedToAI,
			long askingTime);

	boolean deleteAllByEmail(String email);

	Page<CustomerQuestion> findAllByCategory(Pageable pageable, String category);

	Page<CustomerQuestion> findAllByIssueType(Pageable pageable, String issueType);

	Page<CustomerQuestion> findAllBy(Pageable pageable);
}
