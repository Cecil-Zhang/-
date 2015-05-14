package Model.userModel;

import java.util.ArrayList;
import java.util.Date;

import po.AddressPO;
import po.GameRecordPO;
import po.UserPO;

public class UserModelServiceStub implements UserModelService{

	@Override
	public String register(UserPO up) {
		// TODO Auto-generated method stub
		return "12345";
	}

	@Override
	public boolean changePassword(String userId, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserPO findUser(String id) {
		return new UserPO("121250181","mona","121250181", false,0,true,3000);
	}

	@Override
	public String login(String userId, String password) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public GameRecordPO checkRecord(String userId) {
		// TODO Auto-generated method stub
		return new GameRecordPO(userId, 0, 0, 0, 0);
	}

	@Override
	public int getHighestScore(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Rank> getRank(String id) {
		// TODO Auto-generated method stub
		return new ArrayList<Rank>();
	}

	@Override
	public void saveSingleRecord(String userID, Date d, int score, int money,
			int combo) {
        
		
	}

	@Override
	public void logoff(String id) {
		
	}

	@Override
	public AddressPO getIp(String id) {
		// TODO Auto-generated method stub
		return new AddressPO("121250181", "255.255.255.0");
	}

	@Override
	public int[] getEveryRounds(String id) {
		// TODO Auto-generated method stub
		int [] list = {2,2,2,2,2,2,2};
		return list;
	}

	@Override
	public double[] getAverageScores(String id) {
		// TODO Auto-generated method stub
		double[] list = {2,2,2,2,2,2,2};
		return list;
	}

	@Override
	public void updateMoney(String userId, int money) {
		// TODO Auto-generated method stub
		
	}

}
