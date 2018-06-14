package server;

import java.awt.Point;

class GameServer {
	// "Plansza gry"
	
	
//	private boolean noAmmoInTank1;
//	private boolean noAmmoInTank2;
//	
//	
//	public boolean isNoAmmoInTank1() {
//		return noAmmoInTank1;
//	}
//
//	public void setNoAmmoInTank1(boolean noAmmoInTank1) {
//		this.noAmmoInTank1 = noAmmoInTank1;
//	}
//
//	public boolean isNoAmmoInTank2() {
//		return noAmmoInTank2;
//	}
//
//	public void setNoAmmoInTank2(boolean noAmmoInTank2) {
//		this.noAmmoInTank2 = noAmmoInTank2;
//	}
	

	// pozycja pocisku w momencie kontaktu - np na wysokosci y przeciwnika
	Point shellContactPosition = new Point(); 
	
	// gracz ktory moze wykonac ruch:
	PlayerServer currentPlayer;
		
	// sprawdza czy aktualny stan planszy oznacza zwyciestwo jednego z graczy:
		// jesli ktorys z graczy ma status "trafiony" to rozgrywka ma juz zwyciezce
	public boolean hasWinner() {
					// zwraca wynik warunku logicznego:
			return
//					  (currentPlayer.isDown == true)
//			          ||(currentPlayer.opponent.isDown == true);
					(currentPlayer.getHitpoints() <= 0)
			          ||(currentPlayer.opponent.getHitpoints() <= 0);
				
	}
	
	
//	public boolean hasDraw() {
//		return (noAmmoInTank1 && noAmmoInTank2);
//	}
	
	
	
	
	// Sprawdzenie czy ruch jest legalny - tj. czy gracz ktory probuje wykonac ruch jest
	// graczem currentPlayer (graczem "na ruchu")
	// jesli ruch jest legalny - plansza gry jest aktualizowana (liczone jest polozenie pocisku i jego skutek)
	// a drugi z graczy staje sie currentPlayer dla serwera
	// oraz zostaje poinformowany o ruchu przeciwnika
	
	
	// synchronized - poniewaz pole currentPlayer moze byc nadpisywane przez kilka roznych watkow
	// aby uniknac konfliktu uzywa synchronizacji
	
	public synchronized boolean validShot (double power, double angle, PlayerServer player, double x, double y) {
		if (player == currentPlayer) {
//									
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
	
	
}