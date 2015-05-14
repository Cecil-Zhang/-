package View.GameUI;

import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import Control.patternControl.RivalConditionVS;
import LogicService.IPHelper;
import Model.patternModel.TestHelper;
import Model.patternModel.location.MatricLocation;

/**
 * @ClassName ViewObserverHelper
 * @Description 在协作游戏中作为从机，用自己的界面生成监听者注册到RMI供主机调用
 * @caller GameControl.initialMultiGameAsParticipate
 * @author 张舒贤
 * @Date 2014-5-21
 */
public class ViewObserverHelper extends UnicastRemoteObject implements
		ViewObserver {

	ViewObserver vobserver;
	
	public ViewObserverHelper(ViewObserver vo) throws RemoteException {
		super();
		this.vobserver=vo;
		try {
			Naming.rebind("rmi://"+IPHelper.getSelfIP()+":6600/ViewObserverService",this);
			TestHelper.testprint("rmi://"+IPHelper.getSelfIP()+":6600/ViewObserverService rebinded");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see View.GameUI.ViewObserver#update(boolean, int, boolean, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void update(boolean ifSuperMode,int score,boolean flag,
			ArrayList<MatricLocation> tips,ArrayList<MatricLocation> bang,ArrayList<MatricLocation> clears
			,ArrayList<MatricLocation> beginList) throws RemoteException {
		// TODO Auto-generated method stub
		this.vobserver.update(ifSuperMode, score, flag, tips, bang, clears,beginList);

	}

	@Override
	public void gameReady() throws RemoteException{
		// TODO Auto-generated method stub
		vobserver.gameReady();
	}

	@Override
	public void showGameResult(Date d, String s, int combo, int money)
			throws RemoteException {
		// TODO Auto-generated method stub
		vobserver.showGameResult(d, s, combo, money);
	}

	@Override
	public void setRivaltoFriend(int rivalImg)
			throws RemoteException {
		// TODO Auto-generated method stub
		vobserver.setRivaltoFriend(rivalImg);
	}


}
