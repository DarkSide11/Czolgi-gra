package client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * @author Tomasz Sat³awski
 */
public class Sprite {
	Image image;
	ImageView imageView;
	double x ;
	double y; 
	Pane pane;
	double dx;
	double dy;
	double height;
	double width;
	boolean collision=false;
	
	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
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
		this.imageView.setFitHeight(this.image.getHeight());
		this.imageView.setFitWidth(this.image.getWidth());
		this.height=imageView.getFitHeight();
		this.width=imageView.getFitWidth();
	}
	
	public double getHeight() {
		return height;
		
	}

	public void setHeight(double height) {
		this.imageView.setFitHeight(height);
		this.height = height;
		
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.imageView.setFitWidth(width);
		this.width = width;
	}

	public void render() {
	
		imageView.relocate(x, y);
	
	}
	public void update(double time) {
	
		this.x=this.x+this.dx*time;
		this.y=this.y+this.dy*time;
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
		if(this.x>-280&&this.x<800)
		this.dx = dx;
		else {
			this.x=-279;
			this.dx=0;}
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
	public void multiplyDx(int a) {
		this.dx=this.dx*a;
	}
}

