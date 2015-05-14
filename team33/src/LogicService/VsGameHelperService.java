package LogicService;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName VsGameHelperService
 * @Description VsGameService的先行者，当对手的VsGameService绑定之后，通知自己去查找该服务
 * @author 张舒贤
 * @Date 2014-5-23
 */
public interface VsGameHelperService extends Remote{ 
	
	public boolean getFlag() throws RemoteException;	
	
	public void lookupVsService() throws RemoteException;

}
