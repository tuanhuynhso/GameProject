package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;
import main.UtilityTools;
import main.keyhandle;

public class Player extends Entity {

    public boolean Attacking = true, active = true, dash = false, hit = false;
    public int flip = 1, CurrentWorldX, CurrentWorldY,SolidAreaWidth, SolidAreaHeight, dashCounter = 101;
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

    public int moneyCount = 0;

    public Player(GamePanel gp, keyhandle keyH) {

        super(gp);

        this.gp = gp;
        this.keyH = keyH;
        screenY= gp.screenHeight- (3*gp.tileSize);
        screenX= gp.screenWidth/2- (gp.tileSize);
        attackArea = new Rectangle(28, 50, 0, 0);
        solidArea = new Rectangle(28, 50, gp.tileSize-20, gp.tileSize-5);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;



        setDefaultValues();
        getPlayerImage();
    }
    public void hitbox() {
        if(active) {
            CurrentWorldX = worldX;
            CurrentWorldY = worldY;
            SolidAreaWidth = solidArea.width;
            SolidAreaHeight = solidArea.height;
            active = false;
        }
        if (Attacking) {
            switch (flip) {
                case 1:
                    attackArea.width = 100;
                    attackArea.height = 36;
                    break;
                case -1:
                    attackArea.width = 100;
                    attackArea.height = 36;
                    attackArea.x = 2*solidArea.x - 100;
                    break;
            }
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            System.out.println("monster: " + monsterIndex);
            if (monsterIndex == 4 && !hit) {
                life -= 1;
                hit = true;
            }
        }
        else{
            worldX = CurrentWorldX;
            worldY = CurrentWorldY;
            attackArea.width = 0;
            attackArea.height = 0;
            attackArea.x = solidArea.x;
            active = true;
            hit = false;
        }
        //int monsterIndex = gp.cChecker.checkEntity(this,gp.npc);
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
        maxLife = 4;
        life = maxLife;
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
    public BufferedImage setup(String imagePath){
        UtilityTools ut = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ut.scaleImage(ImageIO.read(getClass().getResource(imagePath)), gp.tileSize, gp.tileSize);
//edit here or create a new one

        }catch (Exception e){
            e.printStackTrace();
        }
        return scaledImage;
    }


    public void update() {
        collisionON = false;
        //CHECK TILE COLLISION
        gp.cChecker.checkTile(this);
        //CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
//        debug if the collision works
        System.out.println(objIndex);
        pickUpObject(objIndex);
        //CHECK EVENT
        gp.eHandler.checkEvent();




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
        if (keyH.xPressed && !animationLocked && dashCounter > 50){
            dash = true;
            dashCounter = 0;
        }
        if (keyH.upPressed && grounded && !animationLocked) {
            grounded = false;
            jmp = -jmpfrc; // Apply jump force
            keyPressed = "up";
            if (action.equals("l") || action.equals("il") ) {
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
            if (grounded) {
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
            if (grounded) {
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
        if (keyH.zPressed && zHoldTime == 0 && grounded) {
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
        }
        else if (!keyH.zPressed && !animationLocked) {
            zHoldTime = 0;
            spd = 4;
        }

        // Update collision status

        // Handle ground collision

        // Handle animations
        if (dash == true){
            if (action.equals("l") || action.equals("jl") || action.equals("il") || action.equals("al") && dashCounter < 11 && !collisionON) {
                worldX -= 11;
                dashCounter++;
                System.out.println("Dash ON");
            }
            else if (collisionON) {
                worldX -= 11;
            }
            if (action.equals("r") || action.equals("jr")|| action.equals("il") || action.equals("al") && dashCounter < 11 && !collisionON) {
                worldX += 11;
                dashCounter++;
            }
            else if (collisionON) {
                worldX += 11;
            }
            dashCounter++;
        }
        if (dashCounter > 11){
            dash = false;
            dashCounter ++;
        }
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
                    if (grounded && !action.equals("l") || action.equals("r")) {
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
                        Attacking = false;
                        hitbox();
                    }
                    hitbox();
                    Attacking = true;
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
                        Attacking = false;
                        hitbox();
                    }
                    hitbox();
                    Attacking = true;
                    atk = a1[i-2];
                    image = al[i];
                    i++;
                    break;
            }
            spritecounter = 0;
        }
        System.out.println("Y: " + worldY + " | Jump: " + jmp + " | Grounded: " + grounded + " | Action: " + action + " | zHoldTime: " + zHoldTime + " | CurrentWorldX: " + CurrentWorldX + " | CurrentWorldY: " + CurrentWorldY);
        System.out.println("Frame index: " + i + " | Action: " + action + " | CollisionON: " + collisionON + " | GroundLevel: " + groundLevel + "| worldY: " + worldY + " | AnimationLocked: " + animationLocked + " | DashCounter: " + dashCounter + " | SpriteCounter: " + spritecounter);

        spritecounter++;
    }
    public void pickUpObject(int index){
        if (index!=999){
            System.out.println("obj"+ index+ " will be picked up");
            String objName = gp.obj[index].name;
            switch (objName){
                case "money":
                    moneyCount++;
                    gp.ui.showMessage(objName + " has been picked up");
                    gp.obj[index]= null; // this line will delete the object from the game panel
                    break;
                case "door":
                    if(moneyCount>=2){
                        moneyCount-=2;
                        gp.ui.showMessage(objName + " has been opened");
                        gp.obj[index]= null;
                    }
                    else {
                        gp.ui.showMessage("Get 2 coins b4 open this lmao");
                    }
                    break;
                case "pekora":
                    gp.playSE(0);
                    gp.ui.gameFinished=true;
                    gp.obj[index]= null;

            }

        }
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(atk, screenX + 45, screenY + 40, gp.tileSize * flip * 2, gp.tileSize, null);
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.setColor(Color.RED);
        g2.drawRect(screenX + attackArea.x, screenY + attackArea.y , attackArea.width, attackArea.height);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}