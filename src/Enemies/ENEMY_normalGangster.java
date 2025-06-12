package Enemies;

import main.GamePanel;
import Characters.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ENEMY_normalGangster extends Entity {
    // Add these new variables at the class level
    private int startingX; // To keep track of starting position
    private final int MOVE_RANGE = 250; // How far the enemy moves in each direction
    GamePanel gp;
    public int flip = 1;
    public int groundLevel;
    BufferedImage image = null;
    BufferedImage[] l = new BufferedImage[8];  // Running left sprites
    BufferedImage[] r = new BufferedImage[8];  // Running right sprites
    BufferedImage[] il = new BufferedImage[15]; // Idle left sprites
    BufferedImage[] ir = new BufferedImage[15]; // Idle right sprites
    BufferedImage[] fl = new BufferedImage[7];  // Faint left sprites
    BufferedImage[] fr = new BufferedImage[7];  // Faint right sprites
    // Add these new attack-related variables
    public boolean Attacking = false;
    private boolean faintAnimationCompleted = false;
    public int attackCooldown = 0;
    public final int ATTACK_RANGE = 50; // Distance at which enemy can attack
    BufferedImage[] al = new BufferedImage[22];  // Attack left sprites
    BufferedImage[] ar = new BufferedImage[22];  // Attack right sprites
    BufferedImage atk = null;
    // Add a new variable to track active attack frames
    private int activeAttackFrames = 0;
    private final int ATTACK_ACTIVE_DURATION = 1; // Adjust this number to control how many frames the hitbox is active
    // Keep only the damage tracking variable
    private boolean hasDealtDamage = false;
    // Add new variables for idle state
    private boolean isIdling = false;
    private int idleTimer = 60;
    private final int IDLE_DURATION = 120; // Adjust this value to control idle duration in frames

    public ENEMY_normalGangster(GamePanel gp, int startX, int startY) {
        super(gp);
        this.gp = gp;

        // Add attack area
        attackArea = new Rectangle(28, 50, 0, 0);
        solidArea = new Rectangle(28, 50, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues(startX, startY);
        getEnemyImage();
    }

    public void setDefaultValues(int startX, int startY) {
        moveDirection = 1;
        worldX = startX;
        worldY = startY;
        startingX = startX;  // Set the patrol center point
        spd = 2;
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
        if (Attacking) {
            switch (flip) {
                case 1:
                    attackArea.width = 100;
                    attackArea.height = 36;
                    break;
                case -1:
                    attackArea.width = 100;
                    attackArea.height = 36;
                    break;
            }
            
            // Check if player is hit
            if (attackArea.intersects(gp.player.solidArea)) {
                // Only deal damage if we haven't already dealt damage this attack
                if (!gp.player.HIT && !hasDealtDamage) {
                    gp.player.life -= 1;
                    gp.player.HIT = true;
                    hasDealtDamage = true;
                    
                    // Vertical knockback
                    gp.player.jmp = -10;
                    
                    // Determine knockback direction based on relative position
                    if (worldX < gp.player.worldX) {
                        gp.player.hitdirection = "left";
                        gp.player.spd = 8; // Knock player to the right
                    } else {
                        gp.player.hitdirection = "right";
                        gp.player.spd = -8; // Knock player to the left
                    }
                }
            }
        } else {
            attackArea.width = 0;
            attackArea.height = 0;
            attackArea.x = solidArea.x;
        }
    }

    public void getEnemyImage() {
        try {
            // Load running animations
            for (int i = 0; i < 8; i++) {
                l[i] = ImageIO.read(getClass().getResource("/Enemies/Running/Left/tile" + String.format("%03d", i) + ".png"));
                r[i] = ImageIO.read(getClass().getResource("/Enemies/Running/Right/tile" + String.format("%03d", i) + ".png"));
            }

            // Load idle animations
            for (int i = 0; i < 15; i++) {
                il[i] = ImageIO.read(getClass().getResource("/Enemies/Idle/Left/tile" + String.format("%03d", i) + ".png"));
                ir[i] = ImageIO.read(getClass().getResource("/Enemies/Idle/Right/tile" + String.format("%03d", i) + ".png"));
            }
            // Load attack animations
            for (int i = 0; i < 22; i++) {
                al[i] = ImageIO.read(getClass().getResource("/Enemies/Attacking/Left/tile" + String.format("%03d", i) + ".png"));
                ar[i] = ImageIO.read(getClass().getResource("/Enemies/Attacking/Right/tile" + String.format("%03d", i) + ".png"));
            }
            // Load faint animations
            for (int i = 0; i < 7; i++) {
                fl[i] = ImageIO.read(getClass().getResource("/Enemies/Faint/Left/tile" + String.format("%03d", i) + ".png"));
                fr[i] = ImageIO.read(getClass().getResource("/Enemies/Faint/Right/tile" + String.format("%03d", i) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (life <= 0) {
            handleFaintingState();
            return; // Don't process other updates when dead
        }

        // Rest of the normal update logic...
        handleNormalState();
    }

    private void handleFaintingState() {
        if (!faintAnimationCompleted) {
            // Start fainting animation if not already started
            if (!action.equals("fr") && !action.equals("fl")) {
                i = 0;  // Reset animation frame
                // Keep the direction they were facing when they died
                action = (flip == 1) ? "fr" : "fl";
            }
            
            // Update animation frames at a slightly slower pace for dramatic effect
            if (spritecounter >= 12) {  // Increased from 8 to 12 for slower animation
                switch (action) {
                    case "fr":
                    case "fl":
                        if (i >= 7) {
                            i = 6;  // Stay on last frame
                            faintAnimationCompleted = true;
                        } else {
                            image = (action.equals("fr")) ? fr[i] : fl[i];
                            i++;
                        }
                        break;
                }
                spritecounter = 0;
            }
            spritecounter++;
            
            // Add a slight "falling" effect
            if (!grounded && !faintAnimationCompleted) {
                worldY += Math.min(jmp, 5); // Limit falling speed
                jmp += 0.5; // Slower fall
            }
        }
    }

    private void handleNormalState() {
        // Update attack cooldown
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // Check if player is in attack range
        int distanceToPlayer = Math.abs(worldX - gp.player.worldX);
        if (distanceToPlayer <= ATTACK_RANGE && attackCooldown == 0 && !Attacking && (worldY == gp.player.worldY || worldY + solidArea.height == gp.player.worldY + gp.player.solidArea.height) && !gp.player.dash) {
            // Start attack
            Attacking = true;
            hasDealtDamage = false; // Reset damage flag for new attack
            attackCooldown = 30;
            if (worldX < gp.player.worldX) {
                action = "ar";
                flip = 1;
            } else {
                action = "al";
                flip = -1;
            }
        }

        // Handle idle state first
        if (isIdling && !Attacking) {
            idleTimer--;
            if (idleTimer <= 0) {
                isIdling = false;
                // Resume movement animation based on direction
                action = (moveDirection > 0) ? "r" : "l";
            }
        } 
        // Handle movement when not attacking and not idling
        else if (!Attacking) {
            int distanceFromStart = worldX - startingX;
            
            // Move the enemy
            if (life > 0) {
                worldX += spd * moveDirection;
                action = (moveDirection > 0) ? "r" : "l";
            }

            // Check patrol boundaries and change direction
            if (distanceFromStart >= MOVE_RANGE && moveDirection == 1) {
                moveDirection = -1;
                flip = moveDirection;
                isIdling = true;
                idleTimer = IDLE_DURATION;
                action = "il";
            } 
            else if (distanceFromStart <= -MOVE_RANGE && moveDirection == -1) {
                moveDirection = 1;
                flip = moveDirection;
                isIdling = true;
                idleTimer = IDLE_DURATION;
                action = "ir";
            }
        }

        // Existing collision and movement code...
        collisionON = false;
        gp.cChecker.checkTile(this);

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

        // Handle animations
        if ((action.equals("ar") || action.equals("al")) && spritecounter >= 4) {
// Handle attack animations
            switch (action) {
                case "ar":
                case "al":
                    if (i >= 22) {
                        i = 0;
                        Attacking = false;
                        action = (action.equals("ar")) ? "r" : "l";
                    } else {
                        image = (action.equals("ar")) ? ar[i] : al[i];
                        i++;
                        // Only increment active attack frames during specific animation frames
                        // For example, frames 10-15 of the attack animation
                        if (i >= 10 && i <= 15) {
                            activeAttackFrames++;
                        }
                    }
                    hitbox();
                    break;
            }
            spritecounter = 0;
        }
        else if (spritecounter >= 8) {  // Normal speed for other animations
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
                    i = (i + 1) % 15;
                    image = il[i];
                    break;
                case "ir":
                    i = (i + 1) % 15;
                    image = ir[i];
                    break;
                case "fr":  // Faint right
                    if (i >= 7) {
                        i = 6;  // Stay on last frame
                        faintAnimationCompleted = true;
                    } else {
                        image = fr[i];
                        i++;
                    }
                    break;
                case "fl":  // Faint left
                    if (i >= 7) {
                        i = 6;  // Stay on last frame
                        faintAnimationCompleted = true;
                    } else {
                        image = fl[i];
                        i++;
                    }
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
        if (isOnScreen(screenX, screenY)) {
            // Apply transparency if dead
            Composite originalComposite = null;
            if (faintAnimationCompleted) {
                originalComposite = g2.getComposite();
                float alpha = 0.5f; // 50% transparent when dead
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2.setComposite(alphaComposite);
            }

            g2.drawImage(image, screenX, screenY+30, gp.tileSize * 2, gp.tileSize * 2, null);
            
            // Only draw health bar if alive
            if (life > 0) {
                g2.setColor(Color.black);
                g2.drawRect(screenX + 20, screenY + 40, 50, 10);
                g2.setColor(Color.RED);
                g2.fillRect(screenX + 20, screenY + 40, 50*(life*100/maxLife)/100, 7);
            }

            // Restore original composite if it was changed
            if (faintAnimationCompleted && originalComposite != null) {
                g2.setComposite(originalComposite);
            }
        }
    }

    private boolean isOnScreen(int screenX, int screenY) {
        return worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
}
}