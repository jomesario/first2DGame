package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
	GamePanel gp;
	public OBJ_Key(GamePanel gp) {
		name = "key";
		this.gp = gp;
		try {
			image= ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Key.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize );
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
