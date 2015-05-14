package po;

import java.io.Serializable;

/**
 * @ClassName UserPO
 * @Description
 * @author ê°Ïþ½Ü
 * @Date ÏÂÎç11:37:33
 */
public class UserPO implements Serializable{
	private String userId;
	private String userName;
	private String password;
	
	private boolean gender;
	private int image;
	private boolean isLogin;
	private int money;
	
	public UserPO(String userId,String userName, String password, 
			boolean gender, int image,boolean is,int money) {
		super();
		this.userName = userName;
		this.password = password;
		this.userId = userId;
		this.gender = gender;
		this.image = image;
		isLogin=is;
		this.money=money;
	}
	public int getMoney(){
		return  money;
	}
	public void  setMoney(int m){
		money=m;
	}
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public UserPO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @title getUserName
	 * @Description
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @title setUserName
	 * @Description
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @title getPassword
	 * @Description
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @title setPassword
	 * @Description
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @title getUserId
	 * @Description
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @title setUserId
	 * @Description
	 * @param userID
	 */
	public void setUserId(String userID) {
		this.userId = userID;
	}

	/**
	 * @title isGender
	 * @Description
	 * @return
	 */
	public boolean isGender() {
		return gender;
	}

	/**
	 * @title setGender
	 * @Description
	 * @param gender
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public void setImage(int picture){
	    this.image = picture;
	}
	
	public int getImage(){
		return image;
	}
}
