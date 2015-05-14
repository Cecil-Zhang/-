package Control.friendControl;

import java.util.ArrayList;

import vo.FriendListVO;
import vo.FriendVO;
import vo.RankVO;
import vo.UserVO;

public interface FriendControlService {
	
	public void addFriend(String id, UserVO uv);
	public UserVO findUser(String id);
	public void modifyFriend(FriendVO fv);
	public void deleteFriend(String id, String friendId);
	public ArrayList<RankVO> getRanks(String id);
	public FriendListVO getFriendList(String id);
	public FriendListVO getList(String id);
}
