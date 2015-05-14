package TestDriver;

import java.awt.Point;
import java.rmi.RemoteException;

import Control.patternControl.patternControl;
import Model.patternModel.location.MatricLocation;

/**
 * @ClassName patternControlDriver
 * @Description 测试patternControl的驱动类
 * @author 张舒贤
 * @Date 2014-5-10
 */
public class patternControlDriver {

	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameLogicStub gl=new GameLogicStub();
		patternControl control;
		try {
			MatricLocation m=new MatricLocation(143,342);
			m.setValue(3);
			control = new patternControl(gl);
			control.mouseClick(m);
			MatricLocation x=new MatricLocation(89,123);
			MatricLocation y=new MatricLocation(3,5);
			control.mouseDrag(x,y);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
