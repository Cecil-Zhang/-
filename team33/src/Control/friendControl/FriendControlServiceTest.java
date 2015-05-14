package Control.friendControl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import vo.FriendListVO;
import vo.RankVO;
import vo.UserVO;

public class FriendControlServiceTest {

	private FriendControlService fcs = new FriendControlServiceStub();
	@Test
	public void testFindUser() {
	
		UserVO uv = fcs.findUser("121250181");
		assertTrue(uv.getId().equals("121250181"));
		assertTrue(uv.getName().equals("mona"));
		assertTrue(uv.isGender());
		assertTrue(uv.getImage()==0);
		assertTrue(uv.getMoney()==3000);
	}

	@Test
	public void testGetRanks() {
		ArrayList<RankVO> list = fcs.getRanks("121250181");
		assertTrue(list.size()==0);
	}

	@Test
	public void testGetFriendList() {
		FriendListVO flv = fcs.getFriendList("121250181");
		assertTrue(flv.getId().equals("121250181"));
		assertTrue(flv.getFriendList().size()==0);
	}

	@Test
	public void testGetList() {
		FriendListVO flv = fcs.getList("121250181");
		assertTrue(flv.getId().equals("121250181"));
		assertTrue(flv.getFriendList().size()==0);
	}

}
