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
		Assertions.assertNull(message.getType());
		Assertions.assertNull(message.getMessage());
		Assertions.assertNull(message.getFailMessage());
		Assertions.assertNull(message.getUserMessage());
		Assertions.assertNull(message.getFixedKeyboard());
		Assertions.assertFalse(message.isPrefix());
		Assertions.assertFalse(message.isRelatedUserMessage());
		Assertions.assertFalse(message.isAnswerMessage());
		Assertions.assertFalse(message.isAccountDeletion());
		Assertions.assertFalse(message.isMailApprove());
	}

	@Test
	public void testMutators() {
		StaticBotMessage message = createBotMessage();
		message.setFixedKeyboard(Collections.singletonList(TEST_STR));
		Assertions.assertEquals(TEST_STR, message.getId());
		Assertions.assertEquals(MessageAction.AGREE, message.getType());
		Assertions.assertEquals(TEST_STR, message.getMessage());
		Assertions.assertEquals(TEST_STR, message.getFailMessage());
		Assertions.assertEquals(TEST_STR, message.getUserMessage());
		Assertions.assertTrue(message.isPrefix());
		Assertions.assertNotNull(message.getFixedKeyboard());
	}

	public static StaticBotMessage createBotMessage() {
		StaticBotMessage message = new StaticBotMessage();
		message.setId(TEST_STR);
		message.setType(MessageAction.AGREE);
		message.setMessage(TEST_STR);
		message.setFailMessage(TEST_STR);
		message.setUserMessage(TEST_STR);
		message.setPrefix(true);
		return message;
	}
}
