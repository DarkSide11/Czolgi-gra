package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.ini4j.Ini;

import client.DataProcessing;
import client.Game.ApplicationState;
import client.Game.GameState;
import javafx.application.Platform;
/**
 * Klasa komunikacji aplikacji klienckiej z czeœci¹ serwerow¹
 * @author Robert Adamczuk and Tomasz Sat³awski
 *
 */
public class Client {

	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	private Game game;
	private BufferedReader input;
	private PrintWriter output;
	private Executor executor = Executors.newCachedThreadPool();

	/**
	 * @author Robert Adamczuk
	 * @throws Exception dowolny wyjatek
	 */
	public Client() throws Exception {
		// Pod³¹czanie do serwera:
		// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
		String serverAddress = "localhost";
		
		if(Client.loadConfig(serverAddress) == false) {
			System.out.println("wystapil problem konfiguracyjny");
			serverAddress = "localhost";
			System.out.println("Serwer domyœlny: " + serverAddress);
		}
			
		
		socket = new Socket(serverAddress, PORT);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
	}

	
	/**
	 * £aduje adres serwera z pliku konfiguracyjnego.
	 * @author Robert Adamczuk
	 * @return boolean true jesli operacja sie udala
	 */
	public static boolean loadConfig(String serverAddress) {
		try {
			Ini ini = new Ini(new File("config.ini"));
			System.out.println("adres serwera: " + ini.get("serverAddress", "ip"));
			serverAddress = ini.get("serverAddress", "ip");
				return true;
			} catch (IOException e) {
				System.out.println("Blad ladowania konfiguracji polaczenia: ");
//				e.printStackTrace();
				return false;
				
			}
		
	}
	

	/**
	 * WYSYLANE DANE PO WYKONANIU STRZALU:
	 * @author Robert Adamczuk and Tomasz Satlawski
	 */
	public void SendCoordninates(){
		
		// Po wystrzeleniu wysy³a: power, angle, pozycja x, pozycja y wind
		output.println("SHOT " + "10" + ":" + game.getActiveTank().getCannonAngle() + ":" + game.getActiveTank().x + ":" + game.getActiveTank().y+":"+game.getGameWind());	
		
	}
	
	// do wywolania sytuacji na serwerze jak po oddaniu strzalu, z ta roznica ze strzal animuje siê duzo ponizej pola gry
	/**
	 * @author Robert Adamczuk
	 */
	public void SendCoordinatesNoAmmo() {
		output.println("SHOT " + "10" + ":" + game.getActiveTank().getCannonAngle() + ":" + game.getActiveTank().x + ":" + 10*game.getActiveTank().y);	

	}
	
