package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCanceled=false;

	

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 10;
		solidArea.y = 14;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.height = 32;
		solidArea.width = 32;
		
		attackArea.width=36;
		attackArea.height=36;

		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImages();
		
		
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";

		// PLAYER STATUS
		level=1;
		maxLife = 6;
		life = maxLife;
		strength =1; // the more strength the more damage
		dexterity = 1; // the more dex the less damage receives
		exp=0;
		nextLevelExp= 5;
		coin =0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		attack=getAttackValue();//total attack value is strength and weapon
		defense=getDefenseValue();// total defense value is dex and shield
	}

	private int getDefenseValue() {
		return attack = strength * currentWeapon.attackValue;
	}

	private int getAttackValue() {
		return defense = dexterity * currentShield.defenseValue;
	}

	public void update() {
		
		if(attacking) {
			attacking();
		}else if (isAnyDirectionKeyPressed() || keyH.enterPressed) {
			if (keyH.upPressed) {

				direction = "up";
			}
			if (keyH.downPressed) {

				direction = "down";
			}
			if (keyH.leftPressed) {

				direction = "left";
			}
			if (keyH.rightPressed) {

				direction = "right";
			}
			speed = keyH.shiftPressed ? 8 : 4;

			// checkTileCollision
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// Check NPC Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// Check Monster Collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			// Check Event
			gp.eventHandler.checkEvent();

			// If collision false player can move
			if (!collisionOn && !keyH.enterPressed) {
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
			
			if(keyH.enterPressed && !attackCanceled) {
				gp.playSE(7);
				attacking=true;
				spriteCounter=0;
			}
			
			attackCanceled=false;
			gp.keyH.enterPressed = false;

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

		// Needs to be outside of key if statement
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void attacking() {
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum=1;
		}
		
		if(spriteCounter > 5 && spriteCounter <=25) {
			spriteNum =2;
			
			//Save current worldx and worldy solid area
			int currentWorldX =worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeith = solidArea.height;
			
			//Adjust player's worldx/y for the attack area
			
			switch(direction) {
			case "up" : worldY -= attackArea.height;break;
			case "down" : worldY += attackArea.height;break;
			case "left" : worldX -= attackArea.width;break;
			case "right" : worldX += attackArea.width;break;
			}
			
			//attack area becomes solid area 
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			//check monster collision with the updated worldx, worldy and solid area
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.x = solidAreaWidth;
			solidArea.y = solidAreaHeith;
			damageMonster(monsterIndex);
			
		}
		if (spriteCounter >25) {
			spriteNum=1;
			spriteCounter=0;
			attacking=false;
		}
		
	}

	public void contactMonster(int monsterIndex) {
		if (monsterIndex != 999) {
			if (!invincible) {
				gp.playSE(6);
				life -= 1;
				invincible = true;
			}
		}
	}
	
	private void damageMonster(int ind) {
		if (ind != 999) {
			if (!gp.monster[ind].invincible) {
				gp.playSE(5);
				gp.monster[ind].life -=1;
				gp.monster[ind].invincible = true;
				gp.monster[ind].damageReaction();
				
				if(gp.monster[ind].life <=0) {
					gp.monster[ind].dying=true;
				}
			}
		}
		
	}

	public void interactNPC(int npcIndex) {

		if (gp.keyH.enterPressed) {
			if (npcIndex != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[npcIndex].speak();
			}  
		}
	}

	private boolean isAnyDirectionKeyPressed() {
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			return true;
		} else {
			return false;
		}
	}

	public void getPlayerImage() {

		up1 = setup("up_1");
		up2 = setup("up_2");
		down1 = setup("down_1");
		down2 = setup("down_2");
		left1 = setup("left_1");
		left2 = setup("left_2");
		right1 = setup("right_1");
		right2 = setup("right_2");
	}

	public void getPlayerAttackImages() {
		attackUp1 = setup("player/attack_up_1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("player/attack_up_2", gp.tileSize, gp.tileSize * 2);
		attackDown1 = setup("player/attack_down_1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("player/attack_down_2", gp.tileSize, gp.tileSize * 2);
		attackLeft1 = setup("player/attack_left_1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("player/attack_left_2", gp.tileSize * 2, gp.tileSize);
		attackRight1 = setup("player/attack_right_1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("player/attack_right_2", gp.tileSize * 2, gp.tileSize);
	}

	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;

		try {
			scaledImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scaledImage;
	}

	public void pickUpObject(int index) {
		if (index != 999) {

		}
	}

	public void draw(Graphics2D g2) {

		selectAndRenderImage(direction,g2);
	}

	private void selectAndRenderImage(String direction,Graphics2D g2) {
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch (direction) {
		case "up" -> {
			if(attacking) {
				tempScreenY = screenY-gp.tileSize;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}else {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
		}
		case "down" -> {
			if(attacking) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}else {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
			}
		}
		case "left" -> {
			if(attacking) {
				tempScreenX = screenX-gp.tileSize;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}else {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
			}
		}
		case "right" -> {
			if(attacking) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}else {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
		}
		}
		
		if (invincible) {
			// 70% transparent
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		// RESET Alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));		
	}

}
