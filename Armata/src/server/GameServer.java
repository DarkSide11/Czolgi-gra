package server;


/**
 * Klasa, kt�rej obiekty pe�ni� funkcj� planszy gry.
 */
 class GameServer {

	// gracz ktory moze wykonac ruch:
	private PlayerServer currentPlayer;
	private volatile int playersThatAreReadyCounter;
//	private volatile Hourglass hourglass = new Hourglass(10);
	boolean p2ConfirmedFirst;
		


	/**
	 * Metoda sprawdza czy aktualny stan planszy oznacza zwyciestwo jednego z graczy 
	 * - w aktualnej implementacji na podstawie pozosta�ych graczom punkt�w wytrzymalo�ci
	 * @return boolean (currentPlayer.getHitpoints() <= 0) || (currentPlayer.getOpponent().getHitpoints() <= 0);
	 * @author Robert Adamczuk 
	 */
	public boolean hasWinner() {
				return

					(currentPlayer.getHitpoints() <= 0)
			          ||(currentPlayer.getOpponent().getHitpoints() <= 0);
				
	}
	
	

	/**
	 * Metoda sprawdza czy wykonany strza� jest prawid�owym ruchem z punktu widzenia gry.
	 * To znaczy, czy gracz kt�ry pr�buje strzeli� jest graczem "na ruchu".
	 * Je�li tak, to na planszy gry, przeciwnik gracza kt�ry wykona� ruch staje si� graczem "na ruchu".
	 * Nast�pnie zostaje powiadomiony o ruchu swojego przeciwnika. Dok�adniej wywo�ana zostanie metoda wysy�aj�ca do 
	 * cz�ci klienckiej komunikat "OPPONENT_SHOT", oraz przekazane zostan� parametry strza�u.
	 * Metoda jest synchronizowana poniewa� obiekty graczy s� osobnymi, r�wnolegle pracuj�cymi w�tkami.
	 *  Przekazywane do przeciwnika parametry:
	 * @param power - si�a strza�u
	 * @param angle - k�t strza�u
	 * @param player - gracz kt�ry wykonuje strza�
	 * @param x - pozycja x strzelaj�cego
	 * @param y - pozycja y strzelaj�cego
	 * @return
	 * @author Robert Adamczuk 
	 * @param wind 
	 */
	public synchronized boolean validShot (double power, double angle, PlayerServer player, double x, double y, double wind) {
		if (player == currentPlayer) {
			
			currentPlayer = currentPlayer.getOpponent();
			
			currentPlayer.opponentPlayerShot(power, angle, x, y,wind);
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda sprawdzaj�ca poprawno�� trafienia.
	 * Trafienie mo�e nast�pi� tylko po wykonaniu strza�u przez przeciwnika - dlatego sprawdzany jest warunek,
	 * czy potencjalnie trafiony gracz jest przeciwnikiem tego kt�ry strzela�.
	 * Je�li tak odnotowywane jest trafienie.
	 * @param player - przeciwnik kt�ry wystrzeli� pocisk
	 * @return boolean
	 * @author Robert Adamczuk 
	 */
	public synchronized boolean validHit(PlayerServer player) { 
		if (player == currentPlayer.getOpponent()) {
			currentPlayer.opponentPlayerHit();
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda potwierdzaj�ca gotowo�� go gry.
	 * Je�li potwierdza gracz wykonuj�cy pierwszy ruch, na planszy gry graczem "na ruchu" staje si� jego przeciwnik
	 * Wysy�ana te� jest do niego wiadomo�� o gotowo�ci pierwszego z graczy.
	 * @param player - gracz potwierdzaj�cy gotowo��
	 * @return boolean
	 * @author Robert Adamczuk 
	 */
	public synchronized boolean validConfirmation (PlayerServer player) {
		if (player == currentPlayer) {
		
			currentPlayer = currentPlayer.getOpponent();
			
			currentPlayer.opponentPlayerIsReady();
			return true;
		} 
		
		return false;
	}
	
	/**
	 * Metoda potwierdzaj�ca gotowo�� go gry, je�li jako pierwszy potwierdza gracz P2.
	 * @author Robert Adamczuk 
	 */
	public synchronized void validConfirmationIfFirstFromP2() {
		currentPlayer.opponentPlayerIsReady();
		currentPlayer = currentPlayer.getOpponent();
	}
	
	/**
	 * Metoda zwracajaca obiekt gracza mog�cego wykona� ruch
	 * @return currentPlayer
	 * @author Robert Adamczuk 
	 */
	public PlayerServer getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Setter gracza mog�cego wykona� ruch.
	 * @author Robert Adamczuk 
	 */
	public void setCurrentPlayer(PlayerServer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * @return int Zwraca ilosc gotowych do gry graczy
	 * @author Robert Adamczuk 
	 */
	public synchronized int getPlayersThatAreReadyCounter() {
		return playersThatAreReadyCounter;
	}

	/**
	 * Zwieksza ilosc gotowych graczy o 1
	 * @author Robert Adamczuk 
	 */	
	public synchronized void addOneToPlayersThatAreReadyCounter() {
		this.playersThatAreReadyCounter++;
	}

	




	
	
}