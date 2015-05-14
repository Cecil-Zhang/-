package LogicService;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

import Model.patternModel.TestHelper;
import View.MessageFrame;

/**
 * @ClassName VsGameHelper
 * @Description
 * @author ������
 * @Date 2014-5-23
 */
public class VsGameHelper extends UnicastRemoteObject implements
		VsGameHelperService {

	ArrayList<String> rivals;         //���ֵ�IP��ַ
	ArrayList<VsGameHelperService> helperService;
	ArrayList<VsGameService> vsService;
	private static VsGameHelper instance;
	private boolean flag;            //��ʾ��������Ϸ�Ƿ��ѳ�ʼ����ɣ�VsGameService�Ƿ��Ѱ󶨵�RMI
	
	public static VsGameHelper getInstance(ArrayList<String> r) throws RemoteException{
		if(instance!=null){
			instance=new VsGameHelper(r);
		}
		return instance;
	}

	private VsGameHelper(ArrayList<String> r) throws RemoteException {
		super();
		this.rivals = r;
		this.helperService=new ArrayList<VsGameHelperService>();
		this.vsService=new ArrayList<VsGameService>();
		
		// ��ñ���IP��ַ
		String selfIP = null;
		try {
			InetAddress inet = InetAddress.getLocalHost();
			selfIP = inet.getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			MessageFrame.show("��ȡ������ַʧ��!");
			e1.printStackTrace();
		}
		
		//���Լ��󶨵�RMI�ķ�����
		try {
			Naming.rebind("rmi://"+selfIP+":6600/VsGameHelperService",this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setFlag(boolean f){
		this.flag=f;
		for(VsGameHelperService helper:this.helperService){
			try {
				helper.lookupVsService();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				MessageFrame.show("֪ͨ����VsGameService�����ʧ��!");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void lookupVsService() throws RemoteException{
		// TODO Auto-generated method stub
		Iterator<String> it=this.rivals.iterator();
		boolean temp=false;
		while(it.hasNext()){
			String ip=it.next();
			if(temp){
				it.remove();
				temp=false;
			}
			try{
				VsGameHelperService service=(VsGameHelperService) Naming.lookup("rmi://"+ip+":6600/VsGameHelperService");
				if(service.getFlag()){
					TestHelper.testprint(ip+" �û��Ѿ���!");
					VsGameService vs=(VsGameService) Naming.lookup("rmi://"+ip+":6600/VsGameService");
					this.vsService.add(vs);
					temp=true;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean getFlag() throws RemoteException {
		// TODO Auto-generated method stub
		return this.flag;
	}

}
