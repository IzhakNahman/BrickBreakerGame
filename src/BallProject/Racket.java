package BallProject;

import java.awt.*;
import java.util.ArrayList;

public class Racket extends Shape {
	private boolean haveShoot;
	private boolean connectedBall;
	private int pointerSizeRacket;
	private int sizeArray[];
	private ArrayList<Shot> shotsArray;

	public Racket(int x, int y, int width, int height, Image image, int dx) {
		super(x, y, width, height, image, dx, 0);
		doesntHaveShoot();
		this.connectedBall = true;
		pointerSizeRacket = 1;
		sizeArray = new int[3];
		sizeArray[0] = (int) (width * 0.6);
		sizeArray[1] = (int) (width);
		sizeArray[2] = (int) (width * 1.5);
		shotsArray = new ArrayList<Shot>();
	}

	public int getPointerSizeRacket() {
		return pointerSizeRacket;
	}

	public void minimizeRacket() {
		int racketSize = this.getWidth(), pointer = this.getPointerSizeRacket(), newRacketSize = 0;
		if (pointer > 0) {

			this.setPointerSizeRacket(pointer - 1);
			this.setWidth(this.getSizeArray()[pointer - 1]);
			newRacketSize = this.getWidth();
			this.setX(this.getX() + ((racketSize - newRacketSize) / 2));

		}
	}

	public void addShot() {

		int normalWidth = sizeArray[1];
		Shot s = new Shot(this.getMidXWidth() - (normalWidth / 6 / 2), this.getY() - (int) (this.getHeight() * 3.5),
				normalWidth / 6, (int) (this.getHeight() * 3.5));

		if (shotsArray != null && shotsArray.size() > 0) {
			shotsArray.add(0, s);
		} else {
			shotsArray.add(s);
		}

	}

	public void moveShots() {
		if (shotsArray != null && shotsArray.size() > 0)
			for (int i = 0; i < this.shotsArray.size(); i++)
				shotsArray.get(i).move();
	}

	public ArrayList<Shot> getShotsArray() {
		return shotsArray;
	}

	public void setShotsArray(ArrayList<Shot> shotsArray) {
		this.shotsArray = shotsArray;
	}

	public void maximizeRacket() {

		int racketSize2 = this.getWidth(), pointer2 = this.getPointerSizeRacket(), newRacketSize2 = 0;
		if (pointer2 < 2) {

			this.setPointerSizeRacket(pointer2 + 1);
			this.setWidth(this.getSizeArray()[pointer2 + 1]);
			newRacketSize2 = this.getWidth();
			this.setX(this.getX() - ((newRacketSize2 - racketSize2) / 2));

		}
	}

	public void normalizeRacket() {
		this.setPointerSizeRacket(1);
		this.setWidth(this.getSizeArray()[1]);
	}

	public void setPointerSizeRacket(int curentSizeRacket) {
		this.pointerSizeRacket = curentSizeRacket;
	}

	public int[] getSizeArray() {
		return sizeArray;
	}

	public void move(int keyPress, int widthScreen) {

		switch (keyPress) {
		case 1:
			makeDxPositive();
			if (this.getXWidth() < widthScreen)
				this.move();
			/*
			 * else { this.setX(widthScreen - this.getWidth()); }
			 */
			break;
		case 2:
			makeDxNegative();
			if (this.getX() > 0)
				this.move();
			/*
			 * else { this.setX(0); }
			 */
			break;

		case 3:
			makeDyNegative();
			connectedBall = false;
			break;
		}
	}

	public int bonusHitCheck(Bonus bonus) {
		if (bonus.getYHeight() >= this.getYHeight() && bonus.getY() <= this.getY())
			if ((bonus.getX() >= this.getX() && bonus.getX() <= this.getXWidth())
					|| (bonus.getXWidth() >= this.getX() && bonus.getXWidth() <= this.getXWidth())) {
				bonus.setVisible(false);
				return bonus.getBonusID();
			}
		return 0;
	}

	public boolean isConnectedBall() {
		return connectedBall;
	}

	public void setConnectedBall(boolean connectedBall) {
		this.connectedBall = connectedBall;
	}

	public boolean isHaveShoot() {
		return haveShoot;
	}

	public void setHaveShoot(boolean haveShoot) {
		this.haveShoot = haveShoot;
	}

	public void haveShoot() {
		this.haveShoot = true;
		this.setImage(imageMaker("folder/BrickShooter.png"));
	}

	public void doesntHaveShoot() {
		this.haveShoot = false;
		this.setImage(imageMaker("folder/BreakerImage.png"));
		if (shotsArray != null) {
			int size = this.shotsArray.size();
			if (size > 0)
				for (int i = size-1; i >= 0; i--) {
						shotsArray.remove(i);
				}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);

	}

	public void paintShots(Graphics g) {

		for (int i = 0; i < this.shotsArray.size(); i++)
			shotsArray.get(i).paint(g);

	}

	@Override
	public String toString() {
		return "racket size: " + this.getHeight() + " " + this.getWidth();
	}

	// public boolean ballR(Ball a) { return true;}

}
