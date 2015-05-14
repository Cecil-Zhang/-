package vo;

public class RankVO {
	String secondName;
	int score;
	int rankNo;
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
	public RankVO(int r,String secondName, int score) {
		super();
		this.secondName = secondName;
		this.score = score;
		rankNo=r;
	}
	public int getRankNo() {
		return rankNo;
	}
	public void setRankNo(int rankNo) {
		this.rankNo = rankNo;
	}
	
}
