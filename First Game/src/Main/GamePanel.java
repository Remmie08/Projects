package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import Entity.Player;
import Tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	final int originalTileSize = 16;// 16 x 16 tiles
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;// 48 x 48 tiles
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;//576 pixels
	
	// WOLRD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, keyH);
	
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.WHITE);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void StartGameThread(){
		
		gameThread = new Thread(this);
		gameThread.start();
	}

//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;// 0.01666 seconds
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//			
////			long currentTime = System.nanoTime();
////			long currentTime2 = System.currentTimeMillis();// can use but nano more precise
////			System.out.println("Current Time: " + currentTime);
//			
//			//1 UPDATE: update info such as character positions
//			update();
//			
//			//2 DRAW: draw the screen with the updated info
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;
//				
//				if(remainingTime < 0){
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	public void run() {
		
		double drawInterval = 1000000000/FPS;// 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
			//1 UPDATE: update info such as character positions
			update();
			
			//2 DRAW: draw the screen with the updated info
			repaint();
			
			delta--;
			drawCount++;
			
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update(){
		
		player.update();
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}

}
