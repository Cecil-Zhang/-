package FriendLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.FriendListPO;
import po.FriendPO;
import DataHandler.FriendDataHandler;
import LogicService.FriendLogicService;

/**
 * @ClassName FriendLogic
 * @Description 好友管理模块的读取数据逻辑处理
 * @author 臧晓杰
 * @Date 上午12:28:48
 */
public class FriendLogic extends UnicastRemoteObject implements FriendLogicService {
	public FriendLogic() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	FriendDataHandler dh=new FriendDataHandler();
	
	/* (non-Javadoc)
	 * @see LogicService.FriendLogicService#getFriendList(java.lang.String)
	 * @description 获取好友列表
	 */
	public FriendListPO getFriendList(String id){
		ArrayList<FriendListPO> friendList=dh.getFriendList();
		for(int i=0;i<friendList.size();i++){
			if(friendList.get(i).getId().equals(id)){
				System.out.println(friendList.get(i).getFriendList().size());
				return friendList.get(i);
			}
		}
		System.out.println("In the FriendLogic.get："+id+"'s friendList");
		return null;
	}
	/* (non-Javadoc)
	 * @see LogicService.FriendLogicService#updateFriendList(java.lang.String, java.util.ArrayList)
	 * @description 更新好友列表信息
	 */
	public void updateFriendList(String id,ArrayList<FriendPO> list){
		System.out.println("In the FriendLogic.update:"+id+"'s friendList");
		ArrayList<FriendListPO> friendList=dh.getFriendList();
		boolean isExist=false;
		for(int i=0;i<friendList.size();i++){
			if(friendList.get(i).getId().equals(id)){
				friendList.get(i).setFriendList(list);
				isExist=true;
				break;
			}
			
		}
		if(!isExist){
			System.out.println("FriendPOList not contains"+id);
			System.out.println("Before "+id+"'s friendlist.size():"+list.size());
			System.out.println("Before FriendPOList.size():"+friendList.size());
			friendList.add(new FriendListPO(id,list));
			System.out.println("After FriendPOList.size():"+friendList.size());
		}
		dh.updateFriendList(friendList);
	}
}
