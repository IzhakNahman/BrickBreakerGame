
package BallProject;

import java.awt.*;

public class Heart extends Shape {

	private boolean visible;

	public Heart(int x, int y, int width, int height) {
		super(x, y, width, height, null);
		this.setImage(this.imageMaker("folder/heart.png"));
		this.visible = true;
	}

	// functions that set attributes

	public boolean getVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void paint(Graphics g) {
		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);

	}

}
