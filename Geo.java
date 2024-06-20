/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package weather;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import static weather.Weather.displayWeatherData;
/**
 *
 * @author acrubaugh
 * gets the city and returns weather
 **/

public class Geo {
    
    public void getData(){
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
                city = scanner.nextLine();
                //get location data
                
                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");
                
                
            displayWeatherData(latitude, longitude);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JSONObject getLocationData(String city) throws MalformedURLException, IOException{
        city = city.replaceAll(" ", "+");
        
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="
                + city + "&count=1&language=en&format=json";
        
        try{
            //fetch API response based on API Link
            HttpURLConnection apiConnection = fetchApiResponse(urlString);
            
            //check for response status
            //200 means connetion successful !=200 means failure
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Bad Connection To Api");;
                return null;
            }
            
            //read the reponse and convert into String
            String jsonResponse = readApiResponse(apiConnection);
            
            //parse the string into a jsonobject
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);
            
            //retrive location data
            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return (JSONObject) locationData.get(0);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static HttpURLConnection fetchApiResponse(String urlString) throws MalformedURLException{
        try{
            //attempt conneiton
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //set request method to get
            conn.setRequestMethod("GET");
            
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }
        //no connection made
        return null;
    }
    
    public static String readApiResponse(HttpURLConnection apiConnection){
        try{
            //create stringbuilder to store resulting json data
            StringBuilder resultJson = new StringBuilder();
            
            //create a Scanner to read from the input stream of HttpURLConnection
            Scanner scanner = new Scanner(apiConnection.getInputStream());
            
            //loop through each line and append it to the string builder
            while (scanner.hasNext()){
                //read and append line
                resultJson.append(scanner.nextLine());
            }
            
            //close scanner 
            scanner.close();
            
            //return json data as a STring
            return resultJson.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        //issue reading response
        return null;
    }

    
    
}
