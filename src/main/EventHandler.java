package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    private int eventRectDefaultX;
    private int eventRectDefaultY;
    private List<Rectangle> eventRects;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 8;//trigger area
        eventRect.y = 8;//trigger area
        eventRect.width=8;
        eventRect.height=8;
        eventRectDefaultX=eventRect.x;
        eventRectDefaultY=eventRect.y;
        eventRects = new ArrayList<>();


    }
    public void checkEvent(){
        if(hit(10,45)){
            System.out.println("hit pit");
             damagePit(gp.playState);
        }
        if(hit(10,47)){
            System.out.println("hit pit");
          // teleport(gp.playState);
            }
        if(hit(10,46)){
            System.out.println("hit pit");
             damagePit(gp.playState);
        }
        if(hit(10,48)){
            System.out.println("hit pit");
            // teleport(gp.playState);
        }
        if(hit(10,49)){
            System.out.println("hit pit");
            // teleport(gp.playState);
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
        gp.player.life =- 1;
    }
    public void teleport(int gameState){
        gp.gameState=gameState;
        gp.player.worldX = 32 * gp.tileSize;
        gp.player.worldY = 20 * gp.tileSize;

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
