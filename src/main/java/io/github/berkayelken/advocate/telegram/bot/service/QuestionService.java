package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.ai.VertexService;
import io.github.berkayelken.advocate.telegram.bot.domain.CustomerQuestion;
import io.github.berkayelken.advocate.telegram.bot.domain.TelegramUser;
import io.github.berkayelken.advocate.telegram.bot.repository.CustomerQuestionRepository;
import io.github.berkayelken.advocate.telegram.bot.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService {
	private static final int EACH_PERSON_DAILY_AI_LIMIT = 5;
	private final VertexService vertexService;
	private final CustomerQuestionRepository repository;
	private final TelegramUserService userService;

	public void updateUserContext(String exEmail, TelegramUser user) {
		List<CustomerQuestion> updated = repository.findAllByEmail(exEmail).map(q -> q.updateUserContext(user)).toList();
		repository.saveAll(updated);
	}

	public String getAllQuestionOfUser(Long chatId) {
		TelegramUser user = userService.findUser(chatId);
		StringJoiner joiner = new StringJoiner("\n");
		repository.findAllByEmail(user.getEmail()).map(CustomerQuestion::convertAbstractInfo).forEachOrdered(joiner::add);
		return joiner.toString();
	}

	public String getQuestionDetail(Long chatId, String id) {
		TelegramUser user = userService.findUser(chatId);
		CustomerQuestion question = repository.findById(id).orElse(null);
		if (question == null || !question.getEmail().equalsIgnoreCase(user.getEmail())) {
			return null;
		}

		return question.convertDetailAbstract();
	}

	public CustomerQuestion askQuestion(Long chatId, String question) {
		TelegramUser user = userService.findUser(chatId);
		CustomerQuestion customerQuestion = askQuestion(user, question);
		customerQuestion.handleUserInfo(user);
		return repository.save(customerQuestion);
	}

	public void deleteQuestion(String id) {
		repository.deleteById(id);
	}

	public void deleteAllQuestionOfUser(String email) {
		repository.deleteAllByEmail(email);
	}

	private CustomerQuestion askQuestion(TelegramUser user, String question) {
		if (canAskAI(user)) {
			return askQuestionToAI(question);
		}

		return askQuestionToLawyer(question);
	}

	private boolean canAskAI(TelegramUser user) {
		return user.isAiEnabled() && isNotReachToDailyLimit(user);
	}

	private boolean isNotReachToDailyLimit(TelegramUser user) {
		long startOfDay = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return repository.findAllByEmailAndAskedToAIAndAskingTimeGreaterThanEqual(user.getEmail(), true, startOfDay).size()
				<= EACH_PERSON_DAILY_AI_LIMIT;
	}

	private CustomerQuestion askQuestionToAI(String question) {
		String json = vertexService.ask(question);
		CustomerQuestion customerQuestion = JsonUtil.jsonToObject(json, CustomerQuestion.class);
		if (customerQuestion == null) {
			return askQuestionToLawyer(question);
		}
		customerQuestion.setAskedToAI(true);
		return customerQuestion;
	}

	private CustomerQuestion askQuestionToLawyer(String question) {
		CustomerQuestion customerQuestion = new CustomerQuestion();
		customerQuestion.setQuestion(question);
		return customerQuestion;
	}

}
