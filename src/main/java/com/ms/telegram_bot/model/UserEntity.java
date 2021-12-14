package com.ms.telegram_bot.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> sentMessages;

    @ManyToMany(mappedBy = "recipients")
    private List<MessageEntity> receivedMessages;

    @Column(name = "last_message_at")
    private LocalDateTime lastSentMessageAt;
}
