package Enemies;

import main.GamePanel;
import Characters.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ENEMY_normalGangster extends Entity {
    // Add these new variables at the class level
    private int moveDirection = 1; // 1 for right, -1 for left
    private int startingX; // To keep track of starting position
    private final int MOVE_RANGE = 200; // How far the enemy moves in each direction
    GamePanel gp;
    public int flip = 1;
    public int groundLevel;
    BufferedImage image = null;
    BufferedImage[] l = new BufferedImage[8];  // Running left sprites
    BufferedImage[] r = new BufferedImage[8];  // Running right sprites
    BufferedImage[] il = new BufferedImage[6]; // Idle left sprites
    BufferedImage[] ir = new BufferedImage[6]; // Idle right sprites

    public ENEMY_normalGangster(GamePanel gp, int startX, int startY) {  // Modified constructor
        super(gp);
        this.gp = gp;

        solidArea = new Rectangle(28, 50, gp.tileSize - 20, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues(startX, startY);  // Pass the starting position
        getEnemyImage();
    }

    public void setDefaultValues(int startX, int startY) {
        worldX = startX;
        worldY = startY;
        startingX = startX;  // Set the patrol center point
        spd = 4;
        jmp = 0;
        jmpfrc = 15;
        grounded = false;
        action = "il";
        air = false;
        maxLife = 4;
        life = maxLife;
        groundLevel = gp.screenHeight;
    }

    public void hitbox() {
        int CurrentWorldX = worldX;
        int CurrentWorldY = worldY;
        int SolidAreaWidth = solidArea.width;
        int SolidAreaHeight = solidArea.height;

        switch (flip) {
            case 1:
                worldX += 50; break;
            case -1:
                worldX -= 50; break;
        }

        worldX = CurrentWorldX;
        worldY = CurrentWorldY;
        solidArea.width = SolidAreaWidth;
        solidArea.height = SolidAreaHeight;
    }

    public void getEnemyImage() {
        try {
            // Load running animations
            for (int i = 0; i < 8; i++) {
                l[i] = ImageIO.read(getClass().getResource("/Enemies/Running/Left/tile" + String.format("%03d", i) + ".png"));
                r[i] = ImageIO.read(getClass().getResource("/Enemies/Running/Right/tile" + String.format("%03d", i) + ".png"));
            }

            // Load idle animations
            for (int i = 0; i < 6; i++) {
                il[i] = ImageIO.read(getClass().getResource("/Enemies/Idle/Left/tile" + String.format("%03d", i) + ".png"));
                ir[i] = ImageIO.read(getClass().getResource("/Enemies/Idle/Right/tile" + String.format("%03d", i) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        collisionON = false;
        gp.cChecker.checkTile(this);

        // Movement logic
        int distanceFromStart = worldX - startingX; // Changed from Math.abs to track actual position
        
        // Debug output
        System.out.println("Enemy Position - WorldX: " + worldX +
                          " StartingX: " + startingX +
                          " Distance: " + distanceFromStart +
                          " Direction: " + moveDirection);

        // Change direction when reaching range limits
        if (distanceFromStart >= MOVE_RANGE) {
            moveDirection = -1;
            flip = moveDirection;
        } else if (distanceFromStart <= -MOVE_RANGE) {
            moveDirection = 1;
            flip = moveDirection;
        }

        // Move the enemy
        worldX += spd * moveDirection;

        // Update action based on movement direction
        if (moveDirection > 0) {
            action = "r";
        } else {
            action = "l";
        }

        // Gravity and jumping mechanics
        if (!grounded) {
            worldY += jmp;
            jmp += 1;
            if (jmp > 0) {
                air = true;
            } else {
                air = false;
            }
        } else {
            jmp = 0;
        }

        // Update grounded state based on collision
        if (collisionON) {
            grounded = true;
        } else {
            grounded = false;
        }

        // Debug the exact values
        System.out.println("Enemy Debug:" +
            "\nWorld Y: " + worldY +
            "\nScreen Height: " + gp.screenHeight +
            "\nGround Level: " + groundLevel +
            "\nCollision: " + collisionON +
            "\nGrounded: " + grounded +
            "\nJump: " + jmp);

        // Handle animations
        if (spritecounter >= 5) {
            switch (action) {
                case "r":
                    if (i >= 8) i = 0;
                    image = r[i];
                    i++;
                    break;
                case "l":
                    if (i >= 8) i = 0;
                    image = l[i];
                    i++;
                    break;
                case "il":
                    i = (i + 1) % 6;
                    image = il[i];
                    break;
                case "ir":
                    i = (i + 1) % 6;
                    image = ir[i];
                    break;
            }
            spritecounter = 0;
        }
        spritecounter++;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if the enemy is visible on screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            
            g2.drawImage(image, screenX, screenY+30, gp.tileSize * 2, gp.tileSize * 2, null);
            
            // Draw hitbox (for debugging)
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}