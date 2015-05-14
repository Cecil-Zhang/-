package UserLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;
import DataHandler.UserDataHandler;
import LogicService.UserLogicService;

/**
 * @ClassName UserLogic
 * @Description 用户管理模块的信息逻辑处理
 * @author 臧晓杰
 * @Date 上午12:32:04
 */
public class UserLogic extends UnicastRemoteObject implements UserLogicService {
	public UserLogic() throws RemoteException {

		super();

	}

	UserDataHandler dh = new UserDataHandler();

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getId()
	 * @description 得到可注册的用户ID
	 */
	public String getId() {
		System.out.println("You come!");
		String id = "";
		id = dh.getId("UserId.txt");
		return id;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateId(java.lang.String)
	 * @description 更新最新的可注册的用户ID
	 */
	public void updateId(String id) {
		dh.updateId(id, "UserId.txt");
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getUser(java.lang.String)
	 * @description 根据用户ID得到用户信息
	 */
	public UserPO getUser(String id) {
		ArrayList<UserPO> userList = dh.getUserPO();
		
		for (int i = 0; i < userList.size(); i++) {
			if (id.equals(userList.get(i).getUserId())) {
				System.out.println("I going to return"
						+ userList.get(i).getUserName());
				return userList.get(i);

			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateUser(po.UserPO)
	 * @description 更新用户信息
	 */
	public void updateUser(UserPO up) {
		boolean isExist = false;
		ArrayList<UserPO> userList = dh.getUserPO();
		for (int i = 0; i < userList.size(); i++) {
			if (up.getUserId().equals(userList.get(i).getUserId())) {
				userList.remove(i);
				userList.add(i, up);
				isExist = true;
				break;
			}
		}
		if (!isExist) {
			userList.add(up);
		}
		dh.updateUser(userList);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getRecord(java.lang.String)
	 * @description 根据用户ID得到总体游戏信息
	 */
	public GameRecordPO getRecord(String id) {
		ArrayList<GameRecordPO> list = dh.getGameRecord();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserID().equals(id)) {
				System.out.println("In the UserLogic.getRecord return "+id+"'s record");
				return list.get(i);
			}
		}
		return (new GameRecordPO(id,0,0,0,0));
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getMessage(java.lang.String)
	 * @description 根据用户ID得到该用户接收到的信息
	 */
	public ArrayList<MessagePO> getMessage(String id) {
		ArrayList<MessagePO> result = new ArrayList<MessagePO>();
		ArrayList<MessagePO> messageList = dh.getMessage();
		for (int i = 0; i < messageList.size(); i++) {
			if (id.equals(messageList.get(i).getToId())) {
				result.add(messageList.get(i));
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#saveMessage(po.MessagePO)
	 * @description 存储信息
	 */
	public void saveMessage(MessagePO message) {
		ArrayList<MessagePO> messageList = dh.getMessage();
		messageList.add(message);
		dh.saveMessage(messageList);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#deleteMessage(po.MessagePO)
	 * @description 删除读过的信息
	 */
	public void deleteMessage(MessagePO message) {
		dh.deleteMessage(message);
	}
	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateMessage(po.MessagePO)
	 * @description 更新信息是否被读取的状态
	 */
	public void updateMessage(MessagePO mp){
		dh.updateMessage(mp);
	}
	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#saveAddress(po.AddressPO)
	 * @description 保存用户的IP地址信息
	 */
	public void saveAddress(AddressPO address) {
		System.out.println("UserLogic" + address.getAddress());
		ArrayList<AddressPO> list = dh.getAddress();
		list.add(address);
		dh.saveAddress(list);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#deleteAddress(po.AddressPO)
	 * @description 删除IP地址信息
	 */
	public void deleteAddress(AddressPO address) {
		ArrayList<AddressPO> list = dh.getAddress();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(address)) {
				list.remove(i);
				break;
			}
		}
		dh.saveAddress(list);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getAddress(java.lang.String)
	 * @description 根据用户ID得到其IP地址
	 */
	public AddressPO getAddress(String id) {
		ArrayList<AddressPO> addressList = dh.getAddress();
		for (int i = 0; i < addressList.size(); i++) {
			if (id.equals(addressList.get(i).getId())) {
				return addressList.get(i);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getSingleRecords(java.lang.String)
	 * @description 根据用户id得到其所有局的游戏记录
	 */
	public ArrayList<SingleRecordPO> getSingleRecords(String id) {
		ArrayList<SingleRecordPO> recordList = new ArrayList<SingleRecordPO>();
		recordList = dh.getAllSingleRecord();
		ArrayList<SingleRecordPO> singleList = new ArrayList<SingleRecordPO>();
		for (int i = 0; i < recordList.size(); i++) {
			if (recordList.get(i).getUserID().equals(id)) {
				singleList.add(recordList.get(i));
			}
		}
		return singleList;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#saveSingleRecord(po.SingleRecordPO)
	 * @description 存储一局游戏记录
	 */
	public void saveSingleRecord(SingleRecordPO srp) {
		// TODO Auto-generated method stub

		ArrayList<SingleRecordPO> recordList = new ArrayList<SingleRecordPO>();
		recordList = dh.getAllSingleRecord();
		recordList.add(srp);
		dh.saveSingleRecord(recordList);

	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateRecord(po.GameRecordPO)
	 * @description 更新用户的整体游戏记录
	 */
	public void updateRecord(GameRecordPO grp) {
		boolean isExist = false;
		ArrayList<GameRecordPO> recordList = dh.getGameRecord();
		for (int i = 0; i < recordList.size(); i++) {
			if (recordList.get(i).getUserID().equals(grp.getUserID())) {
				recordList.remove(i);
				recordList.add(i, grp);
				isExist = true;
				break;
			}
		}
		if (!isExist) {
			
			recordList.add(grp);
		}
		dh.saveGameRecord(recordList);

	}

	

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getInvitations(java.lang.String)
	 * @description 根据用户Id得到其游戏邀请的信息
	 */
	@Override
	public ArrayList<MessagePO> getInvitations(String id)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<MessagePO> result = new ArrayList<MessagePO>();
		ArrayList<MessagePO> messageList = dh.getMessage();
		for (int i = 0; i < messageList.size(); i++) {
			if ((id.equals(messageList.get(i).getFromId()))
					&& (messageList.get(i).isType() == true)) {
				result.add(messageList.get(i));
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#deleteInvitations(java.lang.String)
	 * @description 删除游戏邀请信息
	 */
	@Override
	public void deleteInvitations(String id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<MessagePO> messageList = dh.getMessage();
		for (int i = 0; i < messageList.size(); i++) {
			if ((id.equals(messageList.get(i).getFromId()))
					&& (messageList.get(i).isType() == true)) {
				messageList.remove(i);
			}
		}
		dh.saveMessage(messageList);
	}

}
