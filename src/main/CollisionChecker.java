package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity, keyhandle keyH) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x + 28;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y + 35;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        if (!entity.grounded){
            entityBottomRow-=entity.jmp; //case 1 solved//
        }
        if (keyH.rightPressed){
            entityRightCol = (entityRightWorldX - entity.spd)/gp.tileSize;
                //IDEA: make the fucking thing move before the character, thus making the character movement for the collision checker//
                //Character flow: jmp = 20 - 1  ->  run check thus, make this collision check.

        }
    }
}