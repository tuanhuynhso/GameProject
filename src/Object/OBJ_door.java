package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_door extends SuperObject {
    public OBJ_door() {
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
