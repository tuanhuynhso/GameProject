package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Characters.Entity;
import main.GamePanel;

public class OBJ_Life extends Entity {

    public OBJ_Life(GamePanel gp) {
        super(gp);
        name = "Life";
        HP_0=setup("/Object/HP_0");
        HP_1=setup("/Object/HP_1");
        HP_2=setup("/Object/HP_2");
        HP_3=setup("/Object/HP_3");
        HP_4=setup("/Object/HP_4");
    }
}
