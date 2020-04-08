
package BallProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private Image classImage, backgroundImage;
	private int backgroundIndex = 1, xScreen = 0, yScreen = 0, keyPress = 0;// 1 = left, 2 = right, 3 = up 4 = down
	Graphics mainGraphic;
	private ArrayList<UserRank> resultsArray = new ArrayList<UserRank>();
	private boolean flag = true;
	private Racket racket;
	private ArrayList<Heart> heartArray;
	private Stack<Points> pointsStackIndex = new Stack<Points>();
	private ArrayList<Letters> lettersArray = new ArrayList<Letters>();
	private Ball ball;
	private Levels level;
	private int lifeLossFlag = 0, enterFlag = 0, gameFinishedFlag = 0;
	private int secondsPassed = 0, secondsPassedShots = 0;
	Timer myTimerSeconds = new Timer();
	Timer myTimerForShots = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			secondsPassed++;
			// System.out.println("Seconds Passed: " + secondsPassed);
		}

	};
	TimerTask task2 = new TimerTask() {
		public void run() {
			secondsPassedShots++;
			// System.out.println("miliseconds Passed: " + secondsPassedShots);
		}

	};

	public static void main(String[] args) {

		Main brick = new Main();
		brick.startApp();
		brick.setVisible(true);

	}

	public void startTimer() {
		myTimerSeconds.scheduleAtFixedRate(task, 1000, 1000);
	}

	public void startMyTimerForShots() {
		myTimerForShots.scheduleAtFixedRate(task2, 100, 100);
	}

	public void startApp() {
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		getScreenSize();
		this.setSize(xScreen, yScreen);
		this.setVisible(true);
		this.setLocation(0, 0);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		classImage = this.createImage(xScreen, yScreen);
		hideCruser();
		mainGraphic = classImage.getGraphics();
		mainGraphic.drawImage(classImage, 0, 0, this);
		// System.out.println(backgroundIndex);
		heartArray = new ArrayList<Heart>();

		createHeartArray();

		racket = new Racket((xScreen / 2 - (xScreen / 8) / 2), (yScreen - (yScreen / 72) * 4), xScreen / 8,
				yScreen / 60, null, xScreen / 8 / 24);

		// System.out.println("width: " + racket.getWidth() + " Height: " +
		// racket.getHeight());
		
		System.out.println("xScreen: "+xScreen +" yscreen: " + yScreen);
		ball = new Ball(racket.getMidXWidth() - xScreen / 160, racket.getY() - xScreen / 80, xScreen / 140, null);

		level = new Levels(1, xScreen, yScreen);

		Thread t = new Thread(this);
		t.start();
		startTimer();
		startMyTimerForShots();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	@Override
	// paint the objects
	public void paint(Graphics g) {

		if (mainGraphic != null) {
			setBackgroundImage();
			mainGraphic.drawImage(backgroundImage, 0, 0, xScreen, yScreen, this);

			if (backgroundIndex == 3) {
				if (lifeLossFlag == 1)
					lifeLoss();
				else {
					// System.out.println(level.getBrickMatrix().length);
					if (level.endLevelCheck() == true) {
						if (level.getLevel() == 10) {
							gameFinishedFlag = 1;
							heartArray.clear();
							lifeLossFlag = 1;
							secondsPassed = 0;
							level.setSumPoints(level.getSumPoints()+10000);
						}
						else {
							racket.setConnectedBall(true);
							keyPress = 0;
							level.levelUp();
							removeBonus();
						}
					}

					for (int i = 0; i < heartArray.size(); i++)
						heartArray.get(i).paint(mainGraphic);

					level.paint(mainGraphic);
					level.moveBonus();
					racket.paint(mainGraphic);
					racket.paintShots(mainGraphic);
					ball.paint(mainGraphic);
					ListeningRacket lr = new ListeningRacket();
					lr.run();
					ListeningBall lb = new ListeningBall();
					lb.run();
					level.paintPoints(mainGraphic, null);
				}
			} else if (backgroundIndex == 4) {

				int x, y, width, height, sumPoints;

				x = (int) (xScreen / 1.28);
				y = (int) (yScreen * 0.7305);
				width = xScreen / 60;
				height = yScreen / 30;
				sumPoints = level.getSumPoints();

				for (int i = 0; i < 9; i++) {
					Points p = new Points(x, y, width, height, sumPoints % 10);
					sumPoints /= 10;
					pointsStackIndex.push(p);
					x -= width;
					x -= 5;
				}

				level.paintPoints(mainGraphic, pointsStackIndex);

			} else if (backgroundIndex == 5) {

				int x = (int) (xScreen / 1.5);
				int y = (int) (yScreen / 3.050);
				int width = xScreen / 60;
				int height = yScreen / 30;
				int sumPoints = level.getSumPoints();

				for (int i = 0; i < 9; i++) {
					Points p = new Points(x, y, width, height, sumPoints % 10);
					sumPoints /= 10;
					pointsStackIndex.push(p);
					x -= width;
					x -= 5;
				}

				for (int i = 0; i < lettersArray.size(); i++) {
					lettersArray.get(i).paint(mainGraphic);
				}

				level.paintPoints(mainGraphic, pointsStackIndex);

				int xName = (int) (xScreen / 2.9) , xScore = (int) (xScreen / 1.3), yLine = (int) (yScreen / 1.41);
				if (enterFlag == 0)
					txtToArray();
				int forLength = resultsArray.size();
				if (forLength > 2)
					forLength = 3;

				if (resultsArray.size() > 0) {
					System.out.println("size: " + resultsArray.size() + " 2size: " + forLength);
					for (int i = 0; i < forLength; i++) {

						for (int row = 0; row < resultsArray.get(i).getName().length(); row++) {
							Letters letter = new Letters(xName, yLine, width, height,
									resultsArray.get(i).getName().charAt(row));
							letter.paint(mainGraphic);
							xName += (width + 5);
						}

						String score = resultsArray.get(i).scoretoString();
						for (int col = score.length(); col > 0; col--) {
							Letters letter = new Letters(xScore, yLine, width, height, score.charAt(col - 1));
							letter.paint(mainGraphic);
							xScore -= (width + 5);
						}
						xName = (int) (xScreen / 2.9);
						xScore = (int) (xScreen / 1.3);
						yLine += (int) (yScreen / 10.8);

					}

				}
			}

		}

		g.drawImage(classImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void run() {

		while (flag) {

			try {
				repaint();
				Thread.sleep(12);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!flag)
			System.exit(0);
	}

	// get image location (string) and returns the image
	public Image imageMaker(String s) {
		ImageIcon icon;
		Image i;
		icon = new ImageIcon(s);
		i = icon.getImage();
		return i;
	}

	// sets the background image as backgroundIndex
	public void setBackgroundImage() {
		switch (backgroundIndex) {
		case 1:
			backgroundImage = imageMaker("folder/Backgrounds/Background1.png");
			break;
		case 2:
			backgroundImage = imageMaker("folder/Backgrounds/Background2.png");
			break;
		case 3:
			backgroundImage = imageMaker("folder/Backgrounds/Background3.png");
			break;
		case 4:
			if (gameFinishedFlag == 0)
				backgroundImage = imageMaker("folder/Backgrounds/Background4.png");
			else
				backgroundImage = imageMaker("folder/Backgrounds/Background4.2.png");
			break;
		case 5:
			backgroundImage = imageMaker("folder/Backgrounds/Background5.png");
			break;
		case 6:
			backgroundImage = imageMaker("folder/Backgrounds/Background5.2.png");
			break;

		}
	}

	public void lifeLoss() {

		level.paint(mainGraphic);
		racket.paint(mainGraphic);
		ball.paint(mainGraphic);
		for (int i = 0; i < heartArray.size(); i++)
			heartArray.get(i).paint(mainGraphic);
		Image backgroundImageLoss = imageMaker("folder/Backgrounds/BackgroundThirdPageGray.png");
		mainGraphic.drawImage(backgroundImageLoss, 0, 0, xScreen, yScreen, null);

		if (secondsPassed == 1) {
			backgroundImageLoss = imageMaker("folder/Backgrounds/BackgroundThirdPageBlack.png");
			mainGraphic.drawImage(backgroundImageLoss, 0, 0, xScreen, yScreen, null);

		}

		if (secondsPassed == 2) {
			lifeLossFlag = 0;
			int size = heartArray.size();
			// System.out.println("size: " + size);
			if (size > 0) {
				heartArray.remove(size - 1);
				racket.setConnectedBall(true);
				keyPress = 0;
				// racket.setX((xScreen / 2) - (racket.getWidth() / 2));
				ball.setX(racket.getMidXWidth() - xScreen / 160);
				ball.setY(racket.getY() - xScreen / 80);
				level.bonusReamove();
				removeBonus();
				// racket.setX((xScreen / 2 - (xScreen / 8) / 2));
				ball.setX(racket.getMidXWidth() - xScreen / 160);
				ball.setY(racket.getY() - xScreen / 80);

			} else {
				backgroundIndex = 4;
				System.out.println("points: " + level.getSumPoints());
			}
		}

	}

	public void removeBonus() {
		ball.setToBall();
		ball.setDxDyToNormal();
		racket.doesntHaveShoot();
		racket.normalizeRacket();
		racket.setConnectedBall(true);
		keyPress = 0;
	}

	// puts in xScreen and yScreen window's width and height
	public void getScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		xScreen = (int) screenSize.getWidth();
		yScreen = (int) screenSize.getHeight();
		System.out.println("x: " + xScreen + "/n y: " + yScreen);
	}

	class ListeningRacket extends Thread {

		public void run() {

			try {
				racket.move(keyPress, xScreen);
				bonusHitRacketCheck(racket);
				racket.moveShots();
				shotHitBrickCheck();
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void start() {
			Thread t = new Thread(this);
			t.start();
		}

		public void bonusHitRacketCheck(Racket racket) {
			int size = 0;
			for (int i = 0; i < level.getBrickMatrix().length; i++) {
				for (int j = 0; j < level.getBrickMatrix()[i].length; j++) {
					if (level.getBrick(i, j).getBonus() != null
							&& level.getBrick(i, j).getBonus().getVisible() == true) {

						switch (racket.bonusHitCheck(level.getBrick(i, j).getBonus())) {
						case 1: /// Shoots
							racket.haveShoot();
							ball.setToBall();
							break;
						case 2: /// Minimize
							racket.minimizeRacket();
							break;
						case 3: /// Life +
							size = heartArray.size();
							if (size > 0)
								if (heartArray.get(size - 1) != null)
									heartArray.add(new Heart(
											heartArray.get(size - 1).getX() + heartArray.get(size - 1).getWidth() + 10,
											heartArray.get(size - 1).getY(), xScreen / 60, yScreen / 34));
								else
									heartArray.add(new Heart(20, 10, xScreen / 60, yScreen / 34));
							break;
						case 4: /// Fire Ball
							ball.setToFireBall();
							racket.doesntHaveShoot();
							break;
						case 5: /// Expend
							racket.maximizeRacket();
							break;
						case 6: /// Life -
							lifeLossFlag = 1;
							secondsPassed = 0;
							// }

							break;
						}
					}
				}
			}

		}

		public void shotHitBrickCheck() {
			for (int i = 0; i < racket.getShotsArray().size(); i++)
				if (level.shotHitCheck(racket.getShotsArray().get(i)) == true) {
					racket.getShotsArray().remove(i);
				}
		}

	}

	class ListeningBall extends Thread {

		public void run() {
			try {

				if (racket.isConnectedBall() == false) {
					// ball.move(xScreen, yScreen, racket);
					if (ball.wallHitCheck(xScreen, yScreen))
						ball.move();
					if (ball.racketHitCheck(racket) == true)
						ball.move();
					else if (ball.getYHeight() > racket.getYHeight()) {

						lifeLossFlag = 1;
						secondsPassed = 0;
					}

					level.breakBricks(ball);
				} else {
					ball.setX(racket.getMidXWidth() - xScreen / 160);
					ball.setY(racket.getY() - xScreen / 80);
				}

				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void start() {
			Thread t = new Thread(this);
			t.start();
		}

	}

	// convert txt file to array
	public void txtToArray() {

		File file = new File("results.txt");
		if (file.exists()) {

			resultsArray.clear();
			try {
				FileReader fr = new FileReader(file);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String line;

				while ((line = br.readLine()) != null) {

					String name = "";
					int score = 0;
					int i;
					for (i = 0; i < line.length(); i++) {
						if (line.charAt(i) != '-')
							name += line.charAt(i);
						else {
							i++;
							break;
						}
					}
					score = Integer.parseInt(line.substring(i));
					UserRank ur = new UserRank(name, score);
					resultsArray.add(ur);

				}

			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + file.toString());
			} catch (IOException e) {
				System.out.println("Unable to read file: " + file.toString());
			}
		}
	}

	// sorting the array of the results
	public void sortResultsArray() {

		if (resultsArray.size() > 0) {
			UserRank ur;
			for (int i = 1; i < resultsArray.size(); i++) {
				for (int j = i; j > 0; j--) {
					if (resultsArray.get(j).getScore() > resultsArray.get(j - 1).getScore()) {
						ur = resultsArray.get(j);
						resultsArray.set(j, resultsArray.get(j - 1));
						resultsArray.set(j - 1, ur);
					}
				}
			}
		}

	}

	public void arrayToTxt() {

		File f = new File("results.txt");

		try (BufferedWriter br = new BufferedWriter(new FileWriter(f))) {

			for (int i = 0; i < resultsArray.size(); i++) {
				br.write(resultsArray.get(i).getName() + '-' + resultsArray.get(i).getScore());
				br.newLine();
			}
		} catch (IOException e) {
			System.out.println("Unable to read file " + f.toString());
		}

	}

	public void backgroundIndexUp() {
		if (backgroundIndex == 5) {
			if (enterFlag == 1) {
				backgroundIndex = 1;
				level.levelRestart();
				racket.setX((xScreen / 2 - (xScreen / 8) / 2));
				ball.setX(racket.getMidXWidth() - xScreen / 160);
				ball.setY(racket.getY() - xScreen / 80);
				createHeartArray();
				pointsStackIndex = new Stack<Points>();
				level.setSumPoints(0);
				enterFlag = 0;
				lettersArray.clear();
				racket.setConnectedBall(true);
				keyPress = 0;
			} else {
			}
			;

		} else {

			backgroundIndex++;
		}
	}

	// hide the mouse cruiser
	public void hideCruser() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cruser"); //$NON-NLS-1$
		this.setCursor(blankCursor);
	}

	// creating array of hearts
	public void createHeartArray() {
		for (int i = 0; i < 2; i++) {
			if (i > 0) {
				if (heartArray.get(i - 1) != null)
					heartArray.add(new Heart(heartArray.get(i - 1).getX() + heartArray.get(i - 1).getWidth() + 10,
							heartArray.get(i - 1).getY(), xScreen / 60, yScreen / 34));

			} else
				heartArray.add(new Heart(20, 10, xScreen / 60, yScreen / 34));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		if (backgroundIndex == 3) {
			switch (arg0.getKeyCode()) {

			case KeyEvent.VK_RIGHT:
				keyPress = 1;
				break;
			case KeyEvent.VK_LEFT:
				keyPress = 2;
				break;
			case KeyEvent.VK_SPACE:
				keyPress = 3;
				break;
			case KeyEvent.VK_K:
				lifeLossFlag = 1;
				secondsPassed = 0;
				break;

			}
			if (racket.isHaveShoot() == true && secondsPassedShots > 1)
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					racket.addShot();
					secondsPassedShots = 0;
				}

		} else if (backgroundIndex == 5) {

			char c = '*';
			switch (arg0.getKeyCode()) {

			case KeyEvent.VK_ENTER:
				if (lettersArray.size() > 2) {

					String name = "";
					for (int i = 0; i < lettersArray.size(); i++) {
						name += ("" + lettersArray.get(i).getLetter());
					}

					UserRank ur = new UserRank(name, level.getSumPoints());
					txtToArray();
					resultsArray.add(ur);
					sortResultsArray();
					arrayToTxt();
					System.out.println("name: " + ur.getName() + " points: " + ur.getScore());
					enterFlag = 1;

				}
				break;
			case KeyEvent.VK_A:
				c = 'A';
				break;
			case KeyEvent.VK_B:
				c = 'B';
				break;
			case KeyEvent.VK_C:
				c = 'C';
				break;
			case KeyEvent.VK_D:
				c = 'D';
				break;
			case KeyEvent.VK_E:
				c = 'E';
				break;
			case KeyEvent.VK_F:
				c = 'F';
				break;
			case KeyEvent.VK_G:
				c = 'G';
				break;
			case KeyEvent.VK_H:
				c = 'H';
				break;
			case KeyEvent.VK_I:
				c = 'I';
				break;
			case KeyEvent.VK_J:
				c = 'J';
				break;
			case KeyEvent.VK_K:
				c = 'K';
				break;
			case KeyEvent.VK_L:
				c = 'L';
				break;
			case KeyEvent.VK_M:
				c = 'M';
				break;
			case KeyEvent.VK_N:
				c = 'N';
				break;
			case KeyEvent.VK_O:
				c = 'O';
				break;
			case KeyEvent.VK_P:
				c = 'P';
				break;
			case KeyEvent.VK_Q:
				c = 'Q';
				break;
			case KeyEvent.VK_R:
				c = 'R';
				break;
			case KeyEvent.VK_S:
				c = 'S';
				break;
			case KeyEvent.VK_T:
				c = 'T';
				break;
			case KeyEvent.VK_U:
				c = 'U';
				break;
			case KeyEvent.VK_V:
				c = 'V';
				break;
			case KeyEvent.VK_W:
				c = 'W';
				break;
			case KeyEvent.VK_X:
				c = 'X';
				break;
			case KeyEvent.VK_Y:
				c = 'Y';
				break;
			case KeyEvent.VK_Z:
				c = 'Z';
				break;
			case KeyEvent.VK_SPACE:
				c = '_';
				break;
			case KeyEvent.VK_0:
			case KeyEvent.VK_NUMPAD0:
				c = '0';
				break;
			case KeyEvent.VK_1:
			case KeyEvent.VK_NUMPAD1:
				c = '1';
				break;
			case KeyEvent.VK_2:
			case KeyEvent.VK_NUMPAD2:
				c = '2';
				break;
			case KeyEvent.VK_3:
			case KeyEvent.VK_NUMPAD3:
				c = '3';
				break;
			case KeyEvent.VK_4:
			case KeyEvent.VK_NUMPAD4:
				c = '4';
				break;
			case KeyEvent.VK_5:
			case KeyEvent.VK_NUMPAD5:
				c = '5';
				break;
			case KeyEvent.VK_6:
			case KeyEvent.VK_NUMPAD6:
				c = '6';
				break;
			case KeyEvent.VK_7:
			case KeyEvent.VK_NUMPAD7:
				c = '7';
				break;
			case KeyEvent.VK_8:
			case KeyEvent.VK_NUMPAD8:
				c = '8';
				break;
			case KeyEvent.VK_9:
			case KeyEvent.VK_NUMPAD9:
				c = '9';
				break;

			}
			if (enterFlag == 0) {
				if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE && lettersArray.size() > 0) {
					lettersArray.remove(lettersArray.size() - 1);
				} else if (c != '*' && lettersArray.size() < 11) {
					// int x = (int) (xScreen / 2);
					// int y = (int) (yScreen / 2);
					int x =(int)( xScreen / 2.2);
					
					int y = (int)( yScreen / 4.5);
					int width = xScreen / 60;
					int height = yScreen / 30;
					if (lettersArray.size() > 0) {
						x = lettersArray.get(lettersArray.size() - 1).getX() + width + 10;
					}

					Letters l = new Letters(x, y, width, height, c);
					lettersArray.add(l);
				}
			}

		}

		/*
		 * if ((backgroundIndex != 3) && arg0.getKeyCode() == KeyEvent.VK_ENTER) { if
		 * (backgroundIndex == 5 && lettersArray.size() < 3) {
		 * 
		 * } else backgroundIndexUp(); }
		 */

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getKeyCode()) {

		case KeyEvent.VK_RIGHT:
			keyPress = 0;
			break;
		case KeyEvent.VK_LEFT:
			keyPress = 0;
			break;
		case KeyEvent.VK_UP:
			keyPress = 0;
			break;
		case KeyEvent.VK_DOWN:
			keyPress = 0;
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stu

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

		if ((backgroundIndex != 3)) {

			backgroundIndexUp();

		}

		/*
		 * if (backgroundIndex == 3 && racket.isConnectedBall()) { racket.move(3,
		 * yScreen);
		 * 
		 * }
		 */

		if (racket.isHaveShoot() == true && secondsPassedShots > 1 && !racket.isConnectedBall()) {
			racket.addShot();
			secondsPassedShots = 0;
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

		if (backgroundIndex == 3 && lifeLossFlag == 0) {

			if (arg0.getPoint().getX() < xScreen - (racket.getWidth() / 2)
					&& arg0.getPoint().getX() > racket.getWidth() / 2) {
				racket.setX((int) (arg0.getPoint().getX() - (racket.getWidth() / 2)));
				if (arg0.getPoint().getX() > racket.getMidXWidth())
					racket.makeDxPositive();
				if (arg0.getPoint().getX() < racket.getMidXWidth())
					racket.makeDxNegative();
			} else if (arg0.getPoint().getX() > xScreen - (racket.getWidth() / 2)) {
				if (racket.getXWidth() < xScreen) {
					racket.makeDxPositive();
					racket.move();
				}
			} else if (arg0.getPoint().getX() < racket.getWidth() / 2) {
				if (racket.getX() > 0) {
					racket.makeDxNegative();
					racket.move();
				}
			}

		}
	}
}
