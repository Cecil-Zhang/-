package Control.userControl;

import Model.userModel.UserModel;
import Model.userModel.UserModelService;
import View.MainJumpService;
import View.LoginUI.LoginFrame;
import po.GameRecordPO;
import po.UserPO;
import vo.GameRecordVO;
import vo.UserVO;

public class UserControl implements UserControlService {
	UserModelService ums = new UserModel();
	public String register(String userName, String password, boolean gender,
			int image) {
		String result = judgePassword(password);
		if (result.length() == 0) {
			UserPO up = new UserPO("", userName, password, gender, image,false,0);
			result = ums.register(up);
			return result;
		} else {
			return result;
		}
	}
	public UserVO updateUser(String id){
		UserPO up = ums.findUser(id);
		UserVO uv = new UserVO(up.getUserId(), up.getUserName(), 
				up.isGender(), up.getImage(),up.getMoney());
		return uv;
	}
	public String changePassword(String userId, String newPassword) {
		String result = judgePassword(newPassword);
		if (result.length() == 0) {
			boolean isSuccessful = ums.changePassword(userId, newPassword);
			if (isSuccessful) {
				result = "密码修改成功";
			} else {
				result = "系统出错，密码修改失败，请稍后重试。";
			}
		}
		return result;
	}

	private String judgePassword(String newPassword) {
		String result = "";
		if (newPassword.length() < 6) {
			result = "密码不得少于6个字符";
		} else if (newPassword.contains(" ")) {
			result = "密码不得包含空格字符";
		} else {
			result = "";
		}
		return result;
	}
	
	public void login(String id, String password) {	
		MainJumpService mjs = LoginFrame.getInstance();
		if(id==null||password==null){
			mjs.instruction("用户名或密码填写不完整");
			return;
		}
		String str = ums.login(id,password);
		System.out.println("-----------dogeo-");
		if(str.equals("")){
			System.out.println("feijeijgi");
			UserPO up = ums.findUser(id);
			UserVO uv = new UserVO(up.getUserId(), up.getUserName(), 
					up.isGender(), up.getImage(),up.getMoney());
			mjs.loginJump(uv);
		}else{
			mjs.instruction(str);
		}
	}
	public void updateMoney(String userId,int money){
		ums.updateMoney(userId, money);
	}
	public GameRecordVO checkRecord(String userId) {
		int[] rounds = {0,0,0,0,0,0,0};
		double[] averageScores = {0,0,0,0,0,0,0};
		GameRecordVO gvp = new GameRecordVO(userId, 0, 0, 0, 0, rounds, averageScores);
		if(userId ==null){
			return gvp;
		}
		GameRecordPO grp = ums.checkRecord(userId);
		if(grp==null){
			return gvp;
		}
		rounds = ums.getEveryRounds(userId);
		averageScores = ums.getAverageScores(userId);
		gvp = new GameRecordVO(grp.getUserID(),grp.getTotalRound(),
				grp.getAverageScore(),grp.getHighestScore(),grp.getHighestCombo(),
			    rounds,averageScores);
		return gvp;
	}

	public void logoff(String id) {
        ums.logoff(id);
        
	}

}