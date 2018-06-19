package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Icon  {
<<<<<<< HEAD
	private Image image;
	private ImageView imageView;
	private double x;
	private double y; 
	private Pane pane;
	private boolean active=false;
	private double height;
	private double width;
=======
	Image image;
	ImageView imageView;
	double x;
	double y; 
	Pane pane;
	boolean active=false;
	double height;
	double width;
>>>>>>> refs/remotes/origin/master1
	
	
	public Icon(Pane pane,double xPos,double yPos, String ImageURL) {
		this.pane=pane;
		this.x=xPos;
		this.y=yPos;
		this.image=new Image(ImageURL);
		this.imageView=new ImageView(image);
		this.pane.getChildren().add(imageView);
		this.imageView.setFitHeight(this.image.getHeight());
		this.imageView.setFitWidth(this.image.getWidth());
		this.height=imageView.getFitHeight();
		this.width=imageView.getFitWidth();		
}
	
	public void render(double x, double y) {		
<<<<<<< HEAD
		imageView.relocate(x, y); 
=======
		imageView.relocate(x, y);	
>>>>>>> refs/remotes/origin/master1
	}
	
	public void hide( ) {
		imageView.setVisible(false);
	}
	
	
	
	
}

