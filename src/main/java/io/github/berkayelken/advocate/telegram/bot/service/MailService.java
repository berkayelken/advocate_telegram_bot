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
		String code = message.findAndGetUserMessageInsteadOfCommands(text);
		ApprovingRecord record = repository.findByEmailAndCodeAndType(user.getEmail(), code, action)
				.orElseThrow(CodeNotFoundException::new);
		if (ApproveAction.ADD_EMAIL == action) {
			userService.addEmail(chatId, record.getEmail());
			questionService.updateUserContext(user.getEmail(), userService.findUser(chatId));
		} else {
			userService.deleteUser(chatId);
			questionService.deleteAllQuestionOfUser(chatId);
		}
		repository.deleteById(record.getId());
	}

	public void sendMail(Long chatId, StaticBotMessage message) throws MessagingException {
		TelegramUser user = userService.findUser(chatId);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, MAIL_ENCODING);

		helper.setFrom(properties.getSender());
		helper.setTo(user.getEmail());
		helper.setSubject(TITLE_OF_MAIL);

		ApprovingRecord record = saveApprovingRecord(user.getEmail(), message.getType());
		StaticBotMessage mailMessage = getMailTemplate(message);
		String content = String.format(mailMessage.getMessage(), record.getCode());
		helper.setText(content);

		mailSender.send(mimeMessage);
	}

	private StaticBotMessage getMailTemplate(StaticBotMessage message) {
		if (message.getType() == MessageAction.EMAIL) {
			return cache.getMailApproveMessage();
		}

		return cache.getAccountDeletionMessage();
	}

	private ApprovingRecord saveApprovingRecord(String email, MessageAction action) {
		return repository.save(new ApprovingRecord(email, action));
	}

}
