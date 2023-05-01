package com.telegramBot.telegramBot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "users")
@Getter
@Setter
public class Player {
    @Id
    @Column(name="u_id")
    private Integer id;
    @Column(name = "u_name",nullable = false)
    private String nickName;
    @Column(name = "u_level",nullable = false)
    private short level;
    @Column(name = "u_money",nullable = false)
    private int handsMoney;
    @Column(name = "u_bank",nullable = false)
    private int bankMoney;
    @Column(name = "u_donate",nullable = false)
    private int donateCount;
    @Column(name = "u_house",nullable = false)
    private short houseId;
    @Column(name = "u_business",nullable = false)
    private short businessId;
    private String orgName;
    @Column(name = "u_rank",nullable = false)
    private byte rankInOrg;
    @Column(name = "u_email",nullable = false)
    private String email;
    @Column(name = "u_ip_registration",nullable = false)
    private String regIP;
    @Column(name = "u_last_ip",nullable = false)
    private String lastIP;
    @Column(name = "u_last_date",nullable = false)
    private Date lastDate;
    @Column(name = "u_date_registration",nullable = false)
    private Date regDate;

    @Override
    public String toString() {
        return "Нікнейм: " + nickName +
                "\nРівень: " + level +" lvl"+
                "\nГотівки на руках: " + handsMoney + "$" +
                "\nГотівки у банку: "+bankMoney+"$"+
                "\nДонат валюти: "+donateCount+" грн"+
                "\nНомер будинку: "+houseId+
                "\nНомер бізнесу: "+businessId+
                "\nОрганізація: "+ orgName +
                "\nРанг в організації: "+rankInOrg+
                "\n\nЕлектронна адреса: " + email +
                "\nРеєстраційний IP: " + regIP +
                "\nДата реєстрації: " + regDate +
                "\nОстанній IP: " + lastIP +
                "\nДата останнього входу: " + lastDate;
    }
}
