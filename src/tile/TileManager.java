package tile;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/Maps/world01.txt", 0);
        loadMap("/Maps/world02.txt", 1);
    }

    public void getTileImage() {
        setup(0, "sky", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "sky", false);
        setup(5, "sand", false);
    }
    public void setup(int index, String imagePath, boolean collision) {
        UtilityTools ut = new UtilityTools();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/"+imagePath+".png"));
            tile[index].image= ut.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath,int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line != null) {
                    String[] numbers = line.split(" ");
                    for (col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[map][col][row] = num;
                    }
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int tileNum= mapTileNum[gp.currentMap][worldCol][worldRow];
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (
                    worldX + 2*gp.tileSize > gp.player.worldX - gp.player.screenX &&
                            worldX - 2*gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
            ) {
                g2d.drawImage(tile[mapTileNum[gp.currentMap][worldCol][worldRow]].image, screenX, screenY,  null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}