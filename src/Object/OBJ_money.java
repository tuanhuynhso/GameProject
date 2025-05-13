package Object;

import Characters.Entity;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_money extends Entity {
    public OBJ_money(GamePanel gp) {
        super(gp);
        name = "money";
        image = setup("/Object/money_1");
        collision = false;
        solidArea = new Rectangle(0,0,30,30    );

    }
}
