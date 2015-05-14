package UserLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

public class UserLogicTest {

	@Test
	public void test() {
		getUserTest();
		getGameRecordTest();
		MessageTest();
		singleRecordTest();
		addressTest();
	}

	public void getUserTest() {
		try {

			UserLogic ul = new UserLogic();
			UserPO temp = ul.getUser("100000");
			assertTrue(temp.getUserName().equals("AAA"));
			assertTrue(temp.getImage() == 1);
			assertTrue(temp.isGender() == true);

			assertTrue(temp.isLogin() == false);
			assertTrue(temp.getMoney() == 255);
			assertTrue(temp.getUserId().equals("100000"));
			assertEquals(ul.getUser("*****"), null);
			assertEquals(ul.getUser("10000"), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getGameRecordTest() {
		try {
			UserLogic ul = new UserLogic();
			GameRecordPO temp = ul.getRecord("100000");
			assertTrue(temp.getUserID().equals("100000"));
			assertTrue(temp.getTotalRound() == 7);
			assertTrue(temp.getAverageScore() == 4157);
			assertTrue(temp.getHighestScore() == 6000);
			assertTrue(temp.getHighestCombo() == 120);
			assertTrue(ul.getRecord("****").getAverageScore()== 0);
			assertTrue(ul.getRecord("1000").getAverageScore()==0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void MessageTest() {
		try {
			UserLogic ul = new UserLogic();
			MessagePO m = new MessagePO(true, "100003", "100004", false, false,
					"hello");	
			ul.saveMessage(m);
			ArrayList<MessagePO> list = ul.getMessage("100004");
			assertTrue(list.size() == 1);
			MessagePO temp = list.get(0);
			assertTrue(temp.isType() == true);
			assertTrue(temp.getFromId().equals("100003"));
			assertTrue(temp.getToId().equals("100004"));
			assertTrue(temp.isIfRead() == false);
			assertTrue(temp.isReply() == false);
			assertTrue(temp.getContent().equals("hello"));
			ul.deleteMessage(m);
			ArrayList<MessagePO> list2 = ul.getMessage("100004");
			assertTrue(list2.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void singleRecordTest() {
		try {
			UserLogic ul = new UserLogic();
			SingleRecordPO s = new SingleRecordPO("666666", 151515, 6400, 15,
					1500);
			ul.saveSingleRecord(s);
			ArrayList<SingleRecordPO> list = ul.getSingleRecords("666666");
			assertTrue(list.size() == 1);
			SingleRecordPO temp = list.get(0);
			assertTrue(temp.getUserID().equals("666666"));
			assertTrue(temp.getDate() == 151515);
			assertTrue(temp.getScore() == 6400);
			assertTrue(temp.getHighestCombo() == 15);
			assertTrue(temp.getMoney() == 1500);
			ArrayList<SingleRecordPO> list2 = ul.getSingleRecords("***");
			assertTrue(list2.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addressTest() {
		try {
			UserLogic ul = new UserLogic();
			AddressPO p = new AddressPO("100010", "111.111.111.111");
			ul.saveAddress(p);
			AddressPO temp = ul.getAddress("100010");
			assertTrue(temp.getId().equals("100010"));
			assertTrue(temp.getAddress().equals("111.111.111.111"));
			assertEquals(ul.getAddress("****"), null);
			assertEquals(ul.getAddress("1000"), null);
			ul.deleteAddress(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
