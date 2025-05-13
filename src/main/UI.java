package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import Characters.Entity;
import Object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40,arial80B;
    BufferedImage  HP_0, HP_1, HP_2, HP_3, HP_4;
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

        //health lul
        Entity Life = new OBJ_Life(gp);
        HP_0 = Life.HP_0;
        HP_1 = Life.HP_1;
        HP_2 = Life.HP_2;
        HP_3 = Life.HP_3;
        HP_4 = Life.HP_4;
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn=true;
    }
    public void draw(Graphics2D g2d) {
        this.g2d = g2d;
        drawPlayerLife(g2d);}


    public void drawPlayerLife(Graphics2D g2d) {
        System.out.println(gp.player.life);
        int x = gp.tileSize / 2;
        int y = gp.tileSize;

        switch (gp.player.life) {
            case 4:
                g2d.drawImage(HP_4, x, y, gp.tileSize*4, gp.tileSize, null);
                break;
            case 3:
                g2d.drawImage(HP_3, x, y, gp.tileSize*4, gp.tileSize, null);
                break;
            case 2:
                g2d.drawImage(HP_2, x, y, gp.tileSize*4, gp.tileSize, null);
                break;
            case 1:
                g2d.drawImage(HP_1, x, y, gp.tileSize*4, gp.tileSize, null);
                break;
            case 0:
            default:
                g2d.drawImage(HP_0, x, y, gp.tileSize*4, gp.tileSize, null);
                break;
        }
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
