package Object;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_LowHPIndicator extends SuperObject {
    GamePanel gp;
    public OBJ_LowHPIndicator(GamePanel gp) {
    this.gp = gp;
    name = "lowHP";
    try {
        image = ImageIO.read(getClass().getResourceAsStream("/Object/Low_HP.png"));
    }
    catch(IOException e){
        e.printStackTrace();
    }
    }

}
