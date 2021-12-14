package com.ms.telegram_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TelegramBotApplication {

	private static final Logger LOG = LoggerFactory.getLogger(TelegramBotApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class);
	}
}
