package Control.friendControl;

import java.util.ArrayList;

import vo.FriendListVO;
import vo.FriendVO;
import vo.RankVO;
import vo.UserVO;

public class FriendControlServiceStub implements FriendControlService{

	@Override
	public void addFriend(String id, UserVO uv) {
		return;
		
	}
	@Override
	public UserVO findUser(String id) {
		
		return new UserVO("121250181", "mona", true, 0, 3000);
	}

	@Override
	public void modifyFriend(FriendVO fv) {
		return;
	}

	@Override
	public void deleteFriend(String id, String friendId) {
		return;
		
	}

	@Override
	public ArrayList<RankVO> getRanks(String id) {
		return new ArrayList<RankVO>();
	}

	@Override
	public FriendListVO getFriendList(String id) {
		// TODO Auto-generated method stub
		return new FriendListVO(id, new ArrayList<FriendVO>());
	}

	@Override
	public FriendListVO getList(String id) {
		return new FriendListVO(id, new ArrayList<FriendVO>());
	}

}
