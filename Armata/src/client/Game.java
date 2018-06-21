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
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
/**
 * 
 * @author Tomasz Sat³awski and Robert Adamczuk
 *
 */
public class Game {

	/**
	 * Referencja do aktualnego clienta gry, pozwala nawysy³anie komunikatów do
	 * serwera
	 */
	public Client client;
	/**
	 * Stan gdy, informuje stan Play umo¿liwia wykonywanie ruchów czo³giem, Stan
	 * Wait blokuje ruch czo³gu - dzieki temu mechanizmowi zrealizowane s¹ tury w
	 * grze
	 */
	public GameState gameState;
	/**
	 * Stan aplikacji, informuje czy aplikacja czeka na po³aczenie nowego gracza,
	 * oraz o wygranej lub przegranej, odpowiada równie¿ za wyœwietlanie ekranu
	 * startowego
	 */
	public ApplicationState applicationState;

	/**
	 * Czo³g, którym gracz mo¿e wykonywaæ ruchy- w zale¿noœci od komunikatu z
	 * serwera wartoœæ ta ustawiana jest na czolg z lewej(tank1) lub z prawej(tank2)
	 */
	private Tank activeTank;
	/**
	 * Czo³g przeciwnika w zaleznoœci od komunikatu z serwera wartoœæ ta ustawiana
	 * jest na czolg z lewej(tank1) lub z prawej(tank2) strony
	 */
	private Tank passiveTank;
	/**
	 * Okno aplikacj w którym rysowane s¹ animacje
	 */
	private Stage stage;
	/**
	 * Dziêki tej zmiennej obliczany jest czas trwana pojedyñczej klatki,
	 * uniezale¿nia to ruch czolgów od wydajnosci komputera gracza
	 */
	private Long startNanoTime;
	/**
	 * Klasa kontenerowa javafx
	 */
	private Scene scene;
	/**
	 * Klasa "p³ótna" na którym rysowane jest pod³o¿e czogów
	 */
	private Canvas canvas;
	/**
	 * Klasa wykorzystywana od rysowania w obiekcie klasy Canvas
	 */
	private GraphicsContext graphicsContext;
	/**
	 * Dziêki tej klasie rysowane s¹ animcje czo³gów
	 */
	private Pane gameAnimationPane;
	/**
	 * Pozycja pod³o¿a
	 */
	private double groundCoordinatex = 300;
	/**
	 * Czo³g zaczynaj¹cy gre z lewej strony
	 */
	private Tank tank1;
	private long animationTime = System.currentTimeMillis();
	private long frameTime = 0;
	/**
	 * Czo³g zaczynaj¹cy gre z prawej strony
	 */
	private Tank tank2;
	/**
	 * Lista obiektów które maj¹ byc renderowane w danej klatce, dzieki tej liscie
	 * realizowane jest równie¿ wykrywanie kolizji
	 */
	private ArrayList<Sprite> gameObjects = new ArrayList<Sprite>();
	/**
	 * Obiekt reprezentuj¹cy pociski wystrzeliwane przez graczy. W zwi¹zku z tym, ¿e
	 * jest to gra turowa w danej chwili na planszy mo¿e znajdowaæ siê tylko jeden
	 * lec¹cy pocisk
	 */
	private Shell shell1;
	/**
	 * Zmienna reprezentuj¹ca aktualn¹ liczbê ¿yæ gracza
	 */
	private int myHitPoints = 3;
	/**
	 * Zmienna reprezentuj¹ca liczbê nabojów, któr¹ dany gracz mo¿e wykorzystaæ
	 */
	private int myAmmo = 10;
	/**
	 * Obiekt reprezentujacy obraz ekranu tytu³owego
	 */
	private Image titleScreen;
	/**
	 * Obiekt umo¿liwiajacy rysowanie oraz skalowanie ekranu tytu³owego
	 */
	private ImageView titleScreenView;
	/**
	 * Obiekt reprezentujacy obraz ekranu czekania
	 */
	private Image waitingScreen;
	/**
	 * Obiekt umo¿liwiajacy rysowanie oraz skalowanie ekranu tytu³owego
	 */
	private ImageView waitingScreenView;
	/**
	 * Obiekt reprezentujacy obraz ekranu wyœwietlanego po zwyciêstwie gracza
	 */
	private Image victoryScreen;
	/**
	 * Obiekt umo¿liwiajacy rysowanie oraz skalowanie wyœwietlanego po zwyciêstwie
	 * gracza
	 */
	private ImageView victoryScreenView;
	/**
	 * Obiekt reprezentujacy obraz ekranu wyœwietlanego po przegranej gracza
	 */
	private Image defeatScreen;
	/**
	 * Obiekt umo¿liwiajacy rysowanie oraz skalowanie wyœwietlanego po przegranej
	 * gracza
	 */
	private ImageView defeatScreenView;
	/**
	 * Zmienna informuj¹ca czy do gry zostal pod³¹czony drugi gracz
	 */
	private boolean polaczony;
	/**
	 * Informuje czy zmiene gry zosta³y zainicjalizowane
	 */
	private boolean isGameInitialized;
	/**
	 * Tablica przechowuwuj¹ca ikony ¿yæ
	 */
	private Icon[] lives = new Icon[this.myHitPoints];
	/**
	 * Tablica przechowuwuj¹ca ikony amunicji
	 */
	private Icon[] ammo = new Icon[this.myAmmo];

	
	private boolean notifiedOpponent;
	
