package LogicService;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName VsGameHelperService
 * @Description VsGameService�������ߣ������ֵ�VsGameService��֮��֪ͨ�Լ�ȥ���Ҹ÷���
 * @author ������
 * @Date 2014-5-23
 */
public interface VsGameHelperService extends Remote{ 
	
	public boolean getFlag() throws RemoteException;	
	
	public void lookupVsService() throws RemoteException;

}
