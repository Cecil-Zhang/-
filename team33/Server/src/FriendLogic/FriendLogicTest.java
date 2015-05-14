package FriendLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import po.FriendListPO;
import po.FriendPO;

public class FriendLogicTest {

	@Test
	public void test() {
		friendListTest();
	}

	public void friendListTest() {

	
		try {
			FriendLogic fl = new FriendLogic();

			ArrayList<FriendPO> tempList = new ArrayList<FriendPO>();
			tempList.add(new FriendPO("100008", "100009", "jiu", "J", true, 1));
			tempList.add(new FriendPO("100008", "100010", "shi", "S", false, 2));
			fl.updateFriendList("100008", tempList);
			FriendListPO f = fl.getFriendList("100008");
			ArrayList<FriendPO> list = f.getFriendList();
			assertTrue(list.size() == tempList.size());
			FriendPO f1 = list.get(0);
			FriendPO f2 = list.get(1);
			assertTrue(f1.getId().equals("100008"));
			assertTrue(f1.getFriendId().equals("100009"));
			assertTrue(f1.getName().equals("jiu"));
			assertTrue(f1.getSecondName().equals("J"));
			assertTrue(f1.getGender() == true);
			assertTrue(f1.getImage() == 1);
			assertTrue(f2.getId().equals("100008"));
			assertTrue(f2.getFriendId().equals("100010"));
			assertTrue(f2.getName().equals("shi"));
			assertTrue(f2.getSecondName().equals("S"));
			assertTrue(f2.getGender() == false);
			assertTrue(f2.getImage() == 2);
			 
			assertEquals(fl.getFriendList("***"), null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
