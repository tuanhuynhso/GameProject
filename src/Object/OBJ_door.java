package Object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_door extends SuperObject {
    GamePanel gp;
    public OBJ_door(GamePanel gp) {
        this.gp = gp;
        name = "door";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/door_1.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
        //test door: will open if you collect 2 coins
        //update: worked
    }
}
