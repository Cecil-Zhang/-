package Model.patternModel;

import Control.settingControl.SettingControl;
import LogicService.VsGameService;
import Model.SettingModel.SettingModel;
import Model.patternModel.*;
import Model.patternModel.dataContainer.DataMatricReadHelper;
import Model.patternModel.dataContainer.DataMatricwriteHelper;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.location.MatricValueLocation;
import Model.patternModel.paddingStragety.PaddingStragety;
import Model.patternModel.paddingStragety.RightPaddingStagety;
import Model.patternModel.paddingStragety.downPaddingStragety;
import Model.patternModel.propsStragety.PropsStragety;
import Model.patternModel.propsStragety.TwoPropsStragety;
import Model.patternModel.tipStragety.TipStragety;
import Model.patternModel.tipStragety.TwoTipStragety;
import View.MessageFrame;
import View.GameUI.ViewObserver;
import View.GameUI.ViewObserverAdapter;
import po.PatternType;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;

import junit.framework.Test;

/**
 * Modleģ����ϴ�(�����ֲ�Ӧ���ǣ�������һ�������ģ�Ҳ�Ǹ��⽻�١�������Controlģ����н��� �ƹ������Ĳ��� Created by randy
 * on 14-5-9.
 */
public class GameLogic implements Model.patternModel.Observable {
	private DataMatricReadHelper dataMatricReadHelper;
	private DataMatricwriteHelper dataMatricwriteHelper;
	private boolean isdown = true;// ����ķ���
	private ArrayList<MatricLocation> updateList;// TODO:��ӽ�����ģʽ��������
	private ArrayList<VsGameService> vsService;
    private boolean isFrozen =false;
    private ViewType frozenType;//���ڶ�սʱ����Է���ð������

	public GameLogic() {
		dataMatricReadHelper = new DataMatricReadHelper();
		dataMatricwriteHelper = new DataMatricwriteHelper();
		dataMatricInit();
		choosePaddingStragety();
		setfallway();  //�������䷽��
	}
	/**
	 * �������䷽���Թ�ѡ�����
	 */
	private void setfallway() {
		SettingModel controller=new SettingModel();
		isdown=controller.getWay();
		
	}
	
	/**
	 * @title addRivals
	 * @Description ��Ӷ���
	 * @param vs VsGameService
	 */
	public void addRivals(ArrayList<VsGameService> vs){

		this.vsService=vs;
	}
	/*
	 *-----------------------------------------------------------------
	 */
    /**
     * �жϵ�ǰ�ƶ��Ŀ��Ƿ��Ƕ����ͼƬ
     * @param location
     * @return
     */
    private boolean isFrozenType(MatricLocation location) {
        if(location.getValue()==frozenType.ordinal()+1) {

            return true;
        }else {

            return false;
        }
    }
    /**
     * �����Լ��Ķ����pattern����
     * @param type
     */
    private void setFrozenType(ViewType type) {
    	TestHelper.propsTest("the set Frozen type is"+type);
        frozenType=type;
    }
    /**
     * �����Ƿ񶳽������
     * @param isFrozen
     */
    private void setIsFrozen(boolean isFrozen) {
        this.isFrozen=isFrozen;
    }

