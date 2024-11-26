package io.github.berkayelken.advocate.telegram.bot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StandaloneCustomerQuestionTest {
	private static final String TEST_STR = "TEST";
	private static final long DEFAULT_LONG = 0L;

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(StandaloneCustomerQuestion::new);
	}

	@Test
	public void testAccessors() {
		StandaloneCustomerQuestion question = new StandaloneCustomerQuestion();

		Assertions.assertNull(question.getId());
		Assertions.assertNull(question.getEmail());
		Assertions.assertNull(question.getPhone());

		Assertions.assertNull(question.getQuestionId());
		Assertions.assertNull(question.getQuestion());
		Assertions.assertNull(question.getAbstractQuestion());
		Assertions.assertNull(question.getCategory());
		Assertions.assertNull(question.getIssueType());
		Assertions.assertNull(question.getAnswer());
		Assertions.assertNull(question.getAbstractAnswer());
		Assertions.assertNull(question.getLawyerAnswer());
		Assertions.assertFalse(question.isSeenByCustomer());
		Assertions.assertFalse(question.isAskedToAI());
		Assertions.assertEquals(DEFAULT_LONG, question.getAskingTime());
	}

	@Test
	public void testMutators() {
		StandaloneCustomerQuestion question = getStandaloneCustomerQuestion();

		Assertions.assertEquals(TEST_STR, question.getId());
		Assertions.assertEquals(TEST_STR, question.getEmail());
		Assertions.assertEquals(TEST_STR, question.getPhone());

		Assertions.assertEquals(TEST_STR, question.getQuestionId());
		Assertions.assertEquals(TEST_STR, question.getQuestion());
		Assertions.assertEquals(TEST_STR, question.getAbstractQuestion());
		Assertions.assertEquals(TEST_STR, question.getCategory());
		Assertions.assertEquals(TEST_STR, question.getIssueType());
		Assertions.assertEquals(TEST_STR, question.getAnswer());
		Assertions.assertEquals(TEST_STR, question.getAbstractAnswer());
		Assertions.assertEquals(TEST_STR, question.getLawyerAnswer());
		Assertions.assertTrue(question.isSeenByCustomer());
		Assertions.assertTrue(question.isAskedToAI());
		Assertions.assertEquals(Long.MAX_VALUE, question.getAskingTime());
	}

	private StandaloneCustomerQuestion getStandaloneCustomerQuestion() {
		StandaloneCustomerQuestion question = new StandaloneCustomerQuestion();

		question.setId(TEST_STR);
		question.setEmail(TEST_STR);
		question.setPhone(TEST_STR);

		question.setQuestionId(TEST_STR);
		question.setQuestion(TEST_STR);
		question.setAbstractQuestion(TEST_STR);
		question.setCategory(TEST_STR);
		question.setIssueType(TEST_STR);
		question.setAnswer(TEST_STR);
		question.setAbstractAnswer(TEST_STR);
		question.setLawyerAnswer(TEST_STR);
		question.setSeenByCustomer(true);
		question.setAskedToAI(true);
		question.setAskingTime(Long.MAX_VALUE);

		return question;
	}
}
