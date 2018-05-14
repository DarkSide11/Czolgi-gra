package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Tank extends Sprite {

	private boolean tankMovedtoDestX = true;
	private double destinationXcoornidate;
	private double destinationYcoordniate;
	Image cannon;
	ImageView cannonView;
	double cannonAngle;
	double cannonSpeed = 0;

	public Tank(Pane pane, double xPos, double yPos, double xVel, double yVel, double initialHealth,
			String tankImageUrl, String tankCannonUrl) {
		super(pane, xPos, yPos, xVel, yVel, initialHealth, tankImageUrl);
		this.cannon = new Image(tankCannonUrl);
		cannonView = new ImageView(cannon);
		this.pane.getChildren().add(cannonView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render() {

		imageView.relocate(x, y);
		cannonView.relocate(x + 110, y - 75);
		cannonView.resize(500, 500);
		cannonView.autosize();
		cannonView.setFitHeight(200);
		cannonView.setRotate(90 - cannonAngle);
	}

	@Override
	public void update(double time) {
		super.update(time);
		if (collision) {
			if (dx <= 0) {
				this.x += 5;
			} else {
				this.x -= 5;
			}
			collision = false;
		} else {
			if (!tankMovedtoDestX) {
				if (destinationXcoornidate - x > 0) {
					dx = 1;
					if (x - destinationXcoornidate < 10 && x - destinationXcoornidate > -10) {
						dx=0;tankMovedtoDestX=true;}
					}
				else {
					dx = -1;}
				if (x - destinationXcoornidate < 10 && x - destinationXcoornidate > -10) {
					dx = 0;tankMovedtoDestX=true;}
				
			}
		}

	}

	public double getCannonSpeed() {
		return cannonSpeed;
	}

	public void setCannonSpeed(double cannonSpeed) {
		this.cannonSpeed = cannonSpeed;
	}

	public double getCannonAngle() {
		return cannonAngle;
	}

	public void setCannonAngle(double cannonAngle) {
		this.cannonAngle = cannonAngle;
	}

	public void moveTankTo(double x, double y, double katArmaty) {
		destinationXcoornidate=x;
		destinationYcoordniate=y;
		this.tankMovedtoDestX = false;

	}
}
