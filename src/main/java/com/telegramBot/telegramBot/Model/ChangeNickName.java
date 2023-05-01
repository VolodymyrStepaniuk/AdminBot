package com.telegramBot.telegramBot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "nickname_history")
@Getter
@Setter
public class ChangeNickName {
    @Id
    @Column(name="nh_id")
    private Integer id;
    @Column(name = "nh_oldname",nullable = false)
    private String oldNickName;
    @Column(name = "nh_newname",nullable = false)
    private String newNickName;
    @Column(name = "nh_date",nullable = false)
    private String changeDate;
    @Column(name = "nh_admin",nullable = false)
    private String methodChange;

    @Override
    public String toString() {
        return "Старий нікнейм: " + oldNickName +
                "\nНовий нікнейм: " + newNickName +
                "\nДата зміни: " + changeDate +
                "\nСпосіб зміни: " + methodChange;
    }
}
