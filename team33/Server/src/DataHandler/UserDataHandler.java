package DataHandler;

import java.util.ArrayList;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

/**
 * @ClassName UserDataHandler
 * @Description 对用户相关数据进行最基本的转换
 * @author 臧晓杰
 * @Date 下午9:44:22
 */
public class UserDataHandler {
	Helper hp = new Helper();

	/**
	 * @title getId
	 * @Description 得到服务器存储的最新的注册账号
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
	 * @Description 更新最新的注册账号
	 * @param id
	 * @param fileName
	 */
	public void updateId(String id, String fileName) {
		hp.outputTxt(id, fileName);
	}

	/**
	 * @title getUserPO
	 * @Description 得到所有的用户信息
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
	 * @Description 更新所有的用户信息
	 * @param upList
	 */
	public void updateUser(ArrayList<UserPO> upList) {
		hp.outputSer("User.ser", upList);
	}

	/**
	 * @title getAllSingleRecord
	 * @Description 得到所有的单局比赛记录
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
	 * @Description 存储所有单局数据
	 * @param recordList
	 */
	public void saveSingleRecord(ArrayList<SingleRecordPO> recordList) {
		hp.outputSer("SingleRecord.ser", recordList);
	}

	/**
	 * @title getGameRecord
	 * @Description 得到用户的总体游戏记录信息
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
	 * @Description 得到IP地址信息
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
	 * @Description 存储IP地址信息
	 * @param list
	 */
	public void saveAddress(ArrayList<AddressPO> list) {
		// TODO Auto-generated method stub
		hp.outputSer("Address.ser", list);
	}

	/**
	 * @title saveGameRecord
	 * @Description 保存总体游戏记录
	 * @param recordList
	 */
	public void saveGameRecord(ArrayList<GameRecordPO> recordList) {
		// TODO Auto-generated method stub
		hp.outputSer("GameRecord.ser", recordList);

	}

	/**
	 * @title getMessage
	 * @Description 得到信息列表
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
	 * @Description 存储信息列表
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
	 * @Description 根据用户ID删除具体的某一条信息
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
	 * @Description 根据用户ID更新某一条信息
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
