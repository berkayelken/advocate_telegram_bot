package io.github.berkayelken.advocate.telegram.bot.service;

import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessageTest;
import io.github.berkayelken.advocate.telegram.bot.repository.StaticBotMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith (MockitoExtension.class)
public class StaticBotMessageCacheTest {
	@Mock
	private StaticBotMessageRepository repository;

	private StaticBotMessageCache getCacheService() {
		return new StaticBotMessageCache(repository);
	}

	@Test
	public void testRefreshCache() {
		StaticBotMessageCache cache = getCacheService();
		Mockito.when(repository.findAll())
				.thenReturn(List.of(StaticBotMessageTest.createBotMessage(), StaticBotMessageTest.createBotMessage()));
		Assertions.assertDoesNotThrow(cache::refreshCache);
	}

	@Test
	public void testGetAnswer() {
		StaticBotMessageCache cache = getCacheService();
		Mockito.when(repository.findAll())
				.thenReturn(List.of(StaticBotMessageTest.createBotMessage(), StaticBotMessageTest.createBotMessage()));
		cache.refreshCache();
		Assertions.assertNull(cache.getMessage(UUID.randomUUID().toString()));
	}

	@Test
	public void testGetAnswerWithNoMessages() {
		StaticBotMessageCache cache = getCacheService();
		Assertions.assertNull(cache.getMessage(UUID.randomUUID().toString()));
	}

	@Test
	public void testGetMailApprove() {
		StaticBotMessageCache cache = getCacheService();
		Assertions.assertNull(cache.getMailApproveMessage());
	}

	@Test
	public void testGetAccountDeletionMessage() {
		StaticBotMessageCache cache = getCacheService();
		Assertions.assertNull(cache.getAccountDeletionMessage());
	}
}
