package BallProject;

import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;

public class Levels {

	private Brick[][] brickMatrix;
	private int level;
	private int windowWidth, windowHeight;
	private int sumPoints = 0;
	private Stack<Points> pointsStack;

	public Levels(int level, int windowWidth, int windowHeight) {

		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.level = level;
		createPointsStack();
		levelDefiner();

	}

	public int getSumPoints() {
		return sumPoints;
	}

	public void setSumPoints(int sumPoints) {
		this.sumPoints = sumPoints;
	}

	public void createPointsStack() {

		pointsStack = new Stack<Points>();
		int x = windowWidth - ((windowWidth / 80) * 9) - 45;
		int y = 10;
		int width = windowWidth / 80;
		int height = windowHeight / 40;

		for (int i = 0; i < 9; i++) {
			Points p;
			if (sumPoints > 0)
				p = new Points(x, y, width, height, 0);
			else
				p = new Points(x, y, width, height, -1);
			pointsStack.push(p);
			x += width;
			x += 5;
		}

	}

	public void editPointsStack() {

		pointsStack.clear();
		int x = windowWidth - 10 - windowWidth / 80;
		int y = 10;
		int width = windowWidth / 80;
		int height = windowHeight / 40;
		int sumPointsB = sumPoints;

		for (int i = 0; i < 9; i++) {
			Points p;
			if (sumPointsB > 0)
				p = new Points(x, y, width, height, sumPointsB % 10);
			else
				p = new Points(x, y, width, height, -1);
			sumPointsB /= 10;
			pointsStack.push(p);
			x -= width;
			x -= 5;
		}

	}

	public void paintPoints(Graphics g, Stack<Points> p) {

		if(p==null)
			p = this.pointsStack;
		editPointsStack();

		if (p != null)
			for (int i = 0; i < 9; i++) {
				if (p.isEmpty() == false) {
					p.pop().paint(g);
				}

			}
	}

	public void levelRestart() {
		this.setLevel(1);
		levelDefiner();
		sumPoints = 0;
	}

