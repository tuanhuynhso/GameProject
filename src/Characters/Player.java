package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;
import main.keyhandle;

public class Player extends Entity {

    public int groundLevel;
    public int zHoldTime;
    GamePanel gp;
    keyhandle keyH;
    public final int screenX;
    public final int screenY;
    BufferedImage image = null;
    BufferedImage[] jl = new BufferedImage[12];
    BufferedImage[] jr = new BufferedImage[12];
    BufferedImage[] l = new BufferedImage[8];
    BufferedImage[] r = new BufferedImage[8];
    BufferedImage[] il = new BufferedImage[6];
    BufferedImage[] ir = new BufferedImage[6];
    BufferedImage[] al = new BufferedImage[6];
    BufferedImage[] ar = new BufferedImage[6];

    public Player(GamePanel gp, keyhandle keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenY= gp.screenHeight- (3*gp.tileSize);
        screenX= gp.screenWidth/2- (gp.tileSize);
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        spd = 10;
        jmp = 0;
        jumpvl = 0;
        jmpfrc = 15;
        grounded = false;
        groundLevel = gp.screenHeight; // Initialize to the bottom of the screen
        action = "l";
        air = false;
        keyPressed = "up";
    }

    public void getPlayerImage() {
        try {
            for (int i = 0; i < 12; i++) {
                jl[i] = ImageIO.read(getClass().getResource("/Player/jumping/L/tile" + String.format("%03d", i) + ".png"));
                jr[i] = ImageIO.read(getClass().getResource("/Player/jumping/R/tile" + String.format("%03d", i) + ".png"));
            }
            for (int i = 0; i < 8; i++) {
                l[i] = ImageIO.read(getClass().getResource("/Player/Running/L/tile" + String.format("%03d", i) + ".png"));
                r[i] = ImageIO.read(getClass().getResource("/Player/Running/R/tile" + String.format("%03d", i) + ".png"));
            }
            for (int i = 0; i < 6; i++) {
                il[i] = ImageIO.read(getClass().getResource("/Player/Idle/L/tile" + String.format("%03d", i) + ".png"));
                ir[i] = ImageIO.read(getClass().getResource("/Player/Idle/R/tile" + String.format("%03d", i) + ".png"));
            }
            for (int i = 0; i < 6; i++) {
                al[i] = ImageIO.read(getClass().getResource("/Player/Attacking/L/tile" + String.format("%03d", i) + ".png"));
                ar[i] = ImageIO.read(getClass().getResource("/Player/Attacking/R/tile" + String.format("%03d", i) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        collisionON = false;
        gp.cChecker.checkTile(this);
        if (!grounded && worldY+125 < groundLevel) {
            worldY += jmp; // Move downwards
            jmp += 1; // Simulate gravity
            if (jmp > 0) {
                air = true;
            } else {
                air = false;
            }
        } else {
            jmp = 0; // Reset jump force when grounded
            grounded = true;
        }
        // Check key inputs
        if (keyH.upPressed && grounded) {
            grounded = false;
            jmp = -jmpfrc; // Apply jump force
            keyPressed = "up";
            if (action.equals("l") || action.equals("il")) {
                action = "jl";
            } else {
                action = "jr";
            }
        }
        if (keyH.rightPressed && zHoldTime == 0 && !animationLocked) {
            keyPressed = "right";
            if (!collisionON) {
                worldX += spd;
            }
            if (grounded && !action.equals("jr")) {
                action = "r";
            } else {
                action = "jr";
            }
        }
        if (keyH.leftPressed && zHoldTime == 0 && !animationLocked) {
            keyPressed = "left";
            if (!collisionON) {
                worldX -= spd;
            }
            if (grounded && !action.equals("jl")) {
                action = "l";
            } else {
                action = "jl";
            }
        }
        if (!keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed && !keyH.upPressed && zHoldTime == 0 && !animationLocked) {
            if (action.equals("l") || action.equals("al")) {
                action = "il";
            }
            if (action.equals("r") || action.equals("ar")) {
                action = "ir";
            }
        }
        if (keyH.zPressed && zHoldTime == 0) {
            if (action.equals("l") || action.equals("il")) {
                action = "al";
            } else if (action.equals("r") || action.equals("ir")) {
                action = "ar";
            }
            zHoldTime++;
            spd = 10;
            animationLocked = true;
            i = 0;
        } else if (!keyH.zPressed && !animationLocked) {
            zHoldTime = 0;
            spd = 4;
        }

        // Update collision status

        // Handle ground collision

        // Handle animations
        if (spritecounter >= 5) {
            switch (action) {
                case "jl":
                    if (!air && !grounded) {
                        if (i == 8) {
                            image = jl[i];
                        }
                        if (i < 8) {
                            image = jl[8];
                            i = 8;
                        }
                    } else if (i < 7) {
                        image = jl[i];
                        i++;
                    }
                    if (grounded) {
                        if (i < 11) {
                            image = jl[i];
                            i++;
                        } else {
                            action = "il";
                        }
                    }
                    break;
                case "jr":
                    if (!air && !grounded) {
                        if (i == 8) {
                            image = jr[i];
                        }
                        if (i < 8) {
                            image = jr[8];
                            i = 8;
                        }
                    } else if (i < 7) {
                        image = jr[i];
                        i++;
                    }
                    if (grounded) {
                        if (i < 11) {
                            image = jr[i];
                            i++;
                        } else {
                            action = "ir";
                        }
                    }
                    break;
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
                case "ar":
                    if (i >= 5) {
                        i = 3;
                        animationLocked = false;
                    }
                    image = ar[i];
                    i++;
                    break;
                case "al":
                    if (i >= 5) {
                        i = 3;
                        animationLocked = false;
                    }
                    image = al[i];
                    i++;
                    break;
            }
            spritecounter = 0;
        }
        System.out.println("Y: " + worldY + " | Jump: " + jmp + " | Grounded: " + grounded + " | Action: " + action + " | zHoldTime: " + zHoldTime);
        System.out.println("Frame index: " + i + " | Action: " + action + " | CollisionON: " + collisionON + " | GroundLevel: " + groundLevel + "| worldY: " + worldY);

        spritecounter++;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.setColor(Color.RED);
        g2.drawRect(worldX + solidArea.x + 28, worldY + solidArea.y + 60, solidArea.width - 20, solidArea.height -20);
    }
}