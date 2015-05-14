package Model.userModel;

public class Rank {
	String id;
	String secondName;
	int score;
	public Rank(String id,String secondName, int score) {
		super();
		this.id=id;
		this.secondName = secondName;
		this.score = score;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
