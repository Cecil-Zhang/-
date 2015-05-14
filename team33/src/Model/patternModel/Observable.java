package Model.patternModel;

/**
 * @ClassName Observable
 * @Description 被观察者接口
 * @author 张舒贤
 * @Date 2014-5-6
 */
public interface Observable {

	/**
	 * @title registerObserver
	 * @Description 注册观察者
	 * @param o 请求注册的观察者
	 */
	public void registerObserver(Observer o);
	
	/**
	 * @title removeObserver
	 * @Description 撤销观察者
	 * @param o 请求撤销的观察者
	 */
	public void removeObserver(Observer o);
	
	/**
	 * @title myNotify
	 * @Description 当被观察者发生变更时通知观察者调用的方法
	 */
	public void notifyObserver();
	
	/**
	 * @title NotifyViewObserver
	 * @Description 当被观察者发生变更时通知界面观察者调用的方法
	 */
	public void notifyViewObserver();
}
