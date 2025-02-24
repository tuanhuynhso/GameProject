package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol =entityLeftWorldX/gp.tileSize;
        int entityRightCol =entityRightWorldX/gp.tileSize;
        int entityTopRow =entityTopWorldY/gp.tileSize;
        int entityBottomRow =entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.action){
            case "l";
            entityRightCol = entityRightWorldX - entity.spd;
            case "r";

    }
}