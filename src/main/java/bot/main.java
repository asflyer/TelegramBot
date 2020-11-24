package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        String time = "T00:40:00.000"; //Время отправки ежедневного сообщения
        String chat_id = "-1001436821018"; //Новый 2021 = -1001436821018, Тестовый = -1001389593568
        new EveryDayMessage().EveryDayMessageSend(time, chat_id);

    /*
    heroku login
    git push heroku master
    heroku ps:scale worker=1
     */

    }
}
