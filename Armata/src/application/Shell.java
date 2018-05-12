package application;

import javafx.scene.layout.Pane;

public class Shell extends Sprite {

	double angle=20;
	double initialSpeed=2;
	final double gravity=0.01;
	public Shell(Pane pane, double xPos, double yPos, double aInitialSpeed,double aAngle) {
		super(pane, xPos, yPos, 0, 0, 0, "/icons/Pocisk.png");
		this.angle=aAngle;
		this.dy=-initialSpeed*Math.sin(Math.toRadians(angle));
		this.dx=initialSpeed*Math.cos(Math.toRadians(angle));
		System.out.println(dy);
	}

	
	

	public void update(double time) {
		if(collision) {
			if(dx<0) {this.x+=5;}
			else {this.x-=5;}	
			collision=false;
		}
		this.dy=this.dy+gravity*time;
		System.out.println(this.dy);
		this.x=this.x+this.dx*time;
		this.y=this.y+this.dy*time;
		this.render();
	}
	public double getAngle() {
		return angle;
	}




	public void setAngle(double angle) {
		this.angle = angle;
	}




	public double getInitialSpeed() {
		return initialSpeed;
	}




	public void setInitialSpeed(double initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	public void reset(double x, double y, double angle) {
		this.angle=angle;
		this.dy=-initialSpeed*Math.sin(Math.toRadians(angle));
		this.setAngle(angle);
		this.setX(x+100);
		this.setY(y);}
	}




