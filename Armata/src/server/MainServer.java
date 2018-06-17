package server;

import java.net.ServerSocket;


public class MainServer {
	
public static void main(String[] args) throws Exception {
		
		ServerSocket listener = new ServerSocket(9999);
		System.out.println("Serwer uruchommiony");
		
		try {
			while(true) {
				GameServer game = new GameServer();
						
			// Po³¹czenie 2 graczy do serwera - wiaze pare graczy z obiektem gry - game
			PlayerServer playerP1 = new PlayerServer(listener.accept(), "P1", game, 10, 10, false);						
			PlayerServer playerP2 = new PlayerServer(listener.accept(), "P2", game, 310, 10, false);
			
			// parowanie podlaczonych klientow jako przeciwnikow:
			playerP1.setOpponent(playerP2);
			playerP2.setOpponent(playerP1);
			
			// jako pierwszy rozpocznie gracz P1 - podlaczony jako pierwszy
			game.currentPlayer = playerP1;
			
			// obiekty graczy rozszerzaja watki -> .start()
			playerP1.start();
			playerP2.start();
		} 
	} finally {
		// zamkniecie zasobu - inaczej w przypadku awarii nie da sie ponownie na tym samym porcie
		// uruchomic kolejnej instancji
		listener.close();
		}
	}
}


