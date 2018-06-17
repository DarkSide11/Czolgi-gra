package server;

import java.awt.Point;

class GameServer {
	// "Plansza gry"
	

	

	// pozycja pocisku w momencie kontaktu - np na wysokosci y przeciwnika
	Point shellContactPosition = new Point(); 
	
	// gracz ktory moze wykonac ruch:
	PlayerServer currentPlayer;
		
	// sprawdza czy aktualny stan planszy oznacza zwyciestwo jednego z graczy:
		// jesli ktorys z graczy ma status "trafiony" to rozgrywka ma juz zwyciezce
	public boolean hasWinner() {
					// zwraca wynik warunku logicznego:
			return

					(currentPlayer.getHitpoints() <= 0)
			          ||(currentPlayer.opponent.getHitpoints() <= 0);
				
	}
	
	

	
	
	
	// Sprawdzenie czy ruch jest legalny - tj. czy gracz ktory probuje wykonac ruch jest
	// graczem currentPlayer (graczem "na ruchu")
	// jesli ruch jest legalny - plansza gry jest aktualizowana (liczone jest polozenie pocisku i jego skutek)
	// a drugi z graczy staje sie currentPlayer dla serwera
	// oraz zostaje poinformowany o ruchu przeciwnika
	
	
	// synchronized - poniewaz pole currentPlayer moze byc nadpisywane przez kilka roznych watkow
	// aby uniknac konfliktu uzywa synchronizacji
	
	public synchronized boolean validShot (double power, double angle, PlayerServer player, double x, double y) {
		if (player == currentPlayer) {
									
			// ustawienie na serwerze 'currentPlayer' jako drugiego z graczy
			currentPlayer = currentPlayer.opponent;
			
			// drugi z graczy (ktory w tym momencie juz jest currentPlayer) zostaje powiadomiony
			// o ruchu (strzale) swojego przeciwnika 
			// wywoluje "OPPONENT_SHOT"
			currentPlayer.opponentPlayerShot(power, angle, x, y);
			return true;
		}
		return false;
	}
	
	
	public synchronized boolean validHit(PlayerServer player) { 
		if (player == currentPlayer.opponent) {
			currentPlayer.opponentPlayerHit();
			return true;
		}
		return false;
	}
	
	
	public synchronized boolean validConfirmation (PlayerServer player) {
		if (player == currentPlayer) {
		// ustawienie na serwerze 'currentPlayer' jako drugiego z graczy
			currentPlayer = currentPlayer.opponent;
			
			currentPlayer.opponentPlayerIsReady();
			return true;
		}
		return false;
	}
	
	
	
}