package Control.gameControl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.Iterator;

import po.AddressPO;
import po.UserPO;
import vo.InvitationVO;
import vo.UserVO;

import Control.patternControl.RivalConditionVS;
import Control.patternControl.RivalConditionVSService;
import Control.patternControl.patternControl;
import Control.patternControl.patternControlService;
import Control.userControl.MessageControl;
import Control.userControl.MessageControlService;
import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import LogicService.IPHelper;
import LogicService.MultiGameClientService;
import LogicService.MultiGameHandler;
import LogicService.RivalConditionVSHandler;
import LogicService.UserLogic;
import LogicService.UserLogicService;
import LogicService.VsGameService;
import Model.SettingModel.SettingModel;
import Model.patternModel.GameLogic;
import Model.patternModel.GameState;
import Model.patternModel.Observer;
import Model.patternModel.TestHelper;
import Model.userModel.UserModel;
import Model.userModel.UserModelService;
import View.Animation2;
import View.Animation3;

import View.MainFrame;
import View.MessageFrame;
import View.GameUI.GamingPanel;
import View.GameUI.VSPanel;
import View.GameUI.ViewObserver;
import View.GameUI.ViewObserverAdapter;
import View.GameUI.ViewObserverHelper;

/**
 * @ClassName gameController
 * @Description ������Ϸ��ʼʱ�ĳ�ʼ������Ϸ����ʱ�����ݴ洢����
 * @author ������
 * @Date 2014-5-12
 */
public class GameControl implements GameControlService {

	private GameLogic gameLogic;
	private GameState gameState;
	private GamingPanel gamingPanel;
	private UserModelService userModelService;
	private ArrayList<ViewObserverAdapter> friendViews;
	private Animation2 ani2;

	private ArrayList<Integer> getInitialDataMatric() {
		return this.gameLogic.produceInitDataMatric();
	}

	/**
	 * @title initialGame
	 * @Description ��ʼ��һ����Ϸ�����ݺ�״̬
	 * @param gameLogic
	 * @return patternControl����
	 * @throws RemoteException
	 */
	private patternControl initialGame(boolean propC, boolean propD,
			boolean propE) throws RemoteException {
		this.gameLogic = new GameLogic();
		this.gameState = GameState.getInstance();
		this.gameState.set(gameLogic, gameLogic, propC, propD, propE);
		patternControl pc = new patternControl(this.gameLogic);
		return pc;
	}

