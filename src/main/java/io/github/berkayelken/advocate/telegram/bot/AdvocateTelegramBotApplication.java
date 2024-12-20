package io.github.berkayelken.advocate.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class AdvocateTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvocateTelegramBotApplication.class, args);
	}

}
