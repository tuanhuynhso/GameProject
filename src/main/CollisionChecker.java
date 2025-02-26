package main;

import Characters.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x + 28;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y + 35;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        if (!entity.grounded) {
            entityBottomRow -= entity.jmp; // case 1 solved
        }

        switch (entity.keyPressed) {
            case "up":
                int jumpRow = (entityTopWorldY - entity.jmp) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, jumpRow) && isWithinBounds(entityRightCol, jumpRow)) {
                    int tileNum1 = gp.tileM.mapTileNum[entityLeftCol][jumpRow];
                    int tileNum2 = gp.tileM.mapTileNum[entityRightCol][jumpRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                        entity.jmp = 0;
                    }
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.spd) / gp.tileSize;
                if (isWithinBounds(entityLeftCol, entityTopRow) && isWithinBounds(entityLeftCol, entityBottomRow)) {
                    int tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    int tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionON = true;
                    }
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX - entity.spd) / gp.tileSize;
                if (isWithinBounds(entityRightCol, entityTopRow) && isWithinBounds(entityRightCol, entityBottomRow)) {
                    int tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    int tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
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