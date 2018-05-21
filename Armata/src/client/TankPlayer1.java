package client;

import javafx.scene.layout.Pane;

public class TankPlayer1 extends Tank {

	public TankPlayer1(Pane pane, double xPos, double yPos, double xVel, double yVel, double initialHealth) {
		super(pane, xPos, yPos, xVel, yVel, initialHealth, "/icons/czolg.png", "/icons/lufa.png");
		this.cannonAngle = 45;

	}

	@Override
	public void update(double time) {
		super.update(time);

		if (cannonAngle <= 45 && cannonAngle >= 0) {
			this.cannonAngle = this.cannonAngle + cannonSpeed * time;
		} else {
			if ((this.cannonAngle > 0 && cannonSpeed < 0) || (this.cannonAngle < 45 && cannonSpeed > 0)) {
				this.cannonAngle = this.cannonAngle + cannonSpeed * time;
			}

		}

	}
	
	@Override
	public String toString() {
//		return "Tank 1" + "   "+this.hashCode();
		return "Tank 1";
	}
	

}
