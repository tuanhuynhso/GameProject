package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Characters.Player;

public class game_panel extends JPanel implements Runnable{
    // SCREEN SETTING //
    final int originalTileSize = 32;    //16x16 tile//
    final int  scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile//
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    
    keyhandle keyH = new keyhandle();
    Thread gameThread;
    Player player = new Player(this,keyH);

    //Default POS for player//
    int playerX = 100;
    int playerY = 100;
    int playerSPD = 4;

public game_panel() {

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

    double drawInterval = 1000000000/FPS;
    double nextDrawTime = System.nanoTime() + drawInterval;
    int drawCount = 0;  

    while(gameThread!=null){
        long currentTime = System.nanoTime();
        System.out.println("Current time: "+currentTime);
        
        update();

        repaint();

        try{
        double remainingTime = nextDrawTime - System.nanoTime();
        remainingTime = remainingTime/1000000;

        if(remainingTime < 0){
            remainingTime = 0;
        }
        
        Thread.sleep((long)remainingTime);
        
        nextDrawTime += drawInterval;

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
public void update() {
    player.update();
}
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    player.draw(g2);
    g2.dispose();
}
}  