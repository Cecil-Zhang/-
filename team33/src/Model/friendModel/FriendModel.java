package Model.friendModel;

import java.util.ArrayList;

import po.FriendListPO;
import po.FriendPO;
import Model.userModel.Rank;
import Model.userModel.UserModel;

/**
 * @ClassName Friend
 * @Description
 * @author ê°Ïþ½Ü
 * @Date ÏÂÎç11:41:56
 */
public class FriendModel implements FriendModelService {
	FriendList fList = new FriendList();
	UserModel um = new UserModel();

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.friendModel.FriendModelService#addFriend(po.FriendPO)
	 */
	public void addFriend(FriendPO f) {
		if(isFriend(f.getId(), f.getFriendId())){
			return;
		}
		String id = f.getId();
		ArrayList<FriendPO> friendList = fList.findList(id);
		friendList.add(f);
		fList.update(id,friendList);
	}

	/* (non-Javadoc)
	 * @see Model.friendModel.FriendModelService#modifyFriend(po.FriendPO)
	 */
	public void modifyFriend(FriendPO f) {
		String id = f.getId();
		String friendId = f.getFriendId();
		ArrayList<FriendPO> friendList = fList.findList(id);
		for (int i = 0; i < friendList.size(); i++) {
			if (friendId.equals(friendList.get(i).getFriendId())) {
				friendList.set(i, f);
			}
		}
		fList.update(id,friendList);
	}

	/* (non-Javadoc)
	 * @see Model.friendModel.FriendModelService#deleteFriend(java.lang.String, java.lang.String)
	 */
	public void deleteFriend(String id, String friendId) {
		ArrayList<FriendPO> friendList = fList.findList(id);
		for (int i = 0; i < friendList.size(); i++) {
			if (friendId.equals(friendList.get(i).getFriendId())) {
				friendList.remove(i);
			}
		}
		fList.update(id,friendList);
	}

	/* (non-Javadoc)
	 * @see Model.friendModel.FriendModelService#getFriendList(java.lang.String)
	 */
	@Override
	public FriendListPO getFriendList(String id) {
		if(id==null){
			return null;
		}
		// TODO Auto-generated method stub
		ArrayList<FriendPO> friendList = fList.findList(id);
		FriendListPO result = new FriendListPO(id, friendList);
		return result;
	}

	@Override
	public boolean isFriend(String id1, String id2) {
	    ArrayList<FriendPO> friendList = fList.findList(id1);
	    for(int i=0;i<friendList.size();i++){
	    	FriendPO fp = friendList.get(i);
	    	if(fp.getFriendId().equals(id2)){
	    		return true;
	    	}
	    }
		return false;
	}

	
}
