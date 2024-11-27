package io.github.berkayelken.advocate.telegram.bot.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
	private final String message = "Kullan\u0131c\u0131 kayd\u0131n\u0131z bulunamad\u0131. L\u00fctfen /start yazarak KVKK onay\u0131 ve kay\u0131t i\u015flemlerinizi yap\u0131n\u0131z.";
}
