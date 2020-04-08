package BallProject;

public class UserRank {

	
	String name;
	int score;
	
	public UserRank(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String scoretoString() {
		String score = ""+this.score;
		return score;
		
	}
	
	
}
