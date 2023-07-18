package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		speed = 1;
		direction = "down";
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("npc/oldman_up_1");
		up2 = setup("npc/oldman_up_2");
		down1 = setup("npc/oldman_down_1");
		down2 = setup("npc/oldman_down_2");
		left1 = setup("npc/oldman_left_1");
		left2 = setup("npc/oldman_left_2");
		right1 = setup("npc/oldman_right_1");
		right2 = setup("npc/oldman_right_2");
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
