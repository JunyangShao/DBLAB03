package dblab3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pie {
    private static PieChart demo;
    public Pie(String Title) {
        demo = new PieChart("Data", Title);
    }
    public static void PrintJPG(String JPGname){
        demo.create_pie_chart();
        demo.pack();
        Container content=demo.getContentPane();
        BufferedImage img= new BufferedImage(
                demo.getWidth(),demo.getHeight(),BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = img.createGraphics();
        content.printAll(g2d);
        File f=new File("D:\\apache-tomcat-9.0.21\\webapps\\ROOT\\figures\\"+JPGname);
        //File f=new File(JPGname);

        try{
            ImageIO.write(img,"jpg",f);
        }
        catch ( IOException e){
            e.printStackTrace();
        }
        g2d.dispose();
    }
    public static void Fill(String name,int num){
        demo.fill_data(name,num);
    }
}
