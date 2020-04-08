
package BallProject;

import java.awt.*;

public class Shot extends Shape {
	private boolean visible;

	public Shot(int x, int y, int width, int height) {
		super(x, y, width, height,null, 0, -6);
		this.setImage(this.imageMaker("folder/ShotIamge.png"));
		this.visible = true;
	}

	public boolean getVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean brickHitCheck(Brick brick) {
		
		int xShot = this.getMidXWidth();
		int xBrick = brick.getX();
		int xWidthBrick = brick.getXWidth();
		
		if(this.getY()<=brick.getYHeight()&&this.getY()>=brick.getYHeight()-this.getHeight()/2)
			if(xShot>=xBrick && xShot<=xWidthBrick) {
				return true;
			}
		
		return false;
	}
	


	public void paint(Graphics g) {

		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);
	}

}
