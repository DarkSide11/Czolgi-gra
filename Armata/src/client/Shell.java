package client;

import javafx.scene.layout.Pane;

public class Shell extends Sprite {
	boolean active=true;
	double angle=20;
	double initialSpeed=2;
	final double gravity=0.01;
	public Shell(Pane pane, double xPos, double yPos, double aInitialSpeed,double aAngle) {
		super(pane, xPos, yPos, 0, 0, 0, "/icons/Pocisk.png");
		this.angle=aAngle;
		this.dy=-initialSpeed*Math.sin(Math.toRadians(angle));
		this.dx=initialSpeed*Math.cos(Math.toRadians(angle));
	}
	
	
	public Shell(Pane pane, double xPos, double yPos, boolean isActive) {
		super(pane, xPos, yPos, 0, 0, 0, "/icons/Pocisk.png");
		this.active = isActive;
		
	}

	
	

	public void update(double time) {
		if(active) {
		if(collision) {
			active=false;
			dx=0;
			dy=0;
			x=-1;
			y=0;
			collision=false;
		}
		this.dy=this.dy+gravity*time;
		this.x=this.x+this.dx*time;
		this.y=this.y+this.dy*time;
		this.render();
	}}
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

	public void shoot(double x, double y, double angle) {
		this.active=true;
		this.angle=angle;
		this.dx=initialSpeed*Math.cos(Math.toRadians(angle));
		this.dy=-initialSpeed*Math.sin(Math.toRadians(angle));
		this.setAngle(angle);
		this.setX(x+100);
		this.setY(y);}
	}





