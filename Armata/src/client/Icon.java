package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Klasa odpowiedzialna za rysowanie ikon punkto wytrzymalosci, amunicji i tp elementow.
 * @author Robert
 *
 */
public class Icon  {

	private Image image;
	private ImageView imageView;
	private double x;
	private double y; 
	private Pane pane;
	private boolean active=false;
	private double height;
	private double width;

	
	
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
	
	/**
	 * ustawia pozycje elementu
	 * @param x
	 * @param y
	 */
	public void render(double x, double y) {		
		imageView.relocate(x, y);	
	}
	
	/**
	 * ukrywa element (ikonê)
	 */
	public void hide( ) {
		imageView.setVisible(false);
	}
			
}

