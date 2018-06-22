package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * 
 * @author Tomasz Sat³awski
 *
 */
public abstract class Tank extends Sprite {

	private boolean tankMovedtoDestX = true;
	private boolean tankMovedCannonAngle = true;
	private double destinationXcoornidate;
	private double destinationYcoordniate;
	double destinationCannonAngle;
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
		cannonView.relocate(x + 55 , y - 80);
		cannonView.resize(500, 500);
		cannonView.autosize();
		cannonView.setFitHeight(200);
		cannonView.setFitWidth(200);
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
						dx = 0;
						tankMovedtoDestX = true;
					}
				} else {
					dx = -1;
				}
				if (x - destinationXcoornidate < 10 && x - destinationXcoornidate > -10) {
					dx = 0;
					tankMovedtoDestX = true;
				}

				if (!tankMovedCannonAngle) {
					if (destinationCannonAngle - cannonAngle > 0) {
						cannonSpeed = 1 ;
						if (cannonAngle - destinationCannonAngle < 5 && cannonAngle - destinationCannonAngle > -5) {
							cannonSpeed = 0;
							tankMovedCannonAngle = true;
						}
					} else {
						cannonSpeed = -1;
					}
					if (cannonAngle - destinationCannonAngle < 5 && cannonAngle - destinationCannonAngle > -5) {
						cannonSpeed = 0;
						tankMovedCannonAngle = true;
					}

				}
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
		destinationXcoornidate = x;
		destinationYcoordniate = y;
		destinationCannonAngle=katArmaty;
		this.tankMovedtoDestX = false;
		this.tankMovedCannonAngle = false;
	}
}
