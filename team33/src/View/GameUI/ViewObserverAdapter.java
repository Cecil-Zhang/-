package View.GameUI;

import java.awt.Point;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import Control.patternControl.RivalConditionVS;
import LogicService.IPHelper;
import Model.patternModel.Observer;
import Model.patternModel.TestHelper;
import Model.patternModel.location.MatricLocation;
import View.MessageFrame;

/**
 * @ClassName ViewObserverAdapter
 * @Description 将游戏界面的接口转换成能向GameLogic注册监听的接口
 * @author 张舒贤
 * @Date 2014-5-15
 */
public class ViewObserverAdapter implements Observer {
	private ViewObserver vobserver;
	private boolean superMode;
	private boolean flag;
	private int score;
	private ArrayList<MatricLocation> tips;
	private ArrayList<MatricLocation> clears;
	private ArrayList<MatricLocation> bang;
	private ArrayList<MatricLocation> beginList;
	
	public ViewObserverAdapter(ViewObserver vo){
		super();
		this.vobserver=vo;
	}
	
	@Override
	public void update(ArrayList<MatricLocation> list) throws RemoteException {
		vobserver.update(superMode, score, flag, tips, bang, clears,beginList);
	}
	
	/**
	 * @title setParameter
	 * @Description 通知界面监听者数据的改变
	 * @param sm 超级模式的状态
	 * @param s 游戏的分数
	 * @param flag sm,s是否有效的标志
	 * @param t 提示的坐标列表
	 * @param pump 消除时爆炸的坐标列表
	 * @param c 消除时改变的坐标列表
	 * @param 改变的list的起始位置
	 */
	public void setParameter(boolean sm,int s,boolean f,ArrayList<MatricLocation> t,
			ArrayList<MatricLocation> pump,ArrayList<MatricLocation> c,
							ArrayList<MatricLocation> beginList) throws RemoteException{
		superMode=sm;
		score=s;
		flag=f;
		tips=t;
		bang=pump;
		clears=c;
		this.beginList=beginList;
	}

	/**
	 * @title gameReady
	 * @Description 开始进行游戏前倒计时
	 */
	public void gameReady() {
		try {
			vobserver.gameReady();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("网络异常，请稍后重试");
			e.printStackTrace();
		}
	}

	public void showGameResult(Date d,String score,int combo,int money){
		try {
			vobserver.showGameResult(d, score, combo, money);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("网络异常，请稍后重试");
			e.printStackTrace();
		}
	}

	public void setRivalCondtoFriend(int img) {
		// TODO Auto-generated method stub
		try {
			vobserver.setRivaltoFriend(img);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
