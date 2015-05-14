package Control.patternControl;

import Model.patternModel.GameState;

/**
 * @ClassName RestartGameCommand
 * @Description ������Ϸ��������ͣ�����¿�ʼ��Ϸ������
 * @author ������
 * @Date 2014-5-30
 */
public class RestartGameCommand implements Command{

	private GameState gameState;
	
	public RestartGameCommand(GameState gs){
		super();
		this.gameState=gs;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		this.gameState.restartGame();
		return null;
	}

	
}
