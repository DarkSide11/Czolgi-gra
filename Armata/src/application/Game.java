package application;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
	
	
	private Tank activeTank;
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
	private ArrayList<Sprite> gameObjects = new ArrayList<Sprite>();
	private Shell shell1;
	Executor executor = Executors.newCachedThreadPool();
	Executor executor2 = Executors.newCachedThreadPool();
	public void switchplayer(){
	

		if (activeTank == tank1) {
			activeTank = tank2;
		} else {
			activeTank = tank1;
		}
		System.out.println("jebaæ wat");

	};

	Runnable keyInput = () -> {
		gameAnimationPane.setFocusTraversable(true);

		this.gameAnimationPane.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				activeTank.setDx(-1);
				break;
			case RIGHT:
				activeTank.setDx(1);
				break;
			case UP:
				int i;
				if (activeTank == tank1) {
					i = 1;
				} else {
					i = -1;
				}
				activeTank.setCannonSpeed(i);
				break;
			case DOWN:
				int i1;
				if (activeTank == tank1) {
					i1 = -1;
				} else {
					i1 = 1;
				}
				activeTank.setCannonSpeed(i1);
				break;
			case SPACE:
				activeTank.setCannonSpeed(0);
				activeTank.setDx(0);

				if (!gameObjects.contains(shell1)) {
					shell1 = new Shell(gameAnimationPane, activeTank.getX() + 100, activeTank.getY(), 2,
							activeTank.getCannonAngle());
					gameObjects.add(shell1);
				} else {
					shell1.shoot(activeTank.getX(), activeTank.getY(), activeTank.getCannonAngle());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switchplayer();
				

				break;
			}

		});

		this.gameAnimationPane.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case LEFT:
				activeTank.setDx(0);
				break;
			case RIGHT:
				activeTank.setDx(0);
				break;
			case UP:
				activeTank.setCannonSpeed(0);
				break;
			case DOWN:
				activeTank.setCannonSpeed(0);
				break;

			}
		});
	};

	private AnimationTimer animationTimer = new AnimationTimer() {

		@Override
		public void handle(long arg0) {
			frameTime = (System.currentTimeMillis() - animationTime);

			animationTime = System.currentTimeMillis();

			for (Sprite x : gameObjects) {
				x.update(frameTime / 5);
			}
			handleCollisions();

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

		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();

		tank1 = new TankPlayer1(gameAnimationPane, 0, 250, 0, 0, 100);
		tank2 = new TankPlayer2(gameAnimationPane, 550, 250, 0, 0, 100);
		gameObjects.add(tank1);
		this.activeTank = tank1;
		gameObjects.add(tank2);
		executor.execute(keyInput);
		this.startAnimation();

	}

	public boolean isCollisionBettwen(Sprite a, Sprite b) {
		Rectangle rect1 = new Rectangle((int) a.getX(), (int) a.getY(), (int) a.getWidth(), (int) a.getHeight());
		Rectangle rect2 = new Rectangle((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());

		if (rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x && rect1.y < rect2.y + rect2.height
				&& rect1.height + rect1.y > rect2.y) {

			System.out.println("Kolizja" + a.getClass() + "  " + b.getClass());
			return true;

		} else {
			return false;
		}
	}

	public void handleCollisions() {

		for (int i = 0; i < gameObjects.size(); i++) {
			for (int a = 0; a < gameObjects.size(); a++) {
				if (a != i) {
					if (this.isCollisionBettwen(gameObjects.get(i), gameObjects.get(a)))
						if (!(gameObjects.get(a) instanceof Shell))
							if (!(gameObjects.get(i) instanceof Shell && gameObjects.get(a).equals(activeTank)))
								gameObjects.get(i).setCollision(true);

				}
			}

		}
	}

	public void setActiveTank(String tekst) {
		if (tekst.equals("P1")) {

		} else if (tekst.equals("P2")) {
		} else {
			System.out.println("eror");
		}

	}
	public void moveActiveTankTo(double x , double y, double arm){
		activeTank.moveTankTo(x, y, arm);
	}
	
}
