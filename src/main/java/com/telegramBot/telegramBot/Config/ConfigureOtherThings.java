package com.telegramBot.telegramBot.Config;

import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConfigureOtherThings {
    private final List<BotCommand> botCommandList = new ArrayList<>();
    public ConfigureOtherThings() {
        botCommandsInit();
    }

    private void botCommandsInit(){
        botCommandList.add(new BotCommand("/start","param: empty"));
        botCommandList.add(new BotCommand("/help","param: empty"));
        botCommandList.add(new BotCommand("/get_admin","param: nickname"));
        botCommandList.add(new BotCommand("/get_player","param: nickname"));
        botCommandList.add(new BotCommand("/money_log","param: nickname date"));
        botCommandList.add(new BotCommand("/business","param: id of business"));
        botCommandList.add(new BotCommand("/nickname_changes","param: nickname"));
    }
}
