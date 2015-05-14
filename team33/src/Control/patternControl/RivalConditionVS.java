package Control.patternControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import javax.swing.Timer;

import LogicService.IPHelper;
import Model.patternModel.TestHelper;
import View.MessageFrame;
import View.GameUI.GamingPanel;
import View.GameUI.VSPanel;

/**
 * @ClassName RivalConditioninVS
 * @Description 在对战游戏中，负责维护对手的游戏状态，并绑定自己到RMI，供本队主机调用服务
 * @caller GamingPanel.setRivaltoFriend()
 * @author 张舒贤
 * @Date 2014-5-27
 */
public class RivalConditionVS extends UnicastRemoteObject implements RivalConditionVSService {

	private VSPanel vspanel;
	private boolean superMode;
	private int score;
	private int frozenType;
	
	public RivalConditionVS(VSPanel p) throws RemoteException{
		super();
		this.vspanel=p;
		try {
			Naming.rebind("rmi://"+IPHelper.getSelfIP()+":6600/RivalConditionVSService",this);
			TestHelper.testControl("rmi://"+IPHelper.getSelfIP()+":6600/RivalConditionVSService rebinded");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			TestHelper.testControl("rmi://"+IPHelper.getSelfIP()+":6600/RivalConditionVSService rebinded fail!!!");
			e.printStackTrace();
		}
		
	}	
	
	@Override
	public void changeScore(int score) throws RemoteException {
		// TODO Auto-generated method stub
		this.score=score;
		vspanel.updateScore(score);
	}
	@Override
	public void changeSupermode(boolean mode) throws RemoteException {
		// TODO Auto-generated method stub
		this.superMode=mode;
		vspanel.propC();
	}
	@Override
	public void frozenPattern(int patternType) throws RemoteException {
		// TODO Auto-generated method stub
		this.frozenType=patternType;
		vspanel.propB(patternType);
		
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vspanel.propBReleased(frozenType);
			}
			
		});
		t.start();
	}
	
	
}
