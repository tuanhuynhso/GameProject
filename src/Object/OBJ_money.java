package Object;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_money extends SuperObject {
    GamePanel gp;
    public OBJ_money(GamePanel gp) {
        this.gp = gp;
        name = "money";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/money_1.png"));

        }
        catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
