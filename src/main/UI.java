package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import Object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40,arial80B;
    BufferedImage moneyImage;
    public boolean messageOn=false;
    public String message="";
    public int messageCounter=0;
    public boolean gameFinished=false;
    public double playTime=0.0;
    DecimalFormat df = new DecimalFormat("#.00");
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn=true;
    }
    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);
        if (gp.gameState== gp.playState) {

        }
        if(gp.gameState== gp.pauseState) {
            g2d.setFont(arial80B);
         drawPauseScreen();
        }



    }
    public void drawPauseScreen(){
        String text= "PAUSED";
        int x=getXForCenteredText(text);
        int y=gp.screenHeight/2;
        g2d.drawString(text,x,y);
    }
    public int getXForCenteredText(String text){
        int length= (int) g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
        return gp.screenWidth/2-length/2;
    }
}
