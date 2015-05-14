package Model.friendModel;

import po.FriendListPO;
import po.FriendPO;


public interface FriendModelService {

	public void addFriend(FriendPO f);
	public void modifyFriend(FriendPO f);
	public void deleteFriend(String id,String friendId);
	public FriendListPO getFriendList(String id);
	public boolean isFriend(String id1, String id2);
	
}
