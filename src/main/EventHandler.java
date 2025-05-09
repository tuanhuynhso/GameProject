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
            // teleport(gp.playState);
        }
        if(hit(10,47)){
            System.out.println("hit pit");
          // teleport(gp.playState);
            }
        if(hit(10,46)){
            System.out.println("hit pit");
            // teleport(gp.playState);
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

        // Get the player's current solid area position in world space
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Properly center the eventRect inside its tile
        eventRect.x = (eventCol * gp.tileSize) + (gp.tileSize - eventRect.width) / 2;
        eventRect.y = (eventRow * gp.tileSize) + (gp.tileSize - eventRect.height) / 2;

        // Check if the player's solid area collides with this eventRect
        if (gp.player.solidArea.intersects(eventRect)) {
            hit = true;
        }

        // Add the eventRect to the debug rectangles if not already added
        if (!eventRects.contains(eventRect)) {
            eventRects.add(new Rectangle(eventRect)); // Add a new Rectangle to avoid overwriting
        }

        // Reset positions after the check
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState=gameState;
      //  gp.player.life =- 1;
    }
    public void teleport(int gameState){
        gp.gameState=gameState;
        gp.player.worldX = 32 * gp.tileSize;
        gp.player.worldY = 20 * gp.tileSize;

    }

    public void drawEventsArea(Graphics2D g2d) {
        g2d.setColor(Color.RED);

        // Iterate through and draw all debug rectangles
        for (Rectangle rect : eventRects) {
            // Convert world coordinates to screen coordinates
            int screenX = rect.x - gp.player.worldX + gp.player.screenX;
            int screenY = rect.y - gp.player.worldY + gp.player.screenY;

            // Check if the rectangle is within the visible screen area before drawing
            if (rect.x + rect.width > gp.player.worldX - gp.player.screenX &&
                    rect.x < gp.player.worldX + gp.screenWidth &&
                    rect.y + rect.height > gp.player.worldY - gp.player.screenY &&
                    rect.y < gp.player.worldY + gp.screenHeight) {
                // Draw the debug rectangle
                g2d.fillRect(screenX, screenY, rect.width, rect.height);
                g2d.drawRect(screenX, screenY, rect.width, rect.height);
            }
        }
    }


}