	public static void main(String[] args){
		GameControl gc=new GameControl();
		gc.initialSoloGame("100000", false, false, false);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see Control.gameControl.GameControlService#initialSoloGame()
	 */
	public void initialSoloGame(String selfID, boolean propC, boolean propD,
			boolean propE) {
		try {
			patternControlService pcService = this.initialGame(propC, propD,
					propE);	
			ArrayList<Integer> list = gameLogic.produceInitDataMatric();
			TestHelper.testControl("new GamingPanel start at "+new Date());
			SettingModel a = new SettingModel();
			boolean direction = a.getWay();
			ani2=new Animation2(pcService,list,direction);
			gamingPanel = ani2.getPanel();
			gamingPanel.showStopButton();
			ViewObserverAdapter adapter = new ViewObserverAdapter(gamingPanel);
			this.gameLogic.registerObserver(adapter);
			this.gameState.registerObserver(adapter);
			ani2.setNetReady();
			TestHelper.testControl("pcService.startGame");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.consumeMoney(selfID, propC, propD, propE);
	}

	/**
	 * @title initialFriendsNet
	 * @Description ��������ĺ���ID��ȡ���ѵ�IP��ַ
	 * @param friends Э�����ѵ�ID�б�
	 */
	public void initialFriendsNet(String selfID,
			ArrayList<String> friends, boolean propC, boolean propD,
			boolean propE) {
		if (friends != null && friends.size() != 0) {
			boolean result = this.InvitePlayer(selfID, "Э��", friends);
			if (result) {
				TestHelper.testControl(friends + " friends");
				ArrayList<String> ips = this.getPlayerIP(friends);
				TestHelper.testControl(ips + " ips");
				this.initialMultiGameAsSponsor(ips, propC, propD, propE,true);
				this.consumeMoney(selfID, propC, propD, propE);
				
			} else {
				MessageFrame.show("�Է��ܾ�������Ϸ����");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Control.gameControl.GameControlService#initialRivalsandFriendsNet(java
	 * .util.ArrayList, java.util.ArrayList, boolean, boolean, boolean)
	 */
	@Override
	public void initialRivalsandFriendsNet(ArrayList<String> friends,
			ArrayList<String> rivals, boolean propC, boolean propD,
			boolean propE) {
		// TODO Auto-generated method stub
		UserLogic logic = new UserLogic();

		// ��ȡ���ѵ�IP��ַ
		ArrayList<String> friendIPs = new ArrayList<String>();
		if ((friends != null) && (friends.size() != 0)) {
			java.util.Iterator<String> it = friends.iterator();
			while (it.hasNext()) {
				String id = it.next();
				AddressPO ip = logic.getAddress(id);
				friendIPs.add(ip.getAddress());
			}
		}

		patternControl pc = this.initialMultiGameAsSponsor(friendIPs, propC,
				propD, propE,false);
		this.initialVsGame(pc, friendIPs, rivals, propC, propD, propE);
		if(ani2!=null){
			ani2.setNetReady();
		}
	}

	/**
	 * @title initialMultiGameAsSponsor
	 * @Description ע��patternControl���񣬲�����Ϸע����ͻ��˽���ļ���
	 * @param friends  ͬ����Ϸ����ĺ���IP�б�
	 * @param propC �Ƿ�ʹ�õ���C
	 * @param propD �Ƿ�ʹ�õ���D
	 * @param propE �Ƿ�ʹ�õ���E
	 * @return
	 */
	private patternControl initialMultiGameAsSponsor(ArrayList<String> friends,
			boolean propC, boolean propD, boolean propE, boolean ifSponsor) {
		// ��ñ���IP��ַ
		String selfIP = IPHelper.getSelfIP();

		patternControl pcService = null;
		try {
			// ע��patternControl�����Թ������ͻ��˵���
			pcService = this.initialGame(propC, propD, propE);
			ArrayList<Integer> list = this.getInitialDataMatric();
			SettingModel a = new SettingModel();
			boolean direction=a.getWay();
			ani2=new Animation2(pcService,list,direction);
			gamingPanel = ani2.getPanel();
			ViewObserverAdapter adapter = new ViewObserverAdapter(gamingPanel);
			this.gameLogic.registerObserver(adapter);
			this.gameState.registerObserver(adapter);
			TestHelper.testControl("rmi://" + selfIP
					+ ":6600/PatternControlService rebinded");
			Naming.rebind("rmi://" + selfIP + ":6600/PatternControlService",
					pcService);

			// ����ʼ���󴫸�ÿһ���ͻ���,����GameLogic,GameStateע��ÿ���ͻ��˽���ļ���
			if (friends != null && friends.size() != 0) {
				MultiGameHandler service = new MultiGameHandler(friends);
				ArrayList<ViewObserver> clientViews = service
						.initialGameMatric(list,direction);

				this.friendViews = new ArrayList<ViewObserverAdapter>();
				for (ViewObserver observer : clientViews) {
					ViewObserverAdapter voadapter = new ViewObserverAdapter(
							observer);
					this.friendViews.add(voadapter);
					this.gameLogic.registerObserver(voadapter);
					this.gameState.registerObserver(voadapter);
				}
				if(ifSponsor){
					ani2.setNetReady();
				}
//				pcService.startGame();
			}
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			MessageFrame.show("RMIԶ�̵���ʧ��!");
			e2.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pcService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Control.gameControl.GameControlService#initialMultiGameAsParticipate(
	 * java.lang.String, java.util.ArrayList)
	 */
	public ViewObserver initialMultiGameAsParticipate(
			patternControlService pcService, ArrayList<Integer> list,boolean direction) {
		ViewObserver adapter = null;
		try {
			ani2=new Animation2(pcService,list,direction);
			adapter = new ViewObserverHelper(ani2.getPanel());
			TestHelper.testprint("MultiGame client view start...");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("Э����Ϸ���������ɼ�����ʧ��!");
			e.printStackTrace();
		}
		return adapter;
	}

	/**
	 * @title initialVsGame
	 * @Description ��ʼ����ս��ص����ݣ�����ÿ����Ա����ʾ���ֵ�ͷ���Լ�RivalConditionVS�ĳ�ʼ��
	 * @param pControl patternControl
	 * @param friends ����IP��ַ�б�
	 * @param rivals ����ID�б�
	 * @param propC ����C
	 * @param propD ����D
	 * @param propE ����E
	 */
	private void initialVsGame(patternControl pControl,
			ArrayList<String> friends, ArrayList<String> rivals, boolean propC,
			boolean propD, boolean propE) {
		// TODO Auto-generated method stub
		UserModelService um = new UserModel();
		UserPO po = um.findUser(rivals.get(0));
		int img = po.getImage();

		// ֪ͨ�����Ͷ��ְ�RivalConditionVSService����RMI
		this.gamingPanel.setRivaltoFriend(img);

		if ((this.friendViews != null) && (this.friendViews.size() != 0)) {
			for (ViewObserverAdapter adapter : this.friendViews) {
				adapter.setRivalCondtoFriend(img);
			}
		}

		RivalConditionVSHandler RCVSHandler = new RivalConditionVSHandler();
		ArrayList<RivalConditionVSService> RCVSService = null;
		try {
			RCVSService = RCVSHandler.lookupRivalCond(friends);
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pControl.addRivalConds(RCVSService);
		this.bindandLookuoVSGameService(pControl,rivals);
		if(ani2!=null){
			ani2.setNetReady();
		}
	}

	/**
	 * @title bindandLookuoVSGameService
	 * @Description �ڶ�ս��Ϸ�а��Լ��Ͳ�ѯ���ѵ�VSGameService�����ڵ�����״̬�����ı�ʱ֪ͨ����
	 * @param pControl patternControl
	 * @param rivals ����ID�б�
	 */
	private void bindandLookuoVSGameService(patternControl pControl,ArrayList<String> rivals) {
		// ��ñ���IP��ַ
		String selfIP = IPHelper.getSelfIP();

		try {
			// ע��VsGame�����Թ����ֿͻ��˵���
			Naming.rebind("rmi://" + selfIP + ":6600/VsGameService", pControl);
			TestHelper.testControl(selfIP
					+ " MultiVSGame server VsGameService rebinded");
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			MessageFrame.show("RMI�� " + selfIP + " VsGameServiceʧ��!");
			e2.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ��ȡ����IP��ַ

		ArrayList<String> RivalIPs = this.getPlayerIP(rivals);

		// �ȴ�����VsGameService����ɲ���ȡ�����
		if (rivals != null && rivals.size() != 0) {
			ArrayList<VsGameService> vsService = new ArrayList<VsGameService>();
			String ip = RivalIPs.get(0);
			VsGameService vs = null;
			long start = (new Date()).getTime();
			long now;
			TestHelper.testControl("Waiting " + ip + " ....");
			while (true) {
				now = (new Date()).getTime();
				if (now - start > 180000) {
					MessageFrame.show(ip + "���ӳ�ʱ����Ϸ�ѹر�!");
					return;
				}
				try {

					vs = (VsGameService) Naming.lookup("rmi://" + ip
							+ ":6600/VsGameService");
					TestHelper.testControl("rmi://" + ip
							+ ":6600/VsGameService got!!");
					break;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			vsService.add(vs);
			TestHelper.testControl(ip + " �Ѿ���!");

			this.gameState.addRival(vsService);
			this.gameLogic.addRivals(vsService);
		}
	}

	@Override
	public void broadcastVSGame(String ID, ArrayList<String> friends,
			ArrayList<String> rivals, boolean propC, boolean propD,
			boolean propE) {
		// TODO Auto-generated method stub
		if (rivals != null && rivals.size() != 0) {
			// �������
			TestHelper.testControl(rivals + "rivals!!!");
			ArrayList<String> temp=new ArrayList<String>();
			temp.addAll(rivals);
			temp.addAll(friends);
			boolean rivalResult = this.InvitePlayer(ID, "��ս", temp);
			boolean friendResult = true;
//			boolean rivalResult = this.InvitePlayer(ID, "��ս", rivals);
//			boolean friendResult = true;
//			// �������
//			if (friends != null && friends.size() != 0) {
//				friendResult = this.InvitePlayer(ID, "Э����ս", friends);
//			}

			// ���˫����ͬ������
			if (rivalResult && friendResult) {
				UserLogic ums = new UserLogic();
				String rivalID = rivals.get(0);
				String rivalIP = null;
				AddressPO po = ums.getAddress(rivalID);
				rivalIP = po.getAddress();

				// �Զ��ֵĽǶ������µĶ��ѺͶ���ID�б�
				ArrayList<String> newRival = new ArrayList<String>();
				newRival.add(ID);
				for (int i = 0; i < friends.size(); i++) {
					String id = friends.get(i);
					newRival.add(id);
					TestHelper.testControl("newRival: " + id);
				}
				ArrayList<String> newFriends = new ArrayList<String>();
				for (int i = 1; i < rivals.size(); i++) {
					String id = rivals.get(i);
					newFriends.add(id);
					TestHelper.testControl("newFriend: " + id);
				}

				StartVSGameThread vsthread = new StartVSGameThread(rivalIP,
						newFriends, newRival, propC, propD, propE);
				Thread thread = new Thread(vsthread);
				thread.start();
				TestHelper.testControl("�ȳ�ʼ���Լ�����Ϸ");
				this.initialRivalsandFriendsNet(friends, rivals, propC, propD,
						propE);
				this.consumeMoney(ID, propC, propD, propE);
			}
		}
	}

	private ArrayList<String> getPlayerIP(ArrayList<String> IDs) {
		if (IDs != null) {
			UserLogic logic = new UserLogic();
			ArrayList<String> ips = new ArrayList<String>();
			if ((IDs != null) && (IDs.size() != 0)) {
				java.util.Iterator<String> it = IDs.iterator();
				while (it.hasNext()) {
					String id = it.next();
					AddressPO ip = logic.getAddress(id);
					ips.add(ip.getAddress());
				}
			}
			return ips;
		} else {
			return null;
		}
	}

	private boolean InvitePlayer(String selfID, String gameType,
			ArrayList<String> players) {
		// ������Ϸ����
		MessageFrame.show("�ȴ����ѽ�������");
		InvitationVO vo = new InvitationVO(selfID, gameType, players);
		MessageControlService service = new MessageControl();
		boolean result = service.sendInvitation(vo);
		return result;
	}

	private void consumeMoney(String id, boolean propC, boolean propD,
			boolean propE) {
		int money = 0;
		if (propC) {
			money = money - 100;
		}
		if (propD) {
			money = money - 200;
		}
		if (propE) {
			money = money - 300;
		}
		UserControlService userControl = new UserControl();
		userControl.updateMoney(id, money);
	}

	class StartVSGameThread implements Runnable {
		private String ip;
		private ArrayList<String> rivals;
		private ArrayList<String> friends;
		private boolean propC;
		private boolean propD;
		private boolean propE;

		/**
		 * @param ip �Է��ӳ�IP��ַ
		 * @param r �Է�����ID�б�
		 * @param f �Է�����ID�б�
		 * @param c ����C
		 * @param d ����D
		 * @param e ����E
		 */
		public StartVSGameThread(String ip, ArrayList<String> f,
				ArrayList<String> r, boolean c, boolean d, boolean e) {
			super();
			this.ip = ip;
			this.rivals = r;
			this.friends = f;
			this.propC = c;
			this.propD = d;
			this.propE = e;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			MultiGameClientService service;
			try {
				service = (MultiGameClientService) Naming.lookup("rmi://"
						+ this.ip + ":6600/MultiGameClientService");
				TestHelper.testControl("rmi://" + this.ip
						+ ":6600/MultiGameClientService got!!!!!");
				service.startVSGame(friends, rivals, propC, propD, propE);
				TestHelper.testControl("��֪ͨ���ֿ�ʼ��Ϸ");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
