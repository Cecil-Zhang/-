package TestDriver;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import LogicService.MultiGameClient;
import LogicService.MultiGameClientService;

public class MultiGameClientDriver {

	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MultiGameClientDriver driver = new MultiGameClientDriver();
		driver.go();
	}

	public void go() {
		try {
			LocateRegistry.createRegistry(6600);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// 获得服务器IP地址
		String serverIP = "localhost";
		
		try {
			MultiGameClientService client=new MultiGameClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
