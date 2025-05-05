package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Characters.Player;
import tile.TileManager;
import Object.SuperObject;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTING //
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    //WORLD SETTINGS

    public final int maxWorldCol= 50;
    public final int maxWorldRow= 50;
    public final int maxMap=10;
    public int currentMap = 0;
    public final int worldWidth =  maxWorldCol*tileSize;
    public final int worldHeight =  maxWorldRow*tileSize;



    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    keyhandle keyH = new keyhandle();
    public UI ui = new UI(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    // Default POS for player
    int playerX = 100;
    int playerY = 100;
    int playerSPD = 4;

    TileManager tileManager = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
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
        Graphics2D g2 = (Graphics2D) g;
        //TILE
        tileManager.draw(g2);
        //OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2,this);
            }
        }
        //PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);

        g2.dispose();

    }
}