package WeatherFrame;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Die WeatherApi-Klasse bietet Methoden zum Abrufen von Wetterdaten von der OpenWeatherMap-API.
 */
public class WeatherApi {

    // API Key wurde ausgeblendet
    

    /**
     * Ruft Wetterdaten für die angegebene Stadt von der OpenWeatherMap-API ab.
     *
     * @param city Der Name der Stadt, für die Wetterdaten abgerufen werden sollen.
     * @return Ein JSONObject mit den Wetterdaten.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public JSONObject fetchData(String city) throws IOException {
        URL url = new URL(API_BASE_URL + "?q=" + city + "&appid=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Daten von der Verbindung lesen und in JSONObject umwandeln
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            return new JSONObject(stringBuilder.toString());
        } else {
            throw new IOException("Fehler beim Senden einer GET-Anforderung: " + responseCode);
        }
    }

    /**
     * Ruft die aktuelle Temperatur für die angegebene Stadt in Celsius ab.
     *
     * @param city Der Name der Stadt, für die die Temperatur abgerufen werden soll.
     * @return Die aktuelle Temperatur in Celsius.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public double getTemperature(String city) throws IOException {
        JSONObject jsonObject = fetchData(city);
        JSONObject mainObject = jsonObject.getJSONObject("main");
        double temperatureKelvin = mainObject.getDouble("temp");
        return kelvinToCelsius(temperatureKelvin);
    }

    /**
     * Ruft die minimale Temperatur für die angegebene Stadt in Celsius ab.
     *
     * @param city Der Name der Stadt, für die die minimale Temperatur abgerufen werden soll.
     * @return Die minimale Temperatur in Celsius.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public double getMinTemp(String city) throws IOException {
        double tempKelvinMin = fetchData(city).getJSONObject("main").getDouble("temp_min");
        return kelvinToCelsius(tempKelvinMin);
    }

    /**
     * Ruft die maximale Temperatur für die angegebene Stadt in Celsius ab.
     *
     * @param city Der Name der Stadt, für die die maximale Temperatur abgerufen werden soll.
     * @return Die maximale Temperatur in Celsius.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public double getMaxTemp(String city) throws IOException {
        double tempKelvinMax = fetchData(city).getJSONObject("main").getDouble("temp_max");
        return kelvinToCelsius(tempKelvinMax);
    }

    /**
     * Ruft die Wetterbeschreibung für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die die Wetterbeschreibung abgerufen werden soll.
     * @return Die Wetterbeschreibung.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public String getWeatherDescription(String city) throws IOException {
        JSONObject jsonObject = fetchData(city);
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        return weatherObject.getString("description");
    }

    /**
     * Wandelt die Temperatur von Kelvin in Celsius um.
     *
     * @param temperatureKelvin Die Temperatur in Kelvin.
     * @return Die Temperatur in Celsius.
     */
    private double kelvinToCelsius(double temperatureKelvin) {
        return temperatureKelvin - 273.15;
    }

    /**
     * Ruft die Luftfeuchtigkeit für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die die Luftfeuchtigkeit abgerufen werden soll.
     * @return Die Luftfeuchtigkeit in Prozent.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public int getHumidity(String city) throws IOException {
        return fetchData(city).getJSONObject("main").getInt("humidity");
    }

    /**
     * Ruft die Windgeschwindigkeit für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die die Windgeschwindigkeit abgerufen werden soll.
     * @return Die Windgeschwindigkeit in km/h.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public double windSpeed(String city) throws IOException {
        return fetchData(city).getJSONObject("wind").getDouble("speed");
    }

    /**
     * Ruft die Windrichtung für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die die Windrichtung abgerufen werden soll.
     * @return Die Windrichtung in Grad.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public int windDeg(String city) throws IOException {
        return fetchData(city).getJSONObject("wind").getInt("deg");
    }

    /**
     * Ruft den Zeitpunkt des Sonnenaufgangs für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die der Sonnenaufgang abgerufen werden soll.
     * @return Der Zeitpunkt des Sonnenaufgangs als Unix-Timestamp.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public long sunrise(String city) throws IOException {
        return fetchData(city).getJSONObject("sys").getLong("sunrise");
    }

    /**
     * Ruft den Zeitpunkt des Sonnenuntergangs für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die der Sonnenuntergang abgerufen werden soll.
     * @return Der Zeitpunkt des Sonnenuntergangs als Unix-Timestamp.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public long sunset(String city) throws IOException {
        return fetchData(city).getJSONObject("sys").getLong("sunset");
    }

    /**
     * Ruft das Länderkürzel für die angegebene Stadt ab.
     *
     * @param city Der Name der Stadt, für die das Länderkürzel abgerufen werden soll.
     * @return Das Länderkürzel.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public String getCounty(String city) throws IOException {
        return fetchData(city).getJSONObject("sys").getString("country");
    }

    /**
     * Ruft den Zeitpunkt des Sonnenaufgangs für die angegebene Stadt ab und gibt ihn im HH:mm:ss-Format zurück.
     *
     * @param city Der Name der Stadt, für die der Sonnenaufgang abgerufen werden soll.
     * @return Der Zeitpunkt des Sonnenaufgangs im HH:mm:ss-Format.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public String getSunRise(String city) throws IOException {
        long sunriseUnix = sunrise(city);
        Instant sunriseInstant = Instant.ofEpochMilli(sunriseUnix);
        LocalDateTime sunriseDateTime = LocalDateTime.ofInstant(sunriseInstant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return sunriseDateTime.format(formatter);
    }

    /**
     * Ruft den Zeitpunkt des Sonnenuntergangs für die angegebene Stadt ab und gibt ihn im HH:mm:ss-Format zurück.
     *
     * @param city Der Name der Stadt, für die der Sonnenuntergang abgerufen werden soll.
     * @return Der Zeitpunkt des Sonnenuntergangs im HH:mm:ss-Format.
     * @throws IOException Wenn ein Fehler beim Abrufen der Daten auftritt.
     */
    public String getSunSet(String city) throws IOException {
        long sunsetUnix = sunset(city);
        Instant sunsetInstant = Instant.ofEpochSecond(sunsetUnix);
        LocalDateTime sunsetDateTime = LocalDateTime.ofInstant(sunsetInstant, ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return sunsetDateTime.format(formatter);
    }
}
