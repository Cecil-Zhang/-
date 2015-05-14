package View;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageFrameService extends Remote{
	public void show(String s) throws RemoteException;
}
