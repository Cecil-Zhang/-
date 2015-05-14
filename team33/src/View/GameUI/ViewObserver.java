package View.GameUI;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import Control.patternControl.RivalConditionVS;
import Model.patternModel.Observer;
import Model.patternModel.location.MatricLocation;

/**
 * @ClassName ViewObserver
 * @Description 界面监听者，监听游戏数据的变更
 * @author 张舒贤
 * @Date 2014-5-14
 */
public interface ViewObserver extends Remote{

	/**
	 * @title update
	 * @Description 当patternModel发生更改的时候，供model层调用的接口
	 * @param ifSuperMode 超级模式状态
	 * @param score 游戏分数
	 * @param tips 用户无操作时提示的坐标
	 * @param clears 用户消除产生的坐标
	 * @param beginList 游戏初始化的图案矩阵
	 */
	public void update(boolean ifSuperMode,int score,boolean flag,
			ArrayList<MatricLocation> tips,ArrayList<MatricLocation> bang,ArrayList<MatricLocation> clears
			,ArrayList<MatricLocation> beginList) throws RemoteException;
	
	/**
	 * @title gameReady
	 * @Description 游戏数据已准备好，通知游戏开始进入倒计时
	 */
	public void gameReady() throws RemoteException;
	
	/**
	 * @title showGameResult
	 * @Description 游戏结束后向用户显示游戏成绩
	 * @param d 游戏开始时间
	 * @param s 游戏分数
	 * @param combo 游戏连击数
	 * @param money 游戏金钱
	 * @throws RemoteException
	 */
	public void showGameResult(Date d,String s,int combo,int money) throws RemoteException;
	
	/**
	 * @title setRivaltoFriend
	 * @Description 利用GamingPanel生成RivalConditionVS,用于在对战中向队长注册监听
	 * @param rivalImg 对手头像编号
	 * @throws RemoteException
	 */
	public void setRivaltoFriend(int rivalImg) throws RemoteException;
	
}
