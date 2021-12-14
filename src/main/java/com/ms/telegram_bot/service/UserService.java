package com.ms.telegram_bot.service;

import com.ms.telegram_bot.model.UserEntity;
import com.ms.telegram_bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public void update(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> getById(long id){
        return userRepository.findByUserId(id);
    }

    public Optional<UserEntity> getByChatId(long chatId){
        return userRepository.findByChatId(chatId);
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

}
