package main;

import Enemies.ENEMY_normalGangster;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyhandle implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, zPressed, pPressed, xPressed;
    //DEBUG
    public boolean checkDrawTime = false;
    public boolean checkEvents = false;
    public keyhandle(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_X) {
                xPressed = true;
            }
            if (code == KeyEvent.VK_UP){
                upPressed = true;
            }
            if (code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if (code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            if (code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if (code == KeyEvent.VK_Z){
                zPressed = true;
            }
            if (code == KeyEvent.VK_P){
               pPressed = true;
               if (gp.gameState==gp.playState){
                   gp.gameState = gp.pauseState;
               }
               else if (gp.gameState==gp.pauseState){
                   gp.gameState = gp.playState;
               }
            }
             //DEBUG
        if (code == KeyEvent.VK_T){
               checkDrawTime = !checkDrawTime;
               checkEvents = !checkEvents;
            }

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        else if (gp.gameState == gp.deadState) {
            deadState(code);
        }
        else if (gp.gameState == gp.winState) {
            winState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandNum == 1) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_Z) {
            zPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
            gp.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if (code == KeyEvent.VK_E) {
            checkEvents = !checkEvents;
        }
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0: gp.tileM.loadMap("/Maps/world01.txt", 0); break;
                case 1: gp.tileM.loadMap("/Maps/world02.txt", 1); break;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }

    public void deadState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                    gp.monsters.clear();
                    if (gp.currentMap == 0) {
                        gp.player.worldX = 100;
                        gp.player.worldY = 2100;
                    }
                    else if (gp.currentMap == 1){
                        gp.player.worldX = 100;
                        gp.player.worldY = 100;
                    }
                gp.player.life = gp.player.maxLife;
                gp.gameState = gp.playState;
                gp.player.knockbackcancel = true;
                gp.player.moneyCount = 0;
                gp.aSetter.setNPC();
                gp.aSetter.setObject();
            }
        }
    }

    public void winState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            for (int i = 0; i < gp.ui.winButtons.length; i++) {
                    if(i == 0) {
                        System.exit(0);
                    }
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_Z) {
            zPressed = false;
        }
        if (code == KeyEvent.VK_X) {
            xPressed = false;
        }
    }
        @Override
    public void keyTyped(KeyEvent e){

        }
    }