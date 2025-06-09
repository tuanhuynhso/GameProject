package main;

import Enemies.ENEMY_normalGangster;
import Object.*;

import java.util.ArrayList;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject(){

        gp.obj[0][0] = new OBJ_money(gp);
        gp.obj[0][0].worldX = 4 * gp.tileSize;
        gp.obj[0][0].worldY = 47 * gp.tileSize;


        gp.obj[0][1] = new OBJ_money(gp);
        gp.obj[0][1].worldX = 7 * gp.tileSize;
        gp.obj[0][1].worldY = 45 * gp.tileSize;

        gp.obj[0][2] = new OBJ_door(gp);
        gp.obj[0][2].worldX = 12 * gp.tileSize;
        gp.obj[0][2].worldY = 47 * gp.tileSize;

        gp.obj[0][3] = new OBJ_pekora(gp);
        gp.obj[0][3].worldX = 18 * gp.tileSize;
        gp.obj[0][3].worldY = 47 * gp.tileSize;


    }
    public void setNPC() {
        for (int i = 0; i < gp.maxMap; i++) {
            gp.monsters.add(new ArrayList<>());
        }
        gp.monsters.get(0).add(new ENEMY_normalGangster(gp, 500, 200));
        //gp.monsters.get(0).add(new ENEMY_normalGangster(gp, 200, 200));
    }

}