	/**
	 * @author Robert Adamczuk
	 */
	public void switchPlayers() {
		output.println("SWITCH_PLAYERS_TIMEOUT " + game.getActiveTank().getCannonAngle() + ":" + game.getActiveTank().x + ":" + game.getActiveTank().y );
		System.out.println("zamien graczy");
	}
	
	
	// PRZETWARZANIE OTRZYMYWANYCH WIADOMOSCI:
	/**
	 * @author Robert Adamczuk
	 */
	Runnable GetMessages = () -> {
		String response;
		try {
			response = input.readLine();

			if (response.startsWith("WELCOME")) {
				String token = response.substring(8);
				System.out.println("Token: " + token);
				game.setActiveTank(token);
				

			}

			while ((response = input.readLine())!=null) {
				System.out.println(response+" "+game.getActiveTank().toString());
				Thread.sleep(200);

				if (response.startsWith("VALID_SHOT")) {
					System.out.println("validshot"+game.getActiveTank().toString());
				}
					//WYKONA SIE PO NACISNIECIU SPACJI PRZEZ PRZECIWNIKA
					else if (response.startsWith("OPPONENT_SHOT")) { 
					game.setState(game.gameState.Play);
					String shot = response.substring(14);
					System.out.println("Przeciwnik wykonal ruch" + shot + ", Twoja kolej"+game.getActiveTank().toString());
					
					
					// Przemieszczenie czo³gu:
					game.gameState=GameState.Wait;//Tomasz Sat³awski
					double xPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(3, shot));
					double yPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(4, shot));
					double cannonAngle = Double.parseDouble(DataProcessing.parseMoveData(2, shot));
					double wind = Double.parseDouble(DataProcessing.parseMoveData(5, shot));
					System.out.println(xPosOfEnemy);
					System.out.println(yPosOfEnemy);
					System.out.println(cannonAngle);
					game.moveEnemyTankTo(xPosOfEnemy, yPosOfEnemy, cannonAngle);
					Thread.sleep(2000);
					
					
					// animacja strzalu:
					
					double angle = Double.parseDouble(DataProcessing.parseMoveData(2, shot));
	
						System.out.println("Pocisk juz jest");
						game.getShell().shoot(xPosOfEnemy, yPosOfEnemy, angle,wind);
						Thread.sleep(2000);
						game.gameState=GameState.Play;
					
				}  else if (response.startsWith("VICTORY")) {
					game.setApplicationState(game.applicationState.Victory);
					String token = response.substring(8);
					System.out.println("token: " + token);
					System.out.println("Wygrales " + token);
					break;
					
//					
					
				} else if (response.startsWith("DEFEAT")) {
					game.setApplicationState(game.applicationState.Defeat);
					System.out.println("Przegrales");
					break;

					
					
					

				} else if (response.startsWith("OPPONENT_HIT")) {
					System.out.println("przeciwnik trafil");
					int currentHitPoints = game.getMyHitPoints();
					game.eraseLive();
					game.setMyHitPoints(--currentHitPoints);
					System.out.println("pozostalo punktow wytrzymalosci: " + game.getMyHitPoints());
					
				}
				
				
				
				else if (response.startsWith("TIE")) { // do opracowania na serwerze - np koniec amunicji
				game.setApplicationState(ApplicationState.Draw);
					break;
				}
				
				else if (response.startsWith("OPPONENT_IS_READY")) { 
					System.out.println("Opponent is ready");
					game.setOpponentIsReady(true);
					
				}

				else if (response.startsWith("START_HOURGLASS")) { 
					System.out.println("Uruchamiam odliczanie");
					game.getHourglass().start();
					
				}
				
				else if (response.startsWith("RESET_HOURGLASS")) { 
					System.out.println("Uruchamiam odliczanie -  reset");
					game.getHourglass().resetTimer();
					
				}
				
				else if (response.startsWith("YOUR_TURN")) { 
					System.out.println("Uruchamiam odliczanie -  reset");
					game.getHourglass().resetTimer();
					
					
					String move = response.substring(10);
					
					// Przemieszczenie czo³gu:
					double xPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(2, move));
					double yPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(3, move));
					double cannonAngle = Double.parseDouble(DataProcessing.parseMoveData(1, move));
					System.out.println(xPosOfEnemy);
					System.out.println(yPosOfEnemy);
					System.out.println(cannonAngle);
					game.moveEnemyTankTo(xPosOfEnemy, yPosOfEnemy, cannonAngle);
					
					
					
					game.setState(GameState.Play);
					
				}
				

				else if (response.startsWith("MESSAGE Obaj gracze")) {
				game.setPolaczony(true);
					System.out.println(response.substring(8)+game.getActiveTank().toString());
				}
				else if (response.startsWith("MESSAGE")) {
					System.out.println(response.substring(8)+game.getActiveTank().toString());
				}

			}
			output.println("QUIT");
			game.setState(GameState.Wait); // po zakonczeniu gry blokuje

			System.out.println("QUIT");

		} catch (IOException e) {
			System.out.println("Blad IO wewnatrz GetMssages: ");
			e.printStackTrace();
			System.exit(1);
		} catch (InterruptedException e) {
			System.out.println("Blad Interrupted wewnatrz GetMssages: ");
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Blad zamykania socketu: ");
				e.printStackTrace();
				System.exit(1);
			}
		}
	};

	// Wysyla do serwera informacje o trafieniu
	/**
	 * @author Robert Adamczuk
	 */
		public void SendLivesState(){
			
			output.println("HIT");	
			
		}
		
		/**
		 * @author Robert Adamczuk
		 */
		public void iAmReady() {
			game.setNotifiedOpponent(true);
			output.println("I_AM_READY");
		}
	
		/**
		 * @author Robert Adamczuk 
		 * @throws Exception dowolny wyjatek
		 */
	public void play() throws Exception {
	
		executor.execute(GetMessages);
	}

	public void setGame(Game game) {
		this.game = game;
	}
}