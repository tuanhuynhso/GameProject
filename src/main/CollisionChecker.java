package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x + 28;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width + 20;
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
        if (entity.keyPressed!="up") {
            System.out.println("bro i'm running rn");
        entityBottomRow = (entityBottomWorldY + entity.jmp + 70) / gp.tileSize;
        if (isWithinBounds(entityLeftCol, entityBottomRow) && isWithinBounds(entityRightCol, entityBottomRow)) {
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                onGround = true;
                entity.jmp = 0;
                entity.worldY = entityBottomRow * gp.tileSize - entity.solidArea.y - entity.solidArea.height - 50;
                entity.groundLevel = entityBottomRow * gp.tileSize;
            }}
        }

        // Update the grounded state based on collision
        entity.grounded = onGround;

        // Other directions
        switch (entity.keyPressed) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.jmp - 70) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityRightCol, entityTopRow)) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.jmp = 0;
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
                entityRightCol = (entityRightWorldX - entity.spd) / gp.tileSize;
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
}