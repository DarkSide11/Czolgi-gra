package server;

import java.util.Timer;
import java.util.TimerTask;

public class Hourglass {
	private int initialTime;
	private int timeLeft;
	boolean isAlreadyRunning;
	Timer timer = new Timer();
	
	private boolean timeUp;

	TimerTask countingDown = new TimerTask() {
		public void run() {
				timeLeft--;
				System.out.println("Pozosta³y czas: " + timeLeft);
				if (timeLeft < 0) {
					timeUp = true;
				}
		}

	};

	public Hourglass(int initTime) {
		this.initialTime = initTime;
		this.isAlreadyRunning = false;
		this.timeUp = false;

	}
	
//	public void timeIsUp() {
//		System.out.println("TIME IS UP");
//	}
	
	
	public void reset() {

		// this.countingDown.cancel();
		this.timeUp = false;
		this.timeLeft = this.initialTime;
		timer.scheduleAtFixedRate(countingDown, 1000, 1000);

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

	// public void setInitialTime(int initialTime) {
	// this.initialTime = initialTime;
	// }

	public double getTimeLeft() {
		return timeLeft;
	}

	public void resetTimer() {
		this.timeLeft = this.initialTime;

		TimerTask countingDown = new TimerTask() {
			public void run() {
				timeLeft--;
				System.out.println("Pozosta³y czas: " + timeLeft);

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

	/*
	 * dodac do konstruktora w PlayerServer new Hourglass(time) wystartowac po
	 * przelaczeniu na play i jesli jest currentPlayer
	 * 
	 * 
	 * jesli nowa tura: reset() (przy validShot)
	 * 
	 * wewnatrz watku: setCurrentTime() getTimeLeft() if getTimeLeft < 0 -> do
	 * currentPlayer "your time is up" , zmiana gracza "opponent time was up"
	 * 
	 */
}
