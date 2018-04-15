package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tank extends Sprite {
	
	Image cannon ;
	ImageView cannonView;
	double cannonAngle;


	public Tank(Pane pane,double xPos, double yPos, double xVel, double yVel, double InitialHealth, String tankImageURL,String cannonImageURL) {
		super(pane,xPos, yPos, xVel, yVel, InitialHealth, "/icons/czolg.png");
		this.cannon=new Image("/icons/lufa.png");
		this.cannonAngle=45;
		// TODO Auto-generated constructor stub
	}

}
