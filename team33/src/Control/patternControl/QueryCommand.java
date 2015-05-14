package Control.patternControl;

import java.awt.Point;

import Model.patternModel.GameLogic;

import po.PatternType;

/**
 * @ClassName QueryCommand
 * @Description ��GameLogic��ѯĳһ�����ͼ������
 * @author ������
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
	 * @Description ����Ҫ��ѯ������
	 * @param p ����ѯ�������
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
