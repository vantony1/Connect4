
public class player {
	
	String name; 
	int number;
	int opponent;
	
	public player(String name, int number, int opponent) {
		this.name = name;
		this.number = number;
		this.opponent = opponent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int get_next_move(board current) {
		return -1;
	}
}
