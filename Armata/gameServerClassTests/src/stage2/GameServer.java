package src.stage2;

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
					  (currentPlayer.isDown == true)
			          ||(currentPlayer.opponent.isDown == true);
		
				
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
							
							// TODO - odpowiednia formula na wspolrzedne koncowe pocisku
							shellContactPosition.setLocation(power * angle, 10);
							System.out.println("Wspolrzedne upadku pocisku: " + shellContactPosition.x + "  " + shellContactPosition.y);
							
								// jesli pocisk spadnie w promieniu 5 px od czolgu przeciwnika to trafienie zaliczone
							if ((Math.abs(player.opponent.xPos - shellContactPosition.x) <= 5)
								&& (Math.abs(player.opponent.yPos - shellContactPosition.y) <= 5)){
								player.opponent.isDown = true;
							}
						
			
			// ustawienie na serwerze 'currentPlayer' jako drugiego z graczy
			currentPlayer = currentPlayer.opponent;
			
			// drugi z graczy (ktory w tym momencie juz jest currentPlayer) zostaje powiadomiony
			// o ruchu (strzale) swojego przeciwnika 
			// ta czesc sluzy tylko do przeslania tekstowej informacji o ruchu 
			// i ewentualnych konsekwencjach - informacja np o przegranej
			// ta czesc kodu NIE wysyla stanu planszy gry
			currentPlayer.opponentPlayerShot(power, angle, x, y);
			return true;
		}
		return false;
	}
}