    /**
     * ������õķ����������һ����ɫ��ͼ���޷��ƶ���ʹ���̣߳�
     */
    public void setFrozenState(int pattern) {
        setIsFrozen(true);
        
        setFrozenType(ViewType.getTypeFromValue(pattern));  ///TODOӦ���������
        //����һ���µ��̣߳�5����Զ�����״̬
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TestHelper.propsTest("the frozen status has ending");
                setIsFrozen(false);   //5000֮��ֹͣ��ǰ��״̬
            }

        });
        thread.start();
    }
	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------ dataMatric�ĳ�ʼ��
	 */

	/**
	 * ��ʼ�������������Ϊ��readerhelper��writehelper����֪��ȫ���Ķ���
	 */
	private void dataMatricInit() {
		matircLength = dataMatricReadHelper.getDataMatricLength();
		for (int i = 0; i < matircLength; i++) {
			for (int j = 0; j < matircLength; j++) {
				setRandomNumberInDataMatric(i, j);
			}
		} // forѭ��������datamatric��ֵ

	}

    /**
     * ʹ�����������ʼ������
     * @param i
     * @param j
     * @return
     */
	private int setRandomNumberInDataMatric(int i, int j) {
		int seed = productRandomNumber();
		MatricValueLocation temp = new MatricValueLocation(i, j);
		TestHelper.testJunit(seed + "");
		temp.setValue(seed);
		dataMatricwriteHelper.setValue(temp);

		if (dataMatricReadHelper.isClearedWhenInit(temp)) { // dataMatricReadHelper)
															// �ж��Ƿ��γ���������
			TestHelper.testJunit("reset");
			resetNumberInDataMatric(i, j);

		}
		return seed;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 */
	private void resetNumberInDataMatric(int i, int j) {
		setRandomNumberInDataMatric(i, j);
	}

	/**
	 * ���������
	 * 
	 * @return
	 */
	private int productRandomNumber() {
		int result = (int) (Math.random() * 5) + 1;
		TestHelper.testJunit(result + "");
		// ����������
		if (result < 0
				|| result > dataMatricReadHelper.getDataMatricValueNumber()) {
			TestHelper.testprint("productRandomNumber beyond number limit");
			return 1; // Ĭ��Ϊ1
		} else {
			return result;
		}
	}
	/**
	 * ������ֵ�һ��ͼ���޷�ʹ��
	 */
	private void frozenComponent() {
		for(Iterator<VsGameService> itr=vsService.iterator();itr.hasNext();) {
			//�������еķ����Դ˵��ö��᷽���������ֵ�һ����ɫ����
			VsGameService vsGameService=itr.next();
			TestHelper.propsTest("frozen the oponet");
			try {
				vsGameService.FrozenPattern();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
//				TestHelpe
				e.printStackTrace();
			}
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------------------
	 * ���ܽ����������������ͼ����λ��,Ҫ��
	 */
	public synchronized boolean changePatternLocation(MatricLocation location1,
			MatricLocation location2) {
		// ����ֵ
		TestHelper.testprint("-----------------");

		int i = (int) location1.getX();
		int j = (int) location1.getY();
		TestHelper.testprint(i + ""+j);
		MatricLocation temp1 = new MatricLocation(i, j);
		i = (int) location2.getX();

		j = (int) location2.getY();
		TestHelper.testprint(i +""+ j);

		MatricLocation temp2 = new MatricLocation(i, j);
		
			/*
			 * ����ֵ
			 */
			ViewType type1 = dataMatricReadHelper.getViewTypeInDataMatric(temp1);
            temp1.setValue(type1.ordinal()+1);
            ViewType type2= dataMatricReadHelper.getViewTypeInDataMatric(temp2);
            temp2.setValue(type2.ordinal()+1);
           //���ж���ǰ�Ƿ��ڶ���ʱ����
            if(isFrozen) {
                if(isFrozenType(temp1)||isFrozenType(temp2)) {
                    TestHelper.testprint("���ڴ���ɫ���ڶ���״̬�����ƶ�");
                    return false;
                }
            }
			temp2.setValue(type1.ordinal() + 1);
			TestHelper.testprint(type1.ordinal() + 1 + "first");

			TestHelper.testprint(type2.ordinal() + 1 + "after");
			temp1.setValue(type2.ordinal() + 1);
			//����֮ǰ�Ĳ�����temp1����temp2��ֵ�Ѿ��ı䣬�����ڱȽ�ʱ����λ��
			if (temp1.getValue() != location2.getValue()
					|| temp2.getValue() != location1.getValue()) {
				TestHelper.testprint("���ݲ�ͬ");
				return false;
			} else {
			dataMatricwriteHelper.setValue(temp1);
			dataMatricwriteHelper.setValue(temp2);
			// ����ֻ���������ı�λ���ˣ�����һ��ֻ��������������
			ArrayList<MatricLocation> list = new ArrayList<MatricLocation>(2);
			list.add(temp1);
			list.add(temp2);
			// ���̱仯��֪ͨ���仯��viewlist
			setChangeList(list);
			notifyViewObserver();
			ArrayList<MatricLocation> clearList = dataMatricReadHelper
					.checkClearList(list);

			if (clearList != null && clearList.size() != 0) {
				// �γ�����
				clearIn(clearList);

			} else {
				// û���������������ߵ�λ��
				int tempInt = temp1.getValue();
				temp1.setValue(temp2.getValue());
				temp2.setValue(tempInt);
				dataMatricwriteHelper.setValue(temp1);
				dataMatricwriteHelper.setValue(temp2);
				list.clear();
				list.add(temp1);
				list.add(temp2);
				setChangeList(list);
				notifyViewObserver();

			}
			// return 1;
			return true;
		}

	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------
	 */

	/**
	 * ��ѯ�ײ��λ���µ����࣬ͼ�������ߵ���,ǧ��ע�⣬������Prop2ʱ�������frozenComponent����
	 * 
	 * @param x
	 * @return
	 */
	public PatternType queryPosition(Point x) {
		int i = (int) x.getX();
		int j = (int) x.getY();
		MatricLocation location = new MatricLocation(i, j);
		ViewType type = dataMatricReadHelper.getViewTypeInDataMatric(location);

		if(vsService!=null) {
			if(type==ViewType.prop2) {
			frozenComponent();
			}

		} else {
			
		}		
		TestHelper.propsTest("the query position is"+(type.ordinal()+1));
		return type.changeToPatternType(type);

	}
	

	/*
	 * --------------------------------------------------------------------------
	 * -----------------------------------------
	 */
	/**
	 * �������������������һϵ������
	 * 
	 * @param collection
	 * @return
	 */
	public void clear(ArrayList<Point> collection) {
		// // TODO�� ���int��list�еĸ������ϣ����������������ĸ���
		int result = 0;
		TestHelper.propsTest("the control call the clear");
		Collections.sort(collection, Listsort);
		Propscleartype type = checkListType(collection);
		// ������������ ��ֵ��Ϊ-1����������
		ArrayList<MatricLocation> clearList = getClearList(collection);
		clearIn(clearList);
	}
	/**
	 * �ڲ���������
	 * @param clearList ����λ��list
	 */
	private void clearIn(ArrayList<MatricLocation> clearList) {
		ArrayList<MatricLocation> testbangList=new ArrayList<MatricLocation>();
		TestHelper
				.testprint("-----------------------------------------------------------------");
		TestHelper.testprint("call the clearIn");
		
		TestHelper.testprint("the clearlist size is" + clearList.size());
		for(int i=0;i<clearList.size();i++) {
			TestHelper.propsTest(""+clearList.get(i).getX()+" "+clearList.get(i).getY());
			
			
		}
		//�ȴ���һ�µ�ǰ�Ƿ��������б����Ƿ��е��ߣ��еĻ�Ҫ����clearList���б�
		clearList=dataMatricReadHelper.checkpropList(clearList);
		Collections.sort(clearList,matriclocationSort);
		TestHelper.propsTest("__________________________checkPropList");
		for(int i=0;i<clearList.size();i++) {
			MatricLocation temp=clearList.get(i);
			TestHelper.propsTest(temp.getX()+""+temp.getY()+""+temp.getValue());
			//MatricLocation temp=clearList.get(i);
			MatricLocation added=new MatricLocation(temp.getX(), temp.getY());
			testbangList.add(added);
			
		}
		ArrayList<MatricLocation> paddingList = produceChangeList(clearList);

		TestHelper.testprint("in clearIn notifyView");

		// ֪ͨ����
		if (paddingList == null) {
			System.out.println("--------------------paddinglist is null");
		}

		// ʹ��write����д
		if (paddingList != null && paddingList.size() != 0) {
			// ���list֪ͨ
			for(int i=0;i<testbangList.size();i++) {
				TestHelper.propsTest("after opearate "+testbangList.get(i).getX()+" "+testbangList.get(i).getY());
//				testbangList.add(clearList.get(i));
			}
			TestHelper.testprint("֪ͨ����padding");
		
			setChangeList(paddingList);
			setClearedList(testbangList);
			notifyViewObserver();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dataMatricwriteHelper.setValueList(paddingList);
			
			clearList = dataMatricReadHelper.checkClearList(paddingList);
			if (clearList != null && clearList.size() != 0) {
				clearIn(clearList);
			} else {
				TestHelper
						.testprint("the clear list is null so dont call clearIn again");
			}
		} else {
			TestHelper.testJunit("-----------------------------------is null");
		}
		// return result;
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------------------------
	 */
	private ArrayList<MatricLocation> paddingList;
	private ArrayList<MatricLocation> clearedList;
	private ArrayList<MatricLocation> tipsList;

	private void setChangeList(ArrayList<MatricLocation> list) {
		paddingList = list;
	}
	private void setTipsList(ArrayList<MatricLocation> list) {
		tipsList=list;
	}

	private void setClearedList(ArrayList<MatricLocation> list) {

		clearedList = list;
	}

	/**
	 * ÿ��ʹ�ô˷�����paddingList set null
	 * 
	 * @return ArrayList<MatricLcation>
	 */
	private ArrayList<MatricLocation> getChangeList() {
		ArrayList<MatricLocation> result = paddingList;
		paddingList = null;
		return result;
	}

	/**
	 * ÿ��ʹ�ô˷�����clearedList set null
	 * 
	 * @return
	 */
	private ArrayList<MatricLocation> getClearedList() {
		ArrayList<MatricLocation> result = clearedList;
		clearedList = null;
		return result;
	}
	private ArrayList<MatricLocation> getTipsList() {
		ArrayList<MatricLocation> result=tipsList;
		tipsList=null;
		return result;
	}
	
	/*
	 * -----------------------------------------------------------------------
	 */
	private ArrayList<Model.patternModel.Observer> observerArrayList = new ArrayList<Model.patternModel.Observer>();

	@Override
	public void registerObserver(Model.patternModel.Observer o) {
		observerArrayList.add(o);
	}

	@Override
	public void removeObserver(Model.patternModel.Observer o) {

	}

	@Override
	public void notifyObserver() {
		for (Iterator<Model.patternModel.Observer> iterator = observerArrayList
				.iterator(); iterator.hasNext();) {

			Model.patternModel.Observer observer = iterator.next();
			if (observer instanceof Model.patternModel.Observer) {
				// ((ViewObserverAdapter)
				// observer).setParameter(false,1,null,changeList);
				try {
					observer.update(getChangeList());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void notifyViewObserver() {
		
		ArrayList<MatricLocation> list = getChangeList();
		ArrayList<MatricLocation> list2=getClearedList();
		ArrayList<MatricLocation> list3=getTipsList();
		for (Iterator<Model.patternModel.Observer> iterator = observerArrayList
				.iterator(); iterator.hasNext();) {

			Model.patternModel.Observer observer = iterator.next();
			if (observer instanceof ViewObserverAdapter) {
				TestHelper.testprint("-------------------------------------------");
				if(list!=null) {
				for (int i = 0; i < list.size(); i++) {
					
					TestHelper.testprint("the list is" + list.get(i).getX()
							+ " " + list.get(i).getY() + ""
							+ list.get(i).getValue());
				}
				}
				if(list2!=null){
				for(int j=0;j<list2.size();j++) {
					TestHelper.testprint("the list2 is" + list2.get(j).getX()
							+ " " + list2.get(j).getY() + ""
							+ list2.get(j).getValue());
				}
				
				}
				/*
				 * ����
				 */
				if(list!=null&&list2!=null) {
					ArrayList<MatricLocation> result=paddingStragety.getTheBeginLocationList(list2,list);
//					TestHelper.controlTest("ddd");
					for(int m=0;m<result.size();m++) {
						TestHelper.propsTest("################################");
						TestHelper.propsTest("the begin location is "+result.get(m).getX()+
																						""+result.get(m).getY()+
																						""+result.get(m).getValue());	
					}
				}
				
				/*
				 * ����
				 */
				TestHelper.testprint("-------------------------------------------");
				
				try {
					((ViewObserverAdapter) observer).setParameter(false, -1,
							false, list3, list2, list,paddingStragety.getTheBeginLocationList(list2,list));
					observer.update(getChangeList());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				TestHelper.testprint("update for view");
				
			}else{
				if(list2!=null){
					try {
						observer.update(list2);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------ observable��ʵ��
	 */

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------
	 */
	/*
	 * ����һ��ArrayList�ı�־λ�����������ֵ������ɵ������б�
	 */
	enum Propscleartype {
		propsA, propsB
	};

	private int matircLength;

	/**
	 * �жϵ�ǰ��list����ʲô�������ɵ������б�
	 * 
	 * @param collection
	 * @return
	 */
	private Propscleartype checkListType(Collection<Point> collection) {
		int listSize = collection.size();
		// ��򵥡�
		if (listSize == 8) {
			return Propscleartype.propsA;
		}
		if (listSize == 17) {
			return Propscleartype.propsB;
		}
		TestHelper.testprint("checkListType ...no match");
		return Propscleartype.propsA;
	}

	/**
	 * �ڲ��࣬���ڶ�collection��������
	 */
	private final Comparator<Point> Listsort = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			int x1 = o1.x;
			int x2 = o2.x;
			if (x1 != x2) {
				return x1 - x2;
			} else {
				x1 = o1.y;
				x2 = o2.y;
				return x1 - x2;
			}
		}
	};
	private final Comparator<MatricLocation> matriclocationSort = new Comparator<MatricLocation>() {
		@Override
		public int compare(MatricLocation o1, MatricLocation o2) {
			int x1 = o1.getX();
			int x2 = o2.getX();
			if (x1 != x2) {
				return x1 - x2;
			} else {
				x1 = o1.getY();
				x2 = o2.getY();
				return x1 - x2;
			}
		}
	};

	/**
	 * ת��PointList��MatricLocationList������������
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<MatricLocation> getClearList(ArrayList<Point> list) {
		ArrayList<MatricLocation> result = new ArrayList<MatricLocation>(
				list.size());
		for (Iterator<Point> iterator = list.iterator(); iterator.hasNext();) {
			Point temp = iterator.next();
			MatricValueLocation location = new MatricValueLocation(temp.x,
					temp.y);
			location.setValue(dataMatricReadHelper.getViewTypeInDataMatric(
					location).ordinal() + 1);
			result.add(location);
		}
		return result;
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------------------------------- ����
	 */

	/**
	 * 
	 * @param locations
	 * @return
	 */
	private ArrayList<MatricLocation> produceChangeList(
			ArrayList<MatricLocation> locations) {
		choosePaddingStragety();
		return paddingStragety.producePaddingList(locations);
	}

	private PaddingStragety paddingStragety = null; // ��������㷨�Ľӿ���

	/**
	 * �жϸ�ʹ�ú�������㷨
	 */
	private void choosePaddingStragety() {
		if (isdown) {
			paddingStragety = new downPaddingStragety(dataMatricReadHelper);
		} else {
			paddingStragety = new RightPaddingStagety(dataMatricReadHelper);
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------- Junit����ʹ��
	 */

	public ViewType showDataMatric(int i, int j) {
		int length = dataMatricReadHelper.getDataMatricLength();
		MatricLocation temp = new MatricLocation(i, j);

		// TestHelper.testprint(dataMatricReadHelper.getViewTypeInDataMatric(temp).ordinal()+"");
		return dataMatricReadHelper.getViewTypeInDataMatric(temp);

	}

	public void showInit() {
		dataMatricInit();
	}
	
	public ArrayList<Integer> produceInitDataMatric() {

		int length = dataMatricReadHelper.getDataMatricLength();
		ArrayList<Integer> resultList = new ArrayList<Integer>(length * length);
		MatricLocation location = new MatricLocation(0, 0);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				location.setX(i);
				location.setY(j);
				int temp = dataMatricReadHelper.getViewTypeInDataMatric(
						location).ordinal() + 1;
				Integer integer = new Integer(temp);
				TestHelper.testJunit(integer.toString());

				resultList.add(integer);

			}
		}
		return resultList;
	}


	public void showTips() {
		//ʹ�û��ArrayList<MatricLocation>
		//��tipList��ֵ
		//����update
		TipStragety tipStragety=new TwoTipStragety(dataMatricReadHelper);
		setTipsList(tipStragety.getTips());
		notifyViewObserver();
		
		
	}
	/**
	 * ��Ϸ����ʱ���á�ʹ�õ�ǰ���������еĵ���
	 */
	public void endGameClear() {
		//�ȱ������ƻ�õ����б�
		ArrayList<MatricLocation> list=dataMatricReadHelper.getPropList();
		clearIn(list);
	}

}
