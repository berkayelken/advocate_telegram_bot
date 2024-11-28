package io.github.berkayelken.advocate.telegram.bot.message;

import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
public class ReplyKeyboardBuilder {

	public SendMessage buildMessage(Long chatId, StaticBotMessage botMessage, String... outputItems) {
		return buildMessage(chatId, botMessage, false, outputItems);
	}

	public SendMessage buildMessage(Long chatId, StaticBotMessage botMessage, boolean fail, String... outputItems) {
		SendMessage sendMessage = new SendMessage();

		sendMessage.setChatId(chatId);
		if (!CollectionUtils.isEmpty(botMessage.getFixedKeyboard())) {
			sendMessage.setReplyMarkup(buildMarkup(List.of(botMessage.getFixedKeyboard())));
		}

		if (outputItems != null) {
			sendMessage.setText(String.format(botMessage.findFeasibleMessage(fail), (Object[]) outputItems));
		} else {
			sendMessage.setText(botMessage.findFeasibleMessage(fail));
		}

		sendMessage.setParseMode("Markdown");

		return sendMessage;
	}

	public SendMessage buildMessage(Long chatId, String text) {
		SendMessage sendMessage = new SendMessage();

		sendMessage.setChatId(chatId);
		sendMessage.setText(text);
		sendMessage.setParseMode("Markdown");

		return sendMessage;
	}

	public ReplyKeyboardMarkup buildMarkup(List<List<String>> rows) {
		ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

		List<KeyboardRow> keyboardRows = rows.stream().map(ReplyKeyboardBuilder::convertRow).toList();
		replyKeyboard.setKeyboard(keyboardRows);
		replyKeyboard.setResizeKeyboard(true);
		replyKeyboard.setOneTimeKeyboard(true);

		return replyKeyboard;
	}

	private static KeyboardRow convertRow(List<String> row) {
		KeyboardRow keyboardRow = new KeyboardRow();

		keyboardRow.addAll(row);

		return keyboardRow;
	}
}
