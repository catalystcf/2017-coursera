import static org.junit.Assert.*;
import java.util.List;

import java.util.ArrayList;
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
	
	
	
	Board b3x3_0  = new Board( tiles3x3_0 );
	Board b3x3_1A = new Board( tiles3x3_1A );
	Board b3x3_1B = new Board( tiles3x3_1B );
	
	Board b3x3_2AA = new Board( tiles3x3_2AA );
	Board b3x3_2AB = new Board( tiles3x3_2AB );
	Board b3x3_2BA = new Board( tiles3x3_2BA );
	Board b3x3_2BB = new Board( tiles3x3_2BB );
	
	
	
	private void checkSolve( Board b, int expectedMoves) {
		Solver s = new Solver( b );
		assertTrue(s.isSolvable() );
		assertEquals( expectedMoves, s.moves());
		
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
			
			for(int row=0; row < sb.tiles.length; row ++ ) {
				if ( !Arrays.equals( sb.tiles[row], steps.get(i)[row] ) ) {
					System.out.println( "ROW: " + row);
					System.out.println( "1: " + sb.tiles[row]);
					System.out.println( "2: " + steps.get(i)[row] );
				
					fail( "mismatch");
				}
			}
			
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
		
		assertEquals( 1,b3x3_1A.manhattan() );
		assertEquals( 1,b3x3_1B.manhattan() );
		
		assertEquals( 2,b3x3_2AA.manhattan() );
		assertEquals( 2,b3x3_2AB.manhattan() );
		assertEquals( 2,b3x3_2BA.manhattan() );
		assertEquals( 2,b3x3_2BB.manhattan() );
		
		
		
	}
	
		
}
