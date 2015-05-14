package LogicService;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Model.patternModel.GameState;
import Model.patternModel.GameLogic;
import Model.patternModel.location.MatricLocation;
import View.GameUI.ViewObserverAdapter;

public interface MultiGameClientService extends Remote{
	
	/**
	 * @title setServerIP
	 * @Description 设置协作游戏发起者的IP地址
	 * @param ip
	 * @throws RemoteException
	 */
	public void setServerIP(String ip)throws RemoteException;
	
	/**
	 * @title passInitialMatrix
	 * @Description 在协作游戏中，房主传递游戏初始矩阵时调用，向房主获取patternControl服务，注册本机的ViewObserver服务
	 * @param list
	 * @throws RemoteException
	 */
	public void passInitialMatrix(ArrayList<Integer> list,boolean direcition) throws RemoteException;
	
	/**
	 * @title startVSGame
	 * @Description 在对战游戏中，供游戏邀请者调用的接口，本机作为本队的服务器，初始化一个游戏
	 * @param friends 队友ID列表
	 * @param rivals 对手ID列表
	 * @param propC 道具C
	 * @param propD 道具D
	 * @param propE 道具E
	 * @throws RemoteException
	 */
	public void startVSGame(ArrayList<String> friends,
			ArrayList<String> rivals, boolean propC, boolean propD,
			boolean propE) throws RemoteException;
}
