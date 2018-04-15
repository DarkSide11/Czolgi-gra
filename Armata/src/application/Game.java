package application;

import java.util.ArrayList;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Game {

	private Stage stage;
	private Long startNanoTime;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Pane gameAnimationPane;
	private double groundCoordinatex = 300;
	private Tank tank1;
	private String a;
	private long animationTime = System.currentTimeMillis();
	private long frameTime = 0;
	private ArrayList<String> KeyPressedVector;
	

	public void keyInput() 
	{
		gameAnimationPane.setFocusTraversable(true);
		
		this.gameAnimationPane.setOnKeyPressed(e->{
			switch(e.getCode()) {
			case LEFT:tank1.setDx(-1); break;
			case RIGHT:tank1.setDx(1); break;
			case UP:tank1.setDy(-1); break;
			case DOWN:tank1.setDy(1); break;
			}
			
			});
		
		this.gameAnimationPane.setOnKeyReleased(e->{
			switch(e.getCode()) {
			case LEFT:tank1.setDx(0); break;
			case RIGHT:tank1.setDx(0); break;
			case UP:tank1.setDy(0); break;
			case DOWN:tank1.setDy(0); break;
			}
			});
	}
	
	
	public void startAnimation() {
		new AnimationTimer() {
			@Override

			public void handle(long arg0) {
				frameTime = (System.currentTimeMillis() - animationTime) ;
				tank1.update(frameTime/5);
				System.out.println(frameTime/5);
				animationTime=System.currentTimeMillis();

			}

		}.start();
	}

	public Game(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Game screen");
		startNanoTime = System.nanoTime();
		this.initialize();

	}

	public void showTerrain() {
		Rectangle podloze = new Rectangle(20, 20);
		GridPane gridPane = new GridPane();
	}

	private void initialize() {

		Group root = new Group();
		canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);
		this.gameAnimationPane = new Pane();
		root.getChildren().add(gameAnimationPane);

		graphicsContext = canvas.getGraphicsContext2D();

		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0, groundCoordinatex, canvas.getWidth(), canvas.getHeight() - this.groundCoordinatex);
		// graphicsContext.drawImage(tank, 0,this.groundCoordinatex-tank.getHeight());

		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		tank1 = new Tank(gameAnimationPane, 0, 0, 0, 0, 100, "/icons/czolg.png", "/icons/lufa.png");
		this.keyInput();
		this.startAnimation();

	}

}
