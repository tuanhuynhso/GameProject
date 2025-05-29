package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import Object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40, arial80B;
    BufferedImage HP_0, HP_1, HP_2, HP_3, HP_4;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    public double playTime = 0.0;
    DecimalFormat df = new DecimalFormat("#.00");

    // Menu states
    public int commandNum = 0;
    public Rectangle[] titleButtons = new Rectangle[2];
    public Rectangle[] pauseButtons = new Rectangle[2];

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);

        // Initialize button areas
        for(int i = 0; i < titleButtons.length; i++) {
            titleButtons[i] = new Rectangle();
        }
        for(int i = 0; i < pauseButtons.length; i++) {
            pauseButtons[i] = new Rectangle();
        }

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
        messageOn = true;
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;
        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            drawPlayerLife(g2d);
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            drawPlayerLife(g2d);
            drawPauseScreen();
        }
    }

    public void drawPlayerLife(Graphics2D g2d) {
        int x = gp.tileSize / 2;
        int y = gp.tileSize;

        switch (gp.player.life) {
            case 4:
                g2d.drawImage(HP_4, x, y, gp.tileSize * 4, gp.tileSize, null);
                break;
            case 3:
                g2d.drawImage(HP_3, x, y, gp.tileSize * 4, gp.tileSize, null);
                break;
            case 2:
                g2d.drawImage(HP_2, x, y, gp.tileSize * 4, gp.tileSize, null);
                break;
            case 1:
                g2d.drawImage(HP_1, x, y, gp.tileSize * 4, gp.tileSize, null);
                break;
            case 0:
            default:
                g2d.drawImage(HP_0, x, y, gp.tileSize * 4, gp.tileSize, null);
                break;
        }
    }

    public void drawTitleScreen() {
        // Title
        g2d.setFont(arial80B);
        String text = "KATAVANIA";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;
        
        // Shadow
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, x + 5, y + 5);
        // Main color
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

        // Menu options
        g2d.setFont(arial_40);
        
        // NEW GAME option
        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3;
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x - gp.tileSize, y);
        }
        // Store button area for mouse interaction
        titleButtons[0].x = x - gp.tileSize;
        titleButtons[0].y = y - 30;
        titleButtons[0].width = g2d.getFontMetrics().stringWidth(text) + gp.tileSize;
        titleButtons[0].height = 40;

        // QUIT option
        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - gp.tileSize, y);
        }
        // Store button area for mouse interaction
        titleButtons[1].x = x - gp.tileSize;
        titleButtons[1].y = y - 30;
        titleButtons[1].width = g2d.getFontMetrics().stringWidth(text) + gp.tileSize;
        titleButtons[1].height = 40;

        // Draw button highlights on hover
        for(int i = 0; i < titleButtons.length; i++) {
            if(titleButtons[i].contains(gp.Mx, gp.My)) {
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.fill(titleButtons[i]);
                g2d.setColor(Color.WHITE);
                commandNum = i;
            }
        }
    }

    public void drawPauseScreen() {
        // Title
        g2d.setFont(arial80B);
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2 - gp.tileSize * 2;
        g2d.drawString(text, x, y);

        // Menu options
        g2d.setFont(arial_40);
        
        // RESUME option
        text = "RESUME";
        x = getXForCenteredText(text);
        y += gp.tileSize * 2;
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x - gp.tileSize, y);
        }
        // Store button area for mouse interaction
        pauseButtons[0].x = x - gp.tileSize;
        pauseButtons[0].y = y - 30;
        pauseButtons[0].width = g2d.getFontMetrics().stringWidth(text) + gp.tileSize;
        pauseButtons[0].height = 40;

        // QUIT TO MENU option
        text = "QUIT TO MENU";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - gp.tileSize, y);
        }
        // Store button area for mouse interaction
        pauseButtons[1].x = x - gp.tileSize;
        pauseButtons[1].y = y - 30;
        pauseButtons[1].width = g2d.getFontMetrics().stringWidth(text) + gp.tileSize;
        pauseButtons[1].height = 40;

        // Draw button highlights on hover
        for(int i = 0; i < pauseButtons.length; i++) {
            if(pauseButtons[i].contains(gp.Mx, gp.My)) {
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.fill(pauseButtons[i]);
                g2d.setColor(Color.WHITE);
                commandNum = i;
            }
        }
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
