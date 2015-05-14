package Control.patternControl;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Model.patternModel.location.MatricLocation;

/**
 * @ClassName patternControlService
 * @Description 为避免patterView依赖于具体的patternControl而抽象出来的接口
 * @author 张舒贤
 * @Date 2014-5-9
 */
public interface patternControlService extends Remote{
	
	/**
	 * @title mouseClick
	 * @Description 负责鼠标单击后的逻辑处理
	 * @param point 鼠标单击的坐标
	 */
	public boolean mouseClick(MatricLocation point) throws RemoteException;

	/**
	 * @title mouseDrag
	 * @Description 负责鼠标拖动后的逻辑处理
	 * @param before 鼠标拖动的起始坐标
	 * @param after 鼠标拖动的结束坐标
	 */
	public boolean mouseDrag(MatricLocation before,MatricLocation after) throws RemoteException;
	
	/**
	 * @title startGame
	 * @Description 开始游戏，在游戏状态中加入游戏开始的时间
	 */
	public void startGame() throws RemoteException;
	
	/**
	 * @title startGameWithRivals
	 * @Description 通知对战的好友游戏开始
	 * @throws RemoteException
	 */
	public void startGameWithRivals() throws RemoteException;
	
	/**
	 * @title saveGameRecored
	 * @Description 保存游戏的数据，包括时间、得分、最高连击数
	 */
	public void saveGameRecored(String userID) throws RemoteException;
	
	/**
	 * @title haltGame
	 * @Description 暂停游戏
	 */
	public void haltGame() throws RemoteException;
	
	/**
	 * @title restartGame
	 * @Description 重新开始游戏
	 */
	public void restartGame() throws RemoteException;
}
