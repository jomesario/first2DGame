package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica,purisaB ;

	public boolean messageOn = false;
	public String message = "prompt";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialog="";

	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getClassLoader().getResourceAsStream("font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		this.g2 =g2;
		
		g2.setFont(maruMonica);
		//FUENTE MÁS FINA, PULIDA
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//PLAY STATE
		if(gp.gameState==gp.playState) {
			//Do play state Stuff later
		}
		
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}

	}
	
	public void drawDialogueScreen() {
		//Window dialogue
		int x = gp.tileSize *2;
		int y = gp.tileSize/2;
		int width= gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,26F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for (String line: currentDialog.split("\n")) {
			g2.drawString(line, x, y);
			y +=40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25 );
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80));
		String text = "Paused";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
