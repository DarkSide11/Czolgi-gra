package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TankPlayer2 extends Tank {

	public TankPlayer2(Pane pane, double xPos, double yPos, double xVel, double yVel, double initialHealth) {
		super(pane,xPos,  yPos, xVel,  yVel,  initialHealth, "/icons/czolg-wrogi.png","/icons/lufa.png");
	this.cannonAngle=135;
	}

	@Override
	public  void render() {
		
		imageView.relocate(x, y);
		cannonView.relocate(x, y-75);
		cannonView.resize(500, 500);
		cannonView.autosize();
		cannonView.setFitHeight(200);
		cannonView.setRotate(90-cannonAngle);
	}
}
