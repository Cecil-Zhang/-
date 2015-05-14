package po;

import java.io.Serializable;
import java.util.Date;

public class SingleRecordPO implements Serializable{
	private String userID;
	private int  date;
	private int score;
	private int highestCombo;
	private int money;
	public SingleRecordPO(String userID, int  date, int score,
			int highestCombo,int money) {
		super();
		this.userID = userID;
		this.date = date;
		this.score = score;
		this.highestCombo = highestCombo;
		this.money = money;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int  getDate() {
		return date;
	}

	public void setDate(int  date) {
		this.date = date;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHighestCombo() {
		return highestCombo;
	}
	public void setHighestCombo(int highestCombo) {
		this.highestCombo = highestCombo;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money){
		this.money = money;
	}
}
