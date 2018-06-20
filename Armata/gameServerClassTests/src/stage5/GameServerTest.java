package stage5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameServerTest {
	private PlayerServer p1, p2, p3;
	private GameServer game1;
	private ServerSocket listener;
	private ExecutorService executorService;
	private boolean bothPlayersCreated;
	
	private int nullValue; 
	private double nullValue2;
	
	
	
	@Before 
	public void preparePlayerServerObjects() throws Exception {
		
executorService = Executors.newFixedThreadPool(3);
bothPlayersCreated = false;
		
		Runnable createClient = () -> {
		
			while(bothPlayersCreated == false) {
			try {
				Thread.sleep(20);
				FakeClient2 fk1 = new FakeClient2();
				fk1.connectSocketToServer("localhost", 9999);
				
				FakeClient2 fk2 = new FakeClient2();
				fk2.connectSocketToServer("localhost", 9999);
				
								
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			}
			
			return;
		};
		
		
			listener = new ServerSocket(9999);
			game1 = new GameServer();		
			
			executorService.submit(createClient);
			p1 = new PlayerServer(listener.accept(), "P1", game1, 10, 10, 3);	
			p2 = new PlayerServer(listener.accept(), "P2", game1, 310, 10, 3);
					
			p3 = new PlayerServer(listener.accept(), "OUTSIDER", game1, 310, 10, 3);
			
			
			bothPlayersCreated = true; // outsider also created
			
			p1.setOpponent(p2);
			p2.setOpponent(p1);
			game1.setCurrentPlayer(p1);;
			executorService.submit(p1);
			executorService.submit(p2);
	}
	
	
	
	@After
	public void closePlayerServerObjects() throws IOException, InterruptedException {
		Thread.sleep(50);
		listener.close();
		executorService.shutdownNow();
	}
	
	
		
//	@Test
//	public void hasWinnerTestAreBothSameState() {
//		assertTrue(p1.isDown == p1.opponent.isDown);
//		
//	}
//	
//	@Test
//	public void hasWinnerTestAreBothFalse() {
//		p1.opponent = p2;assertTrue(!p1.isDown && !p1.opponent.isDown);		
//	}
//	
//	
//	@Test
//	public void hasWinnerTestIfP1IsDown() {
//		p1.isDown = true;
//		assertTrue(game1.hasWinner());	
//	}
//	
//	@Test
//	public void hasWinnerTestIfP2IsDown() {
//		p2.isDown = true;
//		assertTrue(game1.hasWinner());	
//	}
//	
//	@Test
//	public void hasWinnerTestIfBothDownFantasy() {
//		p2.isDown = true;
//		p1.isDown = true;
//		assertTrue(game1.hasWinner());	
//	}
//	
//	@Test
//	public void hasWinnerTestIfNoneIsDown() {
//		p2.isDown = false;
//		p1.isDown = false;
//		assertFalse(game1.hasWinner());	
//	}
	
	@Test
	public void hasWinnerTestInitialState()  {
		assertFalse(game1.hasWinner());	
	}

	@Test
	public void validShotTestNotCurrentPlayer() {
		game1.setCurrentPlayer(p2);
		assertFalse(game1.validShot(11, 11, p1, 310, 10));
	}
	
	@Test
	public void validShotTestNotCurrentPlayer2() {
		game1.setCurrentPlayer(p1);
		assertFalse(game1.validShot(11, 11, p2, 10, 10));
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayer() {
		game1.setCurrentPlayer(p1);
		assertTrue(game1.validShot(11, 11, p1, 110, 10));
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayer2() {
		game1.setCurrentPlayer(p2);
		assertTrue(game1.validShot(11, 11, p2, 210, 10));
	}
	
	
	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(31, 10, p1, 10, 10) && game1.hasWinner());
//		
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(10, 01, p2, 310, 10) && game1.hasWinner());
//		
//	}
	
	
	
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective() {
		game1.setCurrentPlayer(p1);
		assertTrue(game1.validShot(21, 10, p1, 10, 10) && !game1.hasWinner());

	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective2() {
		game1.setCurrentPlayer(p2);
		assertTrue(game1.validShot(21, 10, p2, 310, 10) && !game1.hasWinner());	

	}
	
	
	@Test 
	public void validShotTestInvalidDataInputExternalPlayer() {
		game1.setCurrentPlayer(p1);
//		PlayerServer p3 = new PlayerServer(new Socket(), "P2", game1, 310, 10, false);
		assertFalse(game1.validShot(10, 11, p3, 10, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput() {
		game1.setCurrentPlayer(p1);		
		assertFalse(game1.validShot(10, nullValue, p2, 10, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput2() {
		game1.setCurrentPlayer(p2);	
		assertFalse(game1.validShot(nullValue, -22, p1, 310, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput3() {
		game1.setCurrentPlayer(p1);		
		assertTrue(game1.validShot(10, nullValue, p1, 10, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput4() {
		game1.setCurrentPlayer(p2);		
		assertTrue(game1.validShot(nullValue, -22, p2, 310, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput5() {
		game1.setCurrentPlayer(p2);		
		assertTrue(game1.validShot(nullValue, -22, p2, nullValue, 10));
	}
	
	@Test
	public void validShotTestInvalidDataInput6() {
		game1.setCurrentPlayer(p2);		
		assertTrue(game1.validShot(nullValue, -22, p2, 310, nullValue));
	}
	
	
	/* stage 3 tests: */
	
	// changed hasWinner():
		
	@Test
	public void hasWinnerTestIfP1IsDown() {
		p1.setHitpoints(0);
		p2.setHitpoints(100);
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfP1IsDown2() {
		p1.setHitpoints(-1);
		p2.setHitpoints(10);
		assertTrue(game1.hasWinner());	
	}
	
	
	@Test
	public void hasWinnerTestIfP2IsDown() {
		p1.setHitpoints(99);
		p2.setHitpoints(0);
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfP2IsDown2() {
		p1.setHitpoints(5);
		p2.setHitpoints(-1);
		assertTrue(game1.hasWinner());	
	}
	
	
	@Test
	public void hasWinnerTestIfBothDownFantasy() {
		p1.setHitpoints(0);
		p2.setHitpoints(0);
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfBothDownFantasy2() {
		p1.setHitpoints(-1);
		p2.setHitpoints(0);
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfBothDownFantasy3() {
		p1.setHitpoints(0);
		p2.setHitpoints(-1);
		assertTrue(game1.hasWinner());	
	}
	
	
	@Test
	public void hasWinnerTestIfNoneIsDown() {
		p1.setHitpoints(1);
		p2.setHitpoints(1);
		assertFalse(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfNoneIsDown2() {
		p1.setHitpoints(3);
		p2.setHitpoints(5);
		assertFalse(game1.hasWinner());	
	}
	
		
	
	
	// new validHit():
	
	@Test
	public void validHitTestNotAnotherPlayer() {
		game1.setCurrentPlayer(p2);
		game1.validShot(11, 11, p1, 310, 10);
		assertFalse(game1.validHit(game1.getCurrentPlayer()));
	}
	
	@Test
	public void validHitTestNotAnotherPlayer2() {
		game1.setCurrentPlayer(p1);
		game1.validShot(11, 11, p2, 10, 10);
		assertFalse(game1.validHit(game1.getCurrentPlayer()));
	}
	
	@Test
	public void validHitTestPlayerIsAnotherPlayer() {
		game1.setCurrentPlayer(p1);
		game1.validShot(11, 11, p1, 110, 10);
		assertTrue(game1.validHit(game1.getCurrentPlayer().getOpponent()));
	}
	
	@Test
	public void validHitTestPlayerIsAnotherPlayer2() {
		game1.setCurrentPlayer(p2);
		game1.validShot(11, 11, p2, 210, 10);
		assertTrue(game1.validHit(game1.getCurrentPlayer().getOpponent()));
	}
	
	
	
	// changed tests "...WasEffective" - after validHit invoked:
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective() {
		game1.setCurrentPlayer(p1);
		p1.setHitpoints(1);
		p2.setHitpoints(1);
		
		game1.validShot(31, 10, p1, 10, 10);
		if (game1.validHit(game1.getCurrentPlayer().getOpponent())) { 
			game1.getCurrentPlayer().setHitpoints(game1.getCurrentPlayer().getHitpoints() - 1);
		}		
		assertTrue(game1.hasWinner());
		
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective2() {
		game1.setCurrentPlayer(p2);
		p1.setHitpoints(1);
		p2.setHitpoints(1);
		
		game1.validShot(31, 10, p1, 10, 10);
		if (game1.validHit(game1.getCurrentPlayer().getOpponent())) { 
			game1.getCurrentPlayer().setHitpoints(game1.getCurrentPlayer().getHitpoints() - 1);
		}		
		assertTrue(game1.hasWinner());
	}
	
	
	
	/* stage 4 tests: */

	
	// valid confirmation:
	
	@Test
	public void validConfirmationTestNotCurrentPlayer() {
		game1.setCurrentPlayer(p2);
		assertFalse(game1.validConfirmation(p1));
	}
	
	@Test
	public void validConfirmationTestNotCurrentPlayer2() {
		game1.setCurrentPlayer(p1);
		assertFalse(game1.validConfirmation(p2));
	}
	
	@Test
	public void validConfirmationTestPlayerIsCurrentPlayer() {
		game1.setCurrentPlayer(p1);
		assertTrue(game1.validConfirmation(p1));
	}
	
	@Test
	public void validConfirmationTestPlayerIsCurrentPlayer2() {
		game1.setCurrentPlayer(p2);
		assertTrue(game1.validConfirmation(p2));
	}
	
	
	
	
	@Test
	public void validConfirmationTestPlayerIsCurrentPlayer3()  {
		game1.setCurrentPlayer(p1);
		game1.validConfirmation(p1);
		assertTrue(game1.getCurrentPlayer() == p2);
		
	}
	
	@Test
	public void ConfirmationIfFirstFromP2Test() {
		game1.setCurrentPlayer(p1);
		game1.validConfirmationIfFirstFromP2();
		assertTrue(game1.getCurrentPlayer() == p2);
		
	}
	
	
	@Test
	public void getCurrentPlayerTest() {
		game1.setCurrentPlayer(p1);
		assertTrue(game1.getCurrentPlayer() == p1);
	}
	
	@Test
	public void getCurrentPlayerTest2() {
		game1.setCurrentPlayer(p2);
		assertTrue(game1.getCurrentPlayer() == p2);
	}
	
	@Test
	public void setCurrentPlayerTest1() {
		game1.setCurrentPlayer(p1);
		game1.setCurrentPlayer(game1.getCurrentPlayer().getOpponent());
		assertTrue(game1.getCurrentPlayer() == p2);
	}
	
	@Test
	public void setCurrentPlayerTest2() {
		game1.setCurrentPlayer(p2);
		game1.setCurrentPlayer(game1.getCurrentPlayer().getOpponent());
		assertTrue(game1.getCurrentPlayer() == p1);
	}
	
}
