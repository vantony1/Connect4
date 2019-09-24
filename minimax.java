import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import javax.crypto.Mac;

public class minimax extends ai{

	public minimax(String name, int number, int opponent) {
		super(name, number, opponent);
	}

	@Override
	public int get_next_move(board current) {
		board test = current.copy();
		System.out.println("thinking about the next optimal move");
		int holder = 0;
		int choice = minimax_algorithm(test, this.number, this.opponent, this.number, true, 1, holder);
		System.out.println("CHOICE: " + choice);
		return choice;
	}
	
	
	public int minimax_algorithm(board board, int number, int opponent, int m, boolean first, int count, int holder) {
		
		if(board.eval_board() != 0 || board.end_board()) {
			
			int u = board.utility(board, number, opponent);
			return u;
			
		} else if(m == number) {
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
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, opponent, false, count+1, hold);
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
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, number, false, count+1, hold);
				
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
	
	
	///THE CODE BELOW IS AN OLDER ATTEMPT AT IMPLEMENTING THE ALGORITHM
	
/*
public int minimax_algorithm(board board, int number, int opponent, int m, boolean first, int count, int holder) {
		
		//System.out.println("printing board inputed into minimax");
		//board.get_board();
		System.out.println("WHOSE MOVE = " + m);
		
		if(board.eval_board() != 0 || board.end_board()) {
			
			int u = board.utility(board, number, opponent);
			System.out.println("UTILITY = " + u);
			return u;
			
		} else if(m == number) {
			
			System.out.println("MAX");
			
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
				moves.get(i).value  =  minimax_algorithm(next_boards.get(i), number, opponent, opponent, false, count+1, hold);
				
				if(first) {
					System.out.println("FIRST ANALYSIS at i = " + i + " holder returns = " + hold);
					System.out.println("Value for move " + moves.get(i).col + " & value = " + moves.get(i).value);
				}
			}
			
			System.out.println("MAX MOVES: at move " + count);
			for(move move : moves) {
				System.out.println("MOVE " + move.col + " has value " +  move.value);
			}
	
			move max = moves.get(0);
			
			System.out.println("EVAL FOR MAX MOVES at move " + count);
			
			for (move move : moves) {
				System.out.println("Max move : " + max.col + " Value = " + max.value);
				System.out.println("Move move : " + move.col + " Value = " + move.value);
				if(move.value > max.value) {
					max = move;
				}
			}
			
			System.out.println("Max Returning Move" + max.col +  "with value = " + max.value);
			
			holder = max.col;
			
			return max.value;
			
			
		} else {
			
			System.out.println("MIN");
			
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
				moves.get(i).value =  minimax_algorithm(next_boards.get(i), number, opponent, number, false, count+1, hold);
				
			}
			
			System.out.println("MIN MOVES: at move " + count);
			for(move move : moves) {
				System.out.println("MOVE " + move.col + " has value " +  move.value);
			}
	
			move min = moves.get(0);
			
			System.out.println("EVAL FOR MIN MOVES at move " + count);
			
			for (move move : moves) {
				System.out.println("Min move : " + min.col + " Value = " + min.value);
				System.out.println("Move move : " + move.col + " Value = " + move.value);
				if(move.value < min.value) {
					min = move;
				}
			}
			
			System.out.println("MIN -- Returning Move" + min.col +  "with value =  " + min.value);
			
			holder = min.col;
			
			return min.value;
			
		}
		
	}
	
	
	public int minimax_algorithm(board board, int number, int opponent, int m) {
		
		System.out.println("printing board inputed into minimax");
		board.get_board();
		System.out.println("WHOSE MOVE = " + m);
		
		if(board.eval_board() != 0 || board.end_board()) {
			
			int u = board.utility(board, number, opponent);
			System.out.println("UTILITY = " + u);
			return u;
			
		} else if(m == number) {
			
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
				
				if(next.eval_board() == number) {
					move.value = 100;
				}
			}
			
	
			
			for (int i = 0; i < next_boards.size(); i++) {
				int holder = minimax_algorithm(next_boards.get(i), number, opponent, opponent);
				if(holder >= moves.get(i).value) {
				moves.get(i).value = holder;
				}
			}
	
			move max = moves.get(0);
			
			for (move move : moves) {
				System.out.println("Move Col = " +  move.col + "Max col = " +  max.col);
				System.out.println("Move Value = " +  move.value + "Max Value = " +  max.value);
				if(move.value > max.value) {
					System.out.println("REPLACING M");
					max = move;
				}
			}
			
			System.out.println("RETURNING MAX = " +  max.col);
			return max.col;
			
			
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
				
				if(next.eval_board() == opponent) {
					move.value = -100;
				}
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				int holder = minimax_algorithm(next_boards.get(i), number, opponent, number);
				if(holder <= moves.get(i).value) {
				moves.get(i).value = holder;
				}
			}
			
	
			move min = moves.get(0);
			
			for (move move : moves) {
				System.out.println("Move Col = " +  move.col + "Min col = " +  min.col);
				System.out.println("Move Value = " +  move.value + "Min Value = " +  min.value);
				if(move.value < min.value) {
					System.out.println("REPLACING");
					
					min = move;
				}
			}
			System.out.println("RETURNING MIN = " +  min.col);
			return min.col;
			
		}
		
	}
	
	

	public int minimax_algorithm(board board, int number, int opponent, int count) {
		
		System.out.println("printing board inputed into minimax");
		board.get_board();
		System.out.println("WHOSE MOVE = " + board.whose_move());
		
		if(board.eval_board() != 0 || board.end_board()) {
			
			int u = board.utility(board, number, opponent);
			System.out.println("UTILITY = " + u);
			return u;
			
		} else if(board.whose_move() == number) {
			
			ArrayList<move> moves = new ArrayList<move>();
			
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
			
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0, count));
			}
			
			for (move move : moves) {
				board test = board.copy();
				next_boards.add(test.result(number, move.col, test));
			}
			
			for (int i = 0; i < next_boards.size(); i++) {
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, count+1);
			}
	
			move max = moves.get(0);
			
			for (move move : moves) {
				if(move.value > max.value) {
					max = move;
				}
			}
			
			return max.col;
			
			
		} else {
			
			ArrayList<move> moves = new ArrayList<move>();
			
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
	
			for (int i : board.valid_moves()) {
				moves.add(new move(i, 0, count));
			}
			
			for (move move : moves) {
				board test = board.copy();
				next_boards.add(test.result(opponent, move.col, test));			
				}
			
			for (int i = 0; i < next_boards.size(); i++) {
				moves.get(i).value = minimax_algorithm(next_boards.get(i), number, opponent, count+1);
			}
			
	
			move min = moves.get(0);
			
			for (move move : moves) {
				if(move.value < min.value) {
					min = move;
				}
			}
			
			return min.col;
			
		}
		
	}
	
	
	/*
	public int minimax_algorithm(board board, int number, int opponent) {

		
		if(board.eval_board() != 0) {
			
			return board.utility(board, number);
			
		} else if(board.whose_move() == number) {
			
			ArrayList<Integer> moves = board.valid_moves();
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
	
			System.out.println();
			for (int i : moves) {
				next_boards.add(board.result(number, i, board.grid, board.next_drop, board.win));
			}
			
			for (board next : next_boards) {
				values.add(minimax_algorithm(next, number, opponent));
			}
	
			
			return Collections.max(values);
			
			
		} else {
			
			ArrayList<Integer> moves = board.valid_moves();
			ArrayList<board> next_boards = new ArrayList<board>();
			ArrayList<Integer> values = new ArrayList<Integer>(); 
			
			board.get_board();
			
			System.out.println("valid moves opponent");
			for (int i : moves) {
				System.out.println(i + " ");
			}
			System.out.println();
			
			for (int i : moves) {
				next_boards.add(board.result(number, i, board.grid, board.next_drop, board.win));
			}
			
			for (board next : next_boards) {
				values.add(minimax_algorithm(next, number, opponent));
			}
			
			return Collections.min(values);
			
		}
		
	}
	*/
}
