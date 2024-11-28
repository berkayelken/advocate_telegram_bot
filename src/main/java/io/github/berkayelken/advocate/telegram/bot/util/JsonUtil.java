/*
 * Copyright (c) 2012-
 *  Vodafone Teknoloji Hizmetleri A.S., Istanbul, Turkey
 *
 *  All rights reserved. This Software or any portion of it can not be translated,
 *  distributed, sold, adapted, arranged, used, copied, modified, de-compiled,
 *  reverse assembled or otherwise reverse engineered, disassembled, replaced or made
 *  additions to and to be reproduced in whole or in part, in any way, manner or form.
 */

package io.github.berkayelken.advocate.telegram.bot.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class JsonUtil {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
		OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * Json to Object
	 *
	 * @param json - The parameter type String
	 * @return returns Object
	 */
	public static <T> T jsonToObject(String json, Class<? extends T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			log.error("[jsonToObject()] :: exception occurred: ", e);
			return null;
		}
	}
}

