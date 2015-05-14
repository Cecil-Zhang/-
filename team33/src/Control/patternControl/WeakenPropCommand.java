package Control.patternControl;

import Model.patternModel.GameState;

/**
 * @ClassName WeakenPropCommand
 * @Description
 * @author уейФом
 * @Date 2014-5-22
 */
public class WeakenPropCommand implements Command {

	GameState gameState;
	
	public WeakenPropCommand(GameState gs){
		super();
		this.gameState=gs;
	}
	
	/* (non-Javadoc)
	 * @see Model.patternModel.Command#execute()
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		this.gameState.weakenPropC();
		return null;
	}

}