	public boolean isNotifiedOpponent() {
		return notifiedOpponent;
	}

	public void setNotifiedOpponent(boolean notifiedOpponent) {
		this.notifiedOpponent = notifiedOpponent;
	}
	
	private boolean opponentIsReady;

	public boolean isOpponentIsReady() {
		return opponentIsReady;
	}

	public void setOpponentIsReady(boolean opponentIsReady) {
		this.opponentIsReady = opponentIsReady;
	}

	/**
	 * Odpowiada ze obsluge wejœcia u¿ytkownika, reaguje w inny sposób w zale¿nosci
	 * od stanu w jakim znajduje siê gra oraz aplikacja
	 */
	public void keyInput() {
		gameAnimationPane.setFocusTraversable(true);

		this.gameAnimationPane.setOnKeyPressed(e -> {
			switch (applicationState) {
			case Playing:
				if (gameState != GameState.Wait) {
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

						if (this.myAmmo > -1) {

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
						} else if (myAmmo > -1) {
							shell1.shoot(activeTank.getX(), activeTank.getY(), activeTank.getCannonAngle());
							client.SendCoordninates();
						} else {
							System.out.println("Brak amunicji");
							client.SendCoordinatesNoAmmo();
						}

						setState(GameState.Wait);

						break;
					}
				}
				break;
			case StartScreen:

				switch (e.getCode()) {

				case SPACE:
					if (polaczony)
						setApplicationState(ApplicationState.Playing);
					else
						setApplicationState(ApplicationState.Waiting);
					break;
				}

				break;
			case Defeat:
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
	}

	/**
	 * Klasa animacji, w metodzie handle realizowane jest aktualizowanie pozycji
	 * obiektów gry na ekranie.
	 */
	private AnimationTimer animationTimer = new AnimationTimer() {

		@Override
		public void handle(long arg0) {
			switch (applicationState) {
			case Playing:
				
				if(!notifiedOpponent) {
				client.iAmReady();
				}
				
				if (opponentIsReady) {
				clearScreen();

				if (!isGameInitialized)
					gameInit();

				frameTime = (System.currentTimeMillis() - animationTime);

				animationTime = System.currentTimeMillis();

				for (Sprite x : gameObjects) {
					x.update(frameTime / 5);
				}
				handleCollisions();
				} else setApplicationState(ApplicationState.Waiting);
				
				
				break;
			case StartScreen:
				if (!gameAnimationPane.getChildren().contains(titleScreenView))
					gameAnimationPane.getChildren().add(titleScreenView);

				break;

			case Victory:
				gameAnimationPane.getChildren().clear();
				if (!gameAnimationPane.getChildren().contains(victoryScreenView))
					gameAnimationPane.getChildren().add(victoryScreenView);

				break;

			case Defeat:
				gameAnimationPane.getChildren().clear();
				if (!gameAnimationPane.getChildren().contains(defeatScreenView))
					gameAnimationPane.getChildren().add(defeatScreenView);

				break;
			case Waiting:
				if (gameAnimationPane.getChildren().contains(titleScreenView))
					gameAnimationPane.getChildren().remove(titleScreenView);

				if (!gameAnimationPane.getChildren().contains(waitingScreenView))
					gameAnimationPane.getChildren().add(waitingScreenView);
				if (polaczony)
					setApplicationState(ApplicationState.Playing);
				break;

			}
		}

		private void clearScreen() {
			if (gameAnimationPane.getChildren().contains(titleScreenView))
				gameAnimationPane.getChildren().remove(titleScreenView);
			if (gameAnimationPane.getChildren().contains(titleScreenView))
				gameAnimationPane.getChildren().remove(titleScreenView);
			if (gameAnimationPane.getChildren().contains(defeatScreenView))
				gameAnimationPane.getChildren().remove(defeatScreenView);
			if (gameAnimationPane.getChildren().contains(waitingScreenView))
				gameAnimationPane.getChildren().remove(waitingScreenView);
		}
	};

	/**
	 * Powoduje rozpoczêcie animowania obiektów w grze
	 */
	public void startAnimation() {
		animationTimer.start();
	}

	/**
	 * Konstruktor gry, ustawia stany pocz¹tkowe gry oraz inicjalizuje okno
	 * @param primaryStage - okno aplikacji, w którym ma byæ rysowana gra 
	 */
	public Game(Stage primaryStage) {
		polaczony = false;
		stage = primaryStage;
		stage.setTitle("Game screen");
		startNanoTime = System.nanoTime();
		this.initialize();
		this.applicationState = applicationState.StartScreen;
		isGameInitialized = false;
		primaryStage.show();

	}

	public void setPolaczony(boolean czyPolaczony) {
		this.polaczony = czyPolaczony;
	}

	/**
	 * Rysuje pod³o¿e, na którym poruszaj¹ sie czo³gi
	 */
	public void showTerrain() {
		Rectangle podloze = new Rectangle(20, 20);
		GridPane gridPane = new GridPane();
	}

	/**
	 * Inicjalizuje obiekty okna gry, wczytuje obrazy ekranów, inicjalizuje obiekty
	 * czo³gow, rozpoczyna animacje oraz wczytywanie wejœcia gracza
	 */
	private void initialize() {
		initlializeGameWindow();

		loadScreenImages();

		initializeTanks();
		keyInput();
		this.startAnimation();

	}

	/**
	 * Inicjalizuje obiekty czo³gow
	 */
	private void initializeTanks() {
		tank1 = new TankPlayer1(gameAnimationPane, 0, 250, 0, 0, 100);
		tank2 = new TankPlayer2(gameAnimationPane, 500, 250, 0, 0, 100);
		this.activeTank = tank1;
	}

	/**
	 * Wczytuje obrazy ekranów gry
	 */
	private void loadScreenImages() {
		titleScreen = new Image("icons/StartScreen.png");
		this.titleScreenView = new ImageView(titleScreen);

		defeatScreen = new Image("icons/DefeatScreen.png");
		defeatScreenView = new ImageView(defeatScreen);

		victoryScreen = new Image("icons/VictoryScreen.png");
		victoryScreenView = new ImageView(victoryScreen);

		waitingScreen = new Image("icons/WaitScreen.png");
		waitingScreenView = new ImageView(waitingScreen);
	}

	/**
	 * Inicjalizuej okno gry
	 */
	private void initlializeGameWindow() {
		Group root = new Group();
		canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);
		this.gameAnimationPane = new Pane();
		root.getChildren().add(gameAnimationPane);

		graphicsContext = canvas.getGraphicsContext2D();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
	}

	/**
	 * Dodaje pocisk oraz czo³gi do tablicy gameObjects, skutkuje to powjawieniem
	 * sie tych obiektów w animacji. Rysuje aktualn¹ liczbê pocisków oraz ¿yæ gracza
	 */
	private void gameInit() {

		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0, groundCoordinatex, canvas.getWidth(), canvas.getHeight() - this.groundCoordinatex);
		gameObjects.add(tank1);
		gameObjects.add(tank2);
		shell1 = new Shell(gameAnimationPane, 400, 800, false); // Ustawic pocisk tak by byl najmniej widoczny (blisko
																// dolu pola gry)
		gameObjects.add(shell1);
		// rysuje "liczbe zyc" oraz "stan amunicji" @author Robert Adamczuk
		drawLives();
		drawAmmo();
		isGameInitialized = true;
	}

