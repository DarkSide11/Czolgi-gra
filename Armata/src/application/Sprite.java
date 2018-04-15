package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {
	Image image;
	ImageView imageView;
	double x ;
	double y; 
	Pane pane;
	double dx;
	double dy;
	
	double health; 
	public Sprite(Pane pane,double xPos,double yPos, double xVel,double yVel,double InitialHealth, String ImageURL) {
		this.pane=pane;
		this.x=xPos;
		this.y=yPos;
		this.dx=xVel;
		this.dy=yVel;
		this.health=InitialHealth;
		this.image=new Image(ImageURL);
		this.imageView=new ImageView(image);
		this.pane.getChildren().add(imageView);
	}
	
	private void render() {
	
		imageView.relocate(x, y);
		imageView.setRotate(45);
		
	}
	public void update(double time) {
		this.x=this.x+this.dx*time;
		this.y=this.x+this.dy*time;
		this.render();
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

