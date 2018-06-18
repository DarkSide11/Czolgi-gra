//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;

//package stage0;
//

//
//
//
//public class GameServerTest {
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
//	
//	@Test
//	public void hasWinnerTestInitialState()  {
//		assertFalse(game1.hasWinner());	
//	}
//
//	@Test
//	public void validShotTestNotCurrentPlayer() {
//		game1.currentPlayer = p2;
//		assertFalse(game1.validShot(??, ??, p1));
//	}
//	
//	@Test
//	public void validShotTestNotCurrentPlayer2() {
//		game1.currentPlayer = p1;
//		assertFalse(game1.validShot(??, ??, p2));
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayer() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(??, ??, p1));
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayer2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(??, ??, p2));
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(??, ??, p1) && game1.hasWinner());
//		
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAndShotWasEffective2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(??, ??, p2) && game1.hasWinner());
//		
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective() {
//		game1.currentPlayer = p1;
//		assertTrue(game1.validShot(??, ??, p1) && !game1.hasWinner());		
//	}
//	
//	@Test
//	public void validShotTestPlayerIsCurrentPlayerAButShotWasNotEffective2() {
//		game1.currentPlayer = p2;
//		assertTrue(game1.validShot(??, ??, p2) && !game1.hasWinner());		
//	
//	}
//}
