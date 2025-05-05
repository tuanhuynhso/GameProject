package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_pekora extends SuperObject {
    public OBJ_pekora() {
        name = "pekora";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/pekora.jpg"));

        }
        catch(IOException e){
            e.printStackTrace();
        }
        collision = false;
    }
}
