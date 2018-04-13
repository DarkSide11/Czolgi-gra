package application;

import com.sun.javafx.geom.Rectangle;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Armata {

	private Stage stage ;
	private Long startNanoTime;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private double groundyCoordinate=300;
	private Image tank;
	
	
	public Armata (Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Armata");
		startNanoTime = System.nanoTime(); 
		this.initialize();
		
	}
	
	public void showTerrain(){
		Rectangle podloze=new Rectangle(20,20);
		GridPane gridPane = new GridPane();
	}
	private void initialize() {
		
		Group root = new Group();
		canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);

		graphicsContext = canvas.getGraphicsContext2D();
		tank=new Image("/icons/Prostokat.png");

		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0,groundyCoordinate, canvas.getWidth(), canvas.getHeight()-this.groundyCoordinate);
		graphicsContext.drawImage(tank, 0,this.groundyCoordinate-tank.getHeight());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		
		stage.sizeToScene();
		
	
		
	}

}
