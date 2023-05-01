package com.telegramBot.telegramBot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BotUserDTO {
    private Long id;
    private String nickName;
    private byte adminLevel;
    private LocalTime authTime;
}
