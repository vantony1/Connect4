import java.util.ArrayList;

public class minimax_h extends ai {

	int depth;
	public minimax_h(String name, int number, int opponent, int depth) {
		super(name, number, opponent);
		this.depth = depth;
	}
	
	public int get_next_move(board current) {
		board test = current.copy();
		System.out.println("thinking about the next optimal move");
		int holder = 0;
		int choice = minimax_algorithm(test, this.number, this.opponent, this.number, true, 1, holder, this.depth);
		System.out.println("CHOICE: " + choice);
		return choice;
	}
	
	public int minimax_algorithm(board board, int number, int opponent, int m, boolean first, int count, int holder, int depth) {

		if(board.eval_board() != 0 || board.end_board()) {
			int u = board.utility(board, number, opponent);
			return u*1000;
			
		} else if (count == depth) {
			int h = board.heuristic(number, opponent);
			return h;
			
		} if(m == number) {

			ArrayList<move> moves = new ArrayList<move>();
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
			
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0));
			}
			
			for (move move : moves) {
				board test = board.copy();
				board next = test.result(number, move.col, test);
				next_boards.add(next);
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				int hold = 0;
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, opponent, false, count+1, hold, depth);
			}
	
			move max = moves.get(0);
					
			for (move move : moves) {
				if(move.value > max.value) {
					max = move;
				}
			}
					
			holder = max.col;
			
			if(first) {
				return max.col;
			} else {
				return max.value;
			}
			
			
			
		} else {			
			ArrayList<move> moves = new ArrayList<move>();
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
	
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0));
			}
			
			for (move move : moves) {
				board test = board.copy();
				board next = test.result(opponent, move.col, test);
				next_boards.add(next);
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				int hold = 0;
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, number, false, count+1, hold, depth);
				
			}
			
			move min = moves.get(0);

			for (move move : moves) {
				if(move.value < min.value) {
					min = move;
				}
			}

			holder = min.col;
			if(first) {
				return min.col;
			} else {
				return min.value;
			}
			
		}
		
	}
	

}
