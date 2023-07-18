package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, shiftPressed, checkDrawTime, enterPressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// Play state
		if (gp.gameState == gp.playState) {
			switch (code) {
			case KeyEvent.VK_W -> upPressed = true;
			case KeyEvent.VK_S -> downPressed = true;
			case KeyEvent.VK_A -> leftPressed = true;
			case KeyEvent.VK_D -> rightPressed = true;
			case KeyEvent.VK_ENTER -> enterPressed = true;
			case KeyEvent.VK_P -> {
				gp.gameState = gp.pauseState;
			}
			case KeyEvent.VK_SPACE -> spacePressed = true;
			case KeyEvent.VK_SHIFT -> shiftPressed = true;
			case KeyEvent.VK_T -> {
				if (checkDrawTime) {
					checkDrawTime = false;
				} else {
					checkDrawTime = true;
				}
			}
			}
		}

		// Pause state
		else if (gp.gameState == gp.pauseState) {
			switch (code) {
			case KeyEvent.VK_P -> {
					gp.gameState = gp.playState;
			}
			case KeyEvent.VK_T -> {
				if (checkDrawTime) {
					checkDrawTime = false;
				} else {
					checkDrawTime = true;
				}
			}
			}
		}

		// Dialogue state
		else if (gp.gameState == gp.dialogueState) {
			switch (code) {
				case KeyEvent.VK_ENTER -> gp.gameState = gp.playState;
				case KeyEvent.VK_T -> {
					if (checkDrawTime) {
						checkDrawTime = false;
					} else {
						checkDrawTime = true;
					}
				}
			}
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		switch (code) {
			case KeyEvent.VK_W -> upPressed = false;
			case KeyEvent.VK_S -> downPressed = false;
			case KeyEvent.VK_A -> leftPressed = false;
			case KeyEvent.VK_D -> rightPressed = false;
			case KeyEvent.VK_ENTER -> enterPressed = false;
			case KeyEvent.VK_SPACE -> spacePressed = false;
			case KeyEvent.VK_SHIFT -> shiftPressed = false;
		}
	}

}