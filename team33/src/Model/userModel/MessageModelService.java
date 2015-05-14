package Model.userModel;

import java.util.ArrayList;

import po.MessagePO;

public interface MessageModelService {
	
	public ArrayList<MessagePO> getMessage(String id) ;
	public void saveMessage (MessagePO message);
	public void deleteMessage(MessagePO message) ;
	public ArrayList<MessagePO> getInvitations(String id) ;
	public void deleteInvitations(String id);
	public void updateMessage(MessagePO mp);
}
