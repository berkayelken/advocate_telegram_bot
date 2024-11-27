package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import io.github.berkayelken.advocate.telegram.bot.repository.StaticBotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class StaticBotMessageCache {
	private final AtomicReference<StaticBotMessage> mailApprove = new AtomicReference<>();
	private final AtomicReference<StaticBotMessage> accountApprove = new AtomicReference<>();
	private final AtomicReference<StaticBotMessage> answer = new AtomicReference<>();
	private List<StaticBotMessage> messages;
	private List<StaticBotMessage> allMessages;

	private final StaticBotMessageRepository repository;

	@Autowired
	public StaticBotMessageCache(StaticBotMessageRepository repository) {
		this.repository = repository;
		refreshCache();
	}

	@Scheduled (initialDelayString = "${message-cache.scheduling-delay}", fixedDelayString = "${message-cache.scheduling-delay}")
	public void refreshCache() {
		handleMessages();
		handleAnswer();
		handleMailApprove();
		handleAccountDeletion();
	}

	public StaticBotMessage getMessage(String text) {
		if (CollectionUtils.isEmpty(messages)) {
			return null;
		}

		return messages.stream().filter(bot -> bot.isCommandMessage(text)).findFirst().orElseGet(answer::get);
	}

	public StaticBotMessage getMailApproveMessage() {
		return mailApprove.get();
	}

	public StaticBotMessage getAccountDeletionMessage() {
		return accountApprove.get();
	}

	private void handleMessages() {
		List<StaticBotMessage> botMessages = new ArrayList<>();
		repository.findAll().forEach(botMessages::add);
		if (CollectionUtils.isEmpty(botMessages)) {
			return;
		}
		allMessages = botMessages;
		messages = allMessages.stream().filter(StaticBotMessage::isRelatedUserMessage).collect(Collectors.toList());
	}

	private void handleAnswer() {
		if(CollectionUtils.isEmpty(allMessages)) {
			return;
		}

		StaticBotMessage answerMessage = allMessages.stream().filter(StaticBotMessage::isAnswerMessage).findFirst().orElse(null);
		if (answerMessage == null) {
			return;
		}

		answer.set(answerMessage);
	}

	private void handleMailApprove() {
		if(CollectionUtils.isEmpty(allMessages)) {
			return;
		}

		StaticBotMessage mailApproveMessage = allMessages.stream().filter(StaticBotMessage::isMailApprove).findFirst().orElse(null);
		if (mailApproveMessage == null) {
			return;
		}

		mailApprove.set(mailApproveMessage);
	}

	private void handleAccountDeletion() {
		if(CollectionUtils.isEmpty(allMessages)) {
			return;
		}

		StaticBotMessage accountDeletionMessage = allMessages.stream().filter(StaticBotMessage::isAccountDeletion).findFirst().orElse(null);
		if (accountDeletionMessage == null) {
			return;
		}

		accountApprove.set(accountDeletionMessage);
	}
}
