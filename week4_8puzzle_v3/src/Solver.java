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
	private Boolean isSolved = null;
	private int moves = -1;

	private Stack<Board> solutionStack; ;
	
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	si = new SolverImpl( initial );
    	si_twin = new SolverImpl( initial.twin() );
    	
    	while( si.hasNext() ){
    		if ( null != si_twin ) {
    			if (si_twin.hasNext()) {
    				si_twin.next();
    			} else {
    				// if twin is solved - we know
    				// that primary would not be 
    				if ( si_twin.solved ) {
    					isSolved = false;
    					si=null;
    					si_twin = null;
    					return;
    				} 
    				
    				isSolved = true;
    				si_twin = null;
    			}
    		}
    		si.next();
    	}
    	
    	if ( !this.si.currentNode.b.isGoal() )  {
    		this.moves = -1;
    		this.isSolved = false;
    	} else {
    		
    		this.moves = this.si.currentNode.moves;
    		this.solutionStack = new Stack<Board>();
    		this.isSolved = true;
			 
	    	SearchNode sn = this.si.currentNode;
	    	
	    	while( null != sn) {
	    		solutionStack.push( sn.b );
	    		sn = sn.prev;
	    		
	    	}
    	}	
    	
    	si_twin = null;
    	si = null;
    	
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
    	return this.isSolved;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
    	return this.moves;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
    	if (!this.isSolved) return null;
    	
    	
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
				if ( nb.equals( prevNode.b )) 
				{
					// throw new RuntimeException( "YES");
					continue; 	
				}
								
				minQ.insert( new SearchNode( nb, this.currentNode.moves+1, currentNode ));
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
		int score;
		SearchNode prev;
		
		SearchNode( Board b, int moves, SearchNode prev) {
			////InvCounter.tick( "SearchNode");
			this.b = b;
			this.moves = moves;
			this.prev = prev;
			this.score = b.manhattan() + moves;
		}
		
		private int score() {
			return this.score;
		}

		@Override
		public int compareTo(SearchNode o) {
			int diff = this.score() - o.score();
			if ( diff !=  0 ) 
				return diff;
			
			diff = this.b.manhattan() - o.b.manhattan();
			if (diff != 0) return diff;
			
			diff = this.b.hamming() - o.b.hamming();
			if ( diff !=0 ) return diff;
			
			diff = o.moves - this.moves;
			if (diff !=0 ) return diff;
			
			return 0;
				
			
		}
		
	}
}
