package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Life extends SuperObject {
    GamePanel gp;

    public OBJ_Life(GamePanel gp) {
        this.gp = gp;
        name = "Life";
        try{
            HP_0 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_0.png"));
            HP_1 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_1.png"));
            HP_2 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_2.png"));
            HP_3 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_3.png"));
            HP_4 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_4.png"));
            HP_0 = ut.scaleImage(HP_0, gp.tileSize, gp.tileSize);
            HP_1 = ut.scaleImage(HP_1, gp.tileSize, gp.tileSize);
            HP_2 = ut.scaleImage(HP_2, gp.tileSize, gp.tileSize);
            HP_3 = ut.scaleImage(HP_3, gp.tileSize, gp.tileSize);
            HP_4 = ut.scaleImage(HP_4, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
