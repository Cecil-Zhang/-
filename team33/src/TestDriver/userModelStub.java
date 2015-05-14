package TestDriver;

import java.util.ArrayList;
import java.util.Date;

import po.AddressPO;
import po.GameRecordPO;
import po.UserPO;
import Model.userModel.Rank;
import Model.userModel.UserModelService;

public class userModelStub implements UserModelService{

	public String register(UserPO up) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePassword(String userId, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserPO findUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameRecordPO checkRecord(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHighestScore(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Rank> getRank(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSingleRecord(String userID, Date d, int score, int money,
			int combo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logoff(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AddressPO getIp(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getEveryRounds(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getAverageScores(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMoney(String userId, int money) {
		// TODO Auto-generated method stub
		
	}
}
