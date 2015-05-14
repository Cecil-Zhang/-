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
 * Modle模块的老大(好像又不应该是）。。。一切听他的，也是个外交官。。。与Control模块进行交互 掌管消除的策略 Created by randy
 * on 14-5-9.
 */
public class GameLogic implements Model.patternModel.Observable {
	private DataMatricReadHelper dataMatricReadHelper;
	private DataMatricwriteHelper dataMatricwriteHelper;
	private boolean isdown = true;// 下落的方向
	private ArrayList<MatricLocation> updateList;// TODO:添加建设者模式。。。。
	private ArrayList<VsGameService> vsService;
    private boolean isFrozen =false;
    private ViewType frozenType;//用于对战时冻结对方的冒个棋子

	public GameLogic() {
		dataMatricReadHelper = new DataMatricReadHelper();
		dataMatricwriteHelper = new DataMatricwriteHelper();
		dataMatricInit();
		choosePaddingStragety();
		setfallway();  //设置下落方向
	}
	/**
	 * 设置下落方向，以供选择策略
	 */
	private void setfallway() {
		SettingModel controller=new SettingModel();
		isdown=controller.getWay();
		
	}
	
	/**
	 * @title addRivals
	 * @Description 添加对手
	 * @param vs VsGameService
	 */
	public void addRivals(ArrayList<VsGameService> vs){

		this.vsService=vs;
	}
	/*
	 *-----------------------------------------------------------------
	 */
    /**
     * 判断当前移动的块是否是冻结的图片
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
     * 设置自己的冻结的pattern种类
     * @param type
     */
    private void setFrozenType(ViewType type) {
    	TestHelper.propsTest("the set Frozen type is"+type);
        frozenType=type;
    }
    /**
     * 设置是否冻结的属性
     * @param isFrozen
     */
    private void setIsFrozen(boolean isFrozen) {
        this.isFrozen=isFrozen;
    }

    /**
     * 对外调用的方法，冻结对一定颜色的图案无法移动，使用线程，
     */
    public void setFrozenState(int pattern) {
        setIsFrozen(true);
        
        setFrozenType(ViewType.getTypeFromValue(pattern));  ///TODO应该是随机的
        //构造一个新的线程，5秒后自动更改状态
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TestHelper.propsTest("the frozen status has ending");
                setIsFrozen(false);   //5000之后停止当前的状态
            }

        });
        thread.start();
    }
	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------ dataMatric的初始化
	 */

	/**
	 * 初始化放在这里，是因为有readerhelper和writehelper，它知道全部的东西
	 */
	private void dataMatricInit() {
		matircLength = dataMatricReadHelper.getDataMatricLength();
		for (int i = 0; i < matircLength; i++) {
			for (int j = 0; j < matircLength; j++) {
				setRandomNumberInDataMatric(i, j);
			}
		} // for循环来设置datamatric的值

	}

    /**
     * 使用随机数来初始化数组
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
															// 判断是否形成消除啦。
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
	 * 产生随机数
	 * 
	 * @return
	 */
	private int productRandomNumber() {
		int result = (int) (Math.random() * 5) + 1;
		TestHelper.testJunit(result + "");
		// 谨慎。。。
		if (result < 0
				|| result > dataMatricReadHelper.getDataMatricValueNumber()) {
			TestHelper.testprint("productRandomNumber beyond number limit");
			return 1; // 默认为1
		} else {
			return result;
		}
	}
	/**
	 * 冻结对手的一种图案无法使用
	 */
	private void frozenComponent() {
		for(Iterator<VsGameService> itr=vsService.iterator();itr.hasNext();) {
			//遍历其中的服务，以此调用冻结方法，将对手的一种颜色冻结
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
	 * 接受交换的命令，交换两个图案的位置,要有
	 */
	public synchronized boolean changePatternLocation(MatricLocation location1,
			MatricLocation location2) {
		// 交换值
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
			 * 交换值
			 */
			ViewType type1 = dataMatricReadHelper.getViewTypeInDataMatric(temp1);
            temp1.setValue(type1.ordinal()+1);
            ViewType type2= dataMatricReadHelper.getViewTypeInDataMatric(temp2);
            temp2.setValue(type2.ordinal()+1);
           //先判读当前是否在冻结时间中
            if(isFrozen) {
                if(isFrozenType(temp1)||isFrozenType(temp2)) {
                    TestHelper.testprint("现在此颜色处于冻结状态。。移动");
                    return false;
                }
            }
			temp2.setValue(type1.ordinal() + 1);
			TestHelper.testprint(type1.ordinal() + 1 + "first");

			TestHelper.testprint(type2.ordinal() + 1 + "after");
			temp1.setValue(type2.ordinal() + 1);
			//由于之前的操作，temp1，和temp2的值已经改变，所以在比较时换了位置
			if (temp1.getValue() != location2.getValue()
					|| temp2.getValue() != location1.getValue()) {
				TestHelper.testprint("数据不同");
				return false;
			} else {
			dataMatricwriteHelper.setValue(temp1);
			dataMatricwriteHelper.setValue(temp2);
			// 由于只有那两个改变位置了，所以一定只有两个可能消除
			ArrayList<MatricLocation> list = new ArrayList<MatricLocation>(2);
			list.add(temp1);
			list.add(temp2);
			// 棋盘变化，通知填充变化的viewlist
			setChangeList(list);
			notifyViewObserver();
			ArrayList<MatricLocation> clearList = dataMatricReadHelper
					.checkClearList(list);

			if (clearList != null && clearList.size() != 0) {
				// 形成消除
				clearIn(clearList);

			} else {
				// 没有消除，返回两者的位置
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
	 * 查询底层的位置下的种类，图案，或者道具,千万注意，当遇到Prop2时，会调用frozenComponent方法
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
	 * 消除点击道具所产生的一系列坐标
	 * 
	 * @param collection
	 * @return
	 */
	public void clear(ArrayList<Point> collection) {
		// // TODO： 这个int是list中的个数加上，下落后产生的消除的个数
		int result = 0;
		TestHelper.propsTest("the control call the clear");
		Collections.sort(collection, Listsort);
		Propscleartype type = checkListType(collection);
		// 先消除。。。 将值设为-1？？？？？
		ArrayList<MatricLocation> clearList = getClearList(collection);
		clearIn(clearList);
	}
	/**
	 * 内部消除方法
	 * @param clearList 消除位置list
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
		//先处理一下当前是否消除的列表中是否有道具，有的话要更新clearList的列表
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

		// 通知界面
		if (paddingList == null) {
			System.out.println("--------------------paddinglist is null");
		}

		// 使用write进行写
		if (paddingList != null && paddingList.size() != 0) {
			// 填充list通知
			for(int i=0;i<testbangList.size();i++) {
				TestHelper.propsTest("after opearate "+testbangList.get(i).getX()+" "+testbangList.get(i).getY());
//				testbangList.add(clearList.get(i));
			}
			TestHelper.testprint("通知界面padding");
		
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
	 * 每次使用此方法，paddingList set null
	 * 
	 * @return ArrayList<MatricLcation>
	 */
	private ArrayList<MatricLocation> getChangeList() {
		ArrayList<MatricLocation> result = paddingList;
		paddingList = null;
		return result;
	}

	/**
	 * 每次使用此方法，clearedList set null
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
				 * 测试
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
				 * 测试
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
	 * ------------------------------------------ observable的实现
	 */

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------
	 */
	/*
	 * 消除一个ArrayList的标志位。。。是哪种道具生成的消除列表
	 */
	enum Propscleartype {
		propsA, propsB
	};

	private int matircLength;

	/**
	 * 判断当前的list是由什么道具生成的消除列表
	 * 
	 * @param collection
	 * @return
	 */
	private Propscleartype checkListType(Collection<Point> collection) {
		int listSize = collection.size();
		// 最简单。
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
	 * 内部类，用于对collection进行排序
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
	 * 转换PointList到MatricLocationList。。。。。。
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
	 * ------------------------------------------------------------------- 消除
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

	private PaddingStragety paddingStragety = null; // 消除填充算法的接口类

	/**
	 * 判断该使用何种填充算法
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
	 * ------------------------------------------- Junit测试使用
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
		//使用获得ArrayList<MatricLocation>
		//给tipList赋值
		//调用update
		TipStragety tipStragety=new TwoTipStragety(dataMatricReadHelper);
		setTipsList(tipStragety.getTips());
		notifyViewObserver();
		
		
	}
	/**
	 * 游戏结束时调用。使用当前界面上所有的道具
	 */
	public void endGameClear() {
		//先遍历棋牌获得道具列表
		ArrayList<MatricLocation> list=dataMatricReadHelper.getPropList();
		clearIn(list);
	}

}
