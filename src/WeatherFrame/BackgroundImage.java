package WeatherFrame;

import javax.swing.*;
import java.awt.*;

public class BackgroundImage extends JPanel {

private Image image;

public BackgroundImage(){
image = new ImageIcon("C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\img_5.png").getImage();
this.setLayout(null);
this.setBounds(0 ,0 , 1080 , 720);
}

public void paintComponent(Graphics g){

   Graphics2D g2 = (Graphics2D) g;

   g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
   g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BICUBIC);
   g2.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY);


   g2.drawImage(image , 0 ,0 , 1080 , 720 , this);


}





}
