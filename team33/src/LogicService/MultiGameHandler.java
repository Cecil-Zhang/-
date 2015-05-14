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
 * @Description ��Э����Ϸ�У������ѯ���ж��ѵ�MultiGameClientService����������Ѵ��ݳ�ʼͼ������
 * @caller GameControl.initialMultiGameAsSponsor
 * @author ������
 * @Date 2014-5-20
 */
public class MultiGameHandler {
	ArrayList<MultiGameClientService> service;
	ArrayList<String> ips;

	/**
	 * @param friends ���ѵ�IP�б�
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
	 * @Description ��ÿһ��������Ϸ����ҷ��ͳ�ʼ���ľ��󣬲�ȡ�����ǵĽ��沢ע���gameLogic�ļ���
	 * @param list ��ʼ������
	 * @return ÿ����ҵ�ViewObserver����
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
