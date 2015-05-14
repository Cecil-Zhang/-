package Control.patternControl;

import Model.patternModel.GameState;


public class StartGamePassivelyCommand implements Command {

	GameState gameState;
	
	public StartGamePassivelyCommand(GameState s){
		super();
		gameState=s;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		gameState.startGame();
		return null;
	}

}
