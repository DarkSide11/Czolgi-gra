package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Tank extends Sprite {
	
	Image cannon ;
	ImageView cannonView;
	double cannonAngle;
	double cannonSpeed=0;


	public Tank(Pane pane,double xPos, double yPos, double xVel, double yVel, double initialHealth,String tankImageUrl,String tankCannonUrl) {
		super(pane,xPos, yPos, xVel, yVel, initialHealth, tankImageUrl);
		this.cannon=new Image(tankCannonUrl);
		cannonView=new ImageView(cannon);
		this.pane.getChildren().add(cannonView);
		// TODO Auto-generated constructor stub
	}
	@Override
	public  void render() {
		
		imageView.relocate(x, y);
		cannonView.relocate(x+110, y-75);
		cannonView.resize(500, 500);
		cannonView.autosize();
		cannonView.setFitHeight(200);
		cannonView.setRotate(90-cannonAngle);
	}

	@Override
	public void update(double time) {
	System.out.println(time);
		super.update(time);
		if(cannonAngle<=45&&cannonAngle>=0) {
		this.cannonAngle=this.cannonAngle+cannonSpeed*time;
		}
		else
		{
			if((this.cannonAngle>45&&cannonSpeed<0)||(this.cannonAngle<0&&cannonSpeed>0)) {
				this.cannonAngle=this.cannonAngle+cannonSpeed*time;
			}
			else {
				this.cannonAngle=this.cannonAngle;
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
}
