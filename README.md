## Overview
This program code is for a simple weather application. It allows the user to enter their desired location and search its weather. It provides a good deal of different weather information; like humidity, wind speed, temperature, and overall weather conditions. The program also displays the weather information along with tailored advice based on the current weather conditions. 

## Implementations
* The WeatherApp class was created to implement the graphical user interface (GUI) for the application. It works on receiving user input, fetches weather data from the OpenWeatherMap API, and displays the weather information.

* The WeatherService class was created to retrieve information from the OpenWeatherMap website using the API key. It sends HTTP requests to fetch weather data based on the user's inputted data.

* The WeatherData class was created to fetch and store the weather information. It handles parameters such as location, temperature, humidity, wind speed, weather conditions, suitable icons, as well as the proper units.

## How to Run
* First, You run the WeatherAPP class as main().
* Then the user enters their desired location and selects the needed temperature unit (Celsius or Fahrenheit).
* And finally, simply click the "Search" button to get the weather information.
* All of the weather information related to the specified location will be displayed as well as a weather icon that represents the weather conditions.
* On the bottom right side of the panel, there would be a list of searched history that stores any new search.

## Dependencies
* Java Development Kit (JDK)
* JSON library (Included in this project)

## API Key
The WeatherApp uses the OpenWeatherMap API key to get weather information.

## Author
Hiba Hassanin.