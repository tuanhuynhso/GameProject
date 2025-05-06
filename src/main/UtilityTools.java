package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTools {
    public BufferedImage scaleImage(BufferedImage originalImg, int width, int height){
        BufferedImage scaledImg = new BufferedImage(width, height, originalImg.getType()) ;
        Graphics2D g2d = scaledImg.createGraphics();
        g2d.drawImage(originalImg, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImg;
        //pre-scaled image helps reduce the draw time
    }
}
