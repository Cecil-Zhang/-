package Control.patternControl;

import java.awt.Point;

import Model.patternModel.GameLogic;
import Model.patternModel.location.MatricLocation;

/**
 * @ClassName SwapCommand
 * @Description
 * @author ÕÅÊæÏÍ
 * @Date 2014-5-10
 */
public class SwapCommand implements Command{
	GameLogic gameLogic;
	private MatricLocation beforePoint;
	private MatricLocation afterPoint;

	public SwapCommand(GameLogic logic){
		super();
		gameLogic=logic;
	}
		
	public void setPoints(MatricLocation b,MatricLocation a){
		if(a.getX()>-1 && a.getX()<9 && a.getY()>-1 && a.getY()<9){
			afterPoint=a;
		}else{
			System.out.println("Wrong Point for afterPoint");
		}
		if(b.getX()>-1 && b.getX()<9 && b.getY()>-1 && b.getY()<9){
			beforePoint=b;
		}else{
			System.out.println("Wrong Point for beforePoint");
		}
	}
	
	/* (non-Javadoc)
	 * @see Model.patternModel.Command#execute()
	 */
	public Object execute() {
		boolean result=gameLogic.changePatternLocation(this.beforePoint, this.afterPoint);
		return result;
	}

	
}
