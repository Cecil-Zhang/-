package Control.patternControl;

import Model.patternModel.TestHelper;

/**
 * @ClassName RemoteControl
 * @Description ����ģʽ�е�ң�����࣬�����������
 * @author ������
 * @Date 2014-5-9
 */
public class RemoteControl {
	private Command slot;
	private Object returnValue;
	
	public RemoteControl(){
		super();
		slot=new NoCommand();
	}
	
	public void run(){
		returnValue=slot.execute();
//		TestHelper.testprint("Get return value: "+returnValue+" from "+slot);
	}
	
	public void setCommand(Command c){
		slot=c;
	}
	
	public Object getReturn(){
		return returnValue;
	}

}
