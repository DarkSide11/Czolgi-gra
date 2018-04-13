package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	Image image;
	ImageView imageView;
	double x ;
	double y; 
	
	double dx;
	double dy;
	
	double health; 
	public Sprite(double xPos,double yPos, double xVel,double yVel,double InitialHealth, String ImageURL) {
		this.x=xPos;
		this.y=yPos;
		this.dx=xVel;
		this.dy=yVel;
		this.health=InitialHealth;
		this.image=new Image(ImageURL);
		this.imageView=new ImageView(image);
	}
	
	public void update(double time) {
		this.x=this.dx*time;
		this.y=this.dx*time;
		
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
}

