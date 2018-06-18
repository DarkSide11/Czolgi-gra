package src.stage3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class FakeClient2 {
	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	private Executor executor = Executors.newCachedThreadPool();
	
	private boolean myOpponentIsReady = false;
	
	String response;
	
	
	
	public FakeClient2() throws Exception {
		// Pod³¹czanie do serwera:
		// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
		String serverAddress = "localhost";

//		socket = new Socket(serverAddress, PORT);
//		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		out = new PrintWriter(socket.getOutputStream(), true);
		myOpponentIsReady = false;
		
//		this.GetMessages.run();
		
//		System.out.println("fake client created");
		
		
		
	
		
				
	}
	
	public void connectSocketToServer (String serverAddress, int PORT) throws UnknownHostException, IOException {
		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void checkMessage() throws IOException {
		this.response = in.readLine();
	}
	
	
	
	
//	public void startMessages() { 
//		this.GetMessages.run();
//	}
//	
//	
//	Runnable GetMessages = () -> {
//		String response;
//			try {
//				
//			
//		
//			while (true) {
//				response = in.readLine();
//				
//				
//				if (response.startsWith("OPPONENT_IS_READY")) { 
//					System.out.println("Opponent is ready");
//					this.myOpponentIsReady=true;
//					break;
//					
//				}
//			}
//			
//			
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}
//		
//	};

	
	
	
}