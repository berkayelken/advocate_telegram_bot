package io.github.berkayelken.advocate.telegram.bot.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdGenerator {
	private static final String SHA_1 = "SHA1PRNG";
	private static final int NUMBER_COUNT = 10;

	public static String getDigitId(int pinSize) {
		StringBuilder builder = new StringBuilder();
		Stream.generate(IdGenerator::createDigit).limit(pinSize).forEachOrdered(builder::append);
		return builder.toString();
	}

	private static int createDigit() {
		Random random = createRandom();
		return random.nextInt(NUMBER_COUNT);
	}

	private static Random createRandom() {
		try {
			return SecureRandom.getInstance(SHA_1);
		} catch (NoSuchAlgorithmException e) {
			return new SecureRandom();
		}
	}

}
