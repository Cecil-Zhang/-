package TestDriver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import View.MessageFrame;
import Control.gameControl.GameControlService;
import Control.gameControl.GameControl;
import LogicService.VsGameHelper;
import LogicService.VsGameHelperService;
import Model.patternModel.TestHelper;

/**
 * @ClassName VsGameDriver
 * @Description
 * @author ������
 * @Date 2014-5-23
 */
public class VsGameDriver {

	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		VsGameDriver driver=new VsGameDriver();
		driver.go();
	}

	public void go(){
		GameControlService control=new GameControl();
		try {
			LocateRegistry.createRegistry(6600);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			MessageFrame.show("RMI���ش���ͨѶ��ʧ��!");
			e2.printStackTrace();
		}
		
		ArrayList<String> rivalIPs=new ArrayList<String>();
		ArrayList<String> friendIPs=new ArrayList<String>();
		rivalIPs.add("192.168.155.2");
		TestHelper.testControl("����Ϊ: "+rivalIPs);
		TestHelper.testControl("ս��Ϊ: "+friendIPs);
		control.initialRivalsandFriendsNet(friendIPs, rivalIPs, false, false, false);
	}
}
