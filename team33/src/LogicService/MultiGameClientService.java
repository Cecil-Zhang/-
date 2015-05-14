package LogicService;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Model.patternModel.GameState;
import Model.patternModel.GameLogic;
import Model.patternModel.location.MatricLocation;
import View.GameUI.ViewObserverAdapter;

public interface MultiGameClientService extends Remote{
	
	/**
	 * @title setServerIP
	 * @Description ����Э����Ϸ�����ߵ�IP��ַ
	 * @param ip
	 * @throws RemoteException
	 */
	public void setServerIP(String ip)throws RemoteException;
	
	/**
	 * @title passInitialMatrix
	 * @Description ��Э����Ϸ�У�����������Ϸ��ʼ����ʱ���ã�������ȡpatternControl����ע�᱾����ViewObserver����
	 * @param list
	 * @throws RemoteException
	 */
	public void passInitialMatrix(ArrayList<Integer> list,boolean direcition) throws RemoteException;
	
	/**
	 * @title startVSGame
	 * @Description �ڶ�ս��Ϸ�У�����Ϸ�����ߵ��õĽӿڣ�������Ϊ���ӵķ���������ʼ��һ����Ϸ
	 * @param friends ����ID�б�
	 * @param rivals ����ID�б�
	 * @param propC ����C
	 * @param propD ����D
	 * @param propE ����E
	 * @throws RemoteException
	 */
	public void startVSGame(ArrayList<String> friends,
			ArrayList<String> rivals, boolean propC, boolean propD,
			boolean propE) throws RemoteException;
}
