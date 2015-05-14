package Control.patternControl;

import Model.patternModel.GameState;

/**
 * @ClassName StartGameCommand
 * @Description
 * @author уейФом
 * @Date 2014-5-14
 */
public class StartGameCommand implements Command {

	GameState gameState;
	
	public StartGameCommand(GameState gs){
		gameState=gs;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		gameState.startGame();
		gameState.broadcastGameStart();
		return null;
	}

}
