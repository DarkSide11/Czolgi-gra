package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



class PlayerServer extends Thread {
	GameServer game;
	String token; // czyli jakby "pionek" P1 lub P2
	PlayerServer opponent;
	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	double xPos;
	double yPos;
	boolean isDown; // przechowuje informacje czy czolg jest trafiony
	

	// konstruuje obiekt gracza, ktory bedzie komunikowal sie przez okreslony port serwera
	// z aplikacja klienta, konstruktor przypisuje tez jeden z dwóch tokenow P1 lub P2
	// oraz wysy³a powitalne wiadomoœci do aplikacji klienta (WELCOME)
	
	// KONCEPCJA:
		// w momencie polaczenia aplikacji klienta z serwerem tworzony jest obiekt
		// przypisany zostaje token P1 lub P2, oraz wyslane powitanie do aplikacji klienta, informujace
		// o tym czy gra z oznaczeniem P1 czy P2
	
	


	public PlayerServer(Socket socket, String token, GameServer game, double xPos, double yPos, boolean isDown) {
		this.socket = socket;
		this.token = token;  
		this.game = game;
		this.xPos = xPos;
		this.yPos = yPos;
		this.isDown = false; 
		
		try {
			input = new BufferedReader(new InputStreamReader (socket.getInputStream()));
			
			output = new PrintWriter(socket.getOutputStream(), true);
			
			// "WELCOME " - aplikacja KLIENTA bedzie szukac tego slowa w strumieniu danych z serwera
			// tak by przypisac znajdujacy sie po nim znak do gracza
			output.println("WELCOME " + token);
			
			// Analogicznie MESSAGE 
			if(token=="P1")
			output.println("MESSAGE Oczekiwanie na drugiego gracza");
			
			
		} catch (IOException e) {
			System.out.println("Gracz rozlaczony, blad: " + e);
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
	
	

	// do obiektu jednego z graczy przypisuje obiekt przeciwnika:
		public void setOpponent(PlayerServer opponent) {
		this.opponent = opponent;
		
	}
	
	
	// obsluguje wiadomosc o ruchu przeciwnika i ewentualnych konsekwencjach
	
		
		public void opponentPlayerShot(double power, double angle, double x, double y) {
			// output.println("OPPONENT_SHOT " + power + angle); // !!!!!! substring //power 10-99 , angle 1-179 -> pierwsze dwie cyfry to zawsze moc pozostale to kat
			
			 output.println("OPPONENT_SHOT " + power + ":" + angle + ":" + x + ":" + y);
			
			// jesli ruch spowodowal zwyciestwo przeciwnika to gracz przegrywa
			
				// TODO dodac warunek remisu - np koniec pociskow lub cos w tym stylu
			
			// jesli nie to nic sie nie dzieje
			
			output.println(game.hasWinner() ? "DEFEAT" : "");
		}


		
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
						
						double power = Double.parseDouble(DataProcessing.parseMoveData(1, commandLine.substring(5)));
						double angle = Double.parseDouble(DataProcessing.parseMoveData(2, commandLine.substring(5)));
						double xPos = Double.parseDouble(DataProcessing.parseMoveData(3, commandLine.substring(5)));
						double yPos = Double.parseDouble(DataProcessing.parseMoveData(4, commandLine.substring(5)));
						
						
						
//						int power = Integer.parseInt((String) commandLine.subSequence(5, 6));
//						int angle = Integer.parseInt((String) commandLine.subSequence(7,9));
//						
						System.out.println("sila: " + power);
						System.out.println("kat: " + angle);
						System.out.println("x: "+ xPos +"    "+ "y: " + yPos);
						
						if (game.validShot(power, angle, this, xPos, yPos)) {
							output.println("VALID_SHOT"); 							
							output.println(game.hasWinner() ? "VICTORY" :  "");
						} else {
							output.println("MESSAGE Nieznane polecenie");
						}
					} else if (commandLine.startsWith("QUIT")) {
						return;
					}
				}
				
				
			} catch (IOException e ) {
				System.out.println("Polaczenie przerwane " + e);
			} finally {
				try {
					socket.close();
					
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			
		}
		
	
}





