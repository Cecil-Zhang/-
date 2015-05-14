package po;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName FriendListPO
 * @Description
 * @author ê°Ïþ½Ü
 * @Date ÏÂÎç7:40:34
 */
public class FriendListPO implements Serializable{
	String id;

	ArrayList<FriendPO> friendList;

	public FriendListPO(String i, ArrayList<FriendPO> f) {
		id = i;
		friendList = f;
	}

	/**
	 * @title getId
	 * @Description
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @title setId
	 * @Description
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @title getFriendList
	 * @Description
	 * @return
	 */
	public ArrayList<FriendPO> getFriendList() {
		return friendList;
	}

	/**
	 * @title setFriendList
	 * @Description
	 * @param friendList
	 */
	public void setFriendList(ArrayList<FriendPO> friendList) {
		this.friendList = friendList;
	}

}
