package Model.patternModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Model.patternModel.location.MatricLocation;

/**
 * @ClassName Observer
 * @Description 观察者接口
 * @author 张舒贤
 * @Date 2014-5-6
 */
public interface Observer extends Remote{
	/**
	 * @title update
	 * @Description 提供给被观察者当发生变更时调用的接口
	 */
	public void update(ArrayList<MatricLocation> list) throws RemoteException;

}
