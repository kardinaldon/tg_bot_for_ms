package com.ms.telegram_bot.repository;

import com.ms.telegram_bot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity>findByUserId(long userId);
    Optional<UserEntity>findByChatId(long chatId);

}
