package vo;

import java.util.ArrayList;

public class FriendListVO {

	private String id;
	ArrayList<FriendVO> friendList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<FriendVO> getFriendList() {
		return friendList;
	}
	public void setFriendList(ArrayList<FriendVO> friendList) {
		this.friendList = friendList;
	}
	public FriendListVO(String id, ArrayList<FriendVO> friendList) {
		super();
		this.id = id;
		this.friendList = friendList;
	}
	
}
