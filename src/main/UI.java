package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import Object.*;

public class UI {
    GamePanel gp;
    Font arial_40,arial80B;
    BufferedImage moneyImage, HP_0, HP_1, HP_2, HP_3, HP_4;
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
        OBJ_money money = new OBJ_money(gp);
        moneyImage= money.image;

        //health lul
        SuperObject Life = new OBJ_Life(gp);
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
        drawPlayerLife(g2d);

        if(gameFinished){
            g2d.setFont(arial80B);
            g2d.setColor(Color.WHITE);
            String text;
            int textLength;
            int x;
            int y;
            text="You rescued Pekora";
            textLength = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();//return the length of the text
            x=gp.getWidth()/2-textLength/2;//text will be aligned to the center
            y=gp.getHeight()/2-(gp.tileSize*3);
            g2d.drawString(text,x,y);


            g2d.setFont(arial_40);
            g2d.setColor(Color.YELLOW);
            text="You completed the game in "+ df.format(playTime)+" seconds";
            textLength = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();//return the length of the text
            x=gp.getWidth()/2-textLength/2;//text will be aligned to the center, getWidth/2 will be at the exact center
                                            //then textLength/2 will align the text at the exact center
            y=gp.getHeight()/2-(gp.tileSize);
            g2d.drawString(text,x,y);


            gp.gameThread=null;//stop the game

        }
        else {
            g2d.setFont(arial_40);
            g2d.setColor(Color.WHITE);
            g2d.drawString(" x " + gp.player.moneyCount, 37, 40);
            g2d.drawImage(moneyImage, gp.tileSize / 2 - 20, gp.tileSize / 2 - 20, gp.tileSize, gp.tileSize, null);
            //TIME
            playTime+= (double)1/gp.FPS;
            g2d.drawString("Time: "+df.format(playTime), gp.tileSize*11, 40);

            //MESSAGES
            if (messageOn) {
                g2d.setFont(g2d.getFont().deriveFont(30f));
                g2d.drawString(message, 10, 80);
                messageCounter++;

                if (messageCounter > 90) { //text display duration
                    messageCounter = 0;
                    messageOn = false;
                }
            }

        }

    }
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
    }
}
