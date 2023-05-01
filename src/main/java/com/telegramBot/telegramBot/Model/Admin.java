package com.telegramBot.telegramBot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users_admins")
@Getter
@Setter
public class Admin {
    @Id
    @Column(name="u_a_id")
    private Integer id;
    @Column(name = "u_a_name",nullable = false)
    private String nickName;
    @Column(name = "u_a_level",nullable = false)
    private byte adminLevel;
    @Column(name = "u_a_reports",nullable = false)
    private int countOfReports;

    @Override
    public String toString() {
        return "Нікнейм адміністратора: " + nickName
                +"\nАдмін рівень: "+adminLevel
                +"\nКількість репортів: "+countOfReports;
    }
}
