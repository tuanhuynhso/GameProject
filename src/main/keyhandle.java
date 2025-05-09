package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyhandle implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, zPressed,pPressed;
    //DEBUG
    public boolean checkDrawTime = false;
    public boolean checkEvents = false;
    public keyhandle(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

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
    }
        @Override
    public void keyTyped(KeyEvent e){

        }
    }