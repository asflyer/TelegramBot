package bot;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.logging.Level;

import static org.telegram.telegrambots.logging.BotLogger.log;

public class Bot extends TelegramLongPollingBot
{


	public void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
		s.setText(text);
		try { //Чтобы не крашнулась программа при вылете Exception
            execute(s);
		} catch (TelegramApiException e){
			e.printStackTrace();
		}
	}

    public void onUpdateReceived(Update e) {

        DateTime date1 = new DateTime("2020-12-31T23:59:55.618-08:00");
        ReadableInstant date2 = DateTime.now();
        int days = Days.daysBetween(date2, date1).getDays();

        Message msg = e.getMessage(); // Это нам понадобится
        if (msg == null) {
            msg = e.getChannelPost(); // Это нам понадобится
        }
        String txt = msg.getText();

        if (txt.equals("а?")||txt.equals("А?")) {
            sendMsg(msg, "До Нового Года осталось " + days + " дня!!!");
        }
        if (txt.equals("у?")||txt.equals("У?")) {
                sendMsg(msg, "Санёк - молодец!");
        }

    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */


    public String getBotUsername() {
        return "2021Bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    public String getBotToken() {
        return "1464941016:AAHixCrgM0gWZGIMn0652xGngXp3BkadQIQ";
    }
}
