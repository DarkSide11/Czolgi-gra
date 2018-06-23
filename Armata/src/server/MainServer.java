package server;

import java.net.ServerSocket;

/** 
 * <h1> Cz�� serwerowa gry </h1>
 * Tworzy serwer gry do kt�rego mog� pod��cza� si� aplikacje klienckie.
 * @author Robert Adamczuk 
 * */
public class MainServer {
	/**
	 * Metoda main() czesci serwerowej. 
	 * Tworzy gniazdo serwerowe. Nast�pnie w petli tworzy jeden obiekt gry, oraz dwa obiekty graczy.
	 * Obiekty graczy tworzone s� w odpowiedzi na nawi�zanie po��czenia aplikacji klienckiej z socketem serwerowym (listener.accept())
	 * Dwa utworzone obiekty graczy ��czone s� w par� - poprzez wzajemne ustawienie siebie jako przeciwnik�w.
	 * Pierwszy gr� zacznie ten z graczy kt�ry po��czy� si� wcze�niej.
	 * Obiekty graczy dziedzicz� z Thread - nast�puje ich uruchomienie i gra rozpoczyna si�.
	 * Klauzula finally zawiera metod� close() - zamyka socket w przypadku wyst�pienia b��du, odblokowuj�c port na potrzeby uruchomienia kolejnej instancji programu.
	 * @param args Unused
	 * @throws Exception Wyst�pienie b��du zamyka program.
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
