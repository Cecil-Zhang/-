package Control.patternControl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import po.PatternType;
import vo.UserVO;
import LogicService.VsGameService;
import Model.patternModel.GameState;
import Model.patternModel.GameLogic;
import Model.patternModel.ViewType;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.TestHelper;
import Model.userModel.UserModel;
import Model.userModel.UserModelService;
import View.MainFrame;

/**
 * @ClassName patternControl
 * @Description
 * @author 张舒贤
 * @Date 2014-5-6
 */
public class patternControl extends UnicastRemoteObject implements patternControlService,VsGameService {

	RemoteControl remoteControl;
	HaltGameCommand haltGameCommand;
	RestartGameCommand restartGameCommand;
	PropClearCommand propClearCommand;
	QueryCommand queryCommand;
	SwapCommand swapCommand;
	StartGameCommand startGameCommand;
	GetGameRecordCommand getGameRecordCommand;
	WeakenPropCommand weakenPropCommand;
	FrozenPatternCommand frozenPatternCommand;
	StartGamePassivelyCommand startGamePassivelyCommand;
	propClear propClearStrategy;
	ArrayList<Point> square=new ArrayList<Point>();
	ArrayList<RivalConditionVSService> rivalConds;
	Point TopLeft;
	
	public patternControl(GameLogic gl) throws RemoteException{
		super();
		GameState gs=GameState.getInstance();
		remoteControl=new RemoteControl();
		haltGameCommand=new HaltGameCommand(gs);
		restartGameCommand=new RestartGameCommand(gs);
		propClearCommand=new PropClearCommand(gl);
		queryCommand=new QueryCommand(gl);
		swapCommand=new SwapCommand(gl);		
		startGameCommand=new StartGameCommand(gs);
		getGameRecordCommand=new GetGameRecordCommand(gs);
		propClearStrategy=new propClear();
		weakenPropCommand=new WeakenPropCommand(gs);
		frozenPatternCommand=new FrozenPatternCommand(gl);
		startGamePassivelyCommand=new StartGamePassivelyCommand(gs);
		TopLeft=new Point(0,0);
	}
	
	
	/**
	 * @title checkIfPattern
	 * @Description 判断游戏界面中的p逻辑位置是否为可移动的图案
	 * @param p boolean型，返回判断结果
	 */
	private PatternType checkPosition(Point p){
		queryCommand.setPoint(p);
		remoteControl.setCommand(queryCommand);
		remoteControl.run();
		try{
			PatternType type=(PatternType) remoteControl.getReturn();
			TestHelper.propsTest("the type is"+type);
			return type;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see Control.patternControl.controlService.patternControlService#mouseClick(java.awt.Point)
	 */
	@Override
	public boolean mouseClick(MatricLocation point) throws RemoteException{
		// TODO Auto-generated method stub
		Point temp=new Point(point.getX(),point.getY());
		PatternType type=checkPosition(temp);
		System.out.println(type.ordinal());
		System.out.println(point.getValue());
		ViewType boardtype=ViewType.getTypeFromValue(point.getValue());
		if((type.ordinal()+1)==(boardtype.changeToPatternType(boardtype).ordinal()+1)){
			if((type==PatternType.propA)||(type==PatternType.propB)){
				//如果坐标处为道具，则生成该道具可消除的坐标列表,并调用命令执行消除
				TestHelper.testprint("You had clicked a prop!");
				ArrayList<Point> clearPoint=propClearStrategy.generateClears(temp, type);
				propClearCommand.setPoints(clearPoint);
				remoteControl.setCommand(propClearCommand);
				remoteControl.run();
				
			}else{
				//如果坐标处为可移动的图案，则设置鬼脸效果
				TestHelper.testprint("You had clicked a pattern!");
			}
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean mouseDrag(MatricLocation before, MatricLocation after) throws RemoteException {
		// TODO Auto-generated method stub
		Point temp1=new Point(before.getX(),before.getY());
		Point temp2=new Point(after.getX(),after.getY());
		//若鼠标在一个图案上拖动则判定为无效
		if(!ifDragValid(temp1,temp2)){
			TestHelper.testprint("Mouse Drag is not valid!");
			return false;
		}else{
			swapCommand.setPoints(before,after);
			remoteControl.setCommand(swapCommand);
			remoteControl.run();
			boolean resulte=(Boolean) remoteControl.getReturn();
			TestHelper.testControl("drag value "+resulte);
			return resulte;
		}
	}
	
	/**
	 * @title ifDragValid
	 * @Description 判定拖动操作是否有效，当鼠标拖动范围不超过一个图案或者斜着拖动时无效
	 * @param before 拖动的起始坐标
	 * @param after 拖动的结束坐标
	 * @return 返回是否有效
	 */
	private boolean ifDragValid(Point before,Point after){
		if(((before.x==after.x) && (before.y==after.y))||((before.x!=after.x) && (before.y!=after.y))){
			return false;
		}else{
			return true;
		}
	}
	
//	/**
//	 * @title validateDrag
//	 * @Description 将跨越两个及以上图案的拖动转换为有效的拖动
//	 * @param before 拖动的开始坐标
//	 * @param after 拖动的结束坐标
//	 * @return 转换后的坐标数组，[0]为起始坐标，[1]为结束坐标
//	 */
//	private Point[] validateDrag(Point before,Point after){
//		Point[] p=new Point[2];
//		if(before.x==after.x){
//			p[0]=before;
//			if(Math.abs(before.y-after.y)==1){
//				p[1]=after;
//			}else if(before.y>after.y){
//				p[1]=new Point(before.x,before.y-1);
//			}else if(before.y<after.y){
//				p[1]=new Point(before.x,before.y+1);
//			}
//			return p;
//		}
//		if(before.y==after.y){
//			p[0]=before;
//			if(Math.abs(before.x-before.x)==1){
//				p[1]=after;
//			}else if(before.x>after.x){
//				p[1]=new Point(before.x-1,after.y);
//			}else if(before.x<after.x){
//				p[1]=new Point(before.x+1,after.y);
//			}
//			return p;
//		}
//		return null;
//	}

	@Override
	public void startGame(){
		// TODO Auto-generated method stub
		remoteControl.setCommand(startGameCommand);
		remoteControl.run();
	}

	/* (non-Javadoc)
	 * @see Control.patternControl.controlService.patternControlService#saveGameRecored()
	 */
	@Override
	public void saveGameRecored(String userID) throws RemoteException {
		// TODO Auto-generated method stub
		remoteControl.setCommand(getGameRecordCommand);
	    remoteControl.run();
	    Object[] record=(Object[]) remoteControl.getReturn();
	    Date d=(Date) record[0];
	    String s=(String) record[1];
	    int h=(Integer) record[2];
	    int m=(Integer) record[3];
	    
	    String[] score=s.split("plus");
	    int sco=Integer.parseInt(score[0]);
	    if (score.length==2){
	    	sco=sco+Integer.parseInt(score[1]);
	    }
	    
	    UserModelService userService=new UserModel();
	    userService.saveSingleRecord(userID, d, sco, m, h);	    
	}

	@Override
	public void FrozenPattern() throws RemoteException {
		// TODO Auto-generated method stub
		double rand = Math.random();
		int patternType = (int) Math.rint( ( rand * 4 )) +1 ;
		for(RivalConditionVSService vs:this.rivalConds){
			vs.frozenPattern(patternType);
		}
		frozenPatternCommand.setFrozenPattern(patternType);
	    remoteControl.setCommand(frozenPatternCommand);
	    remoteControl.run();
	}

	@Override
	public void WeakenPropC() throws RemoteException {
		// TODO Auto-generated method stub
		for(RivalConditionVSService vs:this.rivalConds){
			vs.changeSupermode(true);
		}
		remoteControl.setCommand(weakenPropCommand);
		remoteControl.run();
	}

	/* (non-Javadoc)
	 * @see LogicService.VsGameService#updateScore(int)
	 */
	@Override
	public void updateScore(int score) throws RemoteException {
		// TODO Auto-generated method stub
		for(RivalConditionVSService rival:this.rivalConds){
			rival.changeScore(score);
		}
	}

	@Override
	public void startGameWithRivals() throws RemoteException {
		// TODO Auto-generated method stub
		this.remoteControl.setCommand(startGamePassivelyCommand);
		this.remoteControl.run();
	}

	@Override
	public void haltGame() {
		// TODO Auto-generated method stub
		this.remoteControl.setCommand(haltGameCommand);
		this.remoteControl.run();
	}

	@Override
	public void restartGame() {
		// TODO Auto-generated method stub
		this.remoteControl.setCommand(restartGameCommand);
		this.remoteControl.run();
	}
	
	public void addRivalConds(ArrayList<RivalConditionVSService> rivals){
		if(rivals!=null){
			this.rivalConds=rivals;
		}
	}

}
