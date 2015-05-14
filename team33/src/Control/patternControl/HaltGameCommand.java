package Control.patternControl;

import Model.patternModel.GameState;

/**
 * @ClassName HaltGameCommand
 * @Description ������Ϸ��������ͣ������
 * @author ������
 * @Date 2014-5-30
 */
public class HaltGameCommand implements Command {

	private GameState gameState;
	
	public HaltGameCommand(GameState gs){
		super();
		this.gameState=gs;
	}
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		gameState.haltGame();
		return null;
	}

}
