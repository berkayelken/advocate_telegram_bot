package io.github.berkayelken.advocate.telegram.bot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TelegramUserTest {
	private static final long DEFAULT_LONG = 0L;
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new TelegramUser());
	}

	@Test
	public void testArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new TelegramUser(Long.MAX_VALUE));
	}

	@Test
	public void testAccessors() {
		TelegramUser user = new TelegramUser();
		Assertions.assertEquals(DEFAULT_LONG, user.getChatId());
		Assertions.assertEquals(DEFAULT_LONG, user.getConsentTime());
		Assertions.assertFalse(user.isAiEnabled());
		Assertions.assertNull(user.getEmail());
		Assertions.assertNull(user.getPhone());
	}

	@Test
	public void testMutators() {
		TelegramUser user = new TelegramUser();

		user.setChatId(Long.MAX_VALUE);
		user.setConsentTime(Long.MAX_VALUE);
		user.setAiEnabled(true);
		user.setEmail(TEST_STR);
		user.setPhone(TEST_STR);

		Assertions.assertEquals(Long.MAX_VALUE, user.getChatId());
		Assertions.assertEquals(Long.MAX_VALUE, user.getConsentTime());
		Assertions.assertTrue(user.isAiEnabled());
		Assertions.assertEquals(TEST_STR,user.getEmail());
		Assertions.assertEquals(TEST_STR,user.getPhone());
	}
}
