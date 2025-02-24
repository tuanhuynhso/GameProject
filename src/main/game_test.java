package main;

import javax.swing.JFrame;

public class game_test {
    public static void main(String[] args){

    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("LOL");
    
    game_panel gamePanel = new game_panel();
    window.add(gamePanel);
    
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);
 
    gamePanel.startGameThread();
    }
}
