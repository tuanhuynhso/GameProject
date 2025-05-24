package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        boolean checkUP=false;
        int entityLeftWorldX = entity.worldX + entity.solidArea.x ;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Variable to track if the entity is on the ground
        boolean onGround = false;

        // Check collision for falling
        int tileNum1, tileNum2;

        // Falling down
        if (!"a".equals(entity.keyPressed)) {
            //System.out.println("bro i'm running rn");//
            entityBottomRow = (entityBottomWorldY + entity.jmp + 1) / gp.tileSize;
            if (isWithinBounds(entityLeftCol, entityBottomRow) && isWithinBounds(entityRightCol, entityBottomRow)) {
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    onGround = true;
                    entity.jmp = 0;
                    entity.worldY = entityBottomRow * gp.tileSize - entity.solidArea.y - entity.solidArea.height - 1;
                }}
        }

        // Update the grounded state based on collision
        entity.grounded = onGround;

        // Other directions
        //implement other velocity-based collision check
        //PROBLEM: checking only up OR left/right (need to check both)
        //one more issue is jmp setting kinda messed up the normal colliusion check
        switch (entity.action){
           /* case "jl":
                entityTopRow = (entityTopWorldY - entity.jmp + 1) / gp.tileSize;
                entityLeftCol = (entityLeftWorldX - 1) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityLeftCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)){
                        entity.grounded = false;
                        entity.jmp = 10;
                    }}
                }
                break;
            case "jr":
                entityTopRow = (entityTopWorldY - entity.jmp + 1) / gp.tileSize;
                entityRightCol = (entityRightWorldX + entity.spd + 1) / gp.tileSize;
                if (isWithinBounds(entityRightCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)){
                        entity.jmp = 10;
                        entity.grounded = false;
                    }}
                }*/
        }

        switch (entity.keyPressed) {
            case "up":
                entityTopRow = (entityTopWorldY + entity.jmp + 1) / gp.tileSize;
                //entityLeftCol = (entityLeftWorldX - 1) / gp.tileSize;
                /*if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityLeftCol, entityTopRow) || isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.grounded = false;
                        entity.jmp = 10;
                    }
                }
                else if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityLeftCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                    }
                }*/
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
                //entityLeftCol = (entityLeftWorldX - 1) / gp.tileSize;
                //entityTopRow = (entityTopWorldY - entity.jmp + 1) / gp.tileSize;
                if (isWithinBounds(entityRightCol, (entityTopWorldY - entity.jmp + 1)/ gp.tileSize) && isWithinBounds(entityRightCol, (entityTopWorldY - entity.jmp + 1)/ gp.tileSize)) {
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][(entityTopWorldY - 5)/ gp.tileSize];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][(entityTopWorldY - 5)/ gp.tileSize];
                    System.out.println("left up");
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionON = true;
                    entity.jmp = 5;
                    entity.grounded = false;
                }
            }
                if (isWithinBounds((entityLeftWorldX - 5)/ gp.tileSize, entityTopRow) && isWithinBounds((entityLeftWorldX - entity.spd - 1)/ gp.tileSize, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[(entityLeftWorldX - 5) / gp.tileSize][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[(entityLeftWorldX - 5) / gp.tileSize][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.worldX += 1;
                    }
                }
                break;
            case "right":
                //entityRightCol = (entityRightWorldX + entity.spd + 1) / gp.tileSize;
                //entityTopRow = (entityTopWorldY - entity.jmp + 1) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, (entityTopWorldY)/ gp.tileSize) && isWithinBounds(entityRightCol, (entityTopWorldY)/ gp.tileSize)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][(entityTopWorldY - 5)/ gp.tileSize];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][(entityTopWorldY - 5)/ gp.tileSize];
                    System.out.println("right up");
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.jmp = 5;
                        entity.grounded = false;
                    }
                }
                if (isWithinBounds((entityRightWorldX + entity.spd + 1)/ gp.tileSize, entityTopRow) && isWithinBounds((entityRightWorldX + entity.spd + 1)/ gp.tileSize, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[(entityRightWorldX + 5) / gp.tileSize][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[(entityRightWorldX + 5) / gp.tileSize][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.worldX -= 1;
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
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //let's figure out the OBJECT's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x - 28;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y - 50;

                switch (entity.keyPressed) {
                    case "right":
                        entity.solidArea.x += entity.spd + 5;
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
                        entity.solidArea.x -= entity.spd + 5;
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
                        entity.solidArea.x -= entity.spd;
                        entity.solidArea.y += entity.jmp;
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
                }
                entity.solidArea.y +=  5;
                if (entity.solidArea.intersects(gp.obj[i].solidArea) && entity.jmp > 0) {
                    System.out.println("down collision");
                    if(gp.obj[i].collision && !entity.grounded) {
                        entity.collisionON = false;
                        entity.jmp = 0;
                        entity.grounded = true;
                    }
                    if (player){
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = entity.solidAreaDefaultX;
                gp.obj[i].solidArea.y = entity.solidAreaDefaultY;
            }

        }

        return index;
    }
    public int checkEntity(Entity entity, Entity[] target) {
            int index = 999;

            for (int i = 0; i < target.length; i++){
                if (target[i] != null) {

                    //let's figure out the entity's solid area position
                    entity.solidArea.x = entity.worldX + entity.solidArea.x;
                    entity.solidArea.y = entity.worldY + entity.solidArea.y;

                    //let's figure out the OBJECT's solid area position
                    target[i].solidArea.x = target[i].worldX + target[i].solidArea.x - 28;
                    target[i].solidArea.y = target[i].worldY + target[i].solidArea.y - 50;

                    switch (entity.keyPressed) {
                        case "right":
                            entity.solidArea.x -= entity.spd;
                            if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                                    entity.collisionON = true;
                                    index = i;
                            }
                            break;
                        case "left":
                            entity.solidArea.x += entity.spd;
                            if (entity.solidArea.intersects(target[i].solidArea)){
                                    entity.collisionON = true;
                                    index = i;
                            }
                            break;
                        case "up":
                            entity.solidArea.y -= entity.spd;
                            if (entity.solidArea.intersects(target[i].solidArea)){
                                System.out.println("up collision");
                                    entity.collisionON = true;
                                    index = i;
                            }
                            break;
                        case "down":
                            entity.solidArea.y += entity.spd;
                            if (entity.solidArea.intersects(target[i].solidArea)){
                                System.out.println("down collision");
                                    entity.collisionON = true;
                                    index = i;
                            }
                            break;
                    }
                    entity.solidArea.x = entity.solidAreaDefaultX;
                    entity.solidArea.y = entity.solidAreaDefaultY;
                    target[i].solidArea.x = entity.solidAreaDefaultX;
                    target[i].solidArea.y = entity.solidAreaDefaultY;
                }

            }

            return index;
        }
}