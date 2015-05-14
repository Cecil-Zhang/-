package LogicService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Control.patternControl.RivalConditionVSService;

/**
 * @ClassName RivalConditionVSHandler
 * @Description �����ڶ�ս��Ϸ��Ϊһ�������ѯRivalConditionVSService���Լ�ʱ���Ա���¶���״̬
 * @caller GameControl.initialVSGame()
 * @author ������
 * @Date 2014-6-16
 */
public class RivalConditionVSHandler {
	
	public ArrayList<RivalConditionVSService> lookupRivalCond(ArrayList<String> list) throws MalformedURLException, RemoteException, NotBoundException{
		ArrayList<RivalConditionVSService> service=new ArrayList<RivalConditionVSService>();
		RivalConditionVSService selfService=(RivalConditionVSService) Naming.lookup("rmi://"+IPHelper.getSelfIP()+":6600/RivalConditionVSService");
		service.add(selfService);
		if(list!=null){
			for(String ip:list){
				service.add((RivalConditionVSService)Naming.lookup("rmi://"+ip+":6600/RivalConditionVSService"));
			}
		}
		return service;
	}

}
