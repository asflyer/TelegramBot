package bot;

import java.io.*;
import java.net.*;
import java.io.IOException;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

    public void TimeMessage(int date){
        if (date == 300){
            SendMessage s = new SendMessage();
            s.setChatId("-1001389593568"); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
            s.setText("321321");
            try { //Чтобы не крашнулась программа при вылете Exception
                execute(s);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage(); // Это нам понадобится
        if (msg == null) {
            msg = e.getChannelPost(); // Это нам понадобится
        }
        String txt = msg.getText();

        if (txt.toLowerCase().equals("а?")) {
            int days;
            String ending;
            String starting;

            DaysToNG daysToNG = new DaysToNG().invoke();
            days = daysToNG.getDays();
            ending = daysToNG.getEnding();
            starting = daysToNG.getStarting();
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

        if (txt.toLowerCase().equals("погода?")) {
            String appid = "47fc3e35fc3b29d2170de3f83983baec";
            int city_id = 536203;
            //Найти id города
            //http://api.openweathermap.org/data/2.5/find?q=Saint Petersburg&type=like&APPID=0fb9dfa963c57d2e6a79ec6d11e1ba53

            HttpGet request = null;
            try {
                    String url = "http://api.openweathermap.org/data/2.5/weather?id=" + city_id + "&appid=" + appid + "&lang=ru&units=metric";
                    HttpClient client = HttpClientBuilder.create().build();
                    request = new HttpGet(url);

                    request.addHeader("User-Agent", "Apache HTTPClient");
                    HttpResponse response = null;
                    try {
                        response = client.execute(request);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    assert response != null;
                    HttpEntity entity = response.getEntity();
                    String content = null;
                    try {
                        content = EntityUtils.toString(entity);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        }

                    Gson g = new Gson();
                    Weather.JsonParser weatherOnStreet  = g.fromJson(content, Weather.JsonParser.class);
                    String weatherDescription = weatherOnStreet.weather.get(0).description;
                    int i = weatherOnStreet.weather.size();
                    if (i>1) {
                        while (i > 1) {
                            weatherDescription = weatherDescription + " или " + weatherOnStreet.weather.get(i).description;
                            i--;
                        }
                    }

                sendMsg(msg, "Погода в городе " + weatherOnStreet.name + "\nТемпература " +  weatherOnStreet.main.temp + "°C"
                + "\nЧувствуется как " + weatherOnStreet.main.feels_like + "°C" + "\nВлажность " + weatherOnStreet.main.humidity + "%"
                        + "\nСкорость ветра " + weatherOnStreet.wind.speed +"м/c" + "\nНа улице " + weatherDescription);

            }
                finally {
                if (request != null) {
                    request.releaseConnection();
                }
            }

        }

    }


    public String getBotUsername() {
        return "2021Bot";
    }

    public String getBotToken() {
        return "1464941016:AAHixCrgM0gWZGIMn0652xGngXp3BkadQIQ";
    }


    static class DaysToNG {
        private int days;
        private String ending;
        private String starting;

        int getDays() {
            return days;
        }

        String getEnding() {
            return ending;
        }

        String getStarting() {
            return starting;
        }

        DaysToNG invoke() {
            DateTime date1 = new DateTime("2020-12-31T23:59:59.999");
            ReadableInstant date2 = DateTime.now();
            days = Days.daysBetween(date2, date1).getDays();
            ending = " дней";
            starting = "осталось ";
            if (String.valueOf(days).endsWith("1")){
                ending = " день";
                starting = "остался ";
            }else if (String.valueOf(days).endsWith("2")||String.valueOf(days).endsWith("3")||String.valueOf(days).endsWith("4")){
                ending = " дня";
            }
            return this;
        }
    }
}

