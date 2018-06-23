package server;


/**
 * Klasa, której obiekty pe³ni¹ funkcjê planszy gry.
 */
 class GameServer {

	// gracz ktory moze wykonac ruch:
	private PlayerServer currentPlayer;
	private volatile int playersThatAreReadyCounter;
//	private volatile Hourglass hourglass = new Hourglass(10);
	boolean p2ConfirmedFirst;
		


	/**
	 * Metoda sprawdza czy aktualny stan planszy oznacza zwyciestwo jednego z graczy 
	 * - w aktualnej implementacji na podstawie pozosta³ych graczom punktów wytrzymaloœci
	 * @return boolean (currentPlayer.getHitpoints() <= 0) || (currentPlayer.getOpponent().getHitpoints() <= 0);
	 * @author Robert Adamczuk 
	 */
	public boolean hasWinner() {
				return

					(currentPlayer.getHitpoints() <= 0)
			          ||(currentPlayer.getOpponent().getHitpoints() <= 0);
				
	}
	
	

	/**
	 * Metoda sprawdza czy wykonany strza³ jest prawid³owym ruchem z punktu widzenia gry.
	 * To znaczy, czy gracz który próbuje strzeliæ jest graczem "na ruchu".
	 * Jeœli tak, to na planszy gry, przeciwnik gracza który wykona³ ruch staje siê graczem "na ruchu".
	 * Nastêpnie zostaje powiadomiony o ruchu swojego przeciwnika. Dok³adniej wywo³ana zostanie metoda wysy³aj¹ca do 
	 * czêœci klienckiej komunikat "OPPONENT_SHOT", oraz przekazane zostan¹ parametry strza³u.
	 * Metoda jest synchronizowana poniewa¿ obiekty graczy s¹ osobnymi, równolegle pracuj¹cymi w¹tkami.
	 *  Przekazywane do przeciwnika parametry:
	 * @param power - si³a strza³u
	 * @param angle - k¹t strza³u
	 * @param player - gracz który wykonuje strza³
	 * @param x - pozycja x strzelaj¹cego
	 * @param y - pozycja y strzelaj¹cego
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
	 * Metoda sprawdzaj¹ca poprawnoœæ trafienia.
	 * Trafienie mo¿e nast¹piæ tylko po wykonaniu strza³u przez przeciwnika - dlatego sprawdzany jest warunek,
	 * czy potencjalnie trafiony gracz jest przeciwnikiem tego który strzela³.
	 * Jeœli tak odnotowywane jest trafienie.
	 * @param player - przeciwnik który wystrzeli³ pocisk
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
	 * Metoda potwierdzaj¹ca gotowoœæ go gry.
	 * Jeœli potwierdza gracz wykonuj¹cy pierwszy ruch, na planszy gry graczem "na ruchu" staje siê jego przeciwnik
	 * Wysy³ana te¿ jest do niego wiadomoœæ o gotowoœci pierwszego z graczy.
	 * @param player - gracz potwierdzaj¹cy gotowoœæ
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
	 * Metoda potwierdzaj¹ca gotowoœæ go gry, jeœli jako pierwszy potwierdza gracz P2.
	 * @author Robert Adamczuk 
	 */
	public synchronized void validConfirmationIfFirstFromP2() {
		currentPlayer.opponentPlayerIsReady();
		currentPlayer = currentPlayer.getOpponent();
	}
	
	/**
	 * Metoda zwracajaca obiekt gracza mog¹cego wykonaæ ruch
	 * @return currentPlayer
	 * @author Robert Adamczuk 
	 */
	public PlayerServer getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Setter gracza mog¹cego wykonaæ ruch.
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