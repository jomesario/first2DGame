package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity{

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		name = "Green Slime";
		speed = 1;
		maxLife =4;
		life = maxLife;
		type=2;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		
	}
	
	public void getImage() {
		up1 =setup("monster/green_slime_down_1");
		up2 =setup("monster/green_slime_down_2");
		down1 =setup("monster/green_slime_down_1");
		down2 =setup("monster/green_slime_down_2");
		left1 =setup("monster/green_slime_down_1");
		left2 =setup("monster/green_slime_down_2");
		right1 =setup("monster/green_slime_down_1");
		right2 =setup("monster/green_slime_down_2");
	}
	
	public void setAction() {
actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(150)+1; //pick number from 1 to 100
			
			if(i <= 25) {
				direction ="up";
			}else if( i<=50 ) {
				direction = "down";
			}else if(i <= 75) {
				direction = "left";
			}else if (i <= 100) {
				direction = "right";
			}else if (i <= 150) {
				direction = "stop";
			}
			
			actionLockCounter = 0;
		}
	}

	
}
