package io.github.berkayelken.advocate.telegram.bot.listener;

import io.github.berkayelken.advocate.telegram.bot.domain.CustomerQuestion;
import io.github.berkayelken.advocate.telegram.bot.domain.MessageAction;
import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import io.github.berkayelken.advocate.telegram.bot.domain.TelegramUser;
import io.github.berkayelken.advocate.telegram.bot.exception.CodeNotFoundException;
import io.github.berkayelken.advocate.telegram.bot.exception.UserNotFoundException;
import io.github.berkayelken.advocate.telegram.bot.message.ReplyKeyboardBuilder;
import io.github.berkayelken.advocate.telegram.bot.service.MailService;
import io.github.berkayelken.advocate.telegram.bot.service.QuestionService;
import io.github.berkayelken.advocate.telegram.bot.service.StaticBotMessageCache;
import io.github.berkayelken.advocate.telegram.bot.service.TelegramUserService;
import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Service
public class TelegramListener extends TelegramLongPollingBot {
	private final String username;
	private final ReplyKeyboardBuilder replyKeyboardBuilder;
	private final StaticBotMessageCache cache;
	private final TelegramUserService userService;
	private final MailService mailService;
	private final QuestionService questionService;

	@EventListener ({ ContextRefreshedEvent.class })
	public void init() throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e) {
			log.error("Error occurred: " + e.getMessage());
		}
	}

	public TelegramListener(@Value ("${telegram.token}") String token, @Value ("${telegram.username}") String username,
			ReplyKeyboardBuilder replyKeyboardBuilder, StaticBotMessageCache cache, TelegramUserService userService,
			MailService mailService, QuestionService questionService) {
		super(token);
		this.username = username;
		this.replyKeyboardBuilder = replyKeyboardBuilder;
		this.cache = cache;
		this.userService = userService;
		this.mailService = mailService;
		this.questionService = questionService;
	}

	@SneakyThrows
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		Long chatId = message.getChatId();
		log.info("chat id {}", chatId);

		if (message.hasText()) {
			String text = message.getText();
			StaticBotMessage botMessage = cache.getMessage(text);
 			if (botMessage.hasNoAction()) {
				execute(replyKeyboardBuilder.buildMessage(chatId, botMessage));
				return;
			}

			handleActionableMessage(chatId, botMessage, text);
		}
	}

	private void handleActionableMessage(Long chatId, StaticBotMessage botMessage, String text) throws TelegramApiException {
		try {
			handleMessageAction(chatId, botMessage, text);
		} catch (MessagingException | CodeNotFoundException | NullPointerException e) {
			execute(replyKeyboardBuilder.buildMessage(chatId, botMessage, true));
		} catch (UserNotFoundException e) {
			execute(replyKeyboardBuilder.buildMessage(chatId, e.getMessage()));
		}
	}

	private void handleMessageAction(Long chatId, StaticBotMessage botMessage, String text)
			throws TelegramApiException, MessagingException {
		switch (botMessage.getType()) {
			case AGREE, ENABLE_AI, DISABLE_AI, PHONE -> {
				handleUserAction(chatId, botMessage, text);
				execute(replyKeyboardBuilder.buildMessage(chatId, botMessage));
			}

			case DELETE_ACCOUNT, EMAIL, EMAIL_APPROVE, DELETE_ACCOUNT_APPROVE -> {
				handleEmailAction(chatId, botMessage, text);
				execute(replyKeyboardBuilder.buildMessage(chatId, botMessage));
			}
			case ANSWER, GET_QUESTION, GET_ALL_QUESTIONS, DELETE_QUESTION, DELETE_ALL_QUESTIONS ->
					handleQuestionDetailAction(chatId, botMessage, text);
		}
	}

	private void handleEmailAction(Long chatId, StaticBotMessage botMessage, String text) throws MessagingException {
		switch (botMessage.getType()) {
			case DELETE_ACCOUNT, EMAIL -> mailService.sendMail(chatId, botMessage, text);
			case EMAIL_APPROVE, DELETE_ACCOUNT_APPROVE -> mailService.checkApprove(chatId, botMessage, text);
		}
	}

	private void handleUserAction(Long chatId, StaticBotMessage botMessage, String text) {
		switch (botMessage.getType()) {
			case AGREE -> userService.createUser(chatId);
			case ENABLE_AI -> userService.setEnableAIToUser(chatId);
			case DISABLE_AI -> userService.setDisableAIToUser(chatId);
			case PHONE -> userService.addPhone(chatId, botMessage.findAndGetUserMessageInsteadOfCommands(text));
		}
	}

	private void handleQuestionDetailAction(Long chatId, StaticBotMessage botMessage, String text) throws TelegramApiException {
		switch (botMessage.getType()) {
			case ANSWER ->  {
				CustomerQuestion question = questionService.askQuestion(chatId, text);
				if (question.isAskedToAI()) {
					execute(replyKeyboardBuilder.buildMessage(chatId, botMessage, question.getAnswer(), question.getId()));
				} else {
					execute(replyKeyboardBuilder.buildMessage(chatId, botMessage, true, question.getId()));
				}
			}
			case GET_QUESTION, GET_ALL_QUESTIONS, DELETE_QUESTION -> {
				String output = handleQuestionDetailActionWithOutput(chatId, botMessage, text);
				execute(replyKeyboardBuilder.buildMessage(chatId, botMessage, output));
			}
			case DELETE_ALL_QUESTIONS -> {
				questionService.deleteAllQuestionOfUser(chatId);
				execute(replyKeyboardBuilder.buildMessage(chatId, botMessage));
			}
		}
	}

	private String handleQuestionDetailActionWithOutput(Long chatId, StaticBotMessage botMessage, String text) {
		if (botMessage.getType() == MessageAction.GET_ALL_QUESTIONS) {
			return questionService.getAllQuestionOfUser(chatId);
		}



		String id = botMessage.findAndGetUserMessageInsteadOfCommands(text);

		if (botMessage.getType() == MessageAction.DELETE_QUESTION) {
			questionService.deleteQuestion(id);
			return id;
		}

		return questionService.getQuestionDetail(chatId, id);
	}

	@Override
	public String getBotUsername() {
		return username;
	}

}
