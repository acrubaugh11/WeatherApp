/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static weather.Geo.fetchApiResponse;
import static weather.Geo.readApiResponse;

/**
 *
 * @author acrubaugh
 */
public class Weather extends GUI {


    
    public static void displayWeatherData(double latitude, double longitude) throws MalformedURLException, ParseException{
        try{
            
            //fetch api response based on api link
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";
            HttpURLConnection apiConnection = fetchApiResponse(url);
            
            //check for repsonse status
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Eror; bad connection");
                return;
            }
            
            //read response and convert to string
            String jsonResponse = readApiResponse(apiConnection);
            
            //parse string
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
            //System.out.println(currentWeatherJson.toJSONString());
            
            //store data into corresponding data type
            temp = (double) currentWeatherJson.get("temperature_2m");
            System.out.println(temp);
            
            relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            System.out.println(relativeHumidity);
            
            windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            System.out.println(windSpeed);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    }
    
