package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		name = "door";
		this.gp=gp;
		try {
			image= ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Door.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision=true;
	}
}
