package Control.patternControl;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName RivalConditionVSService
 * @Description 提供RivalConditionVSService可供远程调用的接口
 * @author 张舒贤
 * @Date 2014-6-14
 */
public interface RivalConditionVSService extends Remote{

	/**
	 * @title changeScore
	 * @Description 对方分数改变，更新界面
	 * @param s 对方分数
	 */	
	public void changeScore(int score)throws RemoteException;
	
	/**
	 * @title changeSupermode
	 * @Description 对方超级状态改变，展示特效
	 * @param mode 对方的超级状态开关
	 */
	public void changeSupermode(boolean mode)throws RemoteException;
	
	public void frozenPattern(int patternType)throws RemoteException;
}
