 package client;

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



public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Game armata = new Game(primaryStage);
		Client client = new Client();
		client.setGame(armata);
		armata.setClient(client);
		client.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		launch(args);
				
	}

	


	
	
		
	
	
		
	
	
	
	
	
	
}


