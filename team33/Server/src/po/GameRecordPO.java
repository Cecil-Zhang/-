package po;

import java.io.Serializable;
import java.util.ArrayList;

public class GameRecordPO implements Serializable{
	private String userID;
	private int totalRound;
	private double averageScore;
	private int highestScore;
	private int highestCombo;   
	public GameRecordPO(){};
	public GameRecordPO(String userID, int totalRound, double averageScore,
			int highestScore, int highestCombo) {
		super();
		this.userID = userID;
		this.totalRound = totalRound;
		this.averageScore = averageScore;
		this.highestScore = highestScore;
		this.highestCombo = highestCombo;
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

}
