package View.GameUI;

import java.awt.Point;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import Control.patternControl.RivalConditionVS;
import LogicService.IPHelper;
import Model.patternModel.Observer;
import Model.patternModel.TestHelper;
import Model.patternModel.location.MatricLocation;
import View.MessageFrame;

/**
 * @ClassName ViewObserverAdapter
 * @Description ����Ϸ����Ľӿ�ת��������GameLogicע������Ľӿ�
 * @author ������
 * @Date 2014-5-15
 */
public class ViewObserverAdapter implements Observer {
	private ViewObserver vobserver;
	private boolean superMode;
	private boolean flag;
	private int score;
	private ArrayList<MatricLocation> tips;
	private ArrayList<MatricLocation> clears;
	private ArrayList<MatricLocation> bang;
	private ArrayList<MatricLocation> beginList;
	
	public ViewObserverAdapter(ViewObserver vo){
		super();
		this.vobserver=vo;
	}
	
	@Override
	public void update(ArrayList<MatricLocation> list) throws RemoteException {
		vobserver.update(superMode, score, flag, tips, bang, clears,beginList);
	}
	
	/**
	 * @title setParameter
	 * @Description ֪ͨ������������ݵĸı�
	 * @param sm ����ģʽ��״̬
	 * @param s ��Ϸ�ķ���
	 * @param flag sm,s�Ƿ���Ч�ı�־
	 * @param t ��ʾ�������б�
	 * @param pump ����ʱ��ը�������б�
	 * @param c ����ʱ�ı�������б�
	 * @param �ı��list����ʼλ��
	 */
	public void setParameter(boolean sm,int s,boolean f,ArrayList<MatricLocation> t,
			ArrayList<MatricLocation> pump,ArrayList<MatricLocation> c,
							ArrayList<MatricLocation> beginList) throws RemoteException{
		superMode=sm;
		score=s;
		flag=f;
		tips=t;
		bang=pump;
		clears=c;
		this.beginList=beginList;
	}

	/**
	 * @title gameReady
	 * @Description ��ʼ������Ϸǰ����ʱ
	 */
	public void gameReady() {
		try {
			vobserver.gameReady();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("�����쳣�����Ժ�����");
			e.printStackTrace();
		}
	}

	public void showGameResult(Date d,String score,int combo,int money){
		try {
			vobserver.showGameResult(d, score, combo, money);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			MessageFrame.show("�����쳣�����Ժ�����");
			e.printStackTrace();
		}
	}

	public void setRivalCondtoFriend(int img) {
		// TODO Auto-generated method stub
		try {
			vobserver.setRivaltoFriend(img);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
