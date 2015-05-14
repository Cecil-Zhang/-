package LogicService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.AddressPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;

public class UserLogic {
	UserLogicService uLogicService ;
	public UserLogic(){
		
			try {
				uLogicService = (UserLogicService) Naming
						.lookup("rmi://114.212.42.175"
								+ ":6600/userLogicService");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public String getId(){
		String id = "";
		try {
			id = uLogicService.getId();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public void updateId(String id)  {
        try {
			uLogicService.updateId(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserPO getUser(String id) {
		UserPO up = null;
		try {
			up = uLogicService.getUser(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return up;
	}

	public void updateUser(UserPO up) {
		try {
			uLogicService.updateUser(up);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public GameRecordPO getRecord(String id){
		GameRecordPO grp = null;
		try {
			grp = uLogicService.getRecord(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grp;
	}

	public ArrayList<MessagePO> getMessage(String id)  {
		ArrayList<MessagePO> mp = new ArrayList<MessagePO>();
		try {
			mp = uLogicService.getMessage(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}

	public void saveMessage(MessagePO message) {
		try {
			uLogicService.saveMessage(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void deleteMessage(MessagePO mp) {
		try {
			uLogicService.deleteMessage(mp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void saveAddress(AddressPO address) {
		try {
			uLogicService.saveAddress(address);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAddress(AddressPO address){
		try {
			uLogicService.deleteAddress(address);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public AddressPO getAddress(String id) {
		
		try {
			return uLogicService.getAddress(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveSingleRecord(SingleRecordPO srp){
		try {
			uLogicService.saveSingleRecord(srp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<SingleRecordPO> getSingleRecords(String id) {
		try {
			return uLogicService.getSingleRecords(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void updateRecord(GameRecordPO grp) {
		try {
			uLogicService.updateRecord(grp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ArrayList<MessagePO> getInvitations(String id) {
		try {
			return uLogicService.getInvitations(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void deleteInvitations(String id) {
		try {
			uLogicService.deleteInvitations(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateMessage(MessagePO mp){
		try {
			uLogicService.updateMessage(mp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
