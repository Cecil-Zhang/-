package LogicService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Control.patternControl.RivalConditionVSService;

/**
 * @ClassName RivalConditionVSHandler
 * @Description 负责在对战游戏中为一个队伍查询RivalConditionVSService，以及时向队员更新对手状态
 * @caller GameControl.initialVSGame()
 * @author 张舒贤
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
