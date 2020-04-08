package BallProject;

import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Brick extends Shape {
	private int brickID;
	private int numStrikes;
	private String brickColor;
	private boolean brokenAvailable;
	private boolean visible;
	private Bonus bonus;

	public Brick(int x, int y, int width, int height, int brickID) {

		super(x, y, width, height, null);
		this.brickID = brickID;
		bonus = null;
		brickDefiner();


	}

	public Image Imagemaker(String s) {
		ImageIcon icon;
		Image i;
		icon = new ImageIcon(s);
		i = icon.getImage();
		return i;
	}

	public Bonus getBonus() {
		return bonus;
	}

	public void brickDefiner() {

		switch (brickID) {
		case 1:
			this.numStrikes = 1;
			this.brickColor = "Blue";
			this.brokenAvailable = true;
			this.visible = true;
			this.setImage(Imagemaker("folder/Bricks/BlueBrick1.png"));
			// System.out.println("blue");
			break;
		case 2:
			this.numStrikes = 1;
			this.brickColor = "Green";
			this.brokenAvailable = true;
			this.visible = true;
			super.setImage(Imagemaker("folder/Bricks/GreenBrick2.png"));
			break;
		case 3:
			this.numStrikes = 1;
			this.brickColor = "Orange";
			brokenAvailable = true;
			this.visible = true;
			super.setImage(Imagemaker("folder/Bricks/OrangeBrick3.png"));
			break;
		case 4:
			this.numStrikes = 2;
			this.brickColor = "Red";
			this.brokenAvailable = true;
			this.visible = true;
			super.setImage(Imagemaker("folder/Bricks/RedBrick4.png"));
			break;
		case 5:
			this.numStrikes = 3;
			this.brickColor = "Collorless";
			this.brokenAvailable = true;
			this.visible = true;
			super.setImage(Imagemaker("folder/Bricks/CollorlessBrick5.png"));
			break;
		case 6:
			this.numStrikes = 0;
			this.brickColor = "Black";
			this.brokenAvailable = false;
			this.visible = true;
			super.setImage(Imagemaker("folder/Bricks/BlackBrick6.png"));
			break;

		default:
			this.numStrikes = 0;
			this.visible = false;
			this.brickColor = "Colorless";
			this.brokenAvailable = false;
			this.setImage(null);

		}

	}

	public void randomBonus() {

		Random rand = new Random();
		int r1 = rand.nextInt(60) + 1;

		if (r1 <= 6) {
			this.bonus = new Bonus(this.getX(), this.getY(), this.getWidth(), this.getHeight(), r1);
		}

	}

	public int getBrickID() {
		return brickID;
	}

	public void setBrickID(int brickID) {
		this.brickID = brickID;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void breakBrick() {

		switch (numStrikes) {

		case 0:
			break;
		case 1:
			brickID = 0;
			break;
		case 2:
			brickID = 3;
			break;
		case 3:
			brickID = 4;
			break;

		}

		brickDefiner();

	}

	public int getNumStrikes() {
		return numStrikes;
	}

	public void setNumStrikes(int numStrikes) {
		this.numStrikes = numStrikes;
	}

	public String getBrickColor() {
		return brickColor;
	}

	public void setBrickColor(String brickColor) {
		this.brickColor = brickColor;
	}

	public boolean isBrokenAvailable() {
		return brokenAvailable;
	}

	public void setBrokenAvailable(boolean brokenAvailable) {
		this.brokenAvailable = brokenAvailable;
	}

	public void paint(Graphics g) {

		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);
	}

}
