package Control.patternControl;

import java.awt.Point;
import java.util.ArrayList;

import Model.patternModel.GameLogic;

/**
 * @ClassName PropClearCommand
 * @Description �����������������б��ݸ�GameLogicִ��
 * @author ������
 * @Date 2014-5-10
 */
public class PropClearCommand implements Command {

	private ArrayList<Point> clearPoints;
	private GameLogic gameLogic;
	
	public PropClearCommand(GameLogic gl){
		super();
		gameLogic=gl;
	}
	
	/**
	 * @title setPoints
	 * @Description ���õ��߲�������Ҫ����������б�
	 * @param list ������б�
	 */
	public void setPoints(ArrayList<Point> list){
		if(list!=null){
			clearPoints=list;
		}else{
			System.out.println("Wrong point list for prop clear");
		}
	}
	
	/* (non-Javadoc)
	 * @see Model.patternModel.Command#execute()
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		gameLogic.clear(clearPoints);
		return null;
	}


}
