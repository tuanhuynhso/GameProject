package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import Characters.Entity;
import Characters.Player;
import tile.TileManager;


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
    public int currentMap = 1;
    public int Mx, My;




    //FPS
    int FPS = 60;
    //SYSTEM
    TileManager tileM = new TileManager(this);
    keyhandle keyH = new keyhandle(this);
    Sound music = new Sound();
    Sound SE= new Sound();
    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    //ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAMESTATE
    public int gameState;
    public final int playState = 0;
    public final int pauseState = 1;


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
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Mx = e.getX();
                My = e.getY();
            }
        });
    }
    public void setupGame() {
        aSetter.setObject();
        //playMusic(index of a song you wanna play);
        gameState = playState;

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
              //  System.out.println("FPS: " + FPS);
                timer = 0;
                drawCount = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        //TILE
        tileManager.draw(g2);

        //ENTITIES (NPCs and Objects)
        entityList.add(player);
//        for (int i = 0; i < npc.length; i++) {
//            if(npc[i]!= null){
//                entityList.add(npc[i]);
//            }
//        }
        for(int i = 0; i< obj.length; i++){
            if(obj[i]!=null){
                entityList.add(obj[i]);
            }
        }
        //SORT
        Collections.sort(entityList, (e1, e2) -> {
            int result = Integer.compare(e1.worldY, e2.worldY);
            return result;
        });

        //DRAW ENTITIES
        for(int i = 0; i< entityList.size(); i++){
            entityList.get(i).draw(g2);
        }
        for (int i = 0; i < obj.length; i++) {
            entityList.remove(obj[i]);
        }


        //PLAYER


        //UI
        ui.draw(g2);
        //DEBUG
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;
            g2.drawString("Draw time: "+ passedTime, 10, 200);
            System.out.println("Draw time: " + passedTime);
        }
        if(keyH.checkEvents){
            eHandler.drawEventsArea(g2);
        }
        g2.dispose();

    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {
        SE.setFile(i);
        SE.play();
    }
}