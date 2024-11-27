package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.domain.TelegramUser;
import io.github.berkayelken.advocate.telegram.bot.exception.UserNotFoundException;
import io.github.berkayelken.advocate.telegram.bot.repository.TelegramUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TelegramUserServiceTest {
	@Mock
	private TelegramUserRepository repository;

	private TelegramUserService getService() {
		return new TelegramUserService(repository);
	}

	private TelegramUserService getServiceWithUser() {
		Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(new TelegramUser()));
		return getService();
	}

	@Test
	public void testFindUser() {
		TelegramUserService service = getServiceWithUser();
		Assertions.assertDoesNotThrow(() -> service.findUser(Long.MAX_VALUE));
	}

	@Test
	public void testFindUserWithNotFound() {
		TelegramUserService service = getService();
		Assertions.assertThrows(UserNotFoundException.class, () -> service.findUser(Long.MAX_VALUE));
	}

	@Test
	public void testCreateUser() {
		TelegramUserService service = getService();
		Assertions.assertDoesNotThrow(() -> service.createUser(Long.MAX_VALUE));
	}

	@Test
	public void testCreateUserWhenAlreadyCreated() {
		TelegramUserService service = getServiceWithUser();
		Mockito.when(repository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Assertions.assertDoesNotThrow(() -> service.createUser(Long.MAX_VALUE));
	}

	@Test
	public void testAddEmail() {
		TelegramUserService service = getServiceWithUser();
		Assertions.assertDoesNotThrow(() -> service.addEmail(Long.MAX_VALUE, null));
	}

	@Test
	public void testAddPhone() {
		TelegramUserService service = getServiceWithUser();
		Assertions.assertDoesNotThrow(() -> service.addPhone(Long.MAX_VALUE, null));
	}

	@Test
	public void testDeletePhone() {
		TelegramUserService service = getService();
		Assertions.assertDoesNotThrow(() -> service.deleteUser(Long.MAX_VALUE));
	}

	@Test
	public void testSetEnableAIToUser() {
		TelegramUserService service = getServiceWithUser();
		Assertions.assertDoesNotThrow(() -> service.setEnableAIToUser(Long.MAX_VALUE));
	}

	@Test
	public void testSetDisableAIToUser() {
		TelegramUserService service = getServiceWithUser();
		Assertions.assertDoesNotThrow(() -> service.setDisableAIToUser(Long.MAX_VALUE));
	}

}
