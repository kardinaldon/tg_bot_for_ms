package com.ms.telegram_bot.service;

import com.ms.telegram_bot.component.TelegramBot;
import com.ms.telegram_bot.model.MessageEntity;
import com.ms.telegram_bot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TelegramBot telegramBot;

    private MessageEntity messageEntity;

    public void saveMessage(MessageEntity messageEntity){
        messageRepository.save(messageEntity);
    }

}
