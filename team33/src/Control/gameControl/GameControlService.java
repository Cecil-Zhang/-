package Control.gameControl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Control.patternControl.patternControl;
import Control.patternControl.patternControlService;
import Model.patternModel.GameLogic;
import Model.patternModel.Observer;
import View.GameUI.ViewObserver;

/**
 * @ClassName GameControlService
 * @Description GameController的抽象接口
 * @author 张舒贤
 * @Date 2014-5-12
 */
public interface GameControlService {

	/**
	 * @title initialSoloGame
	 * @Description 初始化单机游戏
	 * @param propC 是否使用道具C
	 * @param propD 是否使用道具D
	 * @param propE 是否使用道具E
	 */
	public void initialSoloGame(String selfID,boolean propC,boolean propD,boolean propE);
	
	
	/**
	 * @title initialRivalandFriendsNet
	 * @Description 初始化对战游戏
	 * @param friends 合作好友的ID列表
	 * @param rivals 对战好友的ID列表
	 * @param propC 是否使用道具C
	 * @param propD 是否使用道具D
	 * @param propE 是否使用道具E
	 * @return
	 */
	public void initialRivalsandFriendsNet(ArrayList<String> friends,ArrayList<String> rivals,
			boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialVSGame
	 * @Description 游戏发起者通知所有邀请的玩家调用initialRivalsandFriendsNet
	 * @param friends 好友ID列表
	 * @param rivals 对手ID列表
	 * @param propC 道具C
	 * @param propD 道具D
	 * @param propE 道具E
	 */
	public void broadcastVSGame(String selfID,ArrayList<String> friends,ArrayList<String> rivals,
			boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialFriendsNet
	 * @Description 初始化协作游戏
	 * @param friends 协作好友的ID
	 * @param propC 是否使用道具C
	 * @param propD 是否使用道具D
	 * @param propE 是否使用道具E
	 * @return
	 */
	public void initialFriendsNet(String selfID,ArrayList<String> friends,boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialMultiGameAsParticipate
	 * @Description 获取房主的patternControl服务，并初始化游戏界面
	 * @param pcService patternControl
	 * @param list	初始化的图案矩阵
	 * @param direction	下落方向
	 * @return
	 * @throws RemoteException
	 */
	public ViewObserver initialMultiGameAsParticipate(patternControlService pcService,ArrayList<Integer> list,boolean direction) throws RemoteException; 
}
