package LogicService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.FriendListPO;
import po.FriendPO;

public class FriendLogic {
	FriendLogicService fLogicService;
	public FriendLogic() {
		try{
			 fLogicService = (FriendLogicService)
					 Naming.lookup("rmi://114.212.42.175:6600/friendLogicService");				
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FriendListPO getFriendList(String id)  {
		System.out.println("FriendLogic"+id);
		FriendListPO list=new FriendListPO(id, new ArrayList<FriendPO>());
		try {
			list=fLogicService.getFriendList(id);
		    if(list==null){
		    	list = new FriendListPO(id, new ArrayList<FriendPO>());
		    }
			System.out.println("userlogic"+list.getFriendList().size());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return list;
	}

	public void updateFriendList(String id, ArrayList<FriendPO> list){
		try {
			fLogicService.updateFriendList(id, list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
