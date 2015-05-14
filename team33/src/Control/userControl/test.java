package Control.userControl;

import java.util.ArrayList;

import Control.friendControl.FriendControl;
import vo.GameRecordVO;
import vo.RankVO;

public class test {

	public static void main(String [] args){
		test t = new test();
		t.go();
	}
	public void go(){
		UserControl userControl = new UserControl();
		GameRecordVO grv = userControl.checkRecord("100000");
    	System.out.println(grv.getAverageScore()+"------ "+grv.getEveryDayRound()[1]+"------ "
		+grv.getHighestCombo()+"------ "+grv.getHighestScore()+"------ "
			);
		FriendControl fControl = new FriendControl();
		ArrayList<RankVO> list = fControl.getRanks("100000");
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getSecondName()+list.get(i).getScore());
		}
	}
}
