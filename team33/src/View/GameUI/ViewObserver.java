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
 * @Description ��������ߣ�������Ϸ���ݵı��
 * @author ������
 * @Date 2014-5-14
 */
public interface ViewObserver extends Remote{

	/**
	 * @title update
	 * @Description ��patternModel�������ĵ�ʱ�򣬹�model����õĽӿ�
	 * @param ifSuperMode ����ģʽ״̬
	 * @param score ��Ϸ����
	 * @param tips �û��޲���ʱ��ʾ������
	 * @param clears �û���������������
	 * @param beginList ��Ϸ��ʼ����ͼ������
	 */
	public void update(boolean ifSuperMode,int score,boolean flag,
			ArrayList<MatricLocation> tips,ArrayList<MatricLocation> bang,ArrayList<MatricLocation> clears
			,ArrayList<MatricLocation> beginList) throws RemoteException;
	
	/**
	 * @title gameReady
	 * @Description ��Ϸ������׼���ã�֪ͨ��Ϸ��ʼ���뵹��ʱ
	 */
	public void gameReady() throws RemoteException;
	
	/**
	 * @title showGameResult
	 * @Description ��Ϸ���������û���ʾ��Ϸ�ɼ�
	 * @param d ��Ϸ��ʼʱ��
	 * @param s ��Ϸ����
	 * @param combo ��Ϸ������
	 * @param money ��Ϸ��Ǯ
	 * @throws RemoteException
	 */
	public void showGameResult(Date d,String s,int combo,int money) throws RemoteException;
	
	/**
	 * @title setRivaltoFriend
	 * @Description ����GamingPanel����RivalConditionVS,�����ڶ�ս����ӳ�ע�����
	 * @param rivalImg ����ͷ����
	 * @throws RemoteException
	 */
	public void setRivaltoFriend(int rivalImg) throws RemoteException;
	
}
