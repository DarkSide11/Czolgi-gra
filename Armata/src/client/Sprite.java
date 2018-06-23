package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

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
	 static protected ArrayList<Integer> lista =new ArrayList<Integer>();
	
	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	double health; 
	public Sprite(Pane pane,double xPos,double yPos, double xVel,double yVel,double InitialHealth, String ImageURL) {
		this.pane=pane;
	
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
		this.x=xPos;
		this.y=yPos;
		wczytajWspolrzednezPliku();
		wypiszWspolrzedne();
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
	
		imageView.relocate(x-(width/2), y+height);
	
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
	/**
	 * @author Robert Adamczuk and Tomasz Sat³awski
	 * 
	 */
	public void wczytajWspolrzednezPliku() {
		try {
	
			InputStream in = getClass().getResourceAsStream("/plikiTekstowe/PozycjaCzolgu.txt"); 
			BufferedReader odczyt = new BufferedReader(new InputStreamReader(in));
//			Scanner odczyt = new Scanner(new File("content/plikiTekstowe/PozycjaCzolgu.txt"));
			while(true) {
				String line = odczyt.readLine();
				if (line == null) {
					break;
				} else {
//				System.out.println(line);
				Integer lineInt = Integer.parseInt(line);
//				lista.add(new Integer(odczyt.readLine()));
				lista.add(lineInt);
				}
			}
			odczyt.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.exit(1);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(1);
			e.printStackTrace();
		}
	}
	public void wypiszWspolrzedne() {
//		for(int i=0;i<lista.size();i++) {
//			System.out.println(lista.get(i));
//	}
}
}

