package LogicService;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Start {
	
	public void go() {
		try {
			MessageService messageService=new MessageRMI();
    	//	LocateRegistry.createRegistry(6600);
			// ×¢²áÍ¨Ñ¶Â·¾¶
			Naming.rebind("rmi://localhost:6600/MessageService",
					 messageService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
