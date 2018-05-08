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
	private Tank tank2;
	private ArrayList<Sprite> gameObjects=new ArrayList<Sprite>();

	public void keyInput() {
		gameAnimationPane.setFocusTraversable(true);

		this.gameAnimationPane.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				tank1.setDx(-1);
				break;
			case RIGHT:
				tank1.setDx(1);
				break;
			case UP:
				tank1.setCannonSpeed(1);
				break;
			case DOWN:
				tank1.setCannonSpeed(-1);
				break;
			}

		});

		this.gameAnimationPane.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case LEFT:
				tank1.setDx(0);
				break;
			case RIGHT:
				tank1.setDx(0);
				break;
			case UP:
				tank1.setCannonSpeed(0);
				break;
			case DOWN:
				tank1.setCannonSpeed(0);
				break;
			}
		});
	}

	private AnimationTimer animationTimer = new AnimationTimer() {

		@Override
		public void handle(long arg0) {
			frameTime = (System.currentTimeMillis() - animationTime);
			tank1.update(frameTime / 5);
			// System.out.println(frameTime/5);
			animationTime = System.currentTimeMillis();
			tank2.update(frameTime / 5);
			if (isCollisionBettwen(tank1, tank2)) {
				System.out.println("Kolizja");
				
			}

		}

	};

	public void startAnimation() {
		animationTimer.start();
	}

	public Game(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Game screen");
		startNanoTime = System.nanoTime();
		this.initialize();
		primaryStage.show();

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

		tank1 = new TankPlayer(gameAnimationPane, 0, 250, 0, 0, 100);
		tank2 = new TankEnemy(gameAnimationPane, 550, 250, 0, 0, 100);
		gameObjects.add(tank1);
		gameObjects.add(tank2);
		this.keyInput();
		this.startAnimation();

	}

	public boolean isCollisionBettwen(Sprite a, Sprite b) {
		Rectangle rect1 = new Rectangle((int) a.getX(), (int) a.getY(), (int) a.getWidth(), (int) a.getHeight());
		Rectangle rect2 = new Rectangle((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());

		if (rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x && rect1.y < rect2.y + rect2.height
				&& rect1.height + rect1.y > rect2.y) {
			return true;
			
		}
		else {
		return false;
		}
	}
	public void handleColisions(){
		for(Sprite sprite :gameObjects) {
			boolean a =isCollisionBettwen(sprite,sprite);//todo wykrywanie kolizji dla wybranych obiektów w grze
		}
	}
}
