package main;

import Object.OBJ_money;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject(){

        gp.obj[0] = new OBJ_money();
        gp.obj[0].worldX = 4 * gp.tileSize;
        gp.obj[0].worldY = 47 * gp.tileSize;
    }

}
