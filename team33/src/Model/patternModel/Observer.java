package Model.patternModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Model.patternModel.location.MatricLocation;

/**
 * @ClassName Observer
 * @Description �۲��߽ӿ�
 * @author ������
 * @Date 2014-5-6
 */
public interface Observer extends Remote{
	/**
	 * @title update
	 * @Description �ṩ�����۲��ߵ��������ʱ���õĽӿ�
	 */
	public void update(ArrayList<MatricLocation> list) throws RemoteException;

}
