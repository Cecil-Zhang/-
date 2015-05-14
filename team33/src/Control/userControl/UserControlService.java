package Control.userControl;

import po.GameRecordPO;
import vo.GameRecordVO;
import vo.UserVO;

public interface UserControlService {
	public String register(String userName,String password,
			boolean gender,int image);
	public String changePassword(String userId,String newPassword);
	public void login(String id,String password);
	public GameRecordVO checkRecord(String userId);
	public void logoff(String id);
	public void updateMoney(String id,int money);
	public UserVO updateUser(String id);
}
