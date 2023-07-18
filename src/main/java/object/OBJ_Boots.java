package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{

	GamePanel gp;
	public OBJ_Boots(GamePanel gp) {
		name = "boots";
		this.gp = gp;
		try {
			image= ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Boots.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}