package io.github.berkayelken.advocate.telegram.bot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApprovingRecordTest {
	private static final long DEFAULT_LONG = 0L;
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new ApprovingRecord());
	}

	@Test
	public void testArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new ApprovingRecord(TEST_STR, MessageAction.EMAIL_APPROVE_MAIL));
	}

	@Test
	public void testAccessors() {
		ApprovingRecord record = new ApprovingRecord();
		Assertions.assertNull(record.getId());
		Assertions.assertNull(record.getEmail());
		Assertions.assertNull(record.getCode());
		Assertions.assertNull(record.getType());
		Assertions.assertEquals(DEFAULT_LONG, record.getExpiresTime());
	}

	@Test
	public void testMutators() {
		ApprovingRecord record = new ApprovingRecord();

		record.setId(TEST_STR);
		record.setCode(TEST_STR);
		record.setEmail(TEST_STR);
		record.setType(ApproveAction.ADD_EMAIL);
		record.setExpiresTime(Long.MAX_VALUE);

		Assertions.assertEquals(TEST_STR, record.getId());
		Assertions.assertEquals(TEST_STR, record.getEmail());
		Assertions.assertEquals(TEST_STR, record.getCode());
		Assertions.assertEquals(ApproveAction.ADD_EMAIL, record.getType());
		Assertions.assertEquals(Long.MAX_VALUE, record.getExpiresTime());
	}

	@Test
	public void testCheckFreshness() {
		ApprovingRecord record = new ApprovingRecord(TEST_STR, MessageAction.EMAIL_APPROVE_MAIL);
		Assertions.assertTrue(record.checkFreshness());
	}

}
