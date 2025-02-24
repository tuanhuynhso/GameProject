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
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
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
            while(col< gp.maxScreenCol && row<gp.maxScreenRow){
                String line = br.readLine();//read one line in the text file
                while(col<gp.maxScreenCol){
                    String numbers[]= line.split(" ");//split numbers in that line into an array

                    int num=Integer.parseInt(numbers[col]);//convert string to int

                    mapTileNum[col][row]=num; //assign the exact tile into the 2D array
                    col++;//moving to the next col
                }
                if(col==gp.maxScreenCol){
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
        int x =0;
        int y =0;
        int col =0;
        int row =0;

        while(col<gp.maxScreenCol && row<gp.maxScreenRow){

            //int tileNum = mapTileNum[row][col];
            g2d.drawImage(tile[mapTileNum[col][row]].image,x,y,gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;


            if (col==gp.maxScreenCol){
                col=0;
                x=0;
                row++;
                y+=gp.tileSize;
            }


        }
    }
}
