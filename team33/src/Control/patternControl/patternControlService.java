package Control.patternControl;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Model.patternModel.location.MatricLocation;

/**
 * @ClassName patternControlService
 * @Description Ϊ����patterView�����ھ����patternControl����������Ľӿ�
 * @author ������
 * @Date 2014-5-9
 */
public interface patternControlService extends Remote{
	
	/**
	 * @title mouseClick
	 * @Description ������굥������߼�����
	 * @param point ��굥��������
	 */
	public boolean mouseClick(MatricLocation point) throws RemoteException;

	/**
	 * @title mouseDrag
	 * @Description ��������϶�����߼�����
	 * @param before ����϶�����ʼ����
	 * @param after ����϶��Ľ�������
	 */
	public boolean mouseDrag(MatricLocation before,MatricLocation after) throws RemoteException;
	
	/**
	 * @title startGame
	 * @Description ��ʼ��Ϸ������Ϸ״̬�м�����Ϸ��ʼ��ʱ��
	 */
	public void startGame() throws RemoteException;
	
	/**
	 * @title startGameWithRivals
	 * @Description ֪ͨ��ս�ĺ�����Ϸ��ʼ
	 * @throws RemoteException
	 */
	public void startGameWithRivals() throws RemoteException;
	
	/**
	 * @title saveGameRecored
	 * @Description ������Ϸ�����ݣ�����ʱ�䡢�÷֡����������
	 */
	public void saveGameRecored(String userID) throws RemoteException;
	
	/**
	 * @title haltGame
	 * @Description ��ͣ��Ϸ
	 */
	public void haltGame() throws RemoteException;
	
	/**
	 * @title restartGame
	 * @Description ���¿�ʼ��Ϸ
	 */
	public void restartGame() throws RemoteException;
}
