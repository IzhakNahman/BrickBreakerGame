
package BallProject;

import java.awt.*;

public class Letters extends Shape {
	// private boolean visible;
	private char letter;

	public Letters(int x, int y, int width, int height, char letter) {
		super(x, y, width, height, null);
		this.letter = letter;
		imageDefiner();
	}

	public void imageDefiner() {

		this.setImage(imageMaker("folder/Letters/" + letter + ".png"));
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public void paint(Graphics g) {
		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);

	}
}
