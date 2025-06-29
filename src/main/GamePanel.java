package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

import Characters.Entity;
import Characters.Player;
import Enemies.ENEMY_normalGangster;
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
    public final int maxMap=2;
    public int currentMap = 0;
    public int Mx, My;




    //FPS
    int FPS = 60;
    //SYSTEM
    public TileManager tileM = new TileManager(this);
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
    public SuperObject obj[][] = new SuperObject[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public ArrayList<ArrayList<Entity>> monsters = new ArrayList<>();

    //GAMESTATE
    public int gameState;
    public final int titleState = -1;
    public final int playState = 0;
    public final int pauseState = 1;
    public final int deadState = 2;
    public final int winState = 3;

    TileManager tileManager = new TileManager(this);
    // Your existing declarations
    public ArrayList<ENEMY_normalGangster> enemies = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Mouse motion listener for hover effects
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Mx = e.getX();
                My = e.getY();
            }
        });

        // Mouse click listener for button clicks
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gameState == titleState) {
                    for(int i = 0; i < ui.titleButtons.length; i++) {
                        if(ui.titleButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if(i == 0) {
                                gameState = playState;
                            }
                            else if(i == 1) {
                                System.exit(0);
                            }
                        }
                    }
                }
                else if(gameState == pauseState) {
                    for(int i = 0; i < ui.pauseButtons.length; i++) {
                        if(ui.pauseButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if(i == 0) {
                                gameState = playState;
                            }
                            else if(i == 1) {
                                gameState = titleState;
                                ui.commandNum = 0;
                            }
                        }
                    }
                }
                else if(gameState == deadState) {
                    for(int i = 0; i < ui.deadButtons.length; i++) {
                        if(ui.deadButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if(i == 0) {
                                monsters.clear();
                                if (currentMap == 0) {
                                    player.worldX = 100;
                                    player.worldY = 2100;
                                }
                                else if (currentMap == 1){
                                    player.worldX = 100;
                                    player.worldY = 100;
                                }
                                player.life = player.maxLife;
                                gameState = playState;
                                player.knockbackcancel = true;
                                player.moneyCount = 0;
                                aSetter.setNPC();
                                aSetter.setObject();
                            }
                        }
                    }
                }
                else if (gameState == winState) {
                    for (int i = 0; i < ui.winButtons.length; i++) {
                        if(ui.winButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if(i == 0) {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        });
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = playState;
        enemies = new ArrayList<>();
        gameState = titleState;
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
            for (Entity monster : monsters.get(currentMap)) {
                monster.update();
            }
        }
        if (gameState == pauseState || gameState == titleState || gameState == deadState) {
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
        //OBJECT
        for (int i = 0; i < obj[currentMap].length; i++) {
            if (obj[currentMap][i] != null) {
                obj[currentMap][i].draw(g2,this);
            }
        }
        //EMEMIES
        for (Entity monster : monsters.get(currentMap)) {
            if (monster != null) {
                monster.draw(g2);
            }
        }
        //PLAYER
        player.draw(g2);

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