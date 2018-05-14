package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	private Game game;
	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	private BufferedReader in;
	private PrintWriter out;


public Client() throws Exception {
	// Pod³¹czanie do serwera:
	// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
	String serverAddress="172.23.242.29";
	
	socket = new Socket (serverAddress, PORT);
	in = new BufferedReader(new InputStreamReader (socket.getInputStream()));
	out = new PrintWriter(socket.getOutputStream(), true);
	out.println("SHOT " + "45" + "1");
	System.out.println("Tresc wiadomosci do serwer: \n" + "SHOT " + "45 " + "1");
}


public void play() throws Exception {
	String response;
	
	try {
		response = in.readLine();
		
		if (response.startsWith("WELCOME")) {
			String token = response.substring(8);
			System.out.println(token);
			game.setActiveTank(token);
		
		//	frame.setTitle("Tanki - gracz: " + token);
					
		}
		
		while (true) {
			response = in.readLine();
			
			if (response.startsWith("VALID_SHOT")) {
			//	messageLabel.setText("Prawidlowy ruch, prosze czekac");
				
				
				
			} else if (response.startsWith("OPPONENT_SHOT")) {
				int shot = Integer.parseInt(response.substring(14));					
			//	messageLabel.setText("Przeciwnik wykonal ruch" + shot + ", Twoja kolej");
				
			} else if (response.startsWith("VICTORY")) {
			//	messageLabel.setText("Wygrales");
				break;
			
			} else if (response.startsWith("DEFEAT")) {
				//messageLabel.setText("Przegrales");
				break;
			
			} else if (response.startsWith("TIE")) { // do opracowania na serwerze - np koniec amunicji
			//	messageLabel.setText("Remis");
				break;
			
			} else if (response.startsWith("MESSAGE")) {
			//	messageLabel.setText(response.substring(8));
			}
			
		} 
		out.println("QUIT");
		
		System.out.println("QUIT");
		
	} finally {
		socket.close();
	}
}
}