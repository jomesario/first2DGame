package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}

	public void checkEvent() {
		if (hit(27, 15, "right")) {
			damagePit(gp.dialogueState);
		}
		if(hit(23,12,"up")){
			healingPool(gp.dialogueState);
		}
	}

	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialog = "You fall into a pit!";
		gp.player.life -= 1;
	}

	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;

		//Getting player current solid area positions
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		//EventRect solid area positions
		eventRect.x = eventCol * gp.tileSize + eventRect.x;
		eventRect.y = eventRow * gp.tileSize + eventRect.y;
		
		if (gp.player.solidArea.intersects(eventRect)) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.equals("any")) {
				hit = true;
			}
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;

		return hit;
	}
	
	public void healingPool(int gameState) {
		if(gp.keyH.enterPressed) {
			gp.gameState = gameState;
			gp.ui.currentDialog = "Tomaste agua!\n Tu vida se regeneró";
			gp.player.life +=1;
		}
	}

}
