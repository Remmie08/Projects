package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;

	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		setdefaultValues();
		getPlayerImage();
	}
	
	public void setdefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY= gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/guy_left_1.png.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/guy_left_2.png.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/guy_still_1.png.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/player/guy_right_1.png.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/guy_right_2.png.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/guy_still_2.png.png"));
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/guy_up_1.png.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/guy_up_2.png.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/guy_still_3.png.png"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/player/guy_down_1.png.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/guy_down_2.png.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/guy_still_4.png.png"));

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
				worldY -= speed;
			}
			if(keyH.downPressed == true) {
				direction = "down";
				worldY += speed;
			}
			if(keyH.leftPressed == true) {
				direction = "left";
				worldX -= speed;
			}
			if(keyH.rightPressed == true) {
				direction = "right";
				worldX += speed;
			}
			
			spriteCounter++;
			if(spriteCounter > 8) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
//		else {
//			spriteNum = getLastKey();
//		}
	}
	
	public int getLastKey() {
		
		int lastKey = keyH.getLastKeyPressed();
		if(lastKey == KeyEvent.VK_W || lastKey == KeyEvent.VK_S || lastKey == KeyEvent.VK_A || lastKey == KeyEvent.VK_D ) {
			return 3;
		}
		return spriteNum;
	}

	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.WHITE);		
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;
		switch(direction) {
		case"up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			if(spriteNum == 3) {
				image = up3;
			}
			break;
		case"down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			if(spriteNum == 3) {
				image = down3;
			}
			break;
		case"left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			if(spriteNum == 3) {
				image = left3;
			}
			break;
		case"right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			if(spriteNum == 3) {
				image = right3;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
