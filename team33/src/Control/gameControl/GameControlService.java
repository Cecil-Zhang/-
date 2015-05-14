package Control.gameControl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Control.patternControl.patternControl;
import Control.patternControl.patternControlService;
import Model.patternModel.GameLogic;
import Model.patternModel.Observer;
import View.GameUI.ViewObserver;

/**
 * @ClassName GameControlService
 * @Description GameController�ĳ���ӿ�
 * @author ������
 * @Date 2014-5-12
 */
public interface GameControlService {

	/**
	 * @title initialSoloGame
	 * @Description ��ʼ��������Ϸ
	 * @param propC �Ƿ�ʹ�õ���C
	 * @param propD �Ƿ�ʹ�õ���D
	 * @param propE �Ƿ�ʹ�õ���E
	 */
	public void initialSoloGame(String selfID,boolean propC,boolean propD,boolean propE);
	
	
	/**
	 * @title initialRivalandFriendsNet
	 * @Description ��ʼ����ս��Ϸ
	 * @param friends �������ѵ�ID�б�
	 * @param rivals ��ս���ѵ�ID�б�
	 * @param propC �Ƿ�ʹ�õ���C
	 * @param propD �Ƿ�ʹ�õ���D
	 * @param propE �Ƿ�ʹ�õ���E
	 * @return
	 */
	public void initialRivalsandFriendsNet(ArrayList<String> friends,ArrayList<String> rivals,
			boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialVSGame
	 * @Description ��Ϸ������֪ͨ�����������ҵ���initialRivalsandFriendsNet
	 * @param friends ����ID�б�
	 * @param rivals ����ID�б�
	 * @param propC ����C
	 * @param propD ����D
	 * @param propE ����E
	 */
	public void broadcastVSGame(String selfID,ArrayList<String> friends,ArrayList<String> rivals,
			boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialFriendsNet
	 * @Description ��ʼ��Э����Ϸ
	 * @param friends Э�����ѵ�ID
	 * @param propC �Ƿ�ʹ�õ���C
	 * @param propD �Ƿ�ʹ�õ���D
	 * @param propE �Ƿ�ʹ�õ���E
	 * @return
	 */
	public void initialFriendsNet(String selfID,ArrayList<String> friends,boolean propC,boolean propD,boolean propE);
	
	/**
	 * @title initialMultiGameAsParticipate
	 * @Description ��ȡ������patternControl���񣬲���ʼ����Ϸ����
	 * @param pcService patternControl
	 * @param list	��ʼ����ͼ������
	 * @param direction	���䷽��
	 * @return
	 * @throws RemoteException
	 */
	public ViewObserver initialMultiGameAsParticipate(patternControlService pcService,ArrayList<Integer> list,boolean direction) throws RemoteException; 
}
