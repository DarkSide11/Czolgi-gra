package server;

import java.net.ServerSocket;

/** 
 * <h1> Czêœæ serwerowa gry </h1>
 * Tworzy serwer gry do którego mog¹ pod³¹czaæ siê aplikacje klienckie.
 * @author Robert Adamczuk 
 * */
public class MainServer {
	/**
	 * Metoda main() czesci serwerowej. 
	 * Tworzy gniazdo serwerowe. Nastêpnie w petli tworzy jeden obiekt gry, oraz dwa obiekty graczy.
	 * Obiekty graczy tworzone s¹ w odpowiedzi na nawi¹zanie po³¹czenia aplikacji klienckiej z socketem serwerowym (listener.accept())
	 * Dwa utworzone obiekty graczy ³¹czone s¹ w parê - poprzez wzajemne ustawienie siebie jako przeciwników.
	 * Pierwszy grê zacznie ten z graczy który po³¹czy³ siê wczeœniej.
	 * Obiekty graczy dziedzicz¹ z Thread - nastêpuje ich uruchomienie i gra rozpoczyna siê.
	 * Klauzula finally zawiera metodê close() - zamyka socket w przypadku wyst¹pienia b³êdu, odblokowuj¹c port na potrzeby uruchomienia kolejnej instancji programu.
	 * @param args Unused
	 * @throws Exception Wyst¹pienie b³êdu zamyka program.
	 * @author Robert Adamczuk 
	 */
public static void main(String[] args) throws Exception {
		
		ServerSocket listener = new ServerSocket(9999);
		System.out.println("Serwer uruchommiony");
		
		try {
			while(true) {
				GameServer game = new GameServer();
			PlayerServer playerP1 = new PlayerServer(listener.accept(), "P1", game, 0, 10, 3);						
			PlayerServer playerP2 = new PlayerServer(listener.accept(), "P2", game, 500, 10, 3);
			
			playerP1.setOpponent(playerP2);
			playerP2.setOpponent(playerP1);
			
			game.setCurrentPlayer(playerP1);
			
			playerP1.start();
			playerP2.start();
		} 
	} finally {
		listener.close();
		}
	}
}
