package com.telegramBot.telegramBot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "businesses")
@Getter
@Setter
public class Business {
    @Id
    @Column(name="b_id")
    private Integer id;
    @Column(name = "b_owner_name",nullable = false)
    private String nickName;
    @Column(name = "b_name",nullable = false)
    private String businessName;
    @Column(name = "b_money",nullable = false)
    private int moneyInTheBusiness;
    @Column(name = "b_price",nullable = false)
    private int businessPrice;
    @Column(name = "b_product",nullable = false)
    private int businessProducts;

    @Override
    public String toString() {
        return "Власник: " + nickName +
                "\nНазва бізнесу: " + businessName +
                "\nГотівки на рахунку бізнесу: " + moneyInTheBusiness +"$"+
                "\nДерж.ціна: " + businessPrice +"$"+
                "\nК-сть продуктів: " + businessProducts;
    }
}
