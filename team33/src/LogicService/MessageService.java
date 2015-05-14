package LogicService;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageService extends Remote{
	   public void newMess()throws RemoteException;
	   public void show(String str)throws RemoteException;
	}
