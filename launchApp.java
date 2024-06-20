/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;
import java.awt.Color;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;
import static weather.GUI.relativeHumidity;
import static weather.GUI.temp;
import static weather.GUI.windSpeed;

/**
 *
 * @author acrubaugh
 */
public class launchApp {
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
            GUI gui = new GUI();
            gui.setVisible(true);

            
    }

    });
}
}