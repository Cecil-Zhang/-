package Control.patternControl;

import Model.patternModel.GameLogic;

/**
 * @ClassName FrozenPatternCommand
 * @Description
 * @author ’≈ ÊœÕ
 * @Date 2014-5-22
 */
public class FrozenPatternCommand implements Command {

	GameLogic gameLogic;
	private int frozenType;
	
	public FrozenPatternCommand(GameLogic gl){
		this.gameLogic=gl;
	}
	
	/* (non-Javadoc)
	 * @see Model.patternModel.Command#execute()
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		this.gameLogic.setFrozenState(this.frozenType);
		return null;
	}
	
	public void setFrozenPattern(int t){
		this.frozenType=t;
	}

}
