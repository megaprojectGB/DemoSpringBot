package com.example.demospringbot.services;

import com.example.demospringbot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            SendMessage messageTo = new SendMessage();
            messageTo.setChatId(String.valueOf(chatId));
            messageTo.setText(text);
            try {
                execute(messageTo);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
