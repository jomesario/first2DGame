package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		speed = 1;
		direction = "down";
		
		getImage();
		setDialog();
	}
	
	public void getImage() {
		up1 = setup("npc/oldman_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("npc/oldman_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("npc/oldman_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("npc/oldman_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("npc/oldman_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("npc/oldman_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("npc/oldman_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("npc/oldman_right_2",gp.tileSize,gp.tileSize);
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
	
	public void speak() {
		super.speak();
		
		//Character specific stuff
	}
	
	public void setDialog() {
		dialogues[0] = "Hello my friend.";
		dialogues[1] = "Así que has venido a la isla a \nencontrar el tesoro.";
		dialogues[2] = "Solía ser un gran mago, pero ahora...\n soy un poco viejo para esas aventuras.";
		dialogues[3] = "Suerte, y que quepa la prudencia en ti.";
	}

	
}
