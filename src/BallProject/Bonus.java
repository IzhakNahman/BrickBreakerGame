
package BallProject;

import java.awt.*;

import javax.swing.ImageIcon;

public class Bonus extends Shape {
	private int bonusID;
	private boolean visible;

	public Bonus(int x, int y, int width, int height, int bonusID) {
		super(x, y, width, height, null, 0, 3);

		this.bonusID = bonusID;
		this.visible = true;
		bonusDefiner();
	}

	public int getBonusID() {
		return bonusID;
	}

	public void setBonusID(int bonusID) {
		this.bonusID = bonusID;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Image Imagemaker(String s) {
		ImageIcon icon;
		Image i;
		icon = new ImageIcon(s);
		i = icon.getImage();
		return i;
	}

	

	public void bonusDefiner() {

		switch (bonusID) {
		case 1:
			this.setImage(Imagemaker("folder/Bonuses/ShootsBonus1.png"));

			break;
		case 2:
			super.setImage(Imagemaker("folder/Bonuses/MinimizeBonus2.png"));

			break;
		case 3:
			super.setImage(Imagemaker("folder/Bonuses/LifeBonus3.png"));

			break;
		case 4:
			super.setImage(Imagemaker("folder/Bonuses/FireBallBonus4.png"));

			break;
		case 5:
			super.setImage(Imagemaker("folder/Bonuses/ExpendBonus5.png"));
			break;
		case 6:
			super.setImage(Imagemaker("folder/Bonuses/DeathBonus6.png"));
			break;

		default:
			this.setImage(null);

		}

	}

	public void paint(Graphics g) {

		g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);
	}

}
