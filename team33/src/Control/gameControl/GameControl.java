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
 * @Description 负责游戏开始时的初始化和游戏结束时的数据存储工作
 * @author 张舒贤
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
	 * @Description 初始化一局游戏的数据和状态
	 * @param gameLogic
	 * @return patternControl服务
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
	 * @Description 根据邀请的好友ID获取好友的IP地址
	 * @param friends 协作好友的ID列表
	 */
	public void initialFriendsNet(String selfID,
			ArrayList<String> friends, boolean propC, boolean propD,
			boolean propE) {
		if (friends != null && friends.size() != 0) {
			boolean result = this.InvitePlayer(selfID, "协作", friends);
			if (result) {
				TestHelper.testControl(friends + " friends");
				ArrayList<String> ips = this.getPlayerIP(friends);
				TestHelper.testControl(ips + " ips");
				this.initialMultiGameAsSponsor(ips, propC, propD, propE,true);
				this.consumeMoney(selfID, propC, propD, propE);
				
			} else {
				MessageFrame.show("对方拒绝接受游戏邀请");
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

		// 获取队友的IP地址
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
	 * @Description 注册patternControl服务，并向游戏注册各客户端界面的监听
	 * @param friends  同意游戏邀请的好友IP列表
	 * @param propC 是否使用道具C
	 * @param propD 是否使用道具D
	 * @param propE 是否使用道具E
	 * @return
	 */
	private patternControl initialMultiGameAsSponsor(ArrayList<String> friends,
			boolean propC, boolean propD, boolean propE, boolean ifSponsor) {
		// 获得本机IP地址
		String selfIP = IPHelper.getSelfIP();

		patternControl pcService = null;
		try {
			// 注册patternControl服务，以供其他客户端调用
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

			// 将初始矩阵传给每一个客户端,并向GameLogic,GameState注册每个客户端界面的监听
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
			MessageFrame.show("RMI远程调用失败!");
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
			MessageFrame.show("协作游戏参与者生成监听者失败!");
			e.printStackTrace();
		}
		return adapter;
	}

	/**
	 * @title initialVsGame
	 * @Description 初始化对战相关的数据，包括每个队员中显示对手的头像，以及RivalConditionVS的初始化
	 * @param pControl patternControl
	 * @param friends 队友IP地址列表
	 * @param rivals 对手ID列表
	 * @param propC 道具C
	 * @param propD 道具D
	 * @param propE 道具E
	 */
	private void initialVsGame(patternControl pControl,
			ArrayList<String> friends, ArrayList<String> rivals, boolean propC,
			boolean propD, boolean propE) {
		// TODO Auto-generated method stub
		UserModelService um = new UserModel();
		UserPO po = um.findUser(rivals.get(0));
		int img = po.getImage();

		// 通知本机和对手绑定RivalConditionVSService服务到RMI
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
	 * @Description 在对战游戏中绑定自己和查询队友的VSGameService，用于当本队状态发生改变时通知对手
	 * @param pControl patternControl
	 * @param rivals 对手ID列表
	 */
	private void bindandLookuoVSGameService(patternControl pControl,ArrayList<String> rivals) {
		// 获得本机IP地址
		String selfIP = IPHelper.getSelfIP();

		try {
			// 注册VsGame服务，以供对手客户端调用
			Naming.rebind("rmi://" + selfIP + ":6600/VsGameService", pControl);
			TestHelper.testControl(selfIP
					+ " MultiVSGame server VsGameService rebinded");
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			MessageFrame.show("RMI绑定 " + selfIP + " VsGameService失败!");
			e2.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取对手IP地址

		ArrayList<String> RivalIPs = this.getPlayerIP(rivals);

		// 等待对手VsGameService绑定完成并获取其服务
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
					MessageFrame.show(ip + "连接超时，游戏已关闭!");
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
			TestHelper.testControl(ip + " 已就绪!");

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
			// 邀请对手
			TestHelper.testControl(rivals + "rivals!!!");
			ArrayList<String> temp=new ArrayList<String>();
			temp.addAll(rivals);
			temp.addAll(friends);
			boolean rivalResult = this.InvitePlayer(ID, "对战", temp);
			boolean friendResult = true;
//			boolean rivalResult = this.InvitePlayer(ID, "对战", rivals);
//			boolean friendResult = true;
//			// 邀请队友
//			if (friends != null && friends.size() != 0) {
//				friendResult = this.InvitePlayer(ID, "协作对战", friends);
//			}

			// 如果双方均同意邀请
			if (rivalResult && friendResult) {
				UserLogic ums = new UserLogic();
				String rivalID = rivals.get(0);
				String rivalIP = null;
				AddressPO po = ums.getAddress(rivalID);
				rivalIP = po.getAddress();

				// 以对手的角度生成新的队友和对手ID列表
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
				TestHelper.testControl("先初始化自己的游戏");
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
		// 发送游戏邀请
		MessageFrame.show("等待好友接受邀请");
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
		 * @param ip 对方队长IP地址
		 * @param r 对方对手ID列表
		 * @param f 对方队友ID列表
		 * @param c 道具C
		 * @param d 道具D
		 * @param e 道具E
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
				TestHelper.testControl("已通知对手开始游戏");
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
