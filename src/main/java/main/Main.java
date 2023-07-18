
package main;

import javax.swing.JFrame;

/**
 * BUG Report:
 * boots not increasing speed
 * @author jose_
 */
public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Phil It");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
