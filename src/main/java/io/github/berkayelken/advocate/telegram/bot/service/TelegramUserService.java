package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.domain.TelegramUser;
import io.github.berkayelken.advocate.telegram.bot.exception.UserNotFoundException;
import io.github.berkayelken.advocate.telegram.bot.repository.TelegramUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TelegramUserService {
	private final TelegramUserRepository repository;

	public TelegramUser createUser(Long chatId) {
		if (repository.existsById(chatId)) {
			return findUser(chatId);
		}
		return repository.save(new TelegramUser(chatId));
	}

	public TelegramUser findUser(Long chatId) {
		return repository.findById(chatId).orElseThrow(UserNotFoundException::new);
	}

	public void addEmail(Long chatId, String email) {
		TelegramUser user = findUser(chatId);
		user.setEmail(email);
		repository.save(user);
	}

	public void addPhone(Long chatId, String phone) {
		TelegramUser user = findUser(chatId);
		user.setPhone(phone);
		repository.save(user);
	}

	public void deleteUser(Long chatId) {
		repository.deleteById(chatId);
	}

	public void setEnableAIToUser(Long chatId) {
		changeUserAIFlag(chatId, true);
	}

	public void setDisableAIToUser(Long chatId) {
		changeUserAIFlag(chatId, false);
	}

	private void changeUserAIFlag(Long chatId, boolean flag) {
		TelegramUser user = findUser(chatId);
		user.setAiEnabled(flag);
		repository.save(user);
	}


}
