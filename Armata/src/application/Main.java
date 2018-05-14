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
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Game armata = new Game(primaryStage);
		Client client = new Client();
		client.setGame(armata);
		client.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		launch(args);
				
	}

	


	
	
		
	
	
		
	
	
	
	
	
	
}


