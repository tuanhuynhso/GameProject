package Object;

import Characters.Entity;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_pekora extends Entity {
    GamePanel gp;
    public OBJ_pekora(GamePanel gp) {
        super(gp);
        name = "pekora";
        image = setup("/Object/pekora");
        collision = false;
        solidArea = new Rectangle(0,0,30,30    );

    }
}
