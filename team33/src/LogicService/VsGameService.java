package LogicService;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName VsGameService
 * @Description
 * @author 张舒贤
 * @Date 2014-5-22
 */
public interface VsGameService extends Remote{

	/**
	 * @title FrozenPattern
	 * @Description 消除一个道具B时，冻结对手的一种图案2s
	 * @throws RemoteException
	 */
	public void FrozenPattern() throws RemoteException;
	
	/**
	 * @title WeakenPropC
	 * @Description 形成超级模式时，消弱对手的道具C效果5s
	 * @throws RemoteException
	 */
	public void WeakenPropC() throws RemoteException;
	
	/**
	 * @title updateScore
	 * @Description 当分数发生改变时，通知对手界面更新
	 * @param score 本局游戏当前分数
	 * @throws RemoteException
	 */
	public void updateScore(int score) throws RemoteException;
	
	/**
	 * @title startGame
	 * @Description 使对战游戏的参与者一同开始游戏
	 * @throws RemoteException
	 */
	public void startGameWithRivals() throws RemoteException;
}
