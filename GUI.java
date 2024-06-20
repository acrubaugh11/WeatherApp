/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static weather.Geo.fetchApiResponse;
import static weather.Geo.getLocationData;
import static weather.Geo.readApiResponse;

/**
 *
 * @author acrubaugh
 */
public class GUI extends JFrame {
    
    public static double temp;
    public static long relativeHumidity;
    public static double windSpeed;
    public static int weatherCode;
    
    public boolean running = false;
    
    public JButton button;
    
    public JLabel weatherImage;
    public JLabel weatherText;
    public JLabel temperatureText;
    public JLabel humidityText;
    public JLabel windSpeedText;
    
    public JLabel windSpeedImage;
    public JLabel humidityImage;
    
    public String time;
    public Integer timeN;
    
    public String city;
    public JLabel cityName;
    
    
    public GUI(){
        super("Weather App");
                
        running = true;
                
                
        // configure gui to end program's process once closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of our gui (pixels)
        setSize(450, 600);
        
        getContentPane().setBackground(new java.awt.Color(36, 51, 128));
        //20, 143, 196
        
        
        
        
        //load our gui in center of screen
        setLocationRelativeTo(null);

        //make our layout manager null to manually position items
        setLayout(null);

        //prevent any resize
        setResizable(false);

        addGuiComponents(); 
        
        
        
    }
    

    public void addGuiComponents() {
        updateImages();
        
        //add a search bar
        JTextField textField = new JTextField("Enter City");

            
        //set boundaries
        textField.setBounds(20, 15, 351, 45);
        //set font
        textField.setFont(new Font("SANS_SERIF", Font.PLAIN, 23));
        
        add(textField);
        
        
        //add search button
        button = new JButton(loadImage("src/assets/search.jpeg"));
        button.setBounds(375, 13, 60, 50);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
            Scanner scanner = new Scanner(textField.getText());
             city = scanner.nextLine();
                //get location data
                
                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");
                
                
                                
                try{
            
            //fetch api response based on api link
            //https://api.open-meteo.com/v1/forecast?latitude=33.767&longitude=-118.1892&current=temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m&timezone=America%2FChicago
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude + "&current=temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m&timezone=America%2FChicago";
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
            
            weatherCode = (int) (long) currentWeatherJson.get("weather_code");
            System.out.println(weatherCode);
            
            
            windSpeedImage.setIcon(loadImage("src/assets/windspeed.jpeg"));
            humidityImage.setIcon(loadImage("src/assets/humidity.jpeg"));
            
            //get hour number
            time = (String) currentWeatherJson.get("time");
            time = time.substring(11, 13);
//          System.out.println(time);
            timeN = Integer.valueOf(time);
            System.out.println(timeN);
            if(timeN > 18){
                getContentPane().setBackground(new java.awt.Color(36, 51, 128));
            }
            else{
                getContentPane().setBackground(new java.awt.Color(20, 143, 196));
            }
            
            
            
            temperatureText.setText("" + temp + "°C");
            humidityText.setText("" + relativeHumidity + " RH");
            windSpeedText.setText("" + windSpeed + " km/h");
            
            
            if(weatherCode == 0){
                weatherImage.setIcon(loadImage("src/assets/clear.jpeg"));
            }
            else if (weatherCode > 0 && weatherCode <=5){
                weatherImage.setIcon(loadImage("src/assets/cloudy.jpeg"));
            }
            else if ((weatherCode > 50 && weatherCode < 68) || weatherCode > 79 && weatherCode < 100){
                    weatherImage.setIcon(loadImage("src/assets/rain.jpeg"));
            }
            else if ((weatherCode > 69 && weatherCode < 78)|| weatherCode == 85 || weatherCode == 86){
                    weatherImage.setIcon(loadImage("src/assets/snow.jpeg"));
            }

            
            
            
            
        }catch(Exception x){
            x.printStackTrace();
        }

        }catch(Exception f){
            f.printStackTrace();
        }
                
                
                   
                
            }
        });
        add(button);
        
        
      
        
        
    }
    
    public void updateImages(){
                //weather image
        weatherImage = new JLabel();
        weatherImage.setBounds(95, 100, 250, 250);
        add(weatherImage);
        
        String t2 = Double.toString(temp);
        temperatureText = new JLabel("");
        temperatureText.setBounds(170, 330, 300, 100);
        temperatureText.setFont(new Font("DIALOG", Font.PLAIN, 36));
        temperatureText.setForeground(Color.white);
        add(temperatureText);   
        
        
        //humidity
        humidityImage = new JLabel();
        humidityImage.setBounds(15, 450, 95, 95);
        add(humidityImage);
        
        //humidity text
        humidityText = new JLabel();
        humidityText.setBounds(110, 450, 90, 90);
        humidityText.setFont(new Font("DIALOG", Font.PLAIN, 26));
        humidityText.setForeground(Color.white);
        add(humidityText);
        
        //windspeed
        windSpeedImage = new JLabel();
        windSpeedImage.setBounds(200, 450, 95, 92);
        add(windSpeedImage);
        
        //windspeed text
        windSpeedText = new JLabel();
        windSpeedText.setBounds(300, 450, 200, 92);
        windSpeedText.setFont(new Font("DIALOG", Font.PLAIN, 26));
        windSpeedText.setForeground(Color.white);
        add(windSpeedText);
        
        
        

    }
    
    
    
    
    public ImageIcon loadImage(String resourcePath) {
        try{
            //read image file path
            BufferedImage image = ImageIO.read(new File(resourcePath));
            
            //return an imgae icon so component can render it
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        System.out.println("Could not find resource");
        return null;
    }
    
}

