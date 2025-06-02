package Characters;

import main.GamePanel;
import main.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {


    protected GamePanel gp;
    public int worldX, worldY, i;
    public int spd;
    public int jmp, jumpvl, jmpfrc, ground;
    public boolean grounded, air, animationLocked;
    public BufferedImage jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8, jl9, jl10, jl11, jl12, jr1, jr2, jr3, jr4, jr5, jr6, jr7, jr8, jr9, jr10, jr11, jr12, l1, l2, l3, l4, l5, l6, l7, l8, r1, r2, r3, r4, r5, r6, r7,r8;
    BufferedImage[] l = new BufferedImage[8];
    BufferedImage[] r = new BufferedImage[8];
    BufferedImage[] il = new BufferedImage[6];
    BufferedImage[] ir = new BufferedImage[6];
    public String action, keyPressed = "";
    public int spritecounter=0;
    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public Rectangle HP_bar = new Rectangle(0,0,0,0);
    public String name;
    public int solidAreaDefaultX, solidAreaDefaultY;


    //Character stats:
    public int maxLife;
    public int life;

    public boolean collisionON;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }

    public void setAction() {}
    public void update() {}

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (
                worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ) {
            switch (action) {
                case "r":
                    if (i >= 8) i = 0;
                    image = r[i];
                    i++;
                    break;
                case "l":
                    if (i >= 8) i = 0;
                    image = l[i];
                    i++;
                    break;
                case "il":
                    i = (i + 1) % 6;
                    image = il[i];
                    break;
                case "ir":
                    i = (i + 1) % 6;
                    image = ir[i];
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    }
