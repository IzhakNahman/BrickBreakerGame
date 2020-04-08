package BallProject;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Shape {
	private int width, height;
	private Image image;
	protected int dx, dy, x, y;

	public Shape(int x, int y, int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public Shape(int x, int y, int width, int height, Image image, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = dx;
		this.dy = dy;
		this.image = image;
	}
	
	public Image imageMaker(String s) {
		ImageIcon icon;
		Image i;
		icon = new ImageIcon(s);
		i = icon.getImage();
		return i;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void makeDxPositive() {
		if (this.getDx() < 0)
			this.setDx(this.getDx() * -1);
	}

	public void makeDxNegative() {
		if (this.getDx() > 0)
			this.setDx(this.getDx() * -1);
	}

	public void makeDyPositive() {
		if (this.getDy() < 0)
			this.setDy(this.getDy() * -1);
	}

	public void makeDyNegative() {
		if (this.getDy() > 0)
			this.setDy(this.getDy() * -1);
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move() {
		this.x += this.dx;
		this.y += this.dy;
	}

	public void changeDirection() {
		dx *= -1;
		dy *= -1;
		move();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXWidth() {
		return x + width;
	}

	public int getMidXWidth() {
		return x + (width / 2);
	}

	public int getMidYHeight() {
		return y + (height / 2);
	}

	public int getYHeight() {
		return y + height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public abstract void paint(Graphics shapeGraphics);

}
