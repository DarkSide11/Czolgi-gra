package client;

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
	
	public Client client;
	public State state;
	public enum State {Play, Wait};
	private Tank activeTank;
	private Tank passiveTank;
	private Stage stage;
	private Long startNanoTime;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Pane gameAnimationPane;
	private double groundCoordinatex = 300;
	private Tank tank1;
	private long animationTime = System.currentTimeMillis();
	private long frameTime = 0;
	private Tank tank2;
	private ArrayList<Sprite> gameObjects = new ArrayList<Sprite>();
	private Shell shell1;
	private int myHitPoints = 3;
	private int myAmmo = 10;
	
	private Icon[] lives = new Icon[this.myHitPoints];
	private Icon[] ammo = new Icon[this.myAmmo];
	
	 
	 public int getMyHitPoints() {
		return myHitPoints;
	}

	public void setMyHitPoints(int myHitPoints) {
		this.myHitPoints = myHitPoints;
	}

	public void switchplayer(){
		
		if (activeTank == tank1) {
			activeTank = tank2;
		} else {
			activeTank = tank1;
		}

	};

	public  void keyInput() {
		gameAnimationPane.setFocusTraversable(true);

		this.gameAnimationPane.setOnKeyPressed(e -> {
			if(state!=State.Wait) {
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
				
				if(this.myAmmo > -1 ) {
				
					if (this.myAmmo > 0) {
					this.eraseAmmo();
					}
					myAmmo--;
					System.out.println("myAmmo: " + myAmmo);
				}
				
				if (!gameObjects.contains(shell1)) {
					shell1 = new Shell(gameAnimationPane, activeTank.getX() + 100, activeTank.getY(), 2,
							activeTank.getCannonAngle());
					gameObjects.add(shell1);
				} else if (myAmmo > -1){
					shell1.shoot(activeTank.getX(), activeTank.getY(), activeTank.getCannonAngle());
					client.SendCoordninates();
				} else {
					System.out.println("Brak amunicji");
					client.SendCoordinatesNoAmmo();
				}
				
				setState(State.Wait);
				
				break;
			}
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
		
		shell1 = new Shell(gameAnimationPane, 400,800, false); // Ustawic pocisk tak by byl najmniej widoczny (blisko dolu pola gry)
		gameObjects.add(shell1);
		
		// rysuje "liczbe zyc" oraz "stan amunicji"
		drawLives();
		drawAmmo();
		
		
		
		keyInput();
		this.startAnimation();

	}
	
	public void drawLives() {
		double space = 60;
		
		for (int i = 0; i <this.myHitPoints; i++) {
			lives[i] = new Icon(gameAnimationPane, 10 + space * i, 520, "/icons/armour4.png");
			lives[i].render(10 + space * i, 520);
		}
	}
	
	public void eraseLive() {
		lives[myHitPoints-1].hide();
	}
	
	public void drawAmmo() {
		double space = 25;
		
		for (int i = 0; i <this.myAmmo; i++) {
			ammo[i] = new Icon(gameAnimationPane, 250 + space * i, 520, "/icons/tankAmmo4.png");
			ammo[i].render(250 + space * i, 520);
		}
	}
	
	public void eraseAmmo() {
		ammo[myAmmo-1].hide();
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
							if (!gameObjects.get(a).equals(activeTank)) { //na te chwile activeTank to tak naprawde czolg gracza, passive to przeciwnika
								
								gameObjects.get(i).setCollision(true); // ustawia pocisk jako zderzony
//					
								if (!gameObjects.get(i).equals(activeTank)) {
								client.SendLivesState();
								}
							}

				}
			}

		}
	}

	public void setActiveTank(String tekst) {
		if (tekst.equals("P1")) {
			activeTank=tank1;
			passiveTank=tank2;
		} else if (tekst.equals("P2")) {
			state=state.Wait;
			activeTank=tank2;
			passiveTank=tank1;
		} else {
			System.out.println("eror");
		}

	}
	public void moveEnemyTankTo(double x , double y, double arm){
		passiveTank.moveTankTo(x, y, arm);
		//TODO  narazie rusza siê tylko czo³g zosta³a jeszcze armata
	}
	
	public  State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		
	}

	public Tank getActiveTank() {
		return activeTank;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
	public ArrayList<Sprite> getGameObjects() {
	    return gameObjects;
	  }
	
	public Shell getShell() {
		return shell1;
	}
	
	public void setShell(Shell shell) {
		this.shell1 = shell;
	}

	
	public Pane getGameAnimationPane() {
		return gameAnimationPane;
	}
	
		
	
}
