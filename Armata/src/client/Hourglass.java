package client;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Klasa obslugujaca odliczanie pozostalego czasu
 * @author Robert Adamczuk
 *
 */
public class Hourglass {
	private int initialTime;
	private int timeLeft;
	boolean isAlreadyRunning;
	Timer timer = new Timer();
	private Game game;
	
	private boolean timeUp;

	TimerTask countingDown = new TimerTask() {
		public void run() {
			if (timeLeft>0) {
			game.timeIcons[timeLeft-1].hide();
				timeLeft--;
			}
								
//				System.out.println("Pozosta³y czas: " + timeLeft);
				if (timeLeft <= 0) {
					timeUp = true;
				}
		}

	};

	public Hourglass(int initTime, Game game) {
		this.initialTime = initTime;
		this.isAlreadyRunning = false;
		this.timeUp = false;
		this.game = game;

	}

	
	
	public void start() {
		if (this.isAlreadyRunning == false) {
			this.timeUp = false;
			this.timeLeft = this.initialTime;
			timer.scheduleAtFixedRate(countingDown, 1000, 1000);
			this.isAlreadyRunning = true;
			
			}

	}

	public double getInitialTime() {
		return initialTime;
	}



	public double getTimeLeft() {
		return timeLeft;
	}

	public void resetTimer() {
		game.revealTime();
		
		this.timeUp = false;
		this.timeLeft = this.initialTime;

		TimerTask countingDown = new TimerTask() {
			public void run() {
				
				if (timeLeft>0) {
					game.timeIcons[timeLeft-1].hide();
						timeLeft--;
					}
				
//				System.out.println("Pozosta³y czas: " + timeLeft);
				if (timeLeft <= 0) {
					timeUp = true;
				}
			}
		};
		timer.cancel();
		timer = new Timer();
		timer.scheduleAtFixedRate(countingDown, 1000, 1000);
		
		

	}
	
	
	public boolean isTimeUp() {
		return timeUp;
	}

	public void setTimeUp(boolean timeUp) {
		this.timeUp = timeUp;
	}

	
}
