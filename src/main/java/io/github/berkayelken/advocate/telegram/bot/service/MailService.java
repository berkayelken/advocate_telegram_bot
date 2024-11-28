package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.domain.ApproveAction;
import io.github.berkayelken.advocate.telegram.bot.domain.ApprovingRecord;
import io.github.berkayelken.advocate.telegram.bot.domain.MessageAction;
import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import io.github.berkayelken.advocate.telegram.bot.domain.TelegramUser;
import io.github.berkayelken.advocate.telegram.bot.exception.CodeNotFoundException;
import io.github.berkayelken.advocate.telegram.bot.properties.MailProperties;
import io.github.berkayelken.advocate.telegram.bot.repository.ApprovingRecordRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class MailService {
	private static final String MAIL_ENCODING = "UTF-8";
	private static final String TITLE_OF_MAIL = "[No-Reply]: Onay Kodu";

	private final JavaMailSender mailSender;
	private final MailProperties properties;
	private final ApprovingRecordRepository repository;
	private final TelegramUserService userService;
	private final QuestionService questionService;
	private final StaticBotMessageCache cache;

	public void checkApprove(Long chatId, StaticBotMessage message, String text) {
		TelegramUser user = userService.findUser(chatId);
		ApproveAction action = message.findAndGetApproveAction();
		ApprovingRecord record = getRecord(user, message, action, text);
		if (ApproveAction.ADD_EMAIL == action) {
			userService.addEmail(chatId, record.getEmail());
			questionService.updateUserContext(user.getEmail(), userService.findUser(chatId));
		} else {
			userService.deleteUser(chatId);
			questionService.deleteAllQuestionOfUser(chatId);
		}
		repository.deleteById(record.getId());
	}

	public void sendMail(Long chatId, StaticBotMessage message, String text) throws MessagingException {
		TelegramUser user = userService.findUser(chatId);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, MAIL_ENCODING);

		helper.setFrom(properties.getSender());
		String email = getEmailAddress(user, message, text);
		helper.setTo(email);
		helper.setSubject(TITLE_OF_MAIL);

		ApprovingRecord record = saveApprovingRecord(email, message.getType());
		StaticBotMessage mailMessage = getMailTemplate(message);
		String content = String.format(mailMessage.getMessage(), record.getCode());
		helper.setText(content);

		mailSender.send(mimeMessage);
	}

	private ApprovingRecord getRecord(TelegramUser user, StaticBotMessage message, ApproveAction action, String text) {
		String code = message.findAndGetUserMessageInsteadOfCommands(text);
		if (action == ApproveAction.ADD_EMAIL) {
			return repository.findByCodeAndType(code, action).orElseThrow(CodeNotFoundException::new);
		}
		return repository.findByEmailAndCodeAndType(user.getEmail(), code, action).orElseThrow(CodeNotFoundException::new);
	}

	private String getEmailAddress(TelegramUser user, StaticBotMessage message, String text) {
		if (StringUtils.hasText(user.getEmail())) {
			return user.getEmail();
		}

		return message.findAndGetUserMessageInsteadOfCommands(text);
	}

	private StaticBotMessage getMailTemplate(StaticBotMessage message) {
		if (message.getType() == MessageAction.EMAIL) {
			return cache.getMailApproveMessage();
		}

		return cache.getAccountDeletionMessage();
	}

	private ApprovingRecord saveApprovingRecord(String email, MessageAction action) {
		ApprovingRecord record = new ApprovingRecord(email, action);
		while(repository.existsByCodeAndType(record.getCode(), record.getType())) {
			record.refreshCode();
		}

		return repository.save(record);
	}

}
