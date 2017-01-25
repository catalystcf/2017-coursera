import static org.junit.Assert.*;
import java.util.List;

import java.util.Arrays;

import org.junit.Test;

public class SolverTest {
	
	int [][] tiles3x3_0 = { {1,2,3}, {4,5,6}, {7,8,0} };
	
	int [][] tiles3x3_1A = {{1, 2,3}, {4,5,6}, {7, 0, 8 } };
	int [][] tiles3x3_1B = {{1, 2,3}, {4,5,0}, {7, 8, 6 } };

	int [][] tiles3x3_2AA = { {1,2,3}, {4,5,6}, {0, 7, 8 } };
	int [][] tiles3x3_2AB = { {1,2,3}, {4,0,6}, {7, 5, 8 } };
	int [][] tiles3x3_2BA = { {1,2,3}, {4,0,5}, {7, 8, 6 } };
	int [][] tiles3x3_2BB = { {1,2,0}, {4,5,3}, {7, 8, 6 } };
	
	int [][] tiles3x3_2BB_1 = { {1,0,2}, {4,5,3}, {7, 8, 6 } };
	int [][] tiles3x3_2BB_2 = { {1,5,2}, {4,0,3}, {7, 8, 6 } };
	int [][] tiles3x3_2BB_3 = { {1,5,2}, {4,8,3}, {7, 0, 6 } };
	int [][] tiles3x3_2BB_4 = { {1,5,2}, {4,8,3}, {7, 6, 0 } };
	
	
	
	
	Board b3x3_0  = new Board( tiles3x3_0 );
	Board b3x3_1A = new Board( tiles3x3_1A );
	Board b3x3_1B = new Board( tiles3x3_1B );
	
	Board b3x3_2AA = new Board( tiles3x3_2AA );
	Board b3x3_2AB = new Board( tiles3x3_2AB );
	Board b3x3_2BA = new Board( tiles3x3_2BA );
	Board b3x3_2BB = new Board( tiles3x3_2BB );
	
	
	@Test
	public void test_solveMore(){
		
		Board b = new Board( this.tiles3x3_2BB_4);
		Solver s = new Solver( b);
		assertTrue( s.isSolvable());
		
		System.out.println(s.moves());
	}
	
	
	private void checkSolve( Board b, int expectedMoves) {
		Solver s = new Solver( b );
		assertTrue(s.isSolvable() );
		assertEquals( expectedMoves, s.moves());
		
		int ms =0;
		for (Board bs:s.solution()) {
			ms++;
		}
		
		assertEquals( ms, s.moves() + 1 );
		
	}
	
	/**
	 * Check one step solution against a sequence
	 */
	@Test 
	public void testSolveOne_Sequence( ) {
		
		
		
		List<int [][] > expected = Arrays.asList( tiles3x3_1A, tiles3x3_0  );
		
		checkSolutionSequence( b3x3_1A, expected );
		
		
	}
	

	private void checkSolutionSequence( Board b, List<int [][]> steps  )
	{
		Solver s = new Solver( b );
		assertTrue(s.isSolvable() );
		assertEquals( steps.size() -1, s.moves());
		
		int i=0;
		for( Board sb : s.solution() ) {
			
			assertEquals( sb, new Board( steps.get(i) ) );
			
			i = i+1;
			
		}
		
	}
	@Test
	public void testSimpleSolve () {
		checkSolve( b3x3_0, 0 );
	}
	
	@Test
	public void testSolve_One () {
		checkSolve( b3x3_1A, 1 );
		checkSolve( b3x3_1B, 1 );
		
	}
	
	@Test 
	public void testUnsolvable_One() {
		Board b = b3x3_1A.twin();
		
		assertEquals( b.twin(), b3x3_1A); // check that we expect twin back
		
		Solver s = new Solver( b );
		assertFalse( s.isSolvable() );
		
		assertNull( s.solution() );
	
	}

	@Test
	public void testSolve_Two () {
		checkSolve( b3x3_2AA, 2 );
		checkSolve( b3x3_2AB, 2 );
		checkSolve( b3x3_2BA, 2 );
		checkSolve( b3x3_2BB, 2 );
	}
	
	
	/** solve example in the assignment
	 *   and compare the trace.
	 */
	@Test
	public void testTrace() {
		
		int [][] tiles = { {0,1,3}, {4,2,5}, {7,8,6} };
		Board b = new Board(tiles);
		
		Solver s = new Solver( b );
		assertTrue(s.isSolvable() );
		assertEquals( 4, s.moves());
		
	}
	
	@Test
	public void checkManhattan() {
		
		assertEquals( 0, b3x3_0.manhattan() );
		
		Board b = new Board( tiles3x3_2AB );
		
		
		assertEquals( 1,b3x3_1A.manhattan() );
		assertEquals( 1,b3x3_1B.manhattan() );
		
		assertEquals( 2,b3x3_2AA.manhattan() );
		assertEquals( 2,b3x3_2AB.manhattan() );
		assertEquals( 2,b3x3_2BA.manhattan() );
		assertEquals( 2,b3x3_2BB.manhattan() );
		
		
		
	}
	
	@Test
	public void checkHamming() {
		
		assertEquals( 0, b3x3_0.hamming() );
		
		assertEquals( 1,b3x3_1A.hamming() );
		assertEquals( 1,b3x3_1B.hamming() );
		
		assertEquals( 2,b3x3_2AA.hamming() );
		assertEquals( 2,b3x3_2AB.hamming() );
		assertEquals( 2,b3x3_2BA.hamming() );
		assertEquals( 2,b3x3_2BB.hamming() );
		
		
		int [][] tiles = { {1,3,2}, {4,6,5}, {7, 8, 0 } };
		Board b = new Board( tiles );
		assertEquals( 4, b.hamming() );
		
		
	}
	
	@Test
	public void test_BoardEquals( ) {
		
		Board b3x3_0_1  = new Board( tiles3x3_0 );
		
		assertEquals( b3x3_0_1, b3x3_0 );
		assertNotEquals( b3x3_0, null );
		assertNotEquals( b3x3_0, "blah" );
		
		
		
	}
	
	@Test 
	public void test_BigNumber() {
		System.out.println( 128 * 128 );
		System.out.println( Math.pow( 2, 7*4 ) );
		System.out.println( Character.MAX_VALUE);
		
		System.out.println( (128*128-1) * 128*128 );
	}
	
	@Test
	public void test_BoardToString() {
		
		assertEquals( b3x3_0.toString(), "3\n" +
				" 1  2  3 \n 4  5  6 \n 7  8  0 \n");
	}
	
		
}
