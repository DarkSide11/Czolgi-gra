package application;


import com.sun.javafx.geom.Rectangle;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Game {

	private Stage stage ;
	private Long startNanoTime;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private double groundCoordinatex=300;
	private Image tank;
	
		
		
	public Game (Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Game screen");
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

		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0,groundCoordinatex, canvas.getWidth(), canvas.getHeight()-this.groundCoordinatex);
		//graphicsContext.drawImage(tank, 0,this.groundCoordinatex-tank.getHeight());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		
		stage.sizeToScene();
	
		
	
		
	}

}
