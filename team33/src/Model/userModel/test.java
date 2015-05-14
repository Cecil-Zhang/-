package Model.userModel;

import LogicService.UserLogic;
import po.UserPO;

public class test {
	public static void main(String[] args){
		UserModel uModel = new UserModel();
		UserLogic uLogic = new UserLogic();
	    System.out.print(uModel.login("100000", "100000"));
	}

}
