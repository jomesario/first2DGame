package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
    public int worldX,worldY;
    public int speed;
    
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction ="down";
    public int spriteCounter=0;
    public int spriteNum=1;
    public BufferedImage image,image2,image3;
	public String name;
	public boolean collision= false;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn=false;
    public int actionLockCounter =0;
    String dialogues[] =new String[20];
    public int dialogIndex = 0;
    public int type; //0 player , 1 npc, 2 monster
    
    //CHARACTER STATUS
    public int maxLife;
    public int life;
    
    public boolean invincible = false;
    public int invincibleCounter=0;
    
    public Entity(GamePanel gp) {
    	this.gp = gp;	
    }
    
    public void speak() {
    	if (dialogues[dialogIndex] == null) {
			dialogIndex=0;
		}
		 gp.ui.currentDialog = dialogues[dialogIndex];	
		 dialogIndex++;
		 
		 switch (gp.player.direction) {
			 case "up":
				 direction = "down";
				 break;
			 case "down":
				 direction ="up";
				 break;
			 case "right":
				 direction = "left";
				 break;
			 case "left":
				 direction = "right";
				 break;
		 }
    }
    
    public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;

		try {
			scaledImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scaledImage;
	}
    
    public void setAction() {
    	
    }
    
    public void update() {
    	setAction();
    	
    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkObject(this, false);
    	gp.cChecker.checkEntity(this, gp.npc);
    	gp.cChecker.checkEntity(this, gp.monster);
    	boolean contactPlayer = gp.cChecker.checkPlayer(this);
    	
    	if(this.type == 2 && contactPlayer) {
    		if(!gp.player.invincible) {
    			//We can give damage
    			gp.player.life -=1;
    			gp.player.invincible=true;
    		}
    	}
    	
    	//If collision is false, entity can move
    	if (!collisionOn) {
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		spriteCounter++;
		if (spriteCounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
    }
    
    public void draw(Graphics2D g2) {
    	int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		BufferedImage image = null;
		
		if (worldX +gp.tileSize> gp.player.worldX - gp.player.screenX && 
				worldX -gp.tileSize< gp.player.worldX + gp.player.screenX&& 
				worldY +gp.tileSize> gp.player.worldY - gp.player.screenY && 
				worldY -gp.tileSize< gp.player.worldY + gp.player.screenY) {
			
			

			switch (direction) {
				case "up" -> {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
				}
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
				}
				case "left" -> {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
				}
				case "stop" -> {
					image = down1;
				}
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
    }
}