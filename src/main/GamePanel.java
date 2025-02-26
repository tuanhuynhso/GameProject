package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Characters.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTING //
    final int originalTileSize = 16;    //16x16 tile//
    final int  scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile//
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    keyhandle keyH = new keyhandle();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    Player player = new Player(this,keyH);

    //Default POS for player//
    int playerX = 100;
    int playerY = 100;
    int playerSPD = 4;

    TileManager tileManager= new TileManager(this);

public GamePanel() {

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
}

public void startGameThread() {

    gameThread = new Thread(this); 
    gameThread.start();
}

@Override
public void run(){

    double drawInterval = 1000000000 / FPS; // 0.0166666 second
    double delta= 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    long drawCount = 0;


    while (gameThread != null) {
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawInterval;
        timer += currentTime - lastTime;
        lastTime = currentTime;

        if (delta >= 1) {
            update();
            repaint();
            delta--;
            drawCount++;
        }
        if (timer >= 1000000000) {
            System.out.println("FPS: " + FPS);
            timer = 0;
            drawCount = 0;
        }
    }
}
public void update() {
    player.update();
}
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    tileManager.draw(g2);
    player.draw(g2);
    g2.dispose();

}
}  