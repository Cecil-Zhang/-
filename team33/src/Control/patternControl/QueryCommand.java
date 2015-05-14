package Control.patternControl;

import java.awt.Point;

import Model.patternModel.GameLogic;

import po.PatternType;

/**
 * @ClassName QueryCommand
 * @Description 向GameLogic查询某一坐标的图标类型
 * @author 张舒贤
 * @Date 2014-5-10
 */
public class QueryCommand implements Command {
	private PatternType result;
	private Point queryPoint;
	private GameLogic gameLogic;
	
	public QueryCommand(GameLogic gl){
		super();
		gameLogic=gl;
	}
	
	/**
	 * @title setPoint
	 * @Description 设置要查询的坐标
	 * @param p 待查询的坐标点
	 */
	public void setPoint(Point p){
		queryPoint=p;
	}

	/* (non-Javadoc)
	 * @see Model.patternModel.Command#execute()
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		PatternType type=gameLogic.queryPosition(queryPoint);
		return type;
	}


}
