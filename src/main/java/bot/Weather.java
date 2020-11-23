package bot;

import java.util.List;

class Weather
{
    public static class JsonParser {
        List<WeatherParser> weather;
        WeatherParser2 main;
        public String base;
        String name;
        WeatherParser3 wind;
    }

    public static class WeatherParser { //блок weather
        public int id;
        public String main;
        String description;
    }

    public static class WeatherParser2 { //блок main json
        String temp;
        String feels_like;
        String humidity;
    }

    public static class WeatherParser3 { //блок wind json
        String speed;
        public String gust;
    }
}
