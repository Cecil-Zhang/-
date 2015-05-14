package vo;

public class UserVO {

	String userId;
	String userName;
	boolean gender;
	int image;
	int money;
	
	public UserVO(String uId , String uName,boolean g,int i,int money){
		userId = uId;
		userName = uName;
		gender=g;
		image=i;
		this.money=money;
	}
	public UserVO() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isGender() {
		return gender;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public void setId(String uId){
		userId = uId;
	}
	public String getId(){
		return userId;
	}
	public void setName(String uName){
		userName = uName;
	}
	public String getName(){
		return userName;
	}
}
