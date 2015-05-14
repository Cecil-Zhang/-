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
 * @Description �û�����ģ�����Ϣ�߼�����
 * @author �����
 * @Date ����12:32:04
 */
public class UserLogic extends UnicastRemoteObject implements UserLogicService {
	public UserLogic() throws RemoteException {

		super();

	}

	UserDataHandler dh = new UserDataHandler();

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getId()
	 * @description �õ���ע����û�ID
	 */
	public String getId() {
		System.out.println("You come!");
		String id = "";
		id = dh.getId("UserId.txt");
		return id;
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateId(java.lang.String)
	 * @description �������µĿ�ע����û�ID
	 */
	public void updateId(String id) {
		dh.updateId(id, "UserId.txt");
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#getUser(java.lang.String)
	 * @description �����û�ID�õ��û���Ϣ
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
	 * @description �����û���Ϣ
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
	 * @description �����û�ID�õ�������Ϸ��Ϣ
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
	 * @description �����û�ID�õ����û����յ�����Ϣ
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
	 * @description �洢��Ϣ
	 */
	public void saveMessage(MessagePO message) {
		ArrayList<MessagePO> messageList = dh.getMessage();
		messageList.add(message);
		dh.saveMessage(messageList);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#deleteMessage(po.MessagePO)
	 * @description ɾ����������Ϣ
	 */
	public void deleteMessage(MessagePO message) {
		dh.deleteMessage(message);
	}
	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#updateMessage(po.MessagePO)
	 * @description ������Ϣ�Ƿ񱻶�ȡ��״̬
	 */
	public void updateMessage(MessagePO mp){
		dh.updateMessage(mp);
	}
	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#saveAddress(po.AddressPO)
	 * @description �����û���IP��ַ��Ϣ
	 */
	public void saveAddress(AddressPO address) {
		System.out.println("UserLogic" + address.getAddress());
		ArrayList<AddressPO> list = dh.getAddress();
		list.add(address);
		dh.saveAddress(list);
	}

	/* (non-Javadoc)
	 * @see LogicService.UserLogicService#deleteAddress(po.AddressPO)
	 * @description ɾ��IP��ַ��Ϣ
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
	 * @description �����û�ID�õ���IP��ַ
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
	 * @description �����û�id�õ������оֵ���Ϸ��¼
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
	 * @description �洢һ����Ϸ��¼
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
	 * @description �����û���������Ϸ��¼
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
	 * @description �����û�Id�õ�����Ϸ�������Ϣ
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
	 * @description ɾ����Ϸ������Ϣ
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
