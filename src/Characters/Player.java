package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;
import main.keyhandle;

public class Player extends Entity {

    public int flip = 1;
    public int groundLevel;
    public int zHoldTime;
    GamePanel gp;
    keyhandle keyH;
    public final int screenX;
    public final int screenY;
    BufferedImage image = null;
    BufferedImage atk = null;
    BufferedImage[] jl = new BufferedImage[12];
    BufferedImage[] jr = new BufferedImage[12];
    BufferedImage[] l = new BufferedImage[8];
    BufferedImage[] r = new BufferedImage[8];
    BufferedImage[] il = new BufferedImage[6];
    BufferedImage[] ir = new BufferedImage[6];
    BufferedImage[] al = new BufferedImage[6];
    BufferedImage[] ar = new BufferedImage[6];
    BufferedImage[] a1 = new BufferedImage[4];

    public Player(GamePanel gp, keyhandle keyH) {

        super(gp);

        this.gp = gp;
        this.keyH = keyH;
        screenY= gp.screenHeight- (3*gp.tileSize);
        screenX= gp.screenWidth/2- (gp.tileSize);
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        attackArea.width = 100;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
    }
    public void hitbox(){
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

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        worldX = CurrentWorldX;
        worldY = CurrentWorldY;
        solidArea.width = SolidAreaWidth;
        solidArea.height = SolidAreaHeight;
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
            for (int i = 1; i < 5; i++) {
                a1[i-1] = ImageIO.read(getClass().getResource("/Attacks/BasicAttacks/R/Slash_color4_frame" + String.format("%d", i) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        collisionON = false;
        gp.cChecker.checkTile(this);
        int objIndex = gp.cChecker.checkObject(this, true);
        System.out.println(objIndex);

        if (!grounded) {
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
        if (keyH.rightPressed && zHoldTime == 0 || keyH.rightPressed && !animationLocked) {
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
        if (keyH.leftPressed && zHoldTime == 0 || keyH.leftPressed && !animationLocked) {
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
                flip = -1;
            } else if (action.equals("r") || action.equals("ir")) {
                action = "ar";
                flip = 1;
            }
            zHoldTime++;
            spd = 4;
            animationLocked = true;
            i = 3;
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
                    if (i >= 5 || !animationLocked) {
                        i = 2;
                        animationLocked = false;
                        flip = 0;
                        action = "r";
                    }
                    hitbox();
                    atk = a1[i-2];
                    image = ar[i];
                    i++;
                    break;

                case "al":
                    if (i >= 5 || !animationLocked) {
                        i = 2;
                        animationLocked = false;
                        flip = 0;
                        action = "l";
                    }
                    atk = a1[i-2];
                    image = al[i];
                    i++;
                    break;
            }
            spritecounter = 0;
        }
        /*System.out.println("Y: " + worldY + " | Jump: " + jmp + " | Grounded: " + grounded + " | Action: " + action + " | zHoldTime: " + zHoldTime);
        System.out.println("Frame index: " + i + " | Action: " + action + " | CollisionON: " + collisionON + " | GroundLevel: " + groundLevel + "| worldY: " + worldY);*/

        spritecounter++;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(atk, screenX + 45, screenY + 40, gp.tileSize * flip * 2, gp.tileSize, null);
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x + 28, screenY + solidArea.y + 60, solidArea.width - 20, solidArea.height -20);
        g2.drawRect(screenX +gp.obj[0].solidArea.x, screenY + gp.obj[0].solidArea.y, 48, 48);

    }
}