package Control.patternControl;

import Model.patternModel.GameState;

/**
 * @ClassName EndGameCommand
 * @Description
 * @author уейФом
 * @Date 2014-5-14
 */
public class GetGameRecordCommand implements Command {

	GameState gameState;
	
	public GetGameRecordCommand(GameState gs){
		gameState=gs;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		gameState.endGame();
		Object[] r=gameState.getGameData();
		return r;
	}


}
