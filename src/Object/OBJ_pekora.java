package Object;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_pekora extends SuperObject {
    GamePanel gp;
    public OBJ_pekora(GamePanel gp) {
        this.gp = gp;
        name = "pekora";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/pekora.png"));
            ut.scaleImage(image,gp.tileSize,gp.tileSize);

        }
        catch(IOException e){
            e.printStackTrace();
        }
        collision = false;
    }
}
