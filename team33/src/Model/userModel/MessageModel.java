package Model.userModel;

import java.util.ArrayList;

import LogicService.UserLogic;
import po.MessagePO;

public class MessageModel implements MessageModelService{
	UserLogic ul;
	public MessageModel(){
		ul = new UserLogic();
	}
	public ArrayList<MessagePO> getMessage(String id) {
		return ul.getMessage(id);
	}

	public void saveMessage(MessagePO message) {
		ul.saveMessage(message);
	}
	public void deleteMessage(MessagePO message) {
		ul.deleteMessage(message);
	}
	@Override
	public ArrayList<MessagePO> getInvitations(String id) {
		// TODO Auto-generated method stub
		return ul.getInvitations(id);
	}
	@Override
	public void deleteInvitations(String id) {
		ul.deleteInvitations(id);
		
	}
	@Override
	public void updateMessage(MessagePO mp) {
		ul.updateMessage(mp);
		
	}
	
}
