package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

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
	private Boolean znak1;
	private Boolean poprzedniZnak1;
	private Boolean znak;
	private Boolean poprzedniZnak;

	
	
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
		changeHeightandWidth(60,80);
		cannonView.setFitHeight(100);
		cannonView.setFitWidth(100);
		imageView.relocate(x-width/2, y-height);
		cannonView.relocate((x-width/2)+width*0.15 , (y-height)-height*0.60);
	
	
		cannonView.autosize();
		
		cannonView.setRotate(90 - cannonAngle);
	}

	@Override
	public void update(double time) {
		if(x<10)
			x=10;
		
		if(x>790)
			x=790;
		this.x=this.x+this.dx*time;
		this.y=lista.get(((int) x));
		this.render();
		if (collision) {
			if (dx <= 0) {
				this.x += 5;
			} else {
				this.x -= 5;
			}
			collision = false;
		} else {

			if (!tankMovedtoDestX) {
				

				if (destinationXcoornidate >= x) {
					dx = 1;
					znak1 = true;
			
				} else {
					dx = -1;
					znak1 = false;
					
				}

				if (poprzedniZnak1 != null) {
					
					if (znak1 != poprzedniZnak1) {
						znak1 = null;
						poprzedniZnak1 = null;
						tankMovedtoDestX = true;
						dx *= 0;
						
					} else {
					
						poprzedniZnak1 = znak1;
					}
				} else {
					poprzedniZnak1 = znak1;
					
				}

			}

			if (!tankMovedCannonAngle) {
				

				if (destinationCannonAngle >= cannonAngle) {
					cannonSpeed = 1;
					znak = true;
					
				} else {
					cannonSpeed = -1;
					znak = false;
					
				}

				if (poprzedniZnak != null) {
					
					if (znak != poprzedniZnak) {
						znak = null;
						poprzedniZnak = null;
						tankMovedCannonAngle = true;
						cannonSpeed *= 0;
						
					} else {
						
						poprzedniZnak = znak;
					}
				} else {
					poprzedniZnak = znak;
					
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

	public void changeHeightandWidth(double height1, double width1 ) {
		
		imageView.setFitHeight(height1);
		height=height1;
		imageView.setFitWidth(width1);
		width=width1;
	}
	public void moveTankTo(double x, double y, double katArmaty) {
		destinationXcoornidate = x;
		destinationYcoordniate = y;
		destinationCannonAngle = katArmaty;
		this.tankMovedtoDestX = false;
		this.tankMovedCannonAngle = false;
	}
	
}
