package src.stage2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class FakeClient {
	private static int PORT = 9999; // port na serwerze który nasluchuje, odbiera i wysyla
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	private Executor executor = Executors.newCachedThreadPool();

	public FakeClient() throws Exception {
		// Pod³¹czanie do serwera:
		// Tworzy port komunikujacy siê z portem serwera o konkretnym adresie IP
		String serverAddress = "localhost";

		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
//		System.out.println("fake client created");
	}
}