	public void levelDefiner() {

		int widthBrick = windowWidth / 20;
		int heightBrick = windowHeight / 20;
		int x = widthBrick;
		int y = heightBrick * 3;

		brickMatrix = new Brick[6][16];

		switch (this.level) {
		case 0:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (i == 5 && j == 8) {
						b = new Brick(x, y, widthBrick, heightBrick, 1);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 6);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 1:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (((i == 1 || i == 4) && (j > 0 && j < 15)) || ((i > 0 && i < 5) && (j == 1 || j == 14))) {
						b = new Brick(x, y, widthBrick, heightBrick, 2);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 1);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;

		case 2:
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (i == 5) {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 1);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;

		case 3:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (((i == 0 || i == 5)) || (j == 0 || j == 15)) {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					} else if (((i == 2 || i == 3)) && (j >= 5 && j <= 10)) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 2);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;

		case 4:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (i == 0) {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					} else if (i == 5 || i == 0 || j == 0 || j == 15) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 2);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 5:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (((i == 2 || i == 3) && (j >= 3 && j <= 6)) || ((i == 2 || i == 3) && (j >= 9 && j <= 12))) {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					} else if ((i == 0 || (i == 5 && (j < 5 || j > 10)) || j == 0 || j == 15)) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 1);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 6:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if ((i == 0 || j == 0 || j == 15)) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 7:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (i == 0 || i == 5) {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					} else if (i == 1 || i == 4 || j == 7 || j == 8) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 2);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 8:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if ((i == 5 || j == 0 || j == 15) || ((i == 2) && (j >= 5 && j <= 10))) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 4);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;

		case 9:

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if (i == 5) {
						b = new Brick(x, y, widthBrick, heightBrick, 5);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, 6);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;
		case 10:
			Random rand = new Random();
			int r1 = rand.nextInt(5) + 1;
			int r2 = rand.nextInt(5) + 1;
			int r3 = rand.nextInt(5) + 1;

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 16; j++) {
					x += (widthBrick + 1);
					Brick b = null;
					if ((i - 2 == r1 || i - 1 == r1 || j - 1 == r1 || j - 1 == r1 * 2)) {
						b = new Brick(x, y, widthBrick, heightBrick, r1);
					} else if (i - 1 == r2 || j - 1 == r2 || j - 1 == r2 * 2) {
						b = new Brick(x, y, widthBrick, heightBrick, r2);
					} else {
						b = new Brick(x, y, widthBrick, heightBrick, r3);
					}
					if (b != null)
						brickMatrix[i][j] = b;

				}
				x = widthBrick;
				y += (heightBrick + 1);
			}
			break;

		}

	}

	public boolean endLevelCheck() {

		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (!brickMatrix[i][j].getBrickColor().equals("Black"))
					if (brickMatrix[i][j].getVisible() == true)
						return false;
			}
		}

		return true;
	}

	public void levelUp() {

		level += 1;
		levelDefiner();
		this.sumPoints += 2000;

	}

	public void breakBricks(Ball ball) {

		int flag = 0;
		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (brickMatrix[i][j] != null) {
					int sumPointsB = pointsForBrick(ball, brickMatrix[i][j]);
					if (ball.brickHitCheck(brickMatrix[i][j]) == true) {
						System.out.println("sumPointsB: " + sumPointsB);
						if (brickMatrix[i][j].getBrickID() != 6 && brickMatrix[i][j].getBrickID() != 3
								&& brickMatrix[i][j].getBrickID() != 2) {
							brickMatrix[i][j].randomBonus();
						}
						this.sumPoints += sumPointsB;
						System.out.println("points: " + sumPoints);
						flag = 1;
						break;
					}
				}
			}
			if (flag == 0)
				for (int j = 0; j < brickMatrix[i].length; j++) {
					if (brickMatrix[i][j] != null) {
						int sumPointsB = pointsForBrick(ball, brickMatrix[i][j]);
						if (ball.brickHitSecondCheck(brickMatrix[i][j]) == true) {
							if (brickMatrix[i][j].getBrickID() != 6 && brickMatrix[i][j].getBrickID() != 3
									&& brickMatrix[i][j].getBrickID() != 2) {
								brickMatrix[i][j].randomBonus();
							}
							this.sumPoints += sumPointsB;
							flag = 1;
							break;
						}
					}
				}
			flag = 0;
		}

	}



	public int pointsForBrick(Ball ball, Brick brick) {
		int sumPointsB = 0;
		if (ball.isFireBall() == true) {
			sumPointsB = brick.getNumStrikes() * 100;
			if (brick.getBrickID() == 6)
				sumPointsB = 100;
		} else {
			if (brick.getBrickID() != 6)
				sumPointsB = 100;
		}

		return sumPointsB;
	}
	

	public void moveBonus() {
		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (brickMatrix[i][j].getBonus() != null && brickMatrix[i][j].getBonus().getVisible() == true)
					brickMatrix[i][j].getBonus().move();
			}
		}

	}

	public boolean shotHitCheck(Shot shot) {

		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (brickMatrix[i][j].getVisible() == true && brickMatrix[i][j].getBrickID() != 6)
					if (shot.brickHitCheck(brickMatrix[i][j]) == true) {
						brickMatrix[i][j].breakBrick();
						int sumPointsB = 100;
						sumPoints+=sumPointsB;
						if (brickMatrix[i][j].getBrickID() != 3 && brickMatrix[i][j].getBrickID() != 2)
							brickMatrix[i][j].randomBonus();
						return true;
					}
			}
		}
		return false;

	}

	public void bonusReamove() {
		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (brickMatrix[i][j].getBonus() != null && brickMatrix[i][j].getBonus().getVisible() == true)
					brickMatrix[i][j].getBonus().setVisible(false);
			}
		}
	}

	public Brick[][] getBrickMatrix() {
		return brickMatrix;
	}

	public Brick getBrick(int i, int j) {
		return brickMatrix[i][j];
	}

	public void setBrickMatrix(Brick[][] brickMatrix) {
		this.brickMatrix = brickMatrix;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < brickMatrix.length; i++) {
			for (int j = 0; j < brickMatrix[i].length; j++) {
				if (brickMatrix[i][j].getVisible() == true)
					brickMatrix[i][j].paint(g);
				if (brickMatrix[i][j].getBonus() != null && brickMatrix[i][j].getBonus().getVisible() == true)
					brickMatrix[i][j].getBonus().paint(g);
			}
		}

	}

}
