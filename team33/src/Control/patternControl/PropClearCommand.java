package Control.patternControl;

import java.awt.Point;
import java.util.ArrayList;

import Model.patternModel.GameLogic;

/**
 * @ClassName PropClearCommand
 * @Description 将道具消除的坐标列表传递给GameLogic执行
 * @author 张舒贤
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
	 * @Description 设置道具产生的需要消除坐标点列表
	 * @param list 坐标点列表
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
