package LogicService;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName VsGameService
 * @Description
 * @author ������
 * @Date 2014-5-22
 */
public interface VsGameService extends Remote{

	/**
	 * @title FrozenPattern
	 * @Description ����һ������Bʱ��������ֵ�һ��ͼ��2s
	 * @throws RemoteException
	 */
	public void FrozenPattern() throws RemoteException;
	
	/**
	 * @title WeakenPropC
	 * @Description �γɳ���ģʽʱ���������ֵĵ���CЧ��5s
	 * @throws RemoteException
	 */
	public void WeakenPropC() throws RemoteException;
	
	/**
	 * @title updateScore
	 * @Description �����������ı�ʱ��֪ͨ���ֽ������
	 * @param score ������Ϸ��ǰ����
	 * @throws RemoteException
	 */
	public void updateScore(int score) throws RemoteException;
	
	/**
	 * @title startGame
	 * @Description ʹ��ս��Ϸ�Ĳ�����һͬ��ʼ��Ϸ
	 * @throws RemoteException
	 */
	public void startGameWithRivals() throws RemoteException;
}
