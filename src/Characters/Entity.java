package Characters;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class Entity {

    GamePanel gp;
    public int worldX, worldY, i;
    public int spd;
    public int jmp, jumpvl, jmpfrc, ground;
    public boolean grounded, air, animationLocked;
    public BufferedImage jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8, jl9, jl10, jl11, jl12, jr1, jr2, jr3, jr4, jr5, jr6, jr7, jr8, jr9, jr10, jr11, jr12, l1, l2, l3, l4, l5, l6, l7, l8, r1, r2, r3, r4, r5, r6, r7,r8;
    public String action, keyPressed;
    public String direction= "down";
    public int spritecounter=0;
    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public String name;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public BufferedImage image,HP_0,HP_1,HP_2,HP_3,HP_4;
    public boolean collision = false;
    //Character stats:
    public int maxLife;
    public int life;

    public boolean collisionON;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (
                worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public BufferedImage setup(String imagePath){
        UtilityTools uTool= new UtilityTools();
        BufferedImage image = null;
        try{
            image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image=uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
        return image;
    }
}
