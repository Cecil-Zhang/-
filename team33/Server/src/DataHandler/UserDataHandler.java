package DataHandler;

import java.util.ArrayList;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

/**
 * @ClassName UserDataHandler
 * @Description ���û�������ݽ����������ת��
 * @author �����
 * @Date ����9:44:22
 */
public class UserDataHandler {
	Helper hp = new Helper();

	/**
	 * @title getId
	 * @Description �õ��������洢�����µ�ע���˺�
	 * @param fileName
	 * @return
	 */
	public String getId(String fileName) {
		String id = "";
		id = hp.inputTxt(fileName);
		return id;
	}

	/**
	 * @title updateId
	 * @Description �������µ�ע���˺�
	 * @param id
	 * @param fileName
	 */
	public void updateId(String id, String fileName) {
		hp.outputTxt(id, fileName);
	}

	/**
	 * @title getUserPO
	 * @Description �õ����е��û���Ϣ
	 * @return
	 */
	public ArrayList<UserPO> getUserPO() {
		ArrayList<Object> list = hp.inputSer("User.ser");
		ArrayList<UserPO> result = new ArrayList<UserPO>();
		for (int i = 0; i < list.size(); i++) {
			result.add((UserPO) (list.get(i)));
		}
		return result;
	}

	/**
	 * @title updateUser
	 * @Description �������е��û���Ϣ
	 * @param upList
	 */
	public void updateUser(ArrayList<UserPO> upList) {
		hp.outputSer("User.ser", upList);
	}

	/**
	 * @title getAllSingleRecord
	 * @Description �õ����еĵ��ֱ�����¼
	 * @return
	 */
	public ArrayList<SingleRecordPO> getAllSingleRecord() {
		ArrayList<Object> list = hp.inputSer("SingleRecord.ser");
		ArrayList<SingleRecordPO> result = new ArrayList<SingleRecordPO>();
		for (int i = 0; i < list.size(); i++) {
			result.add((SingleRecordPO) (list.get(i)));
		}
		return result;

	}

	/**
	 * @title saveSingleRecord
	 * @Description �洢���е�������
	 * @param recordList
	 */
	public void saveSingleRecord(ArrayList<SingleRecordPO> recordList) {
		hp.outputSer("SingleRecord.ser", recordList);
	}

	/**
	 * @title getGameRecord
	 * @Description �õ��û���������Ϸ��¼��Ϣ
	 * @return
	 */
	public ArrayList<GameRecordPO> getGameRecord() {
		ArrayList<Object> list = hp.inputSer("GameRecord.ser");
		ArrayList<GameRecordPO> result = new ArrayList<GameRecordPO>();
		for (int i = 0; i < list.size(); i++) {
			result.add((GameRecordPO) (list.get(i)));
		}
		return result;
	}

	/**
	 * @title getAddress
	 * @Description �õ�IP��ַ��Ϣ
	 * @return ArrayList<AddressPO>
	 */
	public ArrayList<AddressPO> getAddress() {
		// TODO Auto-generated method stub
		ArrayList<Object> list = hp.inputSer("Address.ser");
		
		ArrayList<AddressPO> result = new ArrayList<AddressPO>();
		for (int i = 0; i < list.size(); i++) {
			result.add((AddressPO) (list.get(i)));
			System.out.println(result.get(i).getAddress());
		}
		System.out.println("Address.size():"+result.size());
		return result;

	}

	/**
	 * @title saveAddress
	 * @Description �洢IP��ַ��Ϣ
	 * @param list
	 */
	public void saveAddress(ArrayList<AddressPO> list) {
		// TODO Auto-generated method stub
		hp.outputSer("Address.ser", list);
	}

	/**
	 * @title saveGameRecord
	 * @Description ����������Ϸ��¼
	 * @param recordList
	 */
	public void saveGameRecord(ArrayList<GameRecordPO> recordList) {
		// TODO Auto-generated method stub
		hp.outputSer("GameRecord.ser", recordList);

	}

	/**
	 * @title getMessage
	 * @Description �õ���Ϣ�б�
	 * @return ArrayList<MessagePO>
	 */
	public ArrayList<MessagePO> getMessage() {
		ArrayList<Object> list = hp.inputSer("Message.ser");
		ArrayList<MessagePO> result = new ArrayList<MessagePO>();
		for (int i = 0; i < list.size(); i++) {
			result.add((MessagePO) (list.get(i)));
		}
		return result;
	}

	/**
	 * @title saveMessage
	 * @Description �洢��Ϣ�б�
	 * @param messageList
	 */
	public void saveMessage(ArrayList<MessagePO> messageList) {
		// TODO Auto-generated method stub
		hp.outputSer("Message.ser", messageList);
	}
	
	
	/**
	 * @title deleteMessage
	 * @Description
	 * @param message
	 */
	/**
	 * @title deleteMessage
	 * @Description �����û�IDɾ�������ĳһ����Ϣ
	 * @param message
	 */
	public void deleteMessage(MessagePO message) {
		// TODO Auto-generated method stub
		ArrayList<MessagePO> messageList = getMessage();
		for (int i = 0; i < messageList.size(); i++) {
			MessagePO temp = messageList.get(i);
			
			if (((temp.isType() == message.isType())
					&& (temp.getFromId().equals(message.getFromId()))) && (temp
						.getToId().equals(message.getToId()))){
				messageList.remove(i);
				break;
			}
		}
		saveMessage(messageList);
	}

	/**
	 * @title updateMessage
	 * @Description �����û�ID����ĳһ����Ϣ
	 * @param message
	 */
	public void updateMessage(MessagePO message) {
		// TODO Auto-generated method stub
		ArrayList<MessagePO> messageList = getMessage();
		for (int i = 0; i < messageList.size(); i++) {
			MessagePO temp = messageList.get(i);
			
			if (((temp.isType() == message.isType())
					&& (temp.getFromId().equals(message.getFromId()))) && (temp
						.getToId().equals(message.getToId()))){
				messageList.remove(i);
				messageList.add(i,message);
				break;
			}
		}
		saveMessage(messageList);
	}
}
