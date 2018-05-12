package application;

import javafx.scene.layout.Pane;

public class TankPlayer1 extends Tank {

	public TankPlayer1(Pane pane, double xPos, double yPos, double xVel, double yVel, double initialHealth){
		super(pane,xPos,  yPos, xVel,  yVel,  initialHealth, "/icons/czolg.png","/icons/lufa.png");
		this.cannonAngle=45;
	
	}
}
