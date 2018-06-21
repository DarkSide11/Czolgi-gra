package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa gracza po stronie serwerowej.
 * 
 * @author Robert Adamczuk
 *
 */

	class PlayerServer extends Thread {
	private GameServer game;
	private String token; // czyli jakby "pionek" P1 lub P2
	private PlayerServer opponent;
	
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	
	private double xPos;
	private double yPos;
	
	private int hitpoints = 3;
	
	private int myAmmo = 10;
	private int opponentAmmo = myAmmo;
	private DataProcessor dataProcessor;

	
/**
 * Konstruktor obiektów graczy.
 * W momencie utworzenia obiektu do aplikacji klienta wys³ane zostaje powitanie "WELCOME"
 * Koncepcja w kontekscie serwera: W momencie po³aczenia aplikacji klienta z serwerem tworzony jest obiekt gracza,
 * przypisany zostaje token P1, lub P2, wys³ane powiadomienie do aplikacji klienta, oraz informacja którym "pionkiem" gracz bêdzie gra³.
 * @param socket - port komunkacji
 * @param token - pionek z jakim gracz zaczyna grê np. P1, P2
 * @param game - plansza na której toczy siê gra
 * @param xPos - pozycja pocz¹tkowa gracza - oœ x
 * @param yPos - pozycja pocz¹tkowa gracza - oœ y
 * @param hitpoints - punkty wytrzymalosci
 */

	public PlayerServer(Socket socket, String token, GameServer game, double xPos, double yPos, int hitpoints) {
		this.socket = socket;
		this.token = token;  
		this.game = game;
		this.xPos = xPos;
		this.yPos = yPos;
		this.hitpoints = 3;
		dataProcessor = new DataProcessor();
		
		try {
			input = new BufferedReader(new InputStreamReader (socket.getInputStream()));
			
			output = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(socket.toString());
			
			// "WELCOME " - aplikacja KLIENTA bedzie szukac tego slowa w strumieniu danych z serwera
			// tak by przypisac znajdujacy sie po nim znak do gracza
			output.println("WELCOME " + token);
			
			// Analogicznie MESSAGE 
			if(token=="P1")
			output.println("MESSAGE Oczekiwanie na drugiego gracza");
			
			
		} catch (IOException e) {
			System.out.println("Gracz rozlaczony, blad: " + e);
//			System.exit(1);
		}
		
}
	
	
	// Gettery i settery pozycji czolgu gracza
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	
	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	
	
	public int getHitpoints() {
		return hitpoints;
	}


	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	

	/**
	 * Metoda przypisujaca graczowi przeciwnika
	 * @param opponent
	 */
		public void setOpponent(PlayerServer opponent) {
		this.opponent = opponent;
		
	}
		
		public PlayerServer getOpponent() {
			return opponent;
		}
		
				
		// metody opponent.... obsluguja wiadomosci o ruchu przeciwnika i ewentualnych konsekwencjach
		
		/**
		 * Motoda wywolywana po poprawnym trafieniu, po wystapieniu takiego zdarzenia sprawdzane sa warunki zwyciestwa
		 */
		public void opponentPlayerHit() {
			output.println("OPPONENT_HIT");
			// jesli uderzenie przeciwnika spowodowalo wygrana to wysyla wiadomosc o przegranej
			output.println(game.hasWinner() ? "DEFEAT" : "");
			System.out.println("defeat");
		}
		
		/**
		 * Metoda przekazujaca informacje o strzale przeciwnika
		 * @param power
		 * @param angle
		 * @param x
		 * @param y
		 */
		public void opponentPlayerShot(double power, double angle, double x, double y) {
				opponentAmmo--;
			 output.println("OPPONENT_SHOT " + power + ":" + angle + ":" + x + ":" + y);
			}
		
		
		/**
		 * Metoda informujaca o gotowosci przeciwnika do gry
		 */
		public void opponentPlayerIsReady() {
			 output.println("OPPONENT_IS_READY");
		}
		
		
		
		/**
		 * Nadpisana metoda run().
		 * W petli pobiera polecenia od klienta i odpowiednio je przetwarza
		 */
		@Override
		public void run() {
			try {
				
				output.println("MESSAGE Obaj gracze podlaczeni do serwera");
				
				if (token.equals("P1")) {
					output.println("MESSAGE Twoja tura");
				}
			
				
				// W petli pobiera polecenia od klienta i przetwarza je:
				while(true) {
					String commandLine = input.readLine();
					
					if(commandLine.startsWith("SHOT ")) { 		// 1- power, 2- angle, 3- x , 4- y
						
						double power = Double.parseDouble(dataProcessor.parseMoveData(1, commandLine.substring(5)));
						double angle = Double.parseDouble(dataProcessor.parseMoveData(2, commandLine.substring(5)));
						double xPos = Double.parseDouble(dataProcessor.parseMoveData(3, commandLine.substring(5)));
						double yPos = Double.parseDouble(dataProcessor.parseMoveData(4, commandLine.substring(5)));
						
						
						System.out.println("sila: " + power);
						System.out.println("kat: " + angle);
						System.out.println("x: "+ xPos +"    "+ "y: " + yPos);
						
						if (game.validShot(power, angle, this, xPos, yPos)) {
							
							myAmmo--;
							System.out.println("myAmmo: " + myAmmo + "opponentAmmo: " + opponentAmmo);
							if (myAmmo < 0 && opponentAmmo <= 0) {
								output.println("TIE");
							}
				
							output.println("VALID_SHOT"); 					

						} else {
							output.println("MESSAGE Nieznane polecenie");
						}
					} else if (commandLine.startsWith("HIT")) {
						System.out.println("trafienie w przeciwnika: " + opponent.toString());
						opponent.hitpoints--;
						System.out.println("punkty wytrzymalosci przeciwnika: " + opponent.hitpoints);
						
						if (game.validHit(this)) {
							output.println("VALID_HIT");
							System.out.println("valid hit");
							output.println(game.hasWinner() ? "VICTORY " + token :  "");
						} else {
							output.println("MESSAGE Nieznane polecenie");
						}
					} 
					
					else if (commandLine.startsWith("I_AM_READY")) {
						if (game.validConfirmation(this)) { //true jesli P1:
							
						} else { // jesli P2:
							game.validConfirmationIfFirstFromP2();
							System.out.println("P2 (nieaktywny gracz) potwierdzil gotowosc jako pierwszy");
						}	
					}
					
					else if (commandLine.startsWith("QUIT")) {
						return;
					}
				}
				
				
			} catch (IOException e ) {
				System.out.println("Polaczenie przerwane " + e);
//				System.exit(1);
			} catch ( NullPointerException e ) {
				  System.out.println("null");
				  e.printStackTrace();
				  System.exit(1);
			} catch ( Exception e ) {
				  System.out.println("Nieoczekiwany blad");
				  e.printStackTrace();
				  System.exit(1);
			}
			finally {
				try {
					socket.close();
					
				} catch (IOException e) {
					System.out.println("Blad zamykania socketu: " + e);
					System.exit(1);
				}
			}
			
		}
		
	
}





