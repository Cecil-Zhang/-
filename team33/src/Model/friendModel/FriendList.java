package Model.friendModel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.FriendListPO;
import po.FriendPO;
import LogicService.FriendLogic;
import LogicService.FriendLogicService;

public class FriendList {

	FriendLogic fl;
	public FriendList(){
		fl = new FriendLogic();
	}
	public ArrayList<FriendPO> findList(String id){		
		
		FriendListPO list=fl.getFriendList(id);		
//	  	System.out.println("friendList"+list.getFriendList().size());
		if(list.equals(null)){
			return null;
		}
		return list.getFriendList();
	}
	
	public  void update(String id,ArrayList<FriendPO> friendList) {
		// TODO Auto-generated method stub
	   fl.updateFriendList(id, friendList);
		
	}
	
}
