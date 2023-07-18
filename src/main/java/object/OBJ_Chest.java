package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject{
	
	GamePanel gp;
	public OBJ_Chest(GamePanel gp) {
		name = "chest";
		this.gp = gp;
		try {
			image= ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Chest.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}