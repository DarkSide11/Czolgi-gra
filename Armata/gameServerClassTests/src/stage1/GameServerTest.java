package src.stage1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
	private PlayerServer p1, p2;
	private GameServer game1;
	private ServerSocket listener;
	private ExecutorService executorService;
	private boolean bothPlayersCreated;
	
	private int nullValue; 
	
	@Before 
	public void preparePlayerServerObjects() throws IOException, InterruptedException {
		
executorService = Executors.newFixedThreadPool(3);
bothPlayersCreated = false;
		
		Runnable createClient = () -> {
		
			while(bothPlayersCreated == false) {
			try {
				FakeClient fk1 = new FakeClient();
				FakeClient fk2 = new FakeClient();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			}
			
			return;
		};
		
		
			listener = new ServerSocket(9999);
			game1 = new GameServer();		
			
			executorService.submit(createClient);
			p1 = new PlayerServer(listener.accept(), "P1", game1, 10, 10, false);						
			p2 = new PlayerServer(listener.accept(), "P2", game1, 310, 10, false);
			
			bothPlayersCreated = true;;
			
			p1.setOpponent(p2);
			p2.setOpponent(p1);
			game1.currentPlayer = p1;
			executorService.submit(p1);
			executorService.submit(p2);
	}
	
	
	
	@After
	public void closePlayerServerObjects() throws IOException, InterruptedException {
		listener.close();
		executorService.shutdown();
	}
	
	
		
	@Test
	public void hasWinnerTestAreBothSameState() {
		assertTrue(p1.isDown == p1.opponent.isDown);
		
	}
	
	@Test
	public void hasWinnerTestAreBothFalse() {
		p1.opponent = p2;assertTrue(!p1.isDown && !p1.opponent.isDown);		
	}
	
	
	@Test
	public void hasWinnerTestIfP1IsDown() {
		p1.isDown = true;
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfP2IsDown() {
		p2.isDown = true;
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfBothDownFantasy() {
		p2.isDown = true;
		p1.isDown = true;
		assertTrue(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestIfNoneIsDown() {
		p2.isDown = false;
		p1.isDown = false;
		assertFalse(game1.hasWinner());	
	}
	
	@Test
	public void hasWinnerTestInitialState()  {
		assertFalse(game1.hasWinner());	
	}

	@Test
	public void validShotTestNotCurrentPlayer() {
		game1.currentPlayer = p2;
		assertFalse(game1.validShot(11, 11, p1));
	}
	
	@Test
	public void validShotTestNotCurrentPlayer2() {
		game1.currentPlayer = p1;
		assertFalse(game1.validShot(11, 11, p2));
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayer() {
		game1.currentPlayer = p1;
		assertTrue(game1.validShot(11, 11, p1));
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayer2() {
		game1.currentPlayer = p2;
		assertTrue(game1.validShot(11, 11, p2));
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective() {
		game1.currentPlayer = p1;
		assertTrue(game1.validShot(31, 10, p1) && game1.hasWinner());
		
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective2() {
		game1.currentPlayer = p2;
		assertTrue(game1.validShot(10, 01, p2) && game1.hasWinner());
		
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective() {
		game1.currentPlayer = p1;
		assertTrue(game1.validShot(21, 10, p1) && !game1.hasWinner());		
	}
	
	@Test
	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective2() {
		game1.currentPlayer = p2;
		assertTrue(game1.validShot(21, 10, p2) && !game1.hasWinner());		
	}
	
	
	@Test 
	public void validShotTestInvalidDataInputExternalPlayer() {
		game1.currentPlayer = p1;
		PlayerServer p3 = new PlayerServer(new Socket(), "P2", game1, 310, 10, false);
		assertFalse(game1.validShot(10, 11, p3));
	}
	
	@Test
	public void validShotTestInvalidDataInput() {
		game1.currentPlayer = p1;		
		assertFalse(game1.validShot(10, nullValue, p2));
	}
	
	@Test
	public void validShotTestInvalidDataInput2() {
		game1.currentPlayer = p2;		
		assertFalse(game1.validShot(nullValue, -22, p1));
	}
	
	@Test
	public void validShotTestInvalidDataInput3() {
		game1.currentPlayer = p1;		
		assertTrue(game1.validShot(10, nullValue, p1));
	}
	
	@Test
	public void validShotTestInvalidDataInput4() {
		game1.currentPlayer = p2;		
		assertTrue(game1.validShot(nullValue, -22, p2));
	}
	
	
	/* Stage 2 tests: */
	
	// Modified args:
	
//	@Test
//	public void validShotTestNotCurrentPlayer() {
//		game1.currentPlayer = p2;
//		assertFalse(game1.validShot(11, 11, p1, 310, 10));
//	}
//	
//	@Test
//	public void validShotTestNotCurrentPlayer2() {
//		game1.currentPlayer = p1;
//		assertFalse(game1.validShot(11, 11, p2, 10, 10));
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayer() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(11, 11, p1, 110, 10));
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayer2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(11, 11, p2, 210, 10));
//	}
//	
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
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(21, 10, p1, 10, 10) && !game1.hasWinner());		
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(21, 10, p2, 310, 10) && !game1.hasWinner());		
//	}
//	
//	
//	@Test 
//	public void validShotTestInvalidDataInputExternalPlayer() {
//		game1.currentPlayer = p1;
//		PlayerServer p3 = new PlayerServer(new Socket(), "P2", game1, 310, 10, false);
//		assertFalse(game1.validShot(10, 11, p3, 10, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput() {
//		game1.currentPlayer = p1;		
//		assertFalse(game1.validShot(10, nullValue, p2, 10, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput2() {
//		game1.currentPlayer = p2;		
//		assertFalse(game1.validShot(nullValue, -22, p1, 310, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput3() {
//		game1.currentPlayer = p1;		
//		assertTrue(game1.validShot(10, nullValue, p1, 10, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput4() {
//		game1.currentPlayer = p2;		
//		assertTrue(game1.validShot(nullValue, -22, p2, 310, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput5() {
//		game1.currentPlayer = p2;		
//		assertTrue(game1.validShot(nullValue, -22, p2, nullValue, 10));
//	}
//	
//	@Test
//	public void validShotTestInvalidDataInput6() {
//		game1.currentPlayer = p2;		
//		assertTrue(game1.validShot(nullValue, -22, p2, 310, nullValue));
//	}
	
}
