package LogicService;

import java.rmi.Remote;
import java.util.ArrayList;

import po.FriendListPO;
import po.FriendPO;

public interface FriendLogicService extends Remote {
	public FriendListPO getFriendList(String id)
			throws java.rmi.RemoteException;
	public void updateFriendList(String id, ArrayList<FriendPO> list)
			throws java.rmi.RemoteException;
}
