package com.ms.telegram_bot.component;

import com.ms.telegram_bot.model.MessageEntity;
import com.ms.telegram_bot.model.UserEntity;
import com.ms.telegram_bot.service.MessageService;
import com.ms.telegram_bot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    private UserEntity userEntity;
    private Message message;
    private MessageEntity messageEntity;
    private List<UserEntity> recipients;
    private Optional<Message> messageOpt;


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        message = update.getMessage();
        Long senderId = message.getFrom().getId();
        Optional<UserEntity> senderById = userService.getById(senderId);
        if(senderById.isPresent()){
            userEntity = senderById.get();
            userEntity.setLastSentMessageAt(LocalDateTime.now());
            userService.update(userEntity);
        }
        else {
            userEntity = new UserEntity();
            userEntity.setLastSentMessageAt(LocalDateTime.now());
            userEntity.setUserId(message.getFrom().getId());
            userEntity.setName(message.getFrom().getFirstName());
            userEntity.setUserName(message.getFrom().getUserName());
            userEntity.setChatId(message.getChatId());
            userService.create(userEntity);
        }
        //сохраняем полученное сообщение
        {
            messageEntity = new MessageEntity();
            messageEntity.setMessageBody(message.getText());
            messageEntity.setSender(userEntity);
            recipients = new ArrayList<>();
            recipients.add(userEntity);
            messageEntity.setRecipients(recipients);
            messageService.saveMessage(messageEntity);
        }

        //отправляем и сохраняем в базу ответ бота
        {
            messageOpt = sendMessage(message.getChatId()
                    , "Your message has been received");
            if(messageOpt.isPresent()){
                message = messageOpt.get();
                messageEntity = new MessageEntity();
                messageEntity.setMessageBody(message.getText());
                messageEntity.setSender(userService.getById(1).get());
                recipients = new ArrayList<>();
                recipients.add(userService.getByChatId(message.getChatId()).get());
                messageEntity.setRecipients(recipients);
                messageService.saveMessage(messageEntity);

            }
        }
    }

    public synchronized Optional<Message> sendMessage(long chatId, String messageBody) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(messageBody);
        Optional<Message> response;
        try {
            message = execute(sendMessage);
            return Optional.of(message);
        } catch (TelegramApiException e) {
            LOG.error("Telegram Exception ", e);
            return Optional.empty();
        }
    }

}
