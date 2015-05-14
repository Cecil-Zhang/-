package Model.userModel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import po.AddressPO;
import po.GameRecordPO;
import po.UserPO;

public class UserModelServiceTest {

	private UserModelService ums = new UserModelServiceStub();
	@Test
	public void testRegister() {
		String id = ums.register(new UserPO("", "Jay","12345", true, 0, false, 3000));
		assertTrue(id.equals("12345"));
	}

	@Test
	public void testFindUser() {
		UserPO upPo = ums.findUser("121250181");
		assertTrue(upPo.getUserId().equals("121250181"));
		assertTrue(upPo.getUserName().equals("mona"));
		assertTrue(upPo.getPassword().equals("121250181"));
		assertTrue(upPo.getImage()==0);
		assertTrue(upPo.getMoney()==3000);
		assertTrue(!upPo.isGender());
		assertTrue(upPo.isLogin());
		
	}

	@Test
	public void testLogin() {
		assertTrue(ums.login("121250181", "121250181").equals(""));
	}

	@Test
	public void testCheckRecord() {
		GameRecordPO grp = ums.checkRecord("121250181");
		assertTrue(grp.getUserID().equals("121250181"));
		assertTrue(grp.getTotalRound()==0);
		assertTrue(grp.getHighestScore()==0);
		assertTrue(grp.getHighestCombo()==0);
		assertTrue(grp.getAverageScore()==0);
	}

	@Test
	public void testGetHighestScore() {
		assertTrue(ums.getHighestScore("121250181")==0);
	}

	@Test
	public void testGetRank() {
		ArrayList<Rank> list = ums.getRank("121250181");
		assertTrue(list.size()==0);
	}

	@Test
	public void testGetIp() {
		AddressPO ap = ums.getIp("121250181");
		assertTrue(ap.getAddress().equals("255.255.255.0"));
	}

	@Test
	public void testGetEveryRounds() {
		int [] list = ums.getEveryRounds("121250181");
		for(int i=0;i<7;i++){
			assertTrue(list[i]==2);
		}
	}

	@Test
	public void testGetAverageScores() {
		double [] list = ums.getAverageScores("121250181");
		for(int i=0;i<7;i++){
			assertTrue(list[i]==2);
		}
	}

}
