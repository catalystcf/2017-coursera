
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.Stack;

public class Board {
	private BI bi;
	
	/** Board Impl
	 * Max size is 128x128
	 * Hash is 128x128-1 numbers.
	 *    Each number can be in any of 128x128 positions.
	 *    16383  -> 128x128 times
	 *    
	 *     
	 *     3x3**/
	private static class BI {
		
		int manhattan;
		int hamming;
		char [] tiles;
		char dimension;
		char empty;
		
		@Override
		public String toString() {
			
			StringBuilder s = new StringBuilder();
			int n = (int) dimension;
	        s.append( n + "\n");
	        int x = 0;
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                s.append(String.format("%2d ", tiles[x]));
	                x++;
	            }
	            s.append("\n");
	        }
	        return s.toString();
		}
		
		
		@Override
		public boolean equals(Object obj) {
			
			BI o = (BI) obj; // who else will call it?
			
			if (o.dimension != this.dimension ||
					o.empty != this.empty ||
					o.manhattan != this.manhattan ||
					o.hamming != this.hamming)
				return false;
			
			return Arrays.equals(o.tiles, this.tiles);
					
			
		}
		
		
		BI( int [][] blocks ) {
			
			this.dimension = (char) blocks.length;
			tiles  = new char[ this.dimension * this.dimension ];
			
			this.hamming = 0;
			this.manhattan = 0;
			char index = 0;
			
			for( int r=0; r < this.dimension; r++) {
				for( int c = 0; c < this.dimension; c++ ) {
					
					this.tiles[index] = (char) blocks[r][c];
					
					if (this.tiles[index] == 0) 
						this.empty = index;
					
					if ( this.tiles[index] != index + 1 && this.tiles[index] != 0 )
					{
						this.hamming = this.hamming + 1;
					
						// for manhanttan, we need to figure out instead where the value should be
		    			this.manhattan += Math.abs( r - (this.tiles[index]-1)  / this.dimension );
		    			this.manhattan += Math.abs( c - (this.tiles[index]-1) %  this.dimension  );
						
					}
					
					index ++;
				}
			}
			
			
		}
		
		boolean isGoal() {
		
			return hamming ==0;
		}
		
		int [][] copyTiles() {
			
			int [][] tblocks = new int[ this.dimension ][];
			for(int r =0; r< this.dimension; r++) {
				tblocks[r] = new int[ this.dimension];
				
				for(int c=0; c<this.dimension; c++ ){
					tblocks[r][c] = this.tiles[ r*this.dimension + c];
				}
			}
			return tblocks;
		}
				
		int [][] neighbourTiles( int dR, int dC ) {
			
			int r =(this.empty) / this.dimension;
			int c =(this.empty) % this.dimension;
			
			int r1 = r+ dR;
			int c1 = c + dC;
			
			if (r1 <0 || c1<0  || r1 >= this.dimension ||  c1 >= this.dimension)
				return null;
			
			int [][] tblock = copyTiles();
			
			int temp = tblock[r][c];
			tblock[r][c] = tblock[r1][c1];
			tblock[r1][c1] = temp;
			
			return tblock;
			
		}
			
		int [][] twinTiles( )
		{
			int [][] tblocks = this.copyTiles();
		
			int r1 = -1;
			int c1 = -1;
			for(int r =0; r< this.dimension; r++) {
				
				for(int c=0; c<this.dimension; c++ ){
					
					if ( tblocks[r][c] == 0 ) continue;
					
					if ( r1 == -1){
						r1 = r;
						c1 = c;
						continue;
					} else {
						int temp = tblocks[r][c];
						tblocks[r][c]= tblocks[r1][c1];
						tblocks[r1][c1] = temp;
						return tblocks;
					}	
				}
			}
			throw new RuntimeException( "not reachable" );
			
			
		}
		
	}
	
	
	
	
	
	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	{
		this.bi = new BI( blocks );
	}                                       // (where blocks[i][j] = block in row i, column j)

	public int dimension()                 // board dimension n
	{
		return (int) this.bi.dimension;
	    	
	}
	public int hamming()                   // number of blocks out of place
	{
		return this.bi.hamming;
	}
	public int manhattan()                 // sum of Manhattan distances between blocks and goal
	{
		return this.bi.manhattan;
	}
	
		
	    public boolean isGoal()                // is this board the goal board?
	    {
	    	return this.bi.isGoal();
	    	
	    }
	    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
	    {
	    	return new Board( this.bi.twinTiles() );
	    }
	    public boolean equals(Object y)        // does this board equal y?
	    {
	    	if (null == y) {
	    		return false;
	    	}
	    	
	    	if ( !(y instanceof Board) ) {
	    		return false;
	    	}
	    	Board o = (Board) y;
	    	
	    	return o.bi.equals( this.bi);
	    }
	    public Iterable<Board> neighbors()     // all neighboring boards
	    {
	    	Stack<Board> it = new Stack<Board>();
	
	    	addNeigbour(it,  1,  0);
	    	addNeigbour(it, -1,  0);
	    	addNeigbour(it,  0,  1);
	    	addNeigbour(it,  0, -1);
	
			return it;
	    }
	    
	    private void addNeigbour( Stack<Board> it, int dR, int dC )
	    {	
	    	int [][] tiles = bi.neighbourTiles( dR, dC);
	    	if ( null == tiles) return;
	    	
	    	Board nb = new Board(tiles);
	    	it.push(nb);
	    }
	    
	    public String toString() {
	    	
	    	return bi.toString();
	    	
	    }
	    
	
}
