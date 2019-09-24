import java.util.ArrayList;

public class minimax_h extends ai {

	int depth;
	public minimax_h(String name, int number, int opponent, int depth) {
		super(name, number, opponent);
		this.depth = depth;
		// TODO Auto-generated constructor stub
	}
	
	public int get_next_move(board current) {
		//current.get_board();
		//System.out.println("Returning board");
		board test = current.copy();
		System.out.println("thinking about the next optimal move");
		//test.get_board();
		int holder = 0;
		int choice = minimax_algorithm(test, this.number, this.opponent, this.number, true, 1, holder, this.depth);
		//System.out.println("END MINMAX BOARD + " + holder);
		System.out.println("CHOICE: " + choice);
		//test.get_board();
		//System.out.println("Returning board");
		return choice;
	}
	
	public int minimax_algorithm(board board, int number, int opponent, int m, boolean first, int count, int holder, int depth) {
		
		//System.out.println("printing board inputed into minimax");
		//board.get_board();
		//System.out.println("WHOSE MOVE = " + m);
		
		
		if(board.eval_board() != 0 || board.end_board()) {
			
			int u = board.utility(board, number, opponent);
			//System.out.println("UTILITY = " + u);
			return u*1000;
			
		} else if (count == depth) {
			
			int h = board.heuristic(number, opponent);
			//System.out.println("UTILITY = " + h);
			return h;
			
		} if(m == number) {
			
			//System.out.println("MAX");
			
			ArrayList<move> moves = new ArrayList<move>();
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
			
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0));
			}
			
			for (move move : moves) {
				//System.out.println("result for move " + move.col);
				board test = board.copy();
				board next = test.result(number, move.col, test);
				//next.get_board();
				next_boards.add(next);
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				int hold = 0;
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, opponent, false, count+1, hold, depth);
				
				/*if(first) {
					System.out.println("FIRST ANALYSIS at i = " + i + " holder returns = " + hold);
					System.out.println("Value for move " + moves.get(i).col + " & value = " + moves.get(i).value);
				}*/
			}
			
		/*	System.out.println("MAX MOVES: at move " + count);
			for(move move : moves) {
				System.out.println("MOVE " + move.col + " has value " +  move.value);
			}
	*/
			move max = moves.get(0);
			
			//System.out.println("EVAL FOR MAX MOVES at move " + count);
			
			for (move move : moves) {
				//System.out.println("Max move : " + max.col + " Value = " + max.value);
				//System.out.println("Move move : " + move.col + " Value = " + move.value);
				if(move.value > max.value) {
					max = move;
				}
			}
			
			//System.out.println("Max Returning Move" + max.col +  "with value = " + max.value);
			
			holder = max.col;
			
			if(first) {
				return max.col;
			} else {
				return max.value;
			}
			
			
			
		} else {
			
			//System.out.println("MIN");
			
			ArrayList<move> moves = new ArrayList<move>();
			
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
	
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0));
			}
			
			for (move move : moves) {
				//System.out.println("result for move " + move.col);
				board test = board.copy();
				board next = test.result(opponent, move.col, test);
				//next.get_board();
				next_boards.add(next);
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				int hold = 0;
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, number, false, count+1, hold, depth);
				
			}
			
			/*System.out.println("MIN MOVES: at move " + count);
			for(move move : moves) {
				System.out.println("MOVE " + move.col + " has value " +  move.value);
			}*/
	
			move min = moves.get(0);
			
			///System.out.println("EVAL FOR MIN MOVES at move " + count);
			
			for (move move : moves) {
				//System.out.println("Min move : " + min.col + " Value = " + min.value);
				//System.out.println("Move move : " + move.col + " Value = " + move.value);
				if(move.value < min.value) {
					min = move;
				}
			}
			
			//System.out.println("MIN -- Returning Move" + min.col +  "with value =  " + min.value);
			
			holder = min.col;
			
			if(first) {
				return min.col;
			} else {
				return min.value;
			}
			
		}
		
	}
	

}
