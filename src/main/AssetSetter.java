package main;

import Object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject(){

        gp.obj[0] = new OBJ_money();
        gp.obj[0].worldX = 4 * gp.tileSize;
        gp.obj[0].worldY = 47 * gp.tileSize;


        gp.obj[1] = new OBJ_money();
        gp.obj[1].worldX = 7 * gp.tileSize;
        gp.obj[1].worldY = 45 * gp.tileSize;

        gp.obj[2] = new OBJ_door();
        gp.obj[2].worldX = 12 * gp.tileSize;
        gp.obj[2].worldY = 47 * gp.tileSize;

        gp.obj[3] = new OBJ_pekora();
        gp.obj[3].worldX = 18 * gp.tileSize;
        gp.obj[3].worldY = 47 * gp.tileSize;


    }

}
