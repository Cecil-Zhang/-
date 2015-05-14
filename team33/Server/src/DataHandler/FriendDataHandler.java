package DataHandler;

import java.util.ArrayList;

import po.FriendListPO;

/**
 * @ClassName FriendDataHandler
 * @Description 对好友相关数据读取后做类型转换等初步处理
 * @author 臧晓杰
 * @Date 下午9:39:06
 */
public class FriendDataHandler {
	Helper hp=new Helper();
	/**
	 * @title getFriendList
	 * @Description 将读取的数据转化为FriendList格式
	 * @return
	 */
	public ArrayList<FriendListPO> getFriendList(){
		ArrayList<Object> list=hp.inputSer("FriendList.ser");
		ArrayList<FriendListPO> result=new ArrayList<FriendListPO>();
		for(int i=0;i<list.size();i++){
			result.add((FriendListPO)(list.get(i)));
		}
		return result;
	}
	/**
	 * @title updateFriendList
	 * @Description 存储好友列表
	 * @param friendList
	 */
	public void updateFriendList(ArrayList<FriendListPO> friendList){
		hp.outputSer("FriendList.ser", friendList);
		System.out.println("In the FriendDataHandle:friendList.size():"+friendList.size());
	}
	
}
