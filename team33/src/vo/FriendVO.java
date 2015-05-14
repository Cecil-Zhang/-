package vo;

/**
 * @ClassName FriendVO
 * @Description 
 * @author ê°Ïþ½Ü
 * @Date ÏÂÎç10:43:47
 */
public class FriendVO {
	String id;
	String friendId;
	String friendName;
	String secondName = "";
	boolean gender;
	int  image;

	public FriendVO(String i, String f, String n,String s,boolean g,int im) {
		id = i;
		friendId = f;
		friendName = n;
		secondName = s;
		gender=g;
		image=im;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	/**
	 * @title getFriendId
	 * @Description
	 * @return
	 */
	public String getFriendId() {
		return friendId;
	}
	
	/**
	 * @title setFriendId
	 * @Description
	 * @param friendId
	 */
	public void setFriendId(String friendId) {
		this.friendId = friendId;
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
	 * @title getName
	 * @Description
	 * @return
	 */
	public String getName() {
		return friendName;
	}

	/**
	 * @title setName
	 * @Description
	 * @param name
	 */
	public void setName(String name) {
		this.friendName = name;
	}

	/**
	 * @title getSecondName
	 * @Description
	 * @return
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @title setSecondName
	 * @Description
	 * @param secondName
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public boolean getGender(){
		return gender;
	}
	public void setGender(boolean g){
		gender=g;
	}
}
