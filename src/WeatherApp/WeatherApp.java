package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Entry point - Main class for the weather app.
 * This class provides a graphical user interface for users to input
 * their location and preferred temperature unit (Celsius or Fahrenheit),
 * fetches current weather data from a weather service, and displays
 * the weather information along with tailored advice based on the
 * current weather conditions.
 *
 * @author Hiba Hassanin
 */

public class WeatherApp {

    private JFrame frame;
    private JPanel searchPanel;
    private JTextField locationField;
    private JComboBox<String> unitComboBox;
    private JButton searchButton;

    private JPanel weatherDisplayPanel;
    private JLabel locationLabel;
    private JLabel weatherIconLabel;
    private JLabel infoLabel;
    private JTextArea adviceArea;

    private final WeatherService weatherService;
    private WeatherData currentWeather;

    /**
     * Constructs a new WeatherApp instance.
     * Initializes the WeatherService and sets up the search page.
     */
    public WeatherApp() {
        weatherService = new WeatherService();
        initializeSearchPage();
    }

    /**
     * Initializes the search page where users input their location
     * and temperature unit preferences.
     * Sets up GUI components including logo, slogan, input fields, and search button.
     */
    private void initializeSearchPage() {
        frame = new JFrame("SkyCheck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // Search Panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(new Color(173, 216, 230));

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchPanel.add(logoLabel, BorderLayout.NORTH);

        // Slogan
        JLabel sloganLabel = new JLabel("Stay Ahead with SkyCheck");
        sloganLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sloganLabel.setForeground(Color.BLUE);
        sloganLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchPanel.add(sloganLabel, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputPanel.setBackground(new Color(173, 216, 230));

        locationField = new JTextField(20);
        inputPanel.add(locationField);

        unitComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit"});
        inputPanel.add(unitComboBox);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String location = locationField.getText();
                String unit = Objects.requireNonNull(unitComboBox.getSelectedItem()).toString();
                fetchWeather(location, unit);
            }
        });
        inputPanel.add(searchButton);

        searchPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(searchPanel);
        frame.setVisible(true);
    }

    /**
     * Fetches weather data based on the provided location and temperature unit.
     * Displays the weather information and advice on the weather display page.
     *
     * @param location The location for which weather information is fetched.
     * @param unit     The temperature unit preference (Celsius or Fahrenheit).
     */
    private void fetchWeather(String location, String unit) {
        try {
            currentWeather = weatherService.getWeather(location, unit);
            showWeatherPage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error fetching weather: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Displays the weather information and advice on the weather display page.
     * Sets up GUI components including location, weather icon, weather info, and advice area.
     */
    private void showWeatherPage() {
        frame.getContentPane().removeAll();

        // Weather Display Panel
        weatherDisplayPanel = new JPanel();
        weatherDisplayPanel.setLayout(new BorderLayout());
        weatherDisplayPanel.setBackground(Color.WHITE);

        // Location Label
        locationLabel = new JLabel("Location: " + currentWeather.getLocation());
        locationLabel.setFont(new Font("Arial", Font.BOLD, 24));
        locationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weatherDisplayPanel.add(locationLabel, BorderLayout.NORTH);

        // Center Panel for Weather Icon and Info
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Weather Icon
        String iconFileName = currentWeather.getIconFileName();
        ImageIcon weatherIcon = new ImageIcon(getClass().getResource("/icons/" + iconFileName));
        weatherIconLabel = new JLabel(weatherIcon);
        centerPanel.add(weatherIconLabel, BorderLayout.CENTER);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        infoLabel = new JLabel("<html>Temperature: " + currentWeather.getTemperature() + " °" + currentWeather.getUnit() +
                "<br>Humidity: " + currentWeather.getHumidity() + " %" +
                "<br>Wind Speed: " + currentWeather.getWindSpeed() + " " + currentWeather.getWindUnit() +
                "<br>Conditions: " + currentWeather.getConditions() + "</html>");
        infoPanel.add(infoLabel);

        centerPanel.add(infoPanel, BorderLayout.SOUTH);

        weatherDisplayPanel.add(centerPanel, BorderLayout.CENTER);

        // Advice Panel
        adviceArea = new JTextArea();
        adviceArea.setBackground(Color.WHITE);
        adviceArea.setFont(new Font("Arial", Font.PLAIN, 14));
        adviceArea.setEditable(false);
        adviceArea.setWrapStyleWord(true);
        adviceArea.setLineWrap(true);

        String advice = getWeatherAdvice(currentWeather.getTemperature());
        adviceArea.setText(advice);

        JScrollPane scrollPane = new JScrollPane(adviceArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        weatherDisplayPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(weatherDisplayPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Generates weather-related advice based on the current temperature.
     *
     * @param temperature The current temperature.
     * @return A string containing weather advice based on the temperature.
     */
    private String getWeatherAdvice(double temperature) {
        StringBuilder advice = new StringBuilder("Advice:\n");

        if (temperature >= 39) {
            advice.append(" - Caution: High heat!\n");
            advice.append(" - Stay hydrated.\n");
            advice.append(" - Wear light clothes.\n");
            advice.append(" - Avoid the sun as much as possible.\n");
            advice.append(" - Not suitable for running.\n");
            advice.append(" - Try to avoid going out if possible.\n");
        } else if (temperature >= 35 && temperature < 39) {
            advice.append(" - Stay hydrated.\n");
            advice.append(" - Apply sunscreen.\n");
            advice.append(" - Wear light clothes.\n");
            advice.append(" - Wear sunglasses.\n");
            advice.append(" - Drink fresh juices like orange juice.\n");
        } else if (temperature >= 25 && temperature < 35) {
            advice.append(" - Great weather for outdoor activities!\n");
            advice.append(" - Remember to stay hydrated.\n");
            advice.append(" - Wear light and comfortable clothing.\n");
            advice.append(" - Enjoy the sunshine but apply sunscreen.\n");
        } else if (temperature >= 15 && temperature < 25) {
            advice.append(" - Perfect weather for a walk or jog.\n");
            advice.append(" - Light layers are ideal.\n");
            advice.append(" - Stay hydrated and enjoy the pleasant weather.\n");
        } else if (temperature >= 8 && temperature < 15) {
            advice.append(" - A bit chilly, consider wearing a jacket.\n");
            advice.append(" - Good weather for outdoor exercise.\n");
            advice.append(" - Enjoy the cool breeze.\n");
        } else if (temperature >= 0 && temperature < 8) {
            advice.append(" - Wear warm clothes.\n");
            advice.append(" - Drink hot beverages to stay warm.\n");
            advice.append(" - Wear gloves and a hat.\n");
            advice.append(" - Consider indoor activities if it's too cold.\n");
        } else {
            advice.append(" - Caution: Extremely cold!\n");
            advice.append(" - Stay indoors if possible.\n");
            advice.append(" - Drink hot tea or hot chocolate.\n");
            advice.append(" - Wear multiple layers of warm clothing.\n");
        }

        return advice.toString();
    }

    /**
     * Entry point - Main method for launching the WeatherApp GUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}
