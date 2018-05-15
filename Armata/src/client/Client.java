package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
		String serverAddress = "172.23.242.29";

		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	

	public void SendCoordninates(){
		
			out.println("SHOT " + "5 " + game.getActiveTank().getCannonAngle());	
	}
	
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
				} else if (response.startsWith("OPPONENT_SHOT")) {
					game.setState(game.state.Play);
					int shot = Integer.parseInt(response.substring(14));
					System.out.println("Przeciwnik wykonal ruch" + shot + ", Twoja kolej"+game.getActiveTank().toString());

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