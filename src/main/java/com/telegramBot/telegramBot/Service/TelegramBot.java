package com.telegramBot.telegramBot.Service;

import com.telegramBot.telegramBot.Config.Config;
import com.telegramBot.telegramBot.Config.ConfigureOtherThings;
import com.telegramBot.telegramBot.Model.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final Config config;
    final ConfigureOtherThings configure = new ConfigureOtherThings();
    private final String HELP_MESSAGE = """
                        /start - команда для запуску або перезапуску бота
                        /help - команда для відображення списку команд
                        /get_admin - команда для виведення інформації про адміністратора
                        /get_player - команда для виведення інформації про гравця
                        /money_log - команда для отримання грошових логів
                        /business - команда для виведення інформації про бізнес
                        /nickname_changes - команда для виведення інформації про зміну нікнейму""";
    private final List<BotUserDTO> allUsers = new ArrayList<>();
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MoneyLogRepository moneyLogRepository;
    @Autowired
    private BusinessesRepository businessesRepository;
    @Autowired
    private ChangeNickNameRepository changeNickNameRepository;

    public TelegramBot(Config config) {
        this.config = config;
        try{
            execute(new SetMyCommands(configure.getBotCommandList(),new BotCommandScopeDefault(),null));
        }catch(TelegramApiException e){
            log.error("Error occurred: "+e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            long chatID = update.getMessage().getChatId();
            reactOnCommand(chatID,update.getMessage());
           }
    }

    private void startCommandReceived(long chatID,String userFirstName){
        String answer = EmojiParser.parseToUnicode("Привіт, "+userFirstName+". Приємно познайомитись!"+":blush:");
        sendMessage(chatID,answer);
    }
    public void sendMessage(long chatID,String textToSend){
        SendMessage message = new SendMessage(String.valueOf(chatID),textToSend);

        try{
            execute(message);
        }
        catch(TelegramApiException e){
            log.error("Error occurred: "+e.getMessage());
        }
        log.info("Message was sent to the user");
    }
    private void reactOnCommand(long chatID,Message message){
        BotUserDTO userDTO = checkAuthUsers(chatID);

        boolean isAuth = userDTO != null;

        if(isAuth&&(int)ChronoUnit.MINUTES.between(userDTO.getAuthTime(), LocalTime.now())>=1){
            isAuth = false;
            allUsers.remove(userDTO);
        }

        String messageText = message.getText();
        String nickName = messageText.substring(messageText.indexOf(" ")+1).trim();
        String secondData = "";

        if(!nickName.equals(messageText) && nickName.contains(" ")){
            secondData = nickName.substring(nickName.indexOf(" ")).strip();
            nickName = nickName.substring(0,nickName.indexOf(" "));
        }

        if(messageText.contains("/alogin"))
            getAloginCommandReceived(chatID,nickName,secondData);
        else if (messageText.contains("/get_admin")&&isAuth)
            getAdminCommandReceived(chatID, nickName);
        else if (messageText.contains("/get_player")&&isAuth)
            getPlayerCommandReceived(chatID, nickName);
        else if (messageText.contains("/money_log")&&isAuth)
            getMoneyLogCommandReceived(chatID, nickName, secondData);
        else if (messageText.contains("/business")&&isAuth)
            getBusinessCommandReceived(chatID, nickName);
        else if (messageText.contains("/nickname_changes")&&isAuth)
            getNickNameChangesCommandReceived(chatID, nickName);
        else if (!isAuth)
            sendMessage(chatID,"Ви не авторизовані!");
        else {
            switch (messageText) {
                case "/start" -> startCommandReceived(chatID, message.getChat().getFirstName());
                case "/help" -> sendMessage(chatID, HELP_MESSAGE);
                default -> sendMessage(chatID, EmojiParser.parseToUnicode("Вибачте, але я не знаю, що це означає " + ":pensive:"));
            }
        }
    }

    private BotUserDTO checkAuthUsers(long chatID) {
        BotUserDTO botUser = null;
        for (BotUserDTO authorizedUser : allUsers) {
            if (authorizedUser.getId().equals(chatID)) {
                botUser = authorizedUser;
                break;
            }
        }
        return botUser;
    }

    private void getAloginCommandReceived(long chatID, String nickName, String pass) {
        BotUserDTO auth = checkAuthUsers(chatID);

        if(auth!=null)
            sendMessage(chatID, "Ви вже авторизовані!");
        else{
            var data = adminRepository.findAdminLevelByNickNameAndPassword(nickName,pass);
            if (data != null) {
                BotUserDTO user = new BotUserDTO(chatID,nickName,data, LocalTime.now());
                allUsers.add(user);
                sendMessage(chatID,"Вітаю, "+user.getNickName()+"!\nРівень адмін-прав: "+user.getAdminLevel());
            }else
                sendMessage(chatID,"Такого аккаунту не існує!");
        }
    }

    private void getNickNameChangesCommandReceived(long chatID, String nickName) {
        StringBuilder builder = new StringBuilder();
        var nickNameList = changeNickNameRepository.findAllByNickName(nickName);

        for (var nickNameInList:
                    nickNameList) {
            builder.append(nickNameInList).append("\n-------------------\n");
        }

        if(builder.isEmpty())
            sendMessage(chatID,"Зміни нікнейму не було знайдено або нікнейм було введено некоректно!");
        else
            sendMessage(chatID,builder.toString());
    }

    private void getAdminCommandReceived(long chatID,String nickName){
        try{
            var admin = adminRepository.findAdminByNickName(nickName);
            sendMessage(chatID,admin.toString());
        }
        catch (NullPointerException e){
            log.error("Error occurred: "+e.getMessage());
            sendMessage(chatID,"Такого нікнейму не було знайдено!");
        }
    }
    private void getPlayerCommandReceived(long chatID, String nickName) {
        try{
            var player = playerRepository.findPlayerByNickName(nickName);
            String organization = playerRepository.findOrgNameByNickName(nickName);

            if (organization == null)
                organization = "Відсутня";

            player.setOrgName(organization);
            sendMessage(chatID,player.toString());
        }
        catch (NullPointerException e){
            log.error("Error occurred: "+e.getMessage());
            sendMessage(chatID,"Такого нікнейму не було знайдено!");
        }
    }
    private void getBusinessCommandReceived(long chatID, String id) {
        try{
            var business = businessesRepository.findById(Integer.parseInt(id));
            sendMessage(chatID,business.toString());
        }
        catch (NullPointerException e){
            log.error("Error occurred: "+e.getMessage());
            sendMessage(chatID,"Такого бізнесу не було знайдено!");
        }
    }

    private void getMoneyLogCommandReceived(long chatID, String nickName,String date) {
        StringBuilder logs = new StringBuilder();
        List<MoneyLog> moneyLogs = moneyLogRepository.findAllByNickNameAndDateLike(nickName+"%",date+"%");

        moneyLogs.forEach(log->logs.append(log.toString()).append("\n").append("--------------------\n"));

        if(moneyLogs.isEmpty())
            sendMessage(chatID,"Логи за цю дату відсутні або такий нікнейм не існує!");
        else
            sendMessage(chatID,logs.toString());
    }
}
