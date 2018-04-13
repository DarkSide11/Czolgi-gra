package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tank extends Sprite {
	
	Image cannon ;
	ImageView cannonView;
	double cannonAngle;

	public Tank(double xPos, double yPos, double xVel, double yVel, double InitialHealth, String ImageURL) {
		super(xPos, yPos, xVel, yVel, InitialHealth, "/icons/Prostokat.png");
		this.cannon=new Image("/icons/Prostokat.png");
		this.cannonAngle=45;
		// TODO Auto-generated constructor stub
	}

}
