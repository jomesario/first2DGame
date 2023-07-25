	package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica,purisaB ;
	BufferedImage heart_full,heart_half,heart_blank;
	UtilityTool uTool = new UtilityTool();
	public int commandNumb= 0;

	public boolean messageOn = false;
	public String message = "prompt";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialog="";
	public int titleScreenState=0; // 0 : first screen, 1 : second screen

	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getClassLoader().getResourceAsStream("font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		 
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		this.g2 =g2;
		
		//Title State
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		g2.setFont(maruMonica);
		//FUENTE MÁS FINA, PULIDA
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//PLAY STATE
		if(gp.gameState==gp.playState) {
			drawPlayerLife();
		}
		
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		//CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
		}

	}
	
	private void drawCharacterScreen() {
		//Create a Frame
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//TEXT
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		//NAMES
		drawNames(textX,textY,lineHeight);
		
		//VALUES
		
		int tailX = (frameX + frameWidth)-30;
		//RESET TEXT Y
		textY=frameY +gp.tileSize;
		
		drawValues(textY, lineHeight, tailX);
	}

	private void drawValues(int textY, final int lineHeight, int tailX) {
		int textX;
		String value;
		value = String.valueOf(gp.player.level);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.life+"/"+ gp.player.maxLife);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = uTool.getXForAlignToRightText(value, g2, gp, tailX);
		g2.drawString(value, textX, textY);	
		textY+=lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-14,null);
		textY+=gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-14,null);
	}

	private void drawNames(int textX, int textY, int lineHeight) {
		g2.drawString("Level",textX,textY);
		textY+=lineHeight;
		g2.drawString("Life",textX,textY);
		textY+=lineHeight;
		g2.drawString("Strength",textX,textY);
		textY+=lineHeight;
		g2.drawString("Dexterity",textX,textY);
		textY+=lineHeight;
		g2.drawString("Attack",textX,textY);
		textY+=lineHeight;
		g2.drawString("Defense",textX,textY);
		textY+=lineHeight;
		g2.drawString("Exp",textX,textY);
		textY+=lineHeight;
		g2.drawString("Next Level",textX,textY);
		textY+=lineHeight;
		g2.drawString("Coin",textX,textY);
		textY+=lineHeight+20;
		g2.drawString("Weapon",textX,textY);
		textY+=lineHeight+15;
		g2.drawString("Shield",textX,textY);
		textY+=lineHeight;
	}

	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//Draw blank heart
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		//RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//Draw current life
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i<gp.player.life) {
				g2.drawImage(heart_full, x, y, null);	
			}
			i++;
			x += gp.tileSize;
		}
		
	}
	
	public void drawTitleScreen() {
		//TITLE NAME
		
		if (titleScreenState ==0) {
			g2.setFont(maruMonica);
			//background color
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
			String text = "Phil it";
			int x = uTool.getXForCenteredText(text, g2, gp);
			int y = gp.tileSize*3;
			
			//shadow
			g2.setColor(Color.gray);
			g2.drawString(text,x+5,y+5);
			//main color
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//Blue boy image
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2,  null);
			
			//Menu
			drawTitleScreenMenu(text,x,y);
		}else if (titleScreenState == 1) {
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your class";
			int x = uTool.getXForCenteredText(text, g2, gp);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);	
			
			text = "Fighter";
			x = uTool.getXForCenteredText(text, g2, gp);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNumb == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Sorcerer";
			x = uTool.getXForCenteredText(text, g2, gp);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNumb == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Thief";
			x = uTool.getXForCenteredText(text, g2, gp);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			if(commandNumb == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = uTool.getXForCenteredText(text, g2, gp);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNumb == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}	
		}
	}
	
	private void drawTitleScreenMenu(String text, int x, int y) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "New Game ";
		x = uTool.getXForCenteredText(text, g2, gp);
		y+= gp.tileSize*3;
		g2.drawString(text, x, y);
		if(commandNumb == 0) {
			g2.drawString(">",x-gp.tileSize,y); 
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "Load Game ";
		x = uTool.getXForCenteredText(text, g2, gp);
		y+= gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNumb == 1) {
			g2.drawString(">",x-gp.tileSize,y); 
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "Quit";
		x = uTool.getXForCenteredText(text, g2, gp);
		y+= gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNumb == 2) {
			g2.drawString(">",x-gp.tileSize,y); 
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
		int x = uTool.getXForCenteredText(text,g2,gp);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}
	

}
