package View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Model.patternModel.TestHelper;

public class MessageFrameRMI extends UnicastRemoteObject implements MessageFrameService{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4089347293497662658L;
	
	public MessageFrameRMI(String ipString) throws RemoteException {
		super();
		// ע��ͨѶ·��
		try{
    	Naming.rebind("rmi://"+ipString+":6600/userLogicService",this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void show(String s) throws RemoteException {
		MessageFrame mFrame = new MessageFrame(s);
	    Thread std = new SleepThread();
		std.start();
		try {
			std.join();
			TestHelper.testprint("��Ҫdispose��Ϣ����");
			mFrame.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	class SleepThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
     }
}
