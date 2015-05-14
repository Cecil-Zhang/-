package Model.patternModel;

/**
 * @ClassName Observable
 * @Description ���۲��߽ӿ�
 * @author ������
 * @Date 2014-5-6
 */
public interface Observable {

	/**
	 * @title registerObserver
	 * @Description ע��۲���
	 * @param o ����ע��Ĺ۲���
	 */
	public void registerObserver(Observer o);
	
	/**
	 * @title removeObserver
	 * @Description �����۲���
	 * @param o �������Ĺ۲���
	 */
	public void removeObserver(Observer o);
	
	/**
	 * @title myNotify
	 * @Description �����۲��߷������ʱ֪ͨ�۲��ߵ��õķ���
	 */
	public void notifyObserver();
	
	/**
	 * @title NotifyViewObserver
	 * @Description �����۲��߷������ʱ֪ͨ����۲��ߵ��õķ���
	 */
	public void notifyViewObserver();
}
