package LogicService;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Date;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

public interface UserLogicService extends Remote {
	public String getId() throws java.rmi.RemoteException;

	public void updateId(String id) throws java.rmi.RemoteException;

	public UserPO getUser(String id) throws java.rmi.RemoteException;

	public void updateUser(UserPO up) throws java.rmi.RemoteException;

	public GameRecordPO getRecord(String id) throws java.rmi.RemoteException;
	public ArrayList<MessagePO> getMessage(String id) throws java.rmi.RemoteException;
	public void saveMessage (MessagePO message) throws java.rmi.RemoteException;
	public void deleteMessage(MessagePO message) throws java.rmi.RemoteException;
	public void saveAddress(AddressPO address) throws java.rmi.RemoteException;
	public void deleteAddress(AddressPO address) throws java.rmi.RemoteException;
	public AddressPO getAddress(String id) throws java.rmi.RemoteException;
	public void saveSingleRecord(SingleRecordPO srp)throws java.rmi.RemoteException;
	public ArrayList<SingleRecordPO> getSingleRecords (String id)throws java.rmi.RemoteException;
	public void updateRecord(GameRecordPO grp)throws java.rmi.RemoteException;
	public ArrayList<MessagePO> getInvitations(String id) throws java.rmi.RemoteException;
	public void deleteInvitations(String id) throws java.rmi.RemoteException;
    public void updateMessage(MessagePO mp)throws java.rmi.RemoteException;
}
