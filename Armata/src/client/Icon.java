package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Icon  {
	Image image;
	Image hiddenImage = new Image("/icons/armour31.png");
	ImageView imageView;
	double x;
	double y; 
	Pane pane;
	boolean active=false;
	double height;
	double width;
	
	
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
		
		imageView.relocate(x, y);
	
	}
	
	public void hide( ) {
		imageView.setVisible(false);
	}
	
	
//	public void reveal (boolean isRevealed ) {
//		if (isRevealed == false) {
//			System.out.println("usuwanie");
//						
//			this.setImage(hiddenImage);
//			this.imageView = new ImageView(image);
//			this.pane.getChildren().add(imageView);
//			
//		}
//		
//	}
	
	
}

