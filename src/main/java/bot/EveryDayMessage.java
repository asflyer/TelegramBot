package bot;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

class EveryDayMessage {
    void EveryDayMessageSend(String time, String chat_id) throws InterruptedException, IOException {
        int MinuteNow;
        DateTime date1 = new DateTime(time);
        int date2 = date1.getMinuteOfDay();
        while (true)
        {
            MinuteNow = DateTime.now().getMinuteOfDay();
            if (MinuteNow == date2)
            {
                Bot.DaysToNG daysToNG = new Bot.DaysToNG().invoke();
                int days = daysToNG.getDays();
                String ending = daysToNG.getEnding();
                String starting = daysToNG.getStarting();
                String message = "Доброго времени суток, робяты!\nДо Нового Года " + starting + days + ending;
                URI builder = UriBuilder
                        .fromUri("https://api.telegram.org")
                        .path("/{token}/sendMessage")
                        .queryParam("chat_id", chat_id)
                        .queryParam("text", message)
                        .build("bot" + "1464941016:AAHixCrgM0gWZGIMn0652xGngXp3BkadQIQ");
                HttpGet request = new HttpGet(String.valueOf(builder));
                HttpClient client = HttpClientBuilder.create().build();
                client.execute(request);
                //HttpResponse response = client.execute(request);
                //response.getStatusLine().getStatusCode()
                Thread.sleep(65000); // Ждём чуть больше минуты чтобы не отправилось повторно
            }
            else if (MinuteNow > date2) // Ждем до даты следующего дня минус две минуты
            {
                int sleep = (1438 - MinuteNow + date2)*60000;
                Thread.sleep(sleep);
            }
            else if (MinuteNow == (date2 - 1)) // Если осталась одна минута, ждём по 10 секунд
            {
                Thread.sleep(10000);
            }
            else { // Ждем пока не останется одна минута
                int sleep = (date2 - MinuteNow - 1) * 60000;
                Thread.sleep(sleep);
            }
        }
    }
}
