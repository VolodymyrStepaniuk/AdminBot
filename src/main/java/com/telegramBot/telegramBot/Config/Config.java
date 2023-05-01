package com.telegramBot.telegramBot.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = "application.properties")
public class Config {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
}
