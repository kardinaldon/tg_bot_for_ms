package com.ms.telegram_bot.component;

import com.ms.telegram_bot.model.MessageEntity;
import com.ms.telegram_bot.model.UserEntity;
import com.ms.telegram_bot.service.DailyDomainService;
import com.ms.telegram_bot.service.MessageService;
import com.ms.telegram_bot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DailyDomainService dailyDomainService;

    @Autowired
    private UserService userService;

    @Autowired
    private TelegramBot tgBot;

    private String messageForRecipients;
    private MessageEntity messageForSaveInDB;
    private List<UserEntity> users;
    private List<UserEntity> subscribers;

    @Scheduled(cron = "0 0 14 * * *")
    public void getLinksAndSendSubscribers() {

        dailyDomainService.deleteAllDataFromTable();
        //получить ссылки, сохранить в таблицу "daily_domains", получить кол-во ссылок
        int numberOfLinksToSites = dailyDomainService.saveAllInDB(
                    dailyDomainService.getDataFromDailyDomain());
        messageForRecipients = DateTimeFormatter.ofPattern("YYY-MM-dd").format(LocalDateTime.now())
                + " Собрано " + numberOfLinksToSites + " доменов";

        //получить подписчиков
        users = userService.getAll();
        if(users.size()>1){
            subscribers = new ArrayList<>();
            for(UserEntity userEntity : users){
                //не отправлять самому себе
                if(!userEntity.getName().equals("BOT")) {
                    //отправляем сообщение
                    tgBot.sendMessage(userEntity.getChatId(), messageForRecipients);
                    subscribers.add(userEntity);
                }
            }
        }

        //сохраняем в базу отправленное пользователям сообщение
        messageForSaveInDB = new MessageEntity();
        messageForSaveInDB.setRecipients(subscribers);
        messageForSaveInDB.setMessageBody(messageForRecipients);
        messageForSaveInDB.setSender(userService.getById(1).get());
        messageService.saveMessage(messageForSaveInDB);
        users.clear();
        subscribers.clear();
    }
}
