package com.ms.telegram_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

//data model for getting information from https://backorder.ru/json/?order=desc&ext=1&disp=1&expired=1&by=yandex_tic&page=1&items=50

@Entity
@Table(name = "daily_domains")
@Data
public class DailyDomainDataEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "domain_name")
    @JsonProperty("domainname")
    private String domainName;

    @Column(name = "hotness")
    @JsonProperty("hotness")
    private long hotness;

    @Column(name = "price")
    @JsonProperty("price")
    private BigDecimal price;

    @Column(name = "x_value")
    @JsonProperty("x_value")
    private long xValue;

    @Column(name = "yandex_tic")
    @JsonProperty("yandexTic")
    private long yandex_tic;

    @Column(name = "links")
    @JsonProperty("links")
    private long links;

    @Column(name = "visitors")
    @JsonProperty("visitors")
    private long visitors;

    @Column(name = "registrar")
    @JsonProperty("registrar")
    private String registrar;

    @Column(name = "old")
    @JsonProperty("old")
    private int old;

    @Column(name = "delete_date")
    @JsonProperty("delete_date")
    private LocalDate deleteDate;

    @Column(name = "rkn")
    @JsonProperty("rkn")
    private boolean rkn;

    @Column(name = "judicial")
    @JsonProperty("judicial")
    private boolean judicial;

    @Column(name = "block")
    @JsonProperty("block")
    private boolean block;

}
