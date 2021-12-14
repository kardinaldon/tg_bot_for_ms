package com.ms.telegram_bot.repository;

import com.ms.telegram_bot.model.DailyDomainDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDomainDataRepository extends JpaRepository<DailyDomainDataEntity, String> {
}