	/**
	 * Rysuje aktualn¹ liczbê pocisków oraz ¿yæ gracza
	 * @author Robert Adamczuk
	 */
	public void drawLives() {
		double space = 60;

		for (int i = 0; i < this.myHitPoints; i++) {
			lives[i] = new Icon(gameAnimationPane, 10 + space * i, 520, "/icons/armour4.png");
			lives[i].render(10 + space * i, 520);
		}
	}

	/**
	 * Zmniejsza liczbê punktów ¿ycia gracza
	 * @author Robert Adamczuk
	 */
	public void eraseLive() {
		lives[myHitPoints - 1].hide();
	}

	/**
	 * Rysuje aktualn¹ liczbê amunicji
	 * @author Robert Adamczuk
	 */
	public void drawAmmo() {
		double space = 25;

		for (int i = 0; i < this.myAmmo; i++) {
			ammo[i] = new Icon(gameAnimationPane, 250 + space * i, 520, "/icons/tankAmmo4.png");
			ammo[i].render(250 + space * i, 520);
		}
	}

	/**
	 * Zmniejsza liczbê amunicji gracza
	 * @author Robert Adamczuk
	 */
	public void eraseAmmo() {
		ammo[myAmmo - 1].hide();
	}

	/**
	 * Wykrywa kolizje pomiedzy argumentami
	 * @param  a -Obiekt typu Sprite 
	 * @param b -Obiekt typu Sprite 
	 * @return true- wystêpuje kolizja pomiedzy obiektami 
	 * 		false- brak kolizji 
	 */
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

