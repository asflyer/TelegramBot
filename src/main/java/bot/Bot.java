package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;



public class Bot extends TelegramLongPollingBot
{
	private void sendMsg(Message msg, String text) {
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
        Message msg = e.getMessage(); // Это нам понадобится
        if (msg == null) {
            msg = e.getChannelPost(); // Это нам понадобится
        }
        String txt = msg.getText();

        if (txt.toLowerCase().equals("а?")) {
            DateTime date1 = new DateTime("2020-12-31T23:59:59.999");
            ReadableInstant date2 = DateTime.now();
            int days = Days.daysBetween(date2, date1).getDays();
            String ending = " дней";
            String starting = "осталось ";
            if (String.valueOf(days).endsWith("1")){
                ending = " день";
                starting = "остался ";
            }else if (String.valueOf(days).endsWith("2")||String.valueOf(days).endsWith("3")||String.valueOf(days).endsWith("4")){
                ending = " дня";
            }
            sendMsg(msg, "До Нового Года " + starting + days +  ending);
        }

        if (txt.toLowerCase().equals("у?")) {
                sendMsg(msg, "Санёк - молодец!");
        }

        if (txt.toLowerCase().equals("анекдот?")) {
            String text="";
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URL("http://rzhunemogu.ru/Rand.aspx?CType=1").openStream(), "CP1251")))
            {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                text = sb.toString().substring(sb.toString().indexOf("<content>")+9, sb.toString().lastIndexOf("</content"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            sendMsg(msg, text);
        }

    }


    public String getBotUsername() {
        return "2021Bot";
    }

    public String getBotToken() {
        return "1464941016:AAHixCrgM0gWZGIMn0652xGngXp3BkadQIQ";
    }
}
