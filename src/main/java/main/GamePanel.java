package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

/**
 *
 * @author jose_
 */
public class GamePanel extends JPanel implements Runnable {
	// SCREEN SETTINGS

	final int originalTileSize = 16; // 16*16
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48*48
	public final int maxScreenColumns = 16;
	public final int maxScreenRows = 12;

	// World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	public final int screenWidth = tileSize * maxScreenColumns; // 768 pixel
	public final int screenHeight = tileSize * maxScreenRows; // 576 pixel

	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eventHandler = new EventHandler(this);
	Thread gameThread;

	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int titleState = 0;

	// Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();

	// FPS
	final int FPS = 60;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.GRAY);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setUpGame() {
		aSetter.setObject();
		aSetter.setNpc();
		aSetter.setMonster();
		// playMusic(0);
		gameState = titleState;

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;// 0.016666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				// System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {
		if (gameState == playState) {
			// PLAYER
			player.update();

			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			
			//Monster
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					monster[i].update();
				}
			}
		}
		if (gameState == pauseState) {
			// nothing
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Graphics 2D has more functions than graphics
		Graphics2D g2 = (Graphics2D) g;

		// DEBUG START
		long drawStart = 0;
		if (keyH.checkDrawTime) {
			drawStart = System.nanoTime();
		}

		// TITLE Screen
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// OTHERS
		else {
			// TILE
			tileM.draw(g2);

			entityList.add(player);

			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}

			// OBJ
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			//MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			//SORT 
			Collections.sort(entityList,new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
				}
				
			});
			
			//Draw entities
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			//Empty Entity list
			entityList.clear();
			
			// UI
			ui.draw(g2);
		}

		// DEBUG END
		long drawEnd = 0;
		drawEnd = System.nanoTime();
		if (keyH.checkDrawTime) {
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}

		g2.dispose();

	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
