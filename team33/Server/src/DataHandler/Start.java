package DataHandler;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import po.AddressPO;
import po.FriendListPO;
import po.FriendPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;
import FriendLogic.FriendLogic;
import LogicService.FriendLogicService;
import LogicService.UserLogicService;
import UserLogic.UserLogic;

public class Start {
	public static void main(String[] args) {
		Start start = new Start();
		start.go();
	}

	public void go() {
		try {
			FriendLogicService friendLogicService=new FriendLogic();
			UserLogicService userLogicService = new UserLogic();
			LocateRegistry.createRegistry(6600);
			// ×¢²áÍ¨Ñ¶Â·¾¶
			Naming.rebind("rmi://localhost:6600/userLogicService",
					userLogicService);
			Naming.rebind("rmi://localhost:6600/friendLogicService",
					friendLogicService);
			
			System.out.println("Service Start!");
			// ///////////
			Helper helper = new Helper();
			ArrayList<UserPO> list = new ArrayList<UserPO>();
			list.add(new UserPO("100000", "AAA", "100000", true, 1, false,255));
			list.add(new UserPO("100001", "BBB", "100001", false, 1, false,6660));
			list.add(new UserPO("100002", "CCC", "100002", true, 1, false,78));
			list.add(new UserPO("100003", "DDD", "100003", false, 1, false,0));
			list.add(new UserPO("100004", "EEE", "100004", true, 1, false,0));
			list.add(new UserPO("100005", "FFF", "100005", true, 1, false,0));
			list.add(new UserPO("100006", "GGG", "100006", false, 1, false,0));
			list.add(new UserPO("100007", "HHH", "100007", !true, 1, false,0));
			list.add(new UserPO("100008", "III", "100008", true, 1, false,388));
			list.add(new UserPO("100009", "JJJ", "100009", !true, 1, false,0));
			list.add(new UserPO("100010", "KKK", "100010", true, 1, false,0));
			helper.outputSer("User.ser", list);
			ArrayList<SingleRecordPO> singleList = new ArrayList<SingleRecordPO>();
			singleList.add(new SingleRecordPO("100000", 16242, 4500, 10,157));
			singleList.add(new SingleRecordPO("100000", 16241, 5500, 12,145));
			singleList.add(new SingleRecordPO("100000", 16240, 6000, 8,126));
			singleList.add(new SingleRecordPO("100000", 16239, 3000, 6,126));
			singleList.add(new SingleRecordPO("100000", 16238, 4100, 4,155));
			singleList.add(new SingleRecordPO("100000", 16237, 1500, 2,100));
			singleList.add(new SingleRecordPO("100000", 16236, 4500, 11,144));
			singleList.add(new SingleRecordPO("100001", 16242, 4500, 80,36));
			singleList.add(new SingleRecordPO("100001", 16241, 6000, 130,45));
			singleList.add(new SingleRecordPO("100001", 16240, 7500, 140,156));
			singleList.add(new SingleRecordPO("100001", 16211, 3500, 80,111));
			singleList.add(new SingleRecordPO("100001", 16211, 4200, 110,122));
			singleList.add(new SingleRecordPO("100001", 16211, 4500, 105,111));
			singleList.add(new SingleRecordPO("100002", 16211, 4320, 100,120));
			singleList.add(new SingleRecordPO("100002", 16211, 4320, 107,112));
			singleList.add(new SingleRecordPO("100002", 16211, 4440, 110,98));
			singleList.add(new SingleRecordPO("100002", 16211, 4560, 105,451));
			singleList.add(new SingleRecordPO("100002", 16211, 2200, 40,111));
			singleList.add(new SingleRecordPO("100002", 16211, 5600, 130,111));
			singleList.add(new SingleRecordPO("100002", 16211, 4720, 120,155));
			singleList.add(new SingleRecordPO("100002", 16211, 3200, 80,235));
			singleList.add(new SingleRecordPO("100002", 16211, 1500, 50,111));
			singleList.add(new SingleRecordPO("100002", 16211, 5400, 110,264));
			helper.outputSer("SingleRecord.ser", singleList);

			//
			GameRecordPO g1 = new GameRecordPO("100000", 7, 4157, 6000, 120);
			GameRecordPO g2 = new GameRecordPO("100001", 6, 5033, 7500, 140);
			GameRecordPO g3 = new GameRecordPO("100002", 10, 4026, 5600, 130);
			ArrayList<GameRecordPO> recordList = new ArrayList<GameRecordPO>();
			recordList.add(g1);
			recordList.add(g2);
			recordList.add(g3);

			//
			ArrayList<FriendPO> tempList1 = new ArrayList<FriendPO>();
			tempList1.add(new FriendPO("100000", "100001", "BBB", "bbb",false,2));
			tempList1.add(new FriendPO("100000", "100002", "CCC", "ccc",true,3));
			
			FriendListPO l1 = new FriendListPO("100000", tempList1);
			ArrayList<FriendPO> tempList2 = new ArrayList<FriendPO>();
			tempList2.add(new FriendPO("100001", "100000", "AAA", "aaa",true,1));
			tempList2.add(new FriendPO("100001", "100002", "CCC", "ccc",true,3));
			tempList2.add(new FriendPO("100001", "100003", "DDD", "ddd",false,1));
			FriendListPO l2 = new FriendListPO("100001", tempList2);
			ArrayList<FriendPO> tempList3 = new ArrayList<FriendPO>();
			tempList3.add(new FriendPO("100002", "100001", "BBB", "bbb",false,2));
			tempList3.add(new FriendPO("100002", "100000", "AAA", "aaa",true,1));
			FriendListPO l3 = new FriendListPO("100002", tempList3);
			ArrayList<FriendPO> tempList4 = new ArrayList<FriendPO>();
			tempList4.add(new FriendPO("100003", "100001", "BBB", "bbb",false,2));
			FriendListPO l4 = new FriendListPO("100003", tempList4);
			ArrayList<FriendListPO> lll = new ArrayList<FriendListPO>();
			lll.add(l1);
			lll.add(l2);
			lll.add(l3);
			lll.add(l4);
			helper.outputSer("FriendList.ser", lll);
			////
			// AddressPO a=null;
			AddressPO a=new AddressPO("101111","122.122.122.122");
			 ArrayList<AddressPO> addressList=new ArrayList<AddressPO>();
			 addressList.add(a);
			 helper.outputSer("Address.ser",addressList );
			 helper.outputSer("GameRecord.ser", recordList);
//			 (Boolean t,String fId,String tId, boolean ifRead,boolean reply) {
			 MessagePO m=new MessagePO(true,"999999","999999",true,true,"hah");
			 ArrayList<MessagePO> messageList=new ArrayList<MessagePO>();
			 messageList.add(m);
			 helper.outputSer("Message.ser",messageList);
			// //////

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
