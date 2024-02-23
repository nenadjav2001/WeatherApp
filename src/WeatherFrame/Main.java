package  WeatherFrame;


import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            new Frame().createFrame();

        }
           catch (IOException e) {

               JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
               throw new RuntimeException(e);

        }


    }
}
