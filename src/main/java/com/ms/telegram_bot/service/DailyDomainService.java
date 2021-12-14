package com.ms.telegram_bot.service;

import com.ms.telegram_bot.model.DailyDomainDataEntity;
import com.ms.telegram_bot.repository.DailyDomainDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DailyDomainService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyDomainService.class);

    @Value(value = "${api.daily_domain.url}")
    private String apiHost;

    @Autowired
    private DailyDomainDataRepository dailyDomainDataRepository;

    private RestTemplate restTemplate;
    private ResponseEntity<DailyDomainDataEntity[]> response;
    private DailyDomainDataEntity[] dailyDomainDataEntityArr;

    public DailyDomainDataEntity[] getDataFromDailyDomain(){
        restTemplate = new RestTemplate();
        response = restTemplate.exchange(apiHost, HttpMethod.GET, null,
                    DailyDomainDataEntity[].class);
        if(response.hasBody()){
            return response.getBody();
        }
        else{
            LOG.error("response received from " + apiHost + " contains no data");
            return new DailyDomainDataEntity[0];
        }
    }

    public int saveAllInDB(DailyDomainDataEntity[] data){
        if(data.length > 0){
            if (data.length >= 50){
                for (int i = 0; i < 50; i++) {
                    dailyDomainDataRepository.save(data[i]);
                }
            }
            else {
                for (int i = 0; i < data.length; i++) {
                    dailyDomainDataRepository.save(data[i]);
                }
            }

        }
        else {
            LOG.error("data array is empty, saving to the table is canceled");
        }
        return data.length;
    }

    public void deleteAllDataFromTable(){
        dailyDomainDataRepository.deleteAll();
    }

    public List<DailyDomainDataEntity> getAll(){
        return dailyDomainDataRepository.findAll();
    }

}
