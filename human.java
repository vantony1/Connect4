import java.util.Scanner;

public class human extends player {

	String name;
	
	public human(String name, int number, int opponent) {
		super(name, number, opponent);
	}
	
	@Override
	public int get_next_move(board board) {
		System.out.print("Enter Choice: ");
		
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		
		while(choice >= board.columns || choice < 0) {
			System.out.print("Invalid Choice! Enter Choice: ");
			choice = input.nextInt();
		}
		
		return choice;
	}

}
