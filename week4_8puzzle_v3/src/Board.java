
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.Stack;

public class Board {
	
	private int n;
	private int [][] tiles;
	
	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	    {
			this.n = blocks.length;
			
			tiles = new int[n][];
			for (int r=0; r<n; r++ ) {
				tiles[r] = new int[n];
				
				for (int c=0; c< n; c++ ) {
					tiles[r][c] = blocks[r][c];
				}
			}
	    }                                       // (where blocks[i][j] = block in row i, column j)

	    public int dimension()                 // board dimension n
	    {
	    	return this.n;
	    	
	    }
	    public int hamming()                   // number of blocks out of place
	    {
	    	int hamming = 0; 
	    	for (int r=0; r<n; r++ ) {
	    		for (int c=0; c< n; c++) {
	    			int val = this.tiles[r][c];
	    			
	    			if ( 0 == val)
	    				continue;
	    			
	    			if ( Math.abs( r - (val -1 ) / n ) +  Math.abs( c - (val-1) % n ) > 0 )
	    				hamming ++;
	    		}
	    	}
	    	
	    	return hamming;

	    
	    
	    }
	    public int manhattan()                 // sum of Manhattan distances between blocks and goal
	    {
	    	int manh = 0;
	    	
	    	for (int r=0; r<n; r++ ) {
	    		for (int c=0; c< n; c++) {
	    			int val = this.tiles[r][c];
	    			
	    			if ( 0 == val)
	    				continue;
	    			
	    			manh += Math.abs( r - (val -1 ) / n );
	    			manh += Math.abs( c - (val-1) % n );
 
	    		}
	    	}
	    	
	    	return manh;
	    }
	    public boolean isGoal()                // is this board the goal board?
	    {
	    	int val = 0;
	    	for (int r=0; r<n; r++) {
	    		for (int c=0; c<n; c++) {
	    			val++;
	    			if (c == n-1 && r == n-1 && this.tiles[r][c]== 0 ) return true; // last cell
	    			if ( tiles[r][c] != val )
	    				return false;
	    		}
	    	}
	    	
	    	throw new RuntimeException( "not reachable");
	    	
	    }
	    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
	    {
	    	
	    	BoardHelper b = new BoardHelper( this );
	    	return new Board( b.twinTiles() );
	    }
	    public boolean equals(Object y)        // does this board equal y?
	    {
	    	Board o = (Board) y;
	    	
	    	for ( int row = 0 ; row < this.dimension(); row++) {
	    		if ( !Arrays.equals( this.tiles[row], o.tiles[row] ))
	    			return false;
	    	}
	    	
	    	return true;
	    }
	    
	    private class BoardHelper {
	    	Board b;
	    	int c0;
	    	int r0;
	    	
	    	BoardHelper( Board b) {
	    		this.b = b;
	    	}
	    	// TODO: merge board function ( twin, neighbors ) here

	    	int [][] copyOfTiles() {
	    	
    			int [][] tiles = new int [n] [];
    			
    			for (int r =0; r< n; r++ ) {
    				tiles[r] = new int[n];
    				for (int c=0; c<n; c++ ) {
    					tiles[r][c] = this.b.tiles[r][c];
    				}
    			}
    			return tiles;
	    	}
	    	
    		void set0( Board b ) {
    			
    			for( int r =0; r<n; r++ ) {
    				for (int c=0; c<n; c++ ) {
    					if (b.tiles[r][c] == 0 ) {
    						this.r0 = r;
    						this.c0 = c;
    						
    						return;
    					}
    				}
    			}
    			
    			throw new RuntimeException( "unreachable ");
    		}

    		int [][] twinTiles() {
    			
    			int [][] twinTiles = copyOfTiles();
    			int r1 = -1, c1 =-1; // swappees.
    			
    			for ( int r=0; r < b.n; r++) {
    				for( int c=0;c < b.n; c++ ) {
    					if (b.tiles[r][c] == 0) continue;
    					
    					if ( r1 == -1 ) {
    						r1 = r;
    						c1 = c;
    					} else {
    						
    						twinTiles[r][c]  = b.tiles[ r1][c1];
    						twinTiles[r1][c1] = b.tiles[r][c];
    						// swap and return
    						return twinTiles;
    					}
    				}
    			}
    			
    			throw new RuntimeException( "Invalid tiles");
    		}
	    }
	    public Iterable<Board> neighbors()     // all neighboring boards
	    {
	    	class IB implements Iterable<Board> {
	    		
	    		Stack<Board> neighbors = new Stack<Board>();
	    		int r0;
	    		int c0;
	    		
	    		void set0( Board b ) {
	    			
	    			for( int r =0; r<n; r++ ) {
	    				for (int c=0; c<n; c++ ) {
	    					if (b.tiles[r][c] == 0 ) {
	    						this.r0 = r;
	    						this.c0 = c;
	    						
	    						return;
	    					}
	    				}
	    			}
	    			
	    			throw new RuntimeException( "unreachable ");
	    		}
	    		
	    		void addNeigbor( Board b,int newR0, int newC0) {
	    			
	    			if (newR0 >= n || newC0 >= n || newR0 <0 || newC0 <0 )
	    				return; 
	    			
	    			int [][] tiles = new int [n] [];
	    			
	    			for (int r =0; r< n; r++ ) {
	    				tiles[r] = new int[n];
	    				for (int c=0; c<n; c++ ) {
	    					tiles[r][c] = b.tiles[r][c];
	    				}
	    			}
	    			
	    			tiles[r0][c0] =b.tiles[newR0][newC0];
	    			tiles[newR0][newC0] = b.tiles[r0][c0];
	    		
	    			Board nb = new Board(tiles);
	    			
	    			neighbors.push(nb);
	    		}
	    		
	    		IB( Board b) {
	    			
	    			this.set0( b);
	    			
	    			addNeigbor( b, r0+1, c0);
	    			addNeigbor( b, r0-1, c0);
	    			addNeigbor( b, r0  , c0 + 1);
	    			addNeigbor( b, r0  , c0 - 1);
	    			
	    		} 
	    		
				@Override
				public Iterator<Board> iterator() {
					// TODO Auto-generated method stub
					return neighbors.iterator();
				}
	    		
	    	}
	    	
	    	return new IB(this);
	    }
	    
	    public String toString() {
	        StringBuilder s = new StringBuilder();
	        s.append(n + "\n");
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                s.append(String.format("%2d ", tiles[i][j]));
	            }
	            s.append("\n");
	        }
	        return s.toString();
	    }
	    
	
}
