package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    private int cd;
    private int eventRectDefaultX;
    private int eventRectDefaultY;
    private List<Rectangle> eventRects;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 8;//trigger area
        eventRect.y = 8;//trigger area
        eventRect.width= gp.tileSize;
        eventRect.height= gp.tileSize;
        eventRectDefaultX=eventRect.x;
        eventRectDefaultY=eventRect.y;
        eventRects = new ArrayList<>();


    }
    public void checkEvent(){
        if (cd > 10) {
            if (gp.currentMap == 0) {
                if (gp.player.worldY / gp.tileSize < 47) {
                    for (int i = 0; i < 50; i++) {
                        if (hit(i, 47)) {
                            System.out.println("hit pit");
                            damagePit(gp.playState);
                        }
                        if (hit(i, 48)) {
                            System.out.println("hit pit");
                            damagePit(gp.playState);
                        }
                    }
                }
                for (int i = 9; i < 13; i++) {
                    if (hit(i, 30)) {
                        System.out.println("hit pit");
                        damagePit(gp.playState);
                    }
                    if (hit(i, 31)) {
                        System.out.println("hit pit");
                        damagePit(gp.playState);
                    }
                }
                for (int i = 9; i < 12; i++) {
                    if (hit(i, 7)) {
                        teleport(gp.playState);
                    }
                    if (hit(i, 8)) {
                        teleport(gp.playState);
                    }
                    if (hit(i, 9)) {
                        teleport(gp.playState);
                    }
                    if (hit(i, 10)) {
                        teleport(gp.playState);
                    }
                }
            }
            else{
            for (int i = 48; i < 50; i++) {
                if (hit(i, 0)) {
                    gp.gameState = gp.winState;
                }
                if (hit(i, 1)) {
                    gp.gameState = gp.winState;
                }
            }
        }}
        else {
            cd++;
        }
    }
    public boolean hit(int eventCol, int eventRow) {
        boolean hit = false;

        // Get player's current solid area position in world coordinates
        gp.player.solidArea.x = gp.player.worldX ;
        gp.player.solidArea.y = gp.player.worldY ;

        // Dynamically calculate eventRect position to center it within the tile
        eventRect.x = (eventCol * gp.tileSize) + (gp.tileSize - eventRect.width) / 2;
        eventRect.y = (eventRow * gp.tileSize) + (gp.tileSize - eventRect.height) / 2;

        // Check for a collision between player and eventRect
        if (gp.player.solidArea.intersects(eventRect)) {
            hit = true;
        }

        // Add the current eventRect to debug rectangles for visual aid
        if (!eventRects.contains(eventRect)) {
            eventRects.add(new Rectangle(eventRect));
        }

        // Reset player and eventRect positions back to their default values
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState=gameState;
        gp.player.life -= 1;
        gp.player.worldX = gp.player.lastX - 20 * gp.player.lastflip;
        gp.player.worldY = gp.player.lastY - 10;
        cd = 0;
        if (gp.player.life <= 0) {
            gp.gameState = gp.deadState;
        }
    }
    public void teleport(int gameState){
        gp.currentMap = 1;
        gp.player.worldX = 100;
        gp.player.worldY = 100;
    }

    public void drawEventsArea(Graphics2D g2d) {
        // Draw trigger areas (red rectangles)
        g2d.setColor(Color.RED);
        for (Rectangle rect : eventRects) {
            // Convert trigger rectangle positions from world to screen coordinates
            int screenX = rect.x - (gp.player.worldX - gp.player.screenX);
            int screenY = rect.y - (gp.player.worldY - gp.player.screenY);

            // Draw trigger rectangle as a red outline and filled area
            g2d.drawRect(screenX, screenY, rect.width, rect.height);
            g2d.setColor(new Color(255, 0, 0, 100)); // Semi-transparent fill
            g2d.fillRect(screenX, screenY, rect.width, rect.height);
        }

        // Draw player's solid area (blue rectangle)
        g2d.setColor(Color.BLUE);

        // Calculate player's solid area position in screen space
        int playerSolidAreaScreenX = gp.player.worldX + gp.player.solidArea.x - gp.player.worldX + gp.player.screenX;
        int playerSolidAreaScreenY = gp.player.worldY + gp.player.solidArea.y - gp.player.worldY + gp.player.screenY;

        // Draw the blue solid area
        g2d.drawRect(
                playerSolidAreaScreenX,  // X position on the screen
                playerSolidAreaScreenY,  // Y position on the screen
                gp.player.solidArea.width, // Player solid area's width
                gp.player.solidArea.height // Player solid area's height
        );
        g2d.setColor(new Color(0, 0, 255, 100)); // Semi-transparent blue fill
        g2d.fillRect(
                playerSolidAreaScreenX,
                playerSolidAreaScreenY,
                gp.player.solidArea.width,
                gp.player.solidArea.height
        );
    }


}
