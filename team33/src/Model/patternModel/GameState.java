package Model.patternModel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import LogicService.VsGameService;
import Model.patternModel.GameLogic;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.TestHelper;
import View.MessageFrame;
import View.GameUI.ViewObserverAdapter;

/**
 * @ClassName SuperMode
 * @Description 负责游戏数据的维护，如超级模式，游戏分数，连击次数
 * @author 张舒贤
 * @Date 2014-5-6
 */
public class GameState implements Observer, Observable {
	private final int SUPERTIME = 5000; // 每次的超级状态为5s
	private final int HIT = 4; // 四次连击形成一次超级状态
	private int Interval = 1000; // 连击的时间间隔，默认为1s
	private ArrayList<Observer> observers;
	private int score = 0; // 当前游戏的分数
	private boolean mode = false; // 超级状态的标志
	private ArrayList<Long> clearTimes;
	private Date startTime;
	private long leftTime = 0; // 超级状态剩余的时间，单位为毫秒
	private long showTipTime = 3000; // 当玩家超过showTipTime无动作时，系统进行提示
	private int mostHits = 0;
	private int currentHits = 0;
	private int scoreType;
	private boolean GameOn = false;
	private GameLogic gameLogic;
	private long SuperStartTime; // 最近一次超级模式开启的时间
	private static GameState instance; // GameState采用单例模式
	private boolean propD = false;
	private ArrayList<VsGameService> vsService;
	
	/**
	 * @title getInstance
	 * @Description 得到保存游戏数据的对象
	 * @return
	 */
	public static GameState getInstance() {
		if (instance == null) {
			instance = new GameState();
		}
		return instance;
	}

	/**
	 * @title set
	 * @Description 设置游戏逻辑对象，监听以及道具的选择
	 * @param gl 逻辑操作对象
	 * @param o GameState的监听对象
	 * @param propC 是否使用道具C
	 * @param propD 是否使用道具D
	 * @param propE 是否使用道具E
	 */
	public void set(GameLogic gl, Observable o, boolean propC, boolean propD,
			boolean propE) {
		observers = new ArrayList<Observer>();
		gameLogic = gl;
		o.registerObserver(this);
		vsService = null;

		if (propC) {
			this.Interval = 2000;
		}

		this.propD = propD;

		if (propE) {
			this.showTipTime = 2000;
		}
	}

	/**
	 * @title StartGame
	 * @Description 开始进行游戏数据记录
	 */
	public void startGame() {
		if(!ifGameOn()){
			this.clearTimes = new ArrayList<Long>();
			Date now = new Date();
			this.startTime = now;
			this.clearTimes.add(new Long(now.getTime()+4250));
			this.GameOn = true;
			TestHelper.testControl("The game start at " + (now.getTime()+4250));
			this.showTips();
			
			if(observers!=null){
				for (Observer o : observers) {
					if (o instanceof ViewObserverAdapter) {
						ViewObserverAdapter tmp = (ViewObserverAdapter) o;
						GameReadyThread gt=new GameReadyThread(tmp);
						Thread t=new Thread(gt);
						t.start();
					}
				}
			}
		}
	}

	/**
	 * @title addRival
	 * @Description 设置对战游戏中的对手监听
	 * @param vs
	 */
	public void addRival(ArrayList<VsGameService> vs) {
		this.vsService = vs;
	}
	
