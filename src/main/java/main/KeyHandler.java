package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,spacePressed,
            shiftPressed, checkDrawTime;
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public KeyHandler(GamePanel gp) {
    	this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed=true;
            case KeyEvent.VK_P -> {
            	if(gp.gameState == gp.playState) {
            		gp.gameState = gp.pauseState;
            	}else if(gp.gameState==gp.pauseState){
            		gp.gameState = gp.playState;
            	}
            	rightPressed=true;
            }
            case KeyEvent.VK_SPACE -> spacePressed=true;
            case KeyEvent.VK_SHIFT -> shiftPressed= true;
            case KeyEvent.VK_T -> {
            	if (checkDrawTime) {
            		checkDrawTime = false;
            	}else {
            		checkDrawTime = true;
            	}
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        switch (code){
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed=false;
            case KeyEvent.VK_SPACE -> spacePressed=false;
            case KeyEvent.VK_SHIFT -> shiftPressed= false;
        }
    }

    
}