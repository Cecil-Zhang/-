package LogicService;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Model.patternModel.TestHelper;
import View.MainFrame;
import View.MessageFrame;
import View.MainUI.MainPanel;

public class MessageRMI extends UnicastRemoteObject implements MessageService{

	public MessageRMI() throws RemoteException {
		super();
		InetAddress inet = null;
		String ipString = "";
		try {
			inet = InetAddress.getLocalHost();
			ipString = inet.getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		try {
			MultiGameClientService clientService=new MultiGameClient();
			Naming.rebind("rmi://"+ipString+":6600/MessageService",this);		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void newMess() throws RemoteException {
        MainFrame.getInstance().getMp().newMess();
	}
    public void show(String str)throws RemoteException {
    	MessageFrame.show(str);
    }
    
}
