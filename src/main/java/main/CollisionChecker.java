package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;

	}

	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		for (int i = 0; i < gp.obj.length; i++)
			if (gp.obj[i] != null) {
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if(gp.obj[i].collision) {
						entity.collisionOn = true;
					}
					if (player) {
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		return index;
	}

	public void checkTile(Entity ent) {
		int entityLeftWorldX = ent.worldX + ent.solidArea.x;
		int entityRightWorldX = ent.worldX + ent.solidArea.x + ent.solidArea.width;
		int entityTopWorldY = ent.worldY + ent.solidArea.y;
		int entityBottomWorldY = ent.worldY + ent.solidArea.y + ent.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (ent.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				ent.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				ent.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				ent.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				ent.collisionOn = true;
			}
			break;
		}

	}
	
	//NPC OR MONSTER COLLISION
	public int checkEntity(Entity entity, Entity []target) {
		int index = 999;

		for (int i = 0; i < target.length; i++) {
			
		
			if (target[i] != null) {
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;		
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
			}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		if (gp.player != null) {
			// Get entity's solid area position
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;

			// Get the object solid area position
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

			switch (entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				break;
			}
			
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true; 
				contactPlayer = true;
		}
			
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		}
		return contactPlayer;
	}

}
