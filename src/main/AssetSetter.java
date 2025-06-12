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


    }
    public void setNPC() {
        for (int i = 0; i < gp.maxMap; i++) {
            gp.monsters.add(new ArrayList<>());
        }
        gp.monsters.get(0).add(new ENEMY_normalGangster(gp, 200, 2000));
        gp.monsters.get(0).add(new ENEMY_normalGangster(gp, 1500, 200));
        gp.monsters.get(0).add(new ENEMY_normalGangster(gp, 1200, 200));
        gp.monsters.get(1).add(new ENEMY_normalGangster(gp, 1500, 100));
        gp.monsters.get(1).add(new ENEMY_normalGangster(gp, 1100, 100));
        gp.monsters.get(1).add(new ENEMY_normalGangster(gp, 1900, 100));
        gp.monsters.get(1).add(new ENEMY_normalGangster(gp, 2200, 100));
    }

}