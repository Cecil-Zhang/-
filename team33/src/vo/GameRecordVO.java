package vo;

import java.util.ArrayList;

public class GameRecordVO { 
	private String userID;
	private int totalRound;
	private double averageScore;
	private int highestScore;
	private int highestCombo;
	private int[] rounds;
	private double[] averageScores;
	public GameRecordVO(String userID, int totalRound, double averageScore,
			int highestScore, int highestCombo,int[] everyDayRound,double[] averageScores) {
		super();
		this.userID = userID;
		this.totalRound = totalRound;
		this.averageScore = averageScore;
		this.highestScore = highestScore;
		this.highestCombo = highestCombo;
		this.rounds = everyDayRound;
		this.averageScores = averageScores;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getTotalRound() {
		return totalRound;
	}
	public void setTotalRound(int totalRound) {
		this.totalRound = totalRound;
	}
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	public int getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
	public int getHighestCombo() {
		return highestCombo;
	}
	public void setHighestCombo(int highestCombo) {
		this.highestCombo = highestCombo;
	}
	public int[] getEveryDayRound() {
		return rounds;
	}
	public void setEveryDayRound(int[] everyDayRound) {
		this.rounds = everyDayRound;
	}
    public double[] getAverageScores(){
    	return averageScores;
    }
    public void setAverageScores(double[] averageScores){
    	this.averageScores = averageScores;
    }
	
	
}
