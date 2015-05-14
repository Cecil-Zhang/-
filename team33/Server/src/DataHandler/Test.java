package DataHandler;

import java.rmi.RemoteException;
import java.util.Date;

import UserLogic.UserLogic;
import FriendLogic.FriendLogic;

public class Test {
	public static void main(String  [] args) throws RemoteException{
		Test test=new Test();
		test.go();
	}
	public void go() throws RemoteException{
		
		Helper hp=new Helper();
	    UserLogic ul = new UserLogic();
		FriendDataHandler fd=new FriendDataHandler();
		//	System.out.println("friendListPOList.size():"+fd.getFriendList().size());
		for(int i=0;i<ul.getSingleRecords("100000").size();i++){
		System.out.println(ul.getSingleRecords("100000").get(i).getDate());
		}
		int x = (int) ( new Date().getTime()/(3600*1000*24));
		System.out.println(x);
	}
}
