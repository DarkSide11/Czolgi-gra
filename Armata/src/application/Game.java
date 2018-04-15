package application;


import com.sun.javafx.geom.Rectangle;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Game {

	private Stage stage ;
	private Long startNanoTime;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Pane gameAnimationPane;
	private double groundCoordinatex=300;
	private Tank tank1;
	private long animationTime=System.currentTimeMillis();
	private long frameTime=0;
	public void startAnimation() {
		
		new AnimationTimer() {

			@Override
			
			public void handle(long arg0) {
				frameTime=(System.currentTimeMillis()-animationTime)/1000;
				tank1.update(frameTime);
			System.out.println(tank1.getX());
				
			}
			
		}.start();
	}
		
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
		this.gameAnimationPane=new Pane();
		root.getChildren().add(gameAnimationPane);
		

		graphicsContext = canvas.getGraphicsContext2D();

		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0,groundCoordinatex, canvas.getWidth(), canvas.getHeight()-this.groundCoordinatex);
		//graphicsContext.drawImage(tank, 0,this.groundCoordinatex-tank.getHeight());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		tank1=new Tank(gameAnimationPane,0,0,1,1,100,"/icons/czolg.png","/icons/lufa.png");
		this.startAnimation();
	
		
	}

}
