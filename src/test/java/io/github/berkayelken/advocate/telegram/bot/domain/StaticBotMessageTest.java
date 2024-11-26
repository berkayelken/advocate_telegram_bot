package io.github.berkayelken.advocate.telegram.bot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class StaticBotMessageTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(StaticBotMessage::new);
	}

	@Test
	public void testAccessors() {
		StaticBotMessage message = new StaticBotMessage();
		Assertions.assertNull(message.getId());
		Assertions.assertNull(message.getMessage());
		Assertions.assertNull(message.getUserMessage());
		Assertions.assertNull(message.getFixedKeyboard());
	}

	@Test
	public void testMutators() {
		StaticBotMessage message = new StaticBotMessage();
		message.setId(TEST_STR);
		message.setMessage(TEST_STR);
		message.setUserMessage(TEST_STR);
		message.setFixedKeyboard(Collections.singletonList(TEST_STR));
		Assertions.assertNotNull(message.getId());
		Assertions.assertNotNull(message.getMessage());
		Assertions.assertNotNull(message.getUserMessage());
		Assertions.assertNotNull(message.getFixedKeyboard());
	}
}
