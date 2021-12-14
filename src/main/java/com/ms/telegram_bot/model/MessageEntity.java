package com.ms.telegram_bot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "messages")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long messageId;

    @Column(name = "message_body")
    private String messageBody;

    @ManyToOne
    private UserEntity sender;

    @ManyToMany
    @JoinTable(
            name = "message_recipients",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> recipients;

}
