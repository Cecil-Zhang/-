package Model.patternModel.observerPattern;

public interface Observable {

    /**
     * 添加监听者
     * @param viewObserver
     */
	public void registerObserver(Observer viewObserver) ;

    /**
     * 去除监听者
     * @param viewObserver
     */
	public void removeObserver(Observer viewObserver) ;
    /*
    -----------------------------------------------------------------------------------
     */

    /**
     * 通知一个监听者
     * @param viewObserver
     */
	public void myNotify(Observer viewObserver) ;

    /**
     * 通知所有的监听者
     */
    public void myNofifyAll();
    /**
     * 判断维护的观察者集合是否存在
     * @return
     */
    public boolean isCollectionExist();

}
