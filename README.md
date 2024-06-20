This project is called Weather App, it is a java based made weather application. The
project uses two API calls, one for geolocation to accurately recieve the inputted 
locations longitude and latitude. The second API uses the new found longitude and 
latitude to call a weather API and accurately recieve the weather data at said location
point. The weather data is gathered into 4 variables; Temperature, Windspeed,
RelativeHumidity, and WeatherCode(code associated with weather type i.e rain, sun,
cloudy). The data is then accurately updated using JFrames JLabels.
GUI, handles all frontend panels and updates the data we receive from the weather API
Weather, handles all backend calls to the weather API using a parser and scanner
launchApp, is where we initiate a thread safe runnable instance
Geo, handles the geograpic API call in where we recieve the longitude and latitude of 
our inputted location
