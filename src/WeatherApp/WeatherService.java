package WeatherApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 * This class works on fetching necessary information from
 * the open weather map using the generated API key.
 */
public class WeatherService {

    //The API key for accessing the Openweathermap info
    private static final String API_KEY = "616699cc2571e1edad6d42fad33fd952";

   //Base URL for Openweathermap
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    /**
     * This method gets the weather data from the Openweathermap
     * website based on the location and temperature
     * unit indicated.
     *
     * @param location The location of wanted country.
     * @param unit The unit of the weather temperature.
     * @return The fetched weather information.
     * @throws Exception In case of occurrence of an error
     *                   when handling the API request.
     */
    public WeatherData getWeather(String location, String unit) throws Exception {
        //Constructing the URL.
        String urlString = String.format("%s?q=%s&units=%s&appid=%s", BASE_URL, location,
                unit.equals("Celsius") ? "metric" : "imperial", API_KEY);
        URL url = new URL(urlString);

        //Opening and starting the API connection.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            //The buffer reader enables the program to
            //read the API response.
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            return parseWeather(jsonResponse, unit);
        } finally {
            //Closing the API connection.
            connection.disconnect();
        }
    }

    /**
     * This method extracts the weather data from the
     * Openweathermap by parsing the JSON responses.
     *
     * @param jsonResponse The JASON response.
     * @param unit The unit of the weather temperature.
     * @return  The extracted weather data.
     */
    private WeatherData parseWeather(JSONObject jsonResponse, String unit) {
        //Getting the required weather information.
        String location = jsonResponse.getString("name");
        double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
        int humidity = jsonResponse.getJSONObject("main").getInt("humidity");
        double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
        String conditions = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

        //Return those elements.
        return new WeatherData(location, temperature, humidity, windSpeed, conditions, unit);
    }
}
