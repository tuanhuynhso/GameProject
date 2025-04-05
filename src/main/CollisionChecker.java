package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x + 20;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width + 15 ;
        int entityTopWorldY = entity.worldY + entity.solidArea.y + 60;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height -20;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Variable to track if the entity is on the ground
        boolean onGround = false;

        // Check collision for falling
        int tileNum1, tileNum2;

        // Falling down
        if (entity.keyPressed!="a") {
            //System.out.println("bro i'm running rn");//
        entityBottomRow = (entityBottomWorldY + entity.jmp + 70) / gp.tileSize;
        if (isWithinBounds(entityLeftCol, entityBottomRow) && isWithinBounds(entityRightCol, entityBottomRow)) {
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                onGround = true;
                entity.jmp = 0;
                entity.worldY = entityBottomRow * gp.tileSize - entity.solidArea.y - entity.solidArea.height - 50;
            }}
        }

        // Update the grounded state based on collision
        entity.grounded = onGround;

        // Other directions
        switch (entity.keyPressed) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.jmp - 40) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.jmp = 10;
                        entity.grounded = false;
                    }
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.spd) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityLeftCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                    }
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.spd) / gp.tileSize;
                if (isWithinBounds(entityRightCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                    }
                }
                break;
        }
    }

    private boolean isWithinBounds(int col, int row) {
        return col >= 0 && col < gp.tileM.mapTileNum.length && row >= 0 && row < gp.tileM.mapTileNum[0].length;
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++){
            if (gp.obj[i] != null) {

                //let's figure out the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y+60;

                //let's figure out the OBJECT's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.keyPressed) {
                    case "right":
                        entity.solidArea.x -= entity.spd;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player){
                                index = i;
                            }

                        }
                        break;
                    case "left":
                            entity.solidArea.x += entity.spd;
                            if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                                if(gp.obj[i].collision) {
                                    entity.collisionON = true;
                                }
                                if (player){
                                    index = i;
                                }
                            }
                            break;
                    case "up":
                        entity.solidArea.y -= entity.spd;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            System.out.println("up collision");
                            if(gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.spd;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            System.out.println("down collision");
                            if(gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = entity.solidAreaDefaultX;
                gp.obj[i].solidArea.y = entity.solidAreaDefaultY;
            }

        }

        return index;
    }
}