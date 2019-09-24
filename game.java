import java.util.ArrayList;
import java.util.Scanner;

public class game {
	
	board board;
	player one;
	player two;
	
	public game(board board, player one, player two) {
		this.board = board;
		this.one = one;
		this.two = two;
	}

	public static void main(String[] args) {
		
		/*
		board current = new board(3, 5, 3);
		
		current.drop_block(0, 2);
		current.drop_block(1, 1);
		current.drop_block(0, 2);
		current.drop_block(2, 2);
		current.drop_block(2, 1);
		current.drop_block(3, 2);
		current.drop_block(3, 2);
		current.get_board();
		//minimax minimax = new minimax("minimax", 2, 1);
		System.out.println("EVAL Choice: " + current.heuristic(2, 1));
		*/
		
		boolean restart = true;
		
		while(restart) {
		
		board board = null;
		player minimax = null;
		
		System.out.println("Welcome to Connect by Victor Antony");
	
		System.out.println("Choose your game: ");
		System.out.println("1. Small 3x3x3 Connect-3");
		System.out.println("2. Wider 3x5x3 Connect-3");
		System.out.println("3. Standard 6x7x4 Connect-4");
		System.out.print("Your Choice: ");
		
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		
		if(choice == 1) {
			board = new board(3, 3, 3);
		} else if (choice == 2) {
			board = new board(3, 5, 3);
		} else if (choice == 3) {
			board = new board(6, 7, 4);
		}
		
		System.out.println("Choose your opponent: ");
		System.out.println("1. A agent that uses MINIMAX");
		System.out.println("2. A agent that uses MINIMAX with alpha-beta pruning");
		System.out.println("3. A agent that uses H-MINIMAX with depth-cutoff");
		System.out.println("4. A agent that uses ULTIMATE MINIMAX with depth-cutoff, heuristics & ab-pruning");
		System.out.print("Your Choice: ");

		choice = input.nextInt();
		
		if(choice == 1) {
			minimax = new minimax("minimax", 2, 1);
		} else if (choice == 2) {
			minimax = new minimax_ab("minimax ab", 2, 1);
		} else if (choice == 3) {
			System.out.print("Enter depth_cutoff: ");
			int depth = input.nextInt();
			minimax = new minimax_h("minimax h", 2, 1, depth);
		} else if (choice == 4) {
			System.out.print("Enter depth_cutoff: ");
			int depth = input.nextInt();
			minimax = new minimax_ultimate("minimax ultimate", 2, 1, depth);
		}
		
		human human = new human("human", 1, 2);
		
		game game = new game(board, human, minimax);
		
		boolean player = false;
		
		System.out.println("DO YOU WANT TO PLAY FIRST? (1 = YES, 2 = NO)");
		
		choice = input.nextInt();
		
		if(choice == 1) {
			player = true;
		} else if (choice == 2) {
			player = false;
		}
		
		game.startGame(player);
		
		System.out.println("Would you like to play again? Yes == 1 No == 2");
	
	
		int question = input.nextInt();
		
		if(question != 1) {
			restart = false;
		}
		
		System.out.println("Good Day Human!");
		
		}
	
	}
	
	public void startGame(boolean player) {
		
		int winner = 0;
		int input = 1;
		
		player one = this.one;
		player two = this.two;
		
		boolean p1 = player;
		
		boolean game = true;
		
		
		
		if(p1) {
			System.out.println("YOU ARE PLAYER ONE -- YOU DROP 1");
			System.out.println("COMPUTER IS PLAYER TWO -- IT DROPS 2");
		} else {
			System.out.println("COMPUTER IS PLAYER ONE -- YOU DROP 1");
			System.out.println("YOU ARE PLAYER TWO -- IT DROPS 2");
		}
		
		this.board.get_board();
		while(winner == 0) {
			//System.out.println("the board as of now");
			int play = this.board.whose_move();
			//this.board.get_board();
			
			if(p1) {
				System.out.println("player 1's turn");
				int drop = one.get_next_move(this.board);
				this.board.drop_block(drop, 1);
				p1 = false;
			} else { 
				System.out.println("player 2's turn");
				int drop = two.get_next_move(this.board);
				this.board.drop_block(drop, 2);
				p1 = true;
			}
			
			this.board.get_board();
			int terminated = this.board.eval_board();
			
			if(terminated != 0) {
				System.out.println("GAME OVER");
				System.out.println("Player " + play + " has won");
				winner = play;
			}
			
			else if(this.board.end_board()) {
				System.out.println("GAME OVER");
				System.out.println("The game was tied");
				winner = -1;
				
			}
			
		}
		
		
		
	}

}
