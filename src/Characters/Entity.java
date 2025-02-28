package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {

    public int worldX, worldY, i;
    public int spd;
    public int jmp, jumpvl, jmpfrc, ground;
    public boolean grounded, air, animationLocked;
    public BufferedImage jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8, jl9, jl10, jl11, jl12, jr1, jr2, jr3, jr4, jr5, jr6, jr7, jr8, jr9, jr10, jr11, jr12, l1, l2, l3, l4, l5, l6, l7, l8, r1, r2, r3, r4, r5, r6, r7,r8;
    public String action, keyPressed;
    public int spritecounter=0;
    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean collisionON;
    public int groundLevel;
}
