import java.util.Iterator;


import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/**
 *  First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue. Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node). Repeat this procedure until the search node dequeued corresponds to a goal board. The success of this approach hinges on the choice of priority function for a search node. We consider two priority functions:
 * @author alex
 *
 */
public class Solver {
	
	private SolverImpl si;
	private SolverImpl si_twin;
	
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	si = new SolverImpl( initial );
    	si_twin = new SolverImpl( initial.twin() );
    	
    	while( si.hasNext() ){
    		if (! si_twin.hasNext()) {
    			// if twin is solved - we know
    			// that primary would not be 
    			if ( si_twin.solved ) {
    				break;
    			}
    			// we exhausted nodes ( as not solvable )
    			// but let the main solver continue
    		} else {
    		
    			si_twin.next();
    		}
    		si.next();
    	}
    	
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
    	return this.si.solved;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
    	return this.si.currentNode.moves;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
    	Stack<Board> solutionStack = new Stack<Board>();
    	
    	SearchNode sn = this.si.currentNode;
    	
    	while( null != sn) {
    		solutionStack.push( sn.b );
    		sn = sn.prev;
    	}
    	
    	return solutionStack;
    	
    	
    	
    }
	
	
	
	/** 
	 *  First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
	 *  
	 */
	private class SolverImpl {
		
		MinPQ<SearchNode> minQ;
		boolean solved = false;
		SearchNode currentNode;
		
		
		boolean _verbose = false;
		int _step = 0;
		
		SolverImpl( Board initialBoard ) {
			
			SearchNode initNode = new SearchNode( initialBoard, 0, null );
			
			minQ = new MinPQ<SearchNode>();
			minQ.insert(initNode);
			
			currentNode = initNode;
		}
		/**
		 * 
		 *   Then, delete from the priority queue the search node with the minimum priority, 
		 *   and insert onto the priority queue all neighboring search nodes
		 *    (those that can be reached in one move from the dequeued search node).
		 *    
		 */
		void next() {
			
			/**
			 * Step 0:    priority  = 4
           moves     = 0
           manhattan = 4
           3            
            0  1  3     
            4  2  5     
            7  8  6  
			 */
			
			
			
			SearchNode prevNode = this.currentNode;
			this.currentNode = minQ.delMin();
			
			if (this.currentNode.b.isGoal()) {
				this.solved = true;
				return;
			}
			
			for( Board nb : this.currentNode.b.neighbors() ) {
				if ( nb.equals( prevNode.b )) continue; 	
				minQ.insert( new SearchNode( nb, this.currentNode.moves+1, prevNode ));
			}

			printStep();			
			_step++;
			
		}
		
		private String  [] stringLinesFortNode( SearchNode sn ) {
			
			String szBoard = sn.b.toString();
			String [] boardLines = szBoard.split( "\n");
			String [] lines = new String [ 3 + boardLines.length ];
			
			lines[0] = "priority  = "   + sn.score();
			lines[1] = "moves     = " + sn.moves;
			lines[2] = "manhattan = " + sn.b.manhattan();
			for (int i=3; i < 3+boardLines.length; i++) {
				lines[i] = boardLines[i-3] + "    ";
				
				if ( i == 3) {
					// first line has only one char.. so we need to add n+n-1
					lines[i] = lines[i] + "        ";
				}
			}
			
			return lines;
			
		}
		
		private void printStep() {
			
			if (! this._verbose )
					return ;
			
			System.out.println( "Step: " + _step + " priority  = " + this.currentNode.score() );
			System.out.println( "\tmoves     = " + this.currentNode.moves );
			System.out.println( "\tmanhattan = " + this.currentNode.b.manhattan() );
			System.out.println( "\t" + this.currentNode.b );
			
			// TODO: figure out total number of lines for the board ( 4) 
			StringBuffer [] repLines = new StringBuffer[4 + 4];
			
			for( Iterator<SearchNode> iter =  this.minQ.iterator(); iter.hasNext(); ) {
				
				SearchNode sn = iter.next();
				
				String [] lines = stringLinesFortNode( sn );
				for( int i=0; i < lines.length; i++ ) {
					if (null == repLines[i] ) {
						repLines[i] = new StringBuffer();
						repLines[i].append( i );
					} else {
						repLines[i].append( "  -  " );
						repLines[i].append( lines[i] );
					}
				}	
			}
			for ( int i=0; i < repLines.length; i++) {
				System.out.println(  repLines[i] );
			}
			
			
			System.out.println( "\n---------------------------------------------------------------------\n" );
			
		}
		
		/**
		 *  Repeat this procedure until the search node dequeued corresponds to a goal board.
		 */
		boolean hasNext() {
			return ! ( this.solved || minQ.isEmpty() );
		}
		
	}

	/**
	 *  We define a search node of the game to be:
	 * 	a board, 
	 *  the number of moves made to reach the board, 
	 *  and the previous search node. 
 	 */
	private class SearchNode implements Comparable<SearchNode> {
		
		Board b;
		int moves;
		SearchNode prev;
		
		SearchNode( Board b, int moves, SearchNode prev) {
			this.b = b;
			this.moves = moves;
			this.prev = prev;
		}
		
		private int score() {
			return this.b.manhattan() + this.moves;
		}

		@Override
		public int compareTo(SearchNode o) {
			return this.score() - o.score();
		}
		
	}
}
