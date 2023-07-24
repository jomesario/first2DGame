package monster;

import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {

	GamePanel gp;
	public static BufferedImage dying1, dying2, dying3, dying4, dying5;

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Green Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		type = 2;

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();

	}

	public void getImage() {
		up1 = setup("monster/green_slime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("monster/green_slime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("monster/green_slime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("monster/green_slime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("monster/green_slime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("monster/green_slime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("monster/green_slime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("monster/green_slime_down_2", gp.tileSize, gp.tileSize);
		dying1 = setup("monster/green_slime_dying_1", gp.tileSize, gp.tileSize);
		dying2 = setup("monster/green_slime_dying_2", gp.tileSize, gp.tileSize);
		dying3 = setup("monster/green_slime_dying_3", gp.tileSize, gp.tileSize);
		dying4 = setup("monster/green_slime_dying_4", gp.tileSize, gp.tileSize);
		dying5 = setup("monster/green_slime_dying_5", gp.tileSize, gp.tileSize);
	}

	@Override
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}

	public void setAction() {
		actionLockCounter++;

		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(150) + 1; // pick number from 1 to 100

			if (i <= 25) {
				direction = "up";
			} else if (i <= 50) {
				direction = "down";
			} else if (i <= 75) {
				direction = "left";
			} else if (i <= 100) {
				direction = "right";
			} else if (i <= 150) {
				direction = "stop";
			}

			actionLockCounter = 0;
		}
	}

}
