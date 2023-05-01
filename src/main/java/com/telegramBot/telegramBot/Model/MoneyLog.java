package com.telegramBot.telegramBot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "money_logs")
@Getter
@Setter
public class MoneyLog {
    @Id
    @Column(name="log_id")
    private Integer id;

    @Column(name = "name",nullable = false)
    private String nickName;
    @Column(name = "from_name",nullable = false)
    private String moneyFrom;
    private int money;
    private String reason;
    private String date;
    @Override
    public String toString() {
        return "Гравець "+nickName+ " отримав кошти від "+moneyFrom+" у розмірі "+money+"$"+" з причини "+"\""+reason+"\"";
    }
}
