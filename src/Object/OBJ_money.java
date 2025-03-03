package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_money extends SuperObject {
    public OBJ_money() {
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
