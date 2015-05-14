package DataHandler;

import java.util.ArrayList;

import po.FriendListPO;

/**
 * @ClassName FriendDataHandler
 * @Description �Ժ���������ݶ�ȡ��������ת���ȳ�������
 * @author �����
 * @Date ����9:39:06
 */
public class FriendDataHandler {
	Helper hp=new Helper();
	/**
	 * @title getFriendList
	 * @Description ����ȡ������ת��ΪFriendList��ʽ
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
	 * @Description �洢�����б�
	 * @param friendList
	 */
	public void updateFriendList(ArrayList<FriendListPO> friendList){
		hp.outputSer("FriendList.ser", friendList);
		System.out.println("In the FriendDataHandle:friendList.size():"+friendList.size());
	}
	
}
