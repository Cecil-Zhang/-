package LogicService;

import java.awt.Point;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Control.gameControl.GameControlService;
import Control.gameControl.GameControl;
import Control.patternControl.patternControlService;
import Model.patternModel.GameState;
import Model.patternModel.Observer;
import Model.patternModel.GameLogic;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.TestHelper;
import View.MainFrame;
import View.MessageFrame;
import View.GameUI.GamingPanel;
import View.GameUI.ViewObserver;
import View.GameUI.ViewObserverAdapter;

/**
 * @ClassName MultiGameClient
 * @Description ��ʼ��һ��MultiGameClientService,���󶨵�RMI,�ṩ����Ϸ�����߳�ʼ����Ϸ�Ľӿ�
 * @caller MessageRMI,���û���¼֮�����
 * @author ������
 * @Date 2014-6-16
 */
public class MultiGameClient extends UnicastRemoteObject implements MultiGameClientService {
	String serverIP;
	String selfIP;
	
	public MultiGameClient() throws RemoteException {
		super();
		GameControlService service = new GameControl();
		
		// ��ñ���IP��ַ
		selfIP = IPHelper.getSelfIP();

		// ע��MultiGameClientService�����Թ���Ϸ�����ߴ��ݳ�ʼ���ľ���
		try {
			Naming.rebind("rmi://"+selfIP+":6600/MultiGameClientService",this);
			TestHelper.testControl("MultiGameClientService rebinded");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("��Ϸ������ע��MultiGameClientServiceʧ��!");
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see LogicService.MultiGameClientService#passInitialMatrix(java.util.ArrayList)
	 */
	@Override
	public void passInitialMatrix(ArrayList<Integer> list,boolean direction) throws RemoteException{
		// TODO Auto-generated method stub
		GameControl control=new GameControl();
		
		try {
			patternControlService pcService = (patternControlService) Naming.lookup("rmi://"+this.serverIP+":6600/PatternControlService");
			control.initialMultiGameAsParticipate(pcService,list,direction);			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setServerIP(String ip) throws RemoteException {
		// TODO Auto-generated method stub
		if(ip!=null){
			this.serverIP=ip;
		}
	}

	@Override
	public void startVSGame(ArrayList<String> friends,
			ArrayList<String> rivals, boolean propC, boolean propD,
			boolean propE) throws RemoteException {
		// TODO Auto-generated method stub
		GameControlService control=new GameControl();
		control.initialRivalsandFriendsNet(friends, rivals, propC, propD, propE);
	}
}
