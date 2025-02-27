package tile;

//import main.CollisionChecker;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/Maps/map01.txt");
    }
    public void getTileImage() {

        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col< gp.maxWorldCol && row<gp.maxWorldRow){
                String line = br.readLine();//read one line in the text file
                while(col<gp.maxWorldCol){
                    String numbers[]= line.split(" ");//split numbers in that line into an array

                    int num=Integer.parseInt(numbers[col]);//convert string to int

                    mapTileNum[col][row]=num; //assign the exact tile into the 2D array
                    col++;//moving to the next col
                }
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                    //if the line is full, move to the next line
                }

            }
            br.close();
        }catch (Exception e){


        }
    }



    public void draw(Graphics g2d) {

        int worldCol =0;
        int worldRow =0;

        while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow){

            //int tileNum = mapTileNum[row][col];
            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            int screenX= worldX - gp.player.worldX + gp.player.screenX;
            int screenY= worldY - gp.player.worldY + gp.player.screenY;

            if(
            worldX+gp.tileSize>gp.player.worldX-gp.player.screenX&&
            worldX-gp.tileSize<gp.player.worldX+gp.player.screenX&&
            worldY+gp.tileSize>gp.player.worldY-gp.player.screenY&&
            worldY-gp.tileSize<gp.player.worldY+gp.player.screenY){


                g2d.drawImage(tile[mapTileNum[worldCol][worldRow]].image,screenX,screenY,gp.tileSize, gp.tileSize, null);
            }

            worldCol++;


            if (worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }


        }
    }
}
