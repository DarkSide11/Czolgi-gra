package application;
	
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Client extends Application {
	
	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Game armata=new Game(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
}
	
	public Client(String serverAddress) throws Exception {
		
		// Pod³¹czanie do serwera:
		// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
		socket = new Socket (serverAddress, PORT);
		
		in = new BufferedReader(new InputStreamReader (socket.getInputStream()));
		
		out = new PrintWriter(socket.getOutputStream(), true);
		
		
		frame.getContentPane().add(fieldsPanel, "North");
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout (1, 1, 20, 20));
		
		
		centerPanel.add(shoot);
		shoot.addActionListener(e -> {
			
			out.println("SHOT " + powerTF.getText() + angleTF.getText());
			System.out.println("Tresc wiadomosci do serwer: \n" + "SHOT " + powerTF.getText() + angleTF.getText());
		    
		});
		
		frame.getContentPane().add(centerPanel, "Center");
		
	}
	
	
	
	
	
	/*
	 * G³ówny watek aplikacji klienta - nas³uchuje wiadomosci z serwera.
	 * Pierwsza wiadomoœæ zaczyna siê od WELCOME, zawiera token z jakim gracz zaczyna gre P1 lub P2
	 * Pózniej rozpoczyna sie dzialanie w petli - nasluchiwanie jednej z wiadomosci / komendy:
	 * 		"VALID_MOVE" , "OPPONENT_MOVED", "VICTORY", ...
	 * 		program bêdzie reagowa³ odpowiednio do wiadomosci i przekazywanych argumentow 
	 * 
	 * 		Jeœli nast¹pi "VICTORY", "TIE" lub "DEFEAT" program zapyta uzytkownika czy kontynuowac gre,
	 * 			// TODO - opracowac remis - np brak pociskow
	 * 		w przypadku odpowiedzi negatywnej do serwera zostanie wys³ana wiadomoœæ "QUIT"
	 * 		z serwera do przeciwnika zostanie wys³ana wiadomoœæ "OPPONENT_QUIT" a w odpowiedzi na nia
	 * 		z aplikacji przeciwnika równie¿ wysy³ane jest "QUIT" 
	 */
	
	public void play() throws Exception {
		String response;
		
		try {
			response = in.readLine();
			
			if (response.startsWith("WELCOME")) {
				String token = response.substring(8);
				System.out.println(token);
			
				frame.setTitle("Tanki - gracz: " + token);
						
			}
			
			while (true) {
				response = in.readLine();
				
				if (response.startsWith("VALID_SHOT")) {
					messageLabel.setText("Prawidlowy ruch, prosze czekac");
					
					
					
				} else if (response.startsWith("OPPONENT_SHOT")) {
					int shot = Integer.parseInt(response.substring(14));					
					messageLabel.setText("Przeciwnik wykonal ruch" + shot + ", Twoja kolej");
					
				} else if (response.startsWith("VICTORY")) {
					messageLabel.setText("Wygrales");
					break;
				
				} else if (response.startsWith("DEFEAT")) {
					messageLabel.setText("Przegrales");
					break;
				
				} else if (response.startsWith("TIE")) { // do opracowania na serwerze - np koniec amunicji
					messageLabel.setText("Remis");
					break;
				
				} else if (response.startsWith("MESSAGE")) {
					messageLabel.setText(response.substring(8));
				}
				
			} 
			out.println("QUIT");
			
			System.out.println("QUIT");
			
		} finally {
			socket.close();
		}
	}
	
	private boolean wantsToPlayAgain() {
		int response = JOptionPane.showConfirmDialog (frame, "Chcesz zagrac ponownie? ","Gra zakonczona",JOptionPane.YES_NO_OPTION);
		frame.dispose();
		return response == JOptionPane.YES_NO_OPTION;
		
		// TODO ?? czy dodac tu socket.close()? - juz nie, ale przyda sie wysylanie QUIT w przypadku 
		// zamkniecia okna krzyzykiem.
	}
	
	
		
	
	public static void main(String[] args) throws Exception {
		
		while (true) {
			
			String host = JOptionPane.showInputDialog(null, 
					"Wprowadz IP serwera", 
					"Tanki - konfiguracja serwera",
					JOptionPane.QUESTION_MESSAGE);
			
			if (host == null) host = "localhost";
			
						
			String serverAddress = (args.length == 0) ? host : args[1];
			TankiClient client = new TankiClient(serverAddress);
			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.frame.setSize(320, 180);
			client.frame.setVisible(true);
			client.frame.setResizable(false);
			client.play();
			if (!client.wantsToPlayAgain()) {
				break;
			}
						
		}
				
	}
	
	
	
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
}
