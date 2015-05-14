package Control.patternControl;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName RivalConditionVSService
 * @Description �ṩRivalConditionVSService�ɹ�Զ�̵��õĽӿ�
 * @author ������
 * @Date 2014-6-14
 */
public interface RivalConditionVSService extends Remote{

	/**
	 * @title changeScore
	 * @Description �Է������ı䣬���½���
	 * @param s �Է�����
	 */	
	public void changeScore(int score)throws RemoteException;
	
	/**
	 * @title changeSupermode
	 * @Description �Է�����״̬�ı䣬չʾ��Ч
	 * @param mode �Է��ĳ���״̬����
	 */
	public void changeSupermode(boolean mode)throws RemoteException;
	
	public void frozenPattern(int patternType)throws RemoteException;
}
