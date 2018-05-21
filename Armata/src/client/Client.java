package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import client.DataProcessing;
import javafx.application.Platform;

public class Client {

	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	private Game game;
	private BufferedReader in;
	private PrintWriter out;
	private Executor executor = Executors.newCachedThreadPool();

	public Client() throws Exception {
		// Pod³¹czanie do serwera:
		// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
		String serverAddress = "localhost";

		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	
	// WYSYLANE DANE PO WYKONANIU STRZALU:
	public void SendCoordninates(){
		
		// Po wystrzeleniu wysy³a: power, angle, pozycja x, pozycja y
			

		out.println("SHOT " + "10" + ":" + game.getActiveTank().getCannonAngle() + ":" + game.getActiveTank().x + ":" + game.getActiveTank().y);	
		
	}
	
	// PRZETWARZANIE OTRZYMYWANYCH WIADOMOSCI:
	Runnable GetMessages = () -> {
		String response;
		try {
			response = in.readLine();

			if (response.startsWith("WELCOME")) {
				String token = response.substring(8);
				System.out.println(token);
				game.setActiveTank(token);

			}

			while ((response = in.readLine())!=null) {
				System.out.println(response+" "+game.getActiveTank().toString());
				Thread.sleep(200);

				if (response.startsWith("VALID_SHOT")) {
					System.out.println("validshot"+game.getActiveTank().toString());
				}
					//WYKONA SIE PO NACISNIECIU SPACJI PRZEZ PRZECIWNIKA
					else if (response.startsWith("OPPONENT_SHOT")) { 
					game.setState(game.state.Play);
					String shot = response.substring(14);
					System.out.println("Przeciwnik wykonal ruch" + shot + ", Twoja kolej"+game.getActiveTank().toString());
					
					
					// Przemieszczenie czo³gu:
					double xPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(3, shot));
					double yPosOfEnemy = Double.parseDouble(DataProcessing.parseMoveData(4, shot));
					
					System.out.println(xPosOfEnemy);
					System.out.println(yPosOfEnemy);
					
					game.moveEnemyTankTo(xPosOfEnemy, yPosOfEnemy, 0);
					
					
					
					// animacja strzalu:
					
					double angle = Double.parseDouble(DataProcessing.parseMoveData(2, shot));
	
						System.out.println("Pocisk juz jest");
						game.getShell().shoot(xPosOfEnemy, yPosOfEnemy, angle);
					

					
				} else if (response.startsWith("VICTORY")) {
					System.out.println("Wygrales"+game.getActiveTank().toString());
					break;

				} else if (response.startsWith("DEFEAT")) {
					System.out.println("Przegrales"+game.getActiveTank().toString());
					break;

				} else if (response.startsWith("TIE")) { // do opracowania na serwerze - np koniec amunicji
					System.out.println("Remis"+game.getActiveTank().toString());
					break;

				} else if (response.startsWith("MESSAGE")) {
					System.out.println(response.substring(8)+game.getActiveTank().toString());
				}

			}
			out.println("QUIT");

			System.out.println("QUIT");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void play() throws Exception {
	
		executor.execute(GetMessages);
	}

	public void setGame(Game game) {
		this.game = game;
	}
}