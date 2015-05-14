package LogicService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Model.patternModel.Observer;
import Model.patternModel.TestHelper;
import View.GameUI.ViewObserver;
import View.GameUI.ViewObserverAdapter;


/**
 * @ClassName MultiGameHandler
 * @Description 在协作游戏中，负责查询所有队友的MultiGameClientService，用于向队友传递初始图案矩阵
 * @caller GameControl.initialMultiGameAsSponsor
 * @author 张舒贤
 * @Date 2014-5-20
 */
public class MultiGameHandler {
	ArrayList<MultiGameClientService> service;
	ArrayList<String> ips;

	/**
	 * @param friends 队友的IP列表
	 * @throws RemoteException
	 */
	public MultiGameHandler(ArrayList<String> friends) throws RemoteException{
		this.service=new ArrayList<MultiGameClientService>();
		this.ips=friends;
		String selfIP=IPHelper.getSelfIP();
		for(String ip:friends){
			String s="rmi://"+ip+":6600/MultiGameClientService";
			try{
				MultiGameClientService ser=(MultiGameClientService) Naming.lookup(s);
				TestHelper.testControl(s+" got");
				ser.setServerIP(selfIP);
				this.service.add(ser);
			}catch (NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * @title initialGameMatric
	 * @Description 向每一个参与游戏的玩家发送初始化的矩阵，并取得他们的界面并注册对gameLogic的监听
	 * @param list 初始化矩阵
	 * @return 每个玩家的ViewObserver服务
	 * @throws RemoteException
	 */
	public ArrayList<ViewObserver> initialGameMatric(ArrayList<Integer> list,boolean direction) throws RemoteException{
		ArrayList<ViewObserver> observers=new ArrayList<ViewObserver>();
		
		for(int i=0;i<this.service.size();i++){
			MultiGameClientService ser=this.service.get(i);
			ser.passInitialMatrix(list,direction);
			String ip=this.ips.get(i);
			try {
				ViewObserver observer=(ViewObserver) Naming.lookup("rmi://"+ip+":6600/ViewObserverService");
				TestHelper.testControl("ViewObserverService got");
				observers.add(observer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return observers;
	}
}
