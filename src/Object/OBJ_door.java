package Object;

import Characters.Entity;
import main.GamePanel;

import java.awt.*;


public class OBJ_door extends Entity {
    public OBJ_door(GamePanel gp) {
       super(gp);
       name = "door";
       image = setup("/Object/door_1");
       collision = true;
        solidArea = new Rectangle(0,0,30,30    );

    }
}
