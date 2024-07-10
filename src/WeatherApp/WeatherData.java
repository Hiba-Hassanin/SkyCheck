package WeatherApp;

/**
 * This class works on fetching and storing the weather data based
 * on the specified location. It carries the parameters of
 * location, temperature, humidity, wind speed,
 * and weather conditions.
 *
 * @autor Hiba Hassanin
 */
public class WeatherData {

    private final String location;
    private final double temperature;
    private final int humidity;
    private final double windSpeed;
    private final String conditions;
    private final String unit;

    /**
     * This subclass works on constructing the weather
     * info with all the needed parameters.
     *
     * @param location The location of the country.
     * @param temperature The weather temperature.
     * @param humidity The humidity percentage.
     * @param windSpeed The wind speed in decimal.
     * @param conditions The description of the weather condition.
     * @param unit The unit of the temperature.
     */
    public WeatherData(String location, double temperature, int humidity, double windSpeed,
                       String conditions, String unit) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.conditions = conditions;
        this.unit = unit;
    }

    /**
     * This method works on fetching and implementing
     * the icons based on the weather condition.
     *
     * @return The suitable weather icon based
     *         on the fetched weather condition.
     */
    public String getIconFileName() {
        //First, receiving the weather condition and
        //converting it to lowercase to avoid any errors.
        String condition = getConditions().toLowerCase();
        //Then it chooses the appropriate weather icon
        //based on the weather conditions.
        if (condition.contains("sun")) {
            return "sunny.png";
        } else if (condition.contains("cloud")) {
            return "cloudy.png";
        } else if (condition.contains("rain")) {
            return "rainy.png";
        } else if (condition.contains("snow")) {
            return "snowy.png";
        } else if (condition.contains("wind")) {
            return "windy.png";
        } else {
            return "clear.png"; // Fallback icon the weather is normal.
        }
    }

    /**
     * This method works on getting the location
     * of the weather data.
     *
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method works on getting the temperature
     * of the specified location.
     *
     * @return The temperature.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * This method works on retrieving
     * humidity percentage.
     *
     * @return The humidity percent.
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * This method works on retrieving
     * the wind speed.
     *
     * @return The wind speed.
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * This method works on getting
     * the weather conditions.
     *
     * @return The conditions.
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * This method works on implementing
     * the chosen unit of temperature.
     *
     * @return The temperature unit.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method works on indicating
     * the wind speed unit of measurement.
     *
     * @return The wind speed unit.
     */
    public String getWindUnit() {
        return unit.equals("Celsius") ? "m/s" : "mph";
    }

}