	/**
	 * Obsluguje kolizje, w zale¿noœci od typu obiektów, które koliduja ze sobi¹,
	 * powoduje przesuniêcie czo³gu lub zaliczenie trafienia
	 */
	public void handleCollisions() {

		for (int i = 0; i < gameObjects.size(); i++) {
			for (int a = 0; a < gameObjects.size(); a++) {
				if (a != i) {
					if (this.isCollisionBettwen(gameObjects.get(i), gameObjects.get(a)))
						if (!(gameObjects.get(a) instanceof Shell))
							if (!gameObjects.get(a).equals(activeTank)) { // na te chwile activeTank to tak naprawde
																			// czolg gracza, passive to przeciwnika

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

	/**
	 * Ustawia aktywny czo³g 
	 * @param tekst
	 *            "P1" ustawia aktywny czo³g na ten z lewej
	 * 				"P2" ustaiwa aktywny czo³g na ten z prawej strony
	 */
	public void setActiveTank(String tekst) {
		if (tekst.equals("P1")) {
			activeTank = tank1;
			passiveTank = tank2;
		} else if (tekst.equals("P2")) {
			gameState = gameState.Wait;
			activeTank = tank2;
			passiveTank = tank1;
		} else {
			System.out.println("eror");
		}

	}

	/**
	 * Odpowiada za animacje czo³gu przeciwnika po tym jak wykona on swój ruch
	 * 
	 * @param x
	 *            pozucja x czolgu
	 * @param y
	 *            pozycaja y czo³gu 
	 * @param arm
	 *            k¹t podniesienia armaty 
	 */
	public void moveEnemyTankTo(double x, double y, double arm) {
		int i1;
		if (activeTank == tank1) {
		} else {
		}

		passiveTank.moveTankTo(x, y, arm);

	}

	/**
	 * @return aktualny stan  gry
	 */
	public GameState getState() {
		return gameState;
	}
	/**
	 * @param state Rzadany stan gry 
	 */
	public void setState(GameState state) {
		this.gameState = state;

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

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public enum ApplicationState {
		Playing, StartScreen, Defeat, Victory, Waiting
	};

	public enum GameState {
		Play, Wait
	};

	public int getMyHitPoints() {
		return myHitPoints;
	}

	public void setMyHitPoints(int myHitPoints) {
		this.myHitPoints = myHitPoints;
	}

}
