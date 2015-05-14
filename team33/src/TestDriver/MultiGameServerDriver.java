package TestDriver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import View.MessageFrame;

import Control.gameControl.GameControlService;
import Control.gameControl.GameControl;

public class MultiGameServerDriver {

	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiGameServerDriver driver=new MultiGameServerDriver();
		driver.go();
	}
	
	public void go(){
		GameControlService control=new GameControl();
		try {
			LocateRegistry.createRegistry(6600);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			MessageFrame.show("RMI本地创建通讯簿失败!");
			e2.printStackTrace();
		}
		ArrayList<String> s=new ArrayList<String>();
		s.add("172.25.132.59");
//		s.add("localhost");
		control.initialFriendsNet("100000", s,false,false,false);
	}

}
