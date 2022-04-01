import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/// Класс для получения прогноза погоды с парсинга
public class WeatherApp {
    /// Получаем страницу с погодой
    public static Document getPage() throws IOException {
        String url = "https://world-weather.ru/pogoda/russia/yekaterinburg/";
        return Jsoup.parse(new URL(url), 4000);
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        // Выбираем таблицу с погодой (индекс 1, так как мы хотим получить погоду на текущиий день)
        Element tableWithWeather = page.select("table[class=weather-today]").get(1);
        Elements names = tableWithWeather.select("tbody");
        int index = 0;
        // Получаем и задаем формат текущей даты
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yyyy");
        System.out.println("Прогноз погоды в Екатеринбурге на " + dateFormat.format(date));
        System.out.println( "Время     Температура  Ощущается   Вероятность    Давление   Скорость втр.  Влажность");

        // Получаем данные из таблицы с помощью метода getValues
        for (String valueOfData: getValues(names, index)) {
            String temp = valueOfData;
            // Задаем формат вывода данных (переноса строк)
            if (getValues(names, index).indexOf(temp) % 7 == 0 && getValues(names, index).indexOf(temp) != 0)
                System.out.println();
            System.out.print(temp + "\t\t\t");

        }
    }

    /// Метод для получения значений из таблицы
    private static ArrayList<String> getValues(Elements values, int index) {
        Element valueLine = values.get(index);
        ArrayList<String> data = new ArrayList<>();
        // Перебираем элементы таблицы и добавляем в список, если они с тегом "td"
        for (Element td: valueLine.select("td"))
            data.add(td.text());
        return data;
    }
}
