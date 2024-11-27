package io.github.berkayelken.advocate.telegram.bot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApprovingRecordTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(ApprovingRecord::new);
	}

	@Test
	public void testAccessors() {
		ApprovingRecord record = new ApprovingRecord();
		Assertions.assertNull(record.getId());
		Assertions.assertNull(record.getEmail());
		Assertions.assertNull(record.getCode());
		Assertions.assertNull(record.getType());
	}

	@Test
	public void testMutators() {
		ApprovingRecord record = new ApprovingRecord();

		record.setId(TEST_STR);
		record.setCode(TEST_STR);
		record.setEmail(TEST_STR);
		record.setType(ApproveAction.ADD_EMAIL);

		Assertions.assertEquals(TEST_STR, record.getId());
		Assertions.assertEquals(TEST_STR, record.getEmail());
		Assertions.assertEquals(TEST_STR, record.getCode());
		Assertions.assertEquals(ApproveAction.ADD_EMAIL, record.getType());
	}


}
