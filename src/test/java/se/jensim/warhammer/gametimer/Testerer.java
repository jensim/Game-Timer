package se.jensim.warhammer.gametimer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Testerer {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int testTime = 3000;
		GamePanel gp = new GamePanel("", 2 * testTime);
		
		gp.start();
		try {
			Thread.sleep(testTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Thread exception");
		}
		gp.stop();
		
		try {
			Thread.sleep(testTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Thread exception");
		}
		
		gp.start();
		try {
			Thread.sleep(testTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Thread exception");
		}
		gp.stop();

		System.out.println(String.format("Remaining: %d, chould be as little as possible..",
				gp.timeRemain));
		assertTrue(gp.timeRemain <= 100);
		assertTrue(gp.timeRemain >= -10);
	}

	
}
