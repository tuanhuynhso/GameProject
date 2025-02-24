package Characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.keyhandle;

public class Player extends Entity {

    public int zHoldTime;
    GamePanel gp;
    keyhandle keyH;
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

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        spd = 10;
        jmp = 0;
        jumpvl = 0;
        jmpfrc = 30;
        ground = 500;
        grounded = false;
        action = "l";
        air = false;
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
            for (int i = 0; i < 6; i++){
                il[i] = ImageIO.read(getClass().getResource("/Player/Idle/L/tile" + String.format("%03d", i) + ".png"));
                ir[i] = ImageIO.read(getClass().getResource("/Player/Idle/R/tile" + String.format("%03d", i) + ".png"));
            }
            for (int i = 0; i < 6; i++){
                al[i] = ImageIO.read(getClass().getResource("/Player/Attacking/L/tile" + String.format("%03d", i) + ".png"));
                ar[i] = ImageIO.read(getClass().getResource("/Player/Attacking/R/tile" + String.format("%03d", i) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (grounded == false) {
            worldY -= jmp;
            jmp -= 1;
            grounded = false;
            if (jmp>0){
                air = true;
            }
            else{
                air = false;
            }
        }
        if (worldY >= ground) {
                jmp = 0;
            worldY -=jmp;
            worldY = ground;
            grounded = true;
        }
        if (keyH.upPressed) {
            if (grounded == true) {
                jmp = jmpfrc;
                i=0;
                grounded=false;
                if (action == "l" || action.equals("il")){
                    action = "jl";
                }
                else{
                    action = "jr";
                }
            }
        }
        if (keyH.downPressed) {
            jmp -= 1;
        }
        if (keyH.rightPressed && zHoldTime == 0 && animationLocked == false) {
            worldX += spd;
            if (grounded==true && action!= "jr"){
                action = "r";
            }
            else {
                action = "jr";
            }
        }
        if (keyH.leftPressed && zHoldTime == 0 && animationLocked == false) {
            worldX -= spd;
            if (grounded==true && action!= "jl"){
            action = "l";
            }
            else {
                action = "jl";
            }
        }
        if (keyH.downPressed==false && keyH.leftPressed==false && keyH.rightPressed==false && keyH.upPressed==false && zHoldTime == 0 && animationLocked == false){
            if(action=="l" || action=="al"){
                action = "il";
            }
            if(action=="r" || action=="ar"){
                action = "ir";
            } 
        }
        if (keyH.zPressed == true && zHoldTime==0){
            if (action=="l" || action=="il"){
            action = "al";
            }
            else if (action=="r" || action=="ir"){
            action = "ar";
            }
            else if (action=="jl"){
                //add stopping animation as well as aiming with mouse via rotation
            }
            zHoldTime++;
            spd = 10;
            animationLocked = true;
            i=0;
        }
        else if (keyH.zPressed == false && animationLocked==false){
            zHoldTime=0;
            spd = 20;
        }

        //animation//
        if (spritecounter>=5){
            if(action=="jl"){
            if (air==false && grounded==false){
                if (i==8) {
                    image = jl[i];
                }
                if (i<8){
                    image = jl[8];
                    i=8;
                }
            }
            else if (i<7){
                image = jl[i];
                i++;
            }
            if (grounded==true){
                if (i<11){
                    image = jl[i];
                    i++;
                }
                else{
                    action = "il";
                }
            }
        }
        else if(action=="jr"){
            if (air==false && grounded==false){
                if (i==8) {
                    image = jr[i];
                }
                if (i<8){
                    image = jr[8];
                    i=8;
                }
            }
            else if (i<7){
                image = jr[i];
                i++;
            }
            if (grounded){
                if (i<11){
                    image = jr[i];
                    i++;
                }
                else{
                    action = "ir";
                }
            }
        }
        
        else if (action.equals("r")) {
            if (i >= 8) i = 0; // Reset BEFORE setting the image
            image = r[i];
            i++;
        }
        else if (action.equals("l")) {
            if (i >= 8) i = 0; // Reset BEFORE setting the image
            image = l[i];
            i++;
        }
        else if(action=="il"){
            i = (i + 1) % 6; 
            image = il[i];
        }
        else if(action=="ir"){
            i = (i + 1) % 6; 
            image = ir[i];
        }
        if (action=="ar"){
            if (i>=5){
                i=3;
                animationLocked = false;
            }
           image = ar[i];
           i++;
        }
        if (action=="al"){
            if (i>=5){
                i=3;
                animationLocked = false;
            }
           image = al[i];
           i++;
        }
        spritecounter=0;
    }
    System.out.println("Y: " + worldY + " | Jump: " + jmp + " | Grounded: " + grounded + " | Action: " + action + " | zHoldTime: " + zHoldTime);
    System.out.println("Frame index: " + i + " | Action: " + action);

        spritecounter++;

    }

    public void draw(Graphics2D g2) {
    
        g2.drawImage(image, worldX, worldY, gp.tileSize*2, gp.tileSize*2, null);
    
    
    //    g2.setColor(Color.white);
    //    g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}