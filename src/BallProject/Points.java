
package BallProject;

import java.awt.*;

public class Points extends Shape {
	//private boolean visible;
	private int number;

	public Points(int x, int y, int width, int height, int number) {
		super(x, y, width, height, null);
		this.number = number;
		imageDefiner();
	}

	public void imageDefiner() {

		this.setImage(imageMaker("folder/Numbers/"+number+".png"));

	}

	public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}



	public void paint(Graphics g) {
		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);

	}
}
