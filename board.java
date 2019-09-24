import java.util.ArrayList;

public class board{
	
	int rows;
	int columns;
	int [][] grid;
	int [] next_drop;
	int win;
	

	public board(int rows, int columns, int win) {
		this.rows = rows;
		this.columns = columns;
		this.grid = new int [rows] [columns];
		this.next_drop = new int [columns];
		this.win = win;
		this.initialize_board();
	}
	
	public board(int[][] grid, int [] next_drop, int win) {
		this.grid = grid;
		this.rows = grid.length;
		this.columns = grid[0].length;
		this.next_drop = next_drop;
		this.win = win;
	}
	

	public board() {
	}

	public int whose_move() {
		
		int one = 0;
		int two = 0;
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				if(this.get_block(x, y) == 1) {
					one++;
				} else if (this.get_block(x, y) == 2){
					two++;
				}
			}
		}
		
		if (one > two) {
			return 2;
		} else {
			return 1;
		}
		
	}
	
	public ArrayList<Integer> valid_moves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		for(int i = 0; i < this.columns; i++) {
			if(this.next_drop[i] < this.rows && this.next_drop[i] >= 0) {
				moves.add(i);
			} 
		}
		
		return moves;
	}
	
	public board result(int player, int drop, board board){
		
		board.drop_block(drop, player);
		
		return board;
		
	}
	
	public int utility(board board, int player, int opponent) {
		int result = board.eval_board();
		//ADD FOR TIE
		if(result == player) {
			return 1;
		}if(result == opponent) {
			return -1;
		}else {
			return 0;
		}
	}
	
	public void initialize_next_drop() {
			for(int y = 0; y < this.columns; y++) {
				this.next_drop[y] = this.rows - 1;
			}
	}
	
	public void initialize_board() {
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				this.set_block(x, y, 0);
			}
		}
	}
	
	public boolean end_board() {
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				
				if(this.get_block(x, y) == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void set_block(int x, int y, int value) {
		grid[x][y] = value;
	}
	
	public int get_block(int x, int y) {
		return grid[x][y];
	}
	
	public void get_board() {
		for(int x = this.rows-1; x >= 0; x--) {
			System.out.print("| ");
			for(int y = 0; y < this.columns; y++) {
				int block = this.get_block(x, y);
				System.out.print(block + " ");
			}
			System.out.println("|");
		}
	}
	
	public int [][] return_board(){
		return grid;
	}
	
	public int terminal_test() {
		if(this.eval_board() == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public int heuristic(int player, int opponent) {
		int p_value = 0;
		int o_value = 0;
		boolean p = false;
		boolean o = false;
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				int a = this.get_block(x, y);
				
				if(a != 0) {
					
					//System.out.println("At x = " + x + ", y = " + y + " & a = " + a + " value p = " + p_value + " value o = " + o_value);
					
					if(a == player) {
						p = true;
						o = false;
					} else {
						p = false;
						o = true;
					}
	
					int step = 1;
					
					while(x + step < this.rows) {
						
						if(this.get_block(x + step, y) == a){
							//System.out.println("horx" + p + " " +  o);
							if(p) {
								p_value += 25;
								//System.out.println("Pos added = " + p_value);
							}
							if(o){
								o_value += 25;
								//System.out.println("Opp added = " + o_value);
							}
						} else {
							break;
						}
						step++;
					}
					
					step = 1;
					
					while(y + step < this.columns) {
						if(a == this.get_block(x, y+ step)){
							//System.out.println("vert " + p + " " +  o);
							if(p) {
								p_value += 25;
								//System.out.println("Pos added = " + p_value);
							} if(o){
								o_value += 25;
								//System.out.println("Opp added = " + o_value);
							}
						} else {
							break;
						}
						
						step++;
					}
					
					step = 1;
					
					while(y + step < this.columns && x + step < this.rows) {
						if(this.get_block(x + step, y+ step) == a){
							//System.out.println("daig pos" + p + " " +  o);
							if(p) {
								p_value += 25;
								//System.out.println("Pos added = " + p_value);
							} if(o){
								o_value += 25;
								//System.out.println("Opp added = " + o_value);
							}
						} else {
							break;
						}
						step++;
					}
					
					step = 1;
					
					while(y - step >= 0 && x - step >= 0) {
						if(this.get_block(x - step, y - step) == a){
							//System.out.println("diag neg" + p + " " +  o);
							if(p) {
								p_value += 25;
							} if(o){
								o_value += 25;
							}
						} else {
							break;
						}
						step++;
					}

					
				}
			}
		}
		
		return p_value - o_value;
	}
	
	public int eval_board() {
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				int a = this.get_block(x, y);
				
				//System.out.println("At x = " + x + ", y = " + y + " & a = " + a);
				
				if(a == 0) {
					continue;
				}
				
				boolean vert, horz, diag1, diag2;
				vert = true;
				horz = true;
				diag1 = true;
				diag2 = true;
				
				
				
				for(int i = 1; i < this.win; i++) {
					//System.out.println("Eval diag i = "+ i);
					if(x+i < 0 || x+i >= this.rows) {
						//System.out.println("Out of index: Breaking");
						horz = false; 
						break;
					} else if(a != this.get_block(x+i, y)) {
						//System.out.println("Not Equal: Breaking");
						horz = false; 
						break;
					} 
				}
				
				for(int i = 1; i < this.win; i++) {
					
					if(y+i < 0 || y+i >= this.columns) {
					
						vert = false;
						break;
					} else if(a != this.get_block(x,y+i)) {
					
						vert = false;
						break;
					} 
				
			}
				
				for(int i = 1; i < this.win; i++) {
					//System.out.println("Eval diag i = "+ i);
					if(y+i < 0 || y+i >= this.columns || x+i < 0 || x+i >= this.rows) {
						//System.out.println("Out of index: Breaking");
						diag1 = false;
						break;
					} else if(a != this.get_block(x+i,y+i)) {
						//System.out.println("Not Equal: Breaking");
						diag1 = false;
						break;
					}
			}
				
				for(int i = 1; i < this.win; i++) {
					//System.out.println("Eval diag i = "+ i);
					if(y+i < 0 || y+i >= this.columns || x-i < 0 || x-i >= this.rows) {
						//System.out.println("Out of index: Breaking");
						diag2 = false;
						break;
					} else if(a != this.get_block(x-i,y+i)) {
						//System.out.println("Not Equal: Breaking");
						diag2 = false;
						break;
					}
			}
				
				if(vert || horz || diag1 || diag2) {
					return a;
				}
				
			}	
				
		}
		

		return 0; 
		}
	
	public boolean drop_block(int column, int player) {
		
		int row = this.next_drop[column];
		
		if (row < 0) {
			
			return false;
			
		} else {
			
		this.set_block(row, column, player);
		this.next_drop[column] = row + 1;
			
			return true;
		}
	}
	
	public board copy() {
		
		int[] next_d = new int [this.columns];
		
		int[][] grid_new = new int [this.rows][this.columns];
		
		int win_new = win - 1;
		win_new = win_new + 1;
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y++) {
				grid_new[x][y] = this.get_block(x, y);
			}
		}
		
		for(int y = 0; y < this.columns; y++) {
			next_d[y] = this.next_drop[y];
		}
		
		board new_board = new board(grid_new, next_d, win_new);
		
		return new_board;
	}
	

	
	

}