	/**
	 * @title broadcastGameStart
	 * @Description 通知对战游戏中的对手游戏开始
	 */
	public void broadcastGameStart() {
		if (this.vsService != null && this.vsService.size() != 0) {
			TestHelper.testControl("游戏开始,等待通知对手");
			for (VsGameService vs : this.vsService) {
				try {
					vs.startGameWithRivals();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					MessageFrame.show("通知对手游戏开始失败!");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @title endGame
	 * @Description 结束游戏数据记录
	 */
	public void endGame() {
		System.out.println("^^^^^^^^^^^^^^^^^^^^"+ifGameOn());
		if(ifGameOn()){
			gameLogic.endGameClear();
			
			this.GameOn = false;
			this.instance = null;
			TestHelper.testprint("The game is over!");
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object[] record=(Object[]) getGameData();
			Date d=(Date) record[0];
		    String s=(String) record[1];
		    s=s.replace("plus", " + ");
		    int h=(Integer) record[2];
		    int m=(Integer) record[3];
			if(observers!=null){
				for (Observer o : observers) {
					if (o instanceof ViewObserverAdapter) {
						ViewObserverAdapter tmp = (ViewObserverAdapter) o;
						tmp.showGameResult(d,s,h,m);
					}
				}
			}
		}
	}

	/**
	 * @title getHit
	 * @Description 返回本局游戏的最大连击数
	 * @return 最大连击数
	 */
	public int getHit() {
		if (!GameOn) {
			return mostHits;
		} else {
			return -1;
		}

	}

	/**
	 * @title getGameData
	 * @Description 返回本局游戏的开始时间
	 * @return 开始时间
	 */
	public Object[] getGameData() {
		this.endGame();

		Object[] record = new Object[4];
		Date d = this.startTime;
		
		record[0] = d;
		String sco = String.valueOf(this.score);
		if (this.propD) {
			double temp = this.score * 0.1;
			sco = sco+"plus"+String.valueOf((int) temp);
		}
		record[1] = sco;
		
		record[2] = this.mostHits;
		int money = (int) ((this.score/100+50)*(1+this.mostHits*0.01));
		record[3] = money;
		return record;

	}

	/**
	 * @title ifGameOn
	 * @Description 返回游戏状态
	 * @return 游戏开始与否的状态
	 */
	private boolean ifGameOn() {
		return this.GameOn;
	}

	/**
	 * @title ifSuperMode
	 * @Description 判断此时是否为超级模式
	 * @return 若此时为超级模式，返回true，否则返回false
	 */
	public boolean ifSuperMode() {
		return mode;
	}

	/**
	 * @title setInterval
	 * @Description 设置形成连击的消除间隔，默认为1s，可由道具C增强到2s
	 * @param inter
	 */
	public void setInterval(int inter) {
		if (inter > 0) {
			this.Interval = inter;
		}
	}

	/**
	 * @title haltGame
	 * @Description 暂停游戏数据记录
	 */
	public void haltGame() {
		if(this.ifGameOn()){
			this.GameOn = false;
			Date now = new Date();
			this.clearTimes.add(new Long(now.getTime()));
			TestHelper.testprint("Game Halt!");
		}
	}

	/**
	 * @title restartGame
	 * @Description 重新开启游戏数据记录
	 */
	public void restartGame() {
		if (!this.ifGameOn()){
			this.GameOn = true;
			Date now = new Date();
			Long halt = this.clearTimes.get(this.clearTimes.size() - 1);
			long haltLength = now.getTime() - halt.longValue();
			this.SuperStartTime = this.SuperStartTime + haltLength;
			this.clearTimes.remove(halt);
			Long temp = this.clearTimes.get(this.clearTimes.size() - 1);
			this.clearTimes.remove(temp);
			Long re = new Long(temp.longValue() + haltLength);
			this.clearTimes.add(re);
			this.showTips();
			Thread checkThread = new Thread(new CheckHitRunnable());
			checkThread.start();
			TestHelper.testprint("Game Restart!");
		}
	}

	public void weakenPropC() {
		Thread weakenC = new Thread(new WeakenRunnable());
		weakenC.start();
	}

	/**
	 * @title showTips
	 * @Description 如果玩家在showTipTime时间内没有操作，系统进行提示
	 */
	private void showTips() {
		Thread t = new Thread(new ShowTipRunnable());
		t.start();
	}

	/**
	 * @title checkHit
	 * @Description 设置超级状态的开关
	 */
	private synchronized void checkHit() {
		int length = clearTimes.size();
		if (length < 2) {
			return;
		}
		long now = clearTimes.get(length - 1).longValue();
		long before = clearTimes.get(length - 2).longValue();
		if (now - before <= Interval) {
			this.currentHits++;
			if (this.currentHits > this.mostHits) {
				this.mostHits = this.currentHits;
				TestHelper.testControl("最大连击数已更新为" + String.valueOf(currentHits)
						+ "次！");
			}
			if (this.currentHits % HIT == 0) {
				if (this.leftTime == 0) {	
					TestHelper.testControl(this.currentHits+" "+this.currentHits/this.HIT);
					this.mode = true;
					this.notifyViewObserver();
					Date start = new Date();
					this.SuperStartTime = start.getTime();
					TestHelper.testControl("超级模式已开启于: "+start);
					Thread checkThread = new Thread(new CheckHitRunnable());
					checkThread.start();

					this.broadcastWeakenPropC();
				}
				this.leftTime = this.leftTime + SUPERTIME;
			}
		} else {
			this.currentHits = 0;
		}
	}

	private void broadcastWeakenPropC() {
		if (this.vsService != null && this.vsService.size() != 0) {
			for (VsGameService vs : this.vsService) {
				try {
					vs.WeakenPropC();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
//					MessageFrame.show("形成超级状态，削弱对手道具C效果时，远程调用失败!");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @title modScore
	 * @Description 修改分数
	 */
	private void modScore(int size) {
		int incre = 0;
		if (size == 3) {
			incre = 100;
		} else if (size == 4) {
			incre = 200;
		} else if (size == 5) {
			incre = 500;
		} else if (size == 6) {
			incre = 200;
		} else if (size == 7) {
			incre = 300;
		} else if (size == 8) {
			incre = 400;
		} else if (size == 9) {
			incre = 700;
		}

		if (ifSuperMode()) {
			this.score += incre * 2;
		} else {
			this.score += incre;
		}

		if (this.vsService != null && this.vsService.size() != 0) {
			for (VsGameService vs : this.vsService) {
				try {
					vs.updateScore(this.score);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					MessageFrame.show("通知对手界面刷新分数时网络出错!");
					e.printStackTrace();
				}
			}
		}

		this.notifyViewObserver();
	}

	/**
	 * @title checkIfSuperTimeLeft
	 * @Description 检查超级状态的时间是否还有剩余
	 * @return 超级状态时间还有剩余则返回true,否则返回false
	 */
	private synchronized boolean checkIfSuperTimeLeft() {
		Date now = new Date();
		long left = now.getTime() - this.SuperStartTime;
		// TestHelper.testprint("Super Mode left time is "+left);
		if (left < this.leftTime) {
			return true;
		} else {
			this.mode = false;
			return false;
		}
	}

	@Override
	public synchronized void update(ArrayList<MatricLocation> list) {
		// TODO Auto-generated method stub
		if(ifGameOn()){
			Date d = new Date();
			clearTimes.add(d.getTime());
			this.checkHit();
			if (list != null) {
				this.modScore(list.size());
			} else {
				TestHelper.testprint("计算得分的数组为空");
			}
		}
	}

	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		if (o != null) {
			observers.add(o);
		}
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		if (o != null) {
			observers.remove(o);
		}
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyViewObserver() {
		// TODO Auto-generated method stub
		for (Observer o : observers) {
			if (o instanceof ViewObserverAdapter) {
				ViewObserverAdapter tmp = (ViewObserverAdapter) o;
				try {
					tmp.setParameter(this.mode, this.score, true, null, null,
							null,null);
					tmp.update(null);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class ShowTipRunnable implements Runnable {
		private long now;
		private long last;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (ifGameOn()) {
				this.showTip();
			}
		}

		private synchronized void showTip() {
			now = new Date().getTime();
	        int temp=clearTimes.size();
	        
			try{
				last = clearTimes.get(temp - 1).longValue();
			}catch(NullPointerException e){
//				e.printStackTrace();
			}
			if ((now - last) > showTipTime) {
				gameLogic.showTips();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class CheckHitRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (ifSuperMode()) {
				if (!checkIfSuperTimeLeft()) {
					leftTime = 0;
					notifyViewObserver();
				}
				if (!ifGameOn()) {
					break;
				}
			}
			TestHelper.testControl("超级模式结束于: "+new Date());
		}
	}

	class WeakenRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (Interval != 1000) {
				
				Interval = 2000;
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					MessageFrame.show("冻结道具C效果失败!");
					e.printStackTrace();
				}
				Interval = 1000;
				TestHelper.testJunit("道具C效果减弱5s结束!");
			}
		}

	}
	
	class GameReadyThread implements Runnable{
		private ViewObserverAdapter adapter;
		
		public GameReadyThread(ViewObserverAdapter a){
			super();
			this.adapter=a;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			adapter.gameReady();
		}
		
	}

}
