package Model.userModel;

import java.util.ArrayList;
import java.util.Date;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

public interface UserModelService {
	public String register(UserPO up);
	public boolean  changePassword(String userId,String newPassword);
	public UserPO findUser(String id);
	public String login(String userId,String password);
	public GameRecordPO checkRecord(String userId);
	public int getHighestScore(String userId);
	public ArrayList<Rank> getRank(String id);
	public void saveSingleRecord(String userID,Date d,int score,int money,int combo);
	public void logoff(String id);
	public AddressPO getIp(String id);
	public int[] getEveryRounds(String id);
	public double[] getAverageScores(String id);
	public void updateMoney(String userId,int money);

}
