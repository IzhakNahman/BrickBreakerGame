package BallProject;

import java.awt.*;

public class Ball extends Shape {
	private static final int DXBALL = 6;
	private static final int DYBALL = 8;
	private boolean fireBall;
	private int radius;

	public Ball(int x, int y, int radius, Image image) {
		super(x, y, radius * 2, radius * 2, image, DXBALL, DYBALL);
		this.radius = radius;
		setToBall();

		fireBall = false;
	}

	public boolean isFireBall() {
		return fireBall;
	}

	public void setToBall() {
		this.fireBall = false;
		this.setImage(imageMaker("folder/BallImage.png"));
	}

	public void setToFireBall() {
		this.fireBall = true;
		this.setImage(imageMaker("folder/FireBallImage.png"));
	}

	public boolean wallHitCheck(int widthScreen, int heightScreen) {
		if (this.getXWidth() >= widthScreen) {
			this.makeDxNegative();
		} else if (this.getX() <= 0) {
			this.makeDxPositive();
		} else if (this.getY() <= 0) {
			this.makeDyPositive();
		} else if (this.getYHeight() >= heightScreen) {
			return false;
		}

		return true;

	}
	

	public void setDxDyToNormal() {
		this.setDx(DXBALL);
		this.setDy(DYBALL);
	}

	public boolean racketHitCheck(Racket racket) {
		if (this.getYHeight() >= racket.getY() && this.getYHeight() <= racket.getYHeight()) {

			int xRacket = racket.getX();
			int racketSection = racket.getWidth() / 6;
			int xMidBall = this.getMidXWidth();
			int xWhideBall = this.getXWidth();
			int xBall = this.getX();

			if (xBall >= xRacket - this.getWidth()&& xMidBall < xRacket + racketSection) {
				this.setDx(DXBALL + DXBALL / 2);
				this.makeDxNegative();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 1);
				return true;

			} else if (xMidBall >= xRacket + racketSection && xMidBall < xRacket + (racketSection * 2)) {
				this.setDx(DXBALL);
				this.makeDxNegative();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 2);
				return true;

			} else if (xMidBall >= xRacket + (racketSection * 2) && xMidBall < xRacket + (racketSection * 3)) {
				this.setDx(DXBALL - DXBALL / 2);
				this.makeDxNegative();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 3);
				return true;

			} else if (xMidBall >= xRacket + (racketSection * 3) && xMidBall < xRacket + (racketSection * 4)) {

				this.setDx(DXBALL - DXBALL / 2);
				this.makeDxPositive();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 4);
				return true;

			} else if (xMidBall >= xRacket + (racketSection * 4) && xMidBall < xRacket + (racketSection * 5)) {
				this.setDx(DXBALL);
				this.makeDxPositive();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 5);
				return true;

			} else if (xMidBall >= xRacket + (racketSection * 5) && xWhideBall <= xRacket + (racketSection * 6) + this.getWidth()) {
				this.setDx(DXBALL + DXBALL / 2);
				this.makeDxPositive();
				this.makeDyNegative();
				System.out.println("ball x: " + xMidBall + " racket: " + 6);
				return true;
			}

		}

		return false;

	}

	public boolean brickHitCheck(Brick brick) {

		int xMidBall = this.getMidXWidth();
		int xBall = this.getX();
		int xWidth = this.getXWidth();

		int yMidBall = this.getMidYHeight();
		int yBall = this.getY();
		int yHeight = this.getYHeight();

		if (brick.getVisible() == true) {
			if ((yBall <= brick.getYHeight() && yBall >= brick.getYHeight() - this.radius)
					&& (xMidBall >= brick.getX() && xMidBall <= brick.getXWidth())) {
				if (!this.isFireBall()) {
					this.makeDyPositive();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((yHeight >= brick.getY() && yHeight <= brick.getY() + this.radius)
					&& (xMidBall >= brick.getX() && xMidBall <= brick.getXWidth())) {
				if (!this.isFireBall()) {
					this.makeDyNegative();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((xWidth >= brick.getX() && xWidth <= brick.getX() + this.radius)
					&& (yMidBall >= brick.getY() && yMidBall <= brick.getYHeight())) {
				if (!this.isFireBall()) {
					this.makeDxNegative();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((xBall <= brick.getXWidth() && xBall >= brick.getXWidth() - this.radius)
					&& (yMidBall >= brick.getY() && yMidBall <= brick.getYHeight())) {
				if (!this.isFireBall()) {
					this.makeDxPositive();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;

			}
		}
		return false;

	}
	
	
	public boolean brickHitSecondCheck(Brick brick) {

		int xBall = this.getX();
		int xWidth = this.getXWidth();

		int yBall = this.getY();
		int yHeight = this.getYHeight();

		if (brick.getVisible() == true) {
			if ((yBall <= brick.getYHeight() && yBall >= brick.getYHeight() - this.radius)
					&& ((xBall >= brick.getX() && xBall <= brick.getXWidth())||(xWidth >= brick.getX() && xWidth <= brick.getXWidth()))) {
				if (!this.isFireBall()) {
					this.makeDyPositive();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((yHeight >= brick.getY() && yHeight <= brick.getY() + this.radius)
					&& ((xBall >= brick.getX() && xBall <= brick.getXWidth())||(xWidth >= brick.getX() && xWidth <= brick.getXWidth()))) {
				if (!this.isFireBall()) {
					this.makeDyNegative();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((xWidth >= brick.getX() && xWidth <= brick.getX() + this.radius)
					&& ((yBall >= brick.getY() && yBall <= brick.getYHeight())|| (yHeight >= brick.getY() && yHeight <= brick.getYHeight()))) {
				if (!this.isFireBall()) {
					this.makeDxNegative();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;
			} else if ((xBall <= brick.getXWidth() && xBall >= brick.getXWidth() - this.radius)
					&& ((yBall >= brick.getY() && yBall <= brick.getYHeight())|| (yHeight >= brick.getY() && yHeight <= brick.getYHeight()))) {
				if (!this.isFireBall()) {
					this.makeDxPositive();
					brick.breakBrick();
					this.move();
				} else {
					brick.setBrickID(0);
					brick.breakBrick();
				}
				return true;

			}
		}
		return false;

	}


	public void move(int widthScreen, int heightScreen, Racket racket) {

		if (wallHitCheck(widthScreen, heightScreen)) {
			this.x += this.dx;
			this.y += this.dy;
		}
		if (racketHitCheck(racket)) {
			this.x += this.dx;
			this.y += this.dy;
		}
	}

	public void setFireBall(boolean fireBall) {
		this.fireBall = fireBall;
	}

	public int xRadius() {
		return super.getX() + radius;
	}

	public int xDiameter() {
		return super.getX() + radius * 2;
	}

	public int yRadius() {
		return super.getY() + radius;
	}

	public int yDiameter() {
		return super.getY() + radius * 2;
	}

	public void paint(Graphics g) {

		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);
	}

}
