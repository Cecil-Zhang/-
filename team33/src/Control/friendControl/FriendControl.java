package Control.friendControl;

import java.util.ArrayList;

import po.AddressPO;
import po.FriendListPO;
import po.FriendPO;
import po.UserPO;
import vo.FriendListVO;
import vo.FriendVO;
import vo.RankVO;
import vo.UserVO;
import LogicService.ViewRMI;
import Model.friendModel.FriendModel;
import Model.friendModel.FriendModelService;
import Model.userModel.Rank;
import Model.userModel.UserModel;
import Model.userModel.UserModelService;

/**
 * @author 徐仪萍
 * @description 本类负责好友管理的相关输入处理和界面跳转
 */
public class FriendControl implements FriendControlService {
	FriendModelService fms = new FriendModel();
	UserModelService ums = new UserModel();

	public void addFriend(String id, UserVO uv) {
		if(uv==null){
			return;
		}
		FriendPO fp = new FriendPO(id, uv.getId(), uv.getName(), uv.getName(),
				uv.isGender(), uv.getImage());
		fms.addFriend(fp);
		/*AddressPO ap = ums.getIp(id);
		if(ap==null){
				
		}else{
		   String ipString = ums.getIp(id).getAddress();	
			ViewRMI vrs = new ViewRMI(ipString) ;
		//	vrs.showMessage("您已成功添加"+uv.getId()+"为好友");
		}*/
	}

	/**
	 * @title findUser
	 * @Description
	 * @param id
	 * @return
	 */
	public UserVO findUser(String id) {

		UserPO up = ums.findUser(id);
		if (up==null){
			return null;
		}	else {

			UserVO uv = new UserVO(up.getUserId(), up.getUserName(),
					up.isGender(), up.getImage(),up.getMoney());
			return uv;
		}
	}

	public void modifyFriend(FriendVO fv) {
		FriendPO fp = new FriendPO(fv.getId(), fv.getFriendId(), fv.getName(),
				fv.getSecondName(), fv.getGender(), fv.getImage());
		fms.modifyFriend(fp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Control.friendControl.FriendControlService#deleteFriend(java.lang.String,
	 * java.lang.String)
	 */
	public void deleteFriend(String id, String friendId) {
		fms.deleteFriend(id, friendId);
	}

	/*得到好友列表*/
	public FriendListVO getFriendList(String id) {
		FriendListPO flp = fms.getFriendList(id);

		FriendListVO flv = new FriendListVO(id, new ArrayList<FriendVO>());
		ArrayList<FriendVO> fList = new ArrayList<FriendVO>();
		if (flp==null) {
			return flv;
		} else {
			for (int i = 0; i < flp.getFriendList().size(); i++) {
				FriendPO fP = flp.getFriendList().get(i);
				fList.add(new FriendVO(fP.getId(), fP.getFriendId(), fP
						.getName(), fP.getSecondName(), fP.getGender(), fP
						.getImage()));
			}
		}
		flv.setFriendList(fList);
		return flv;
	}

	/*得到排行榜信息*/
	public ArrayList<RankVO> getRanks(String id) {
		ArrayList<Rank> rankPOList = ums.getRank(id);
		ArrayList<RankVO> rankVOList = new ArrayList<RankVO>();
		for (int i = 0; i < rankPOList.size(); i++) {
			// RankVO rank = new RankVO(rankPOList.get(i).getSecondName(),
			// rankPOList.get(i).getScore());
			RankVO rank = new RankVO(i + 1, rankPOList.get(i).getSecondName(),
					rankPOList.get(i).getScore());
			rankVOList.add(rank);

		}
		System.out.println("in friendcontrol" + rankVOList.size());
		return rankVOList;

	}
	
	/*
	 * 得到在线好友列表*/
	public FriendListVO getList(String id) {
		FriendListVO flv = getFriendList(id);
		FriendListVO fList = new FriendListVO(id,new ArrayList<FriendVO>());
		for(int i=0;i<flv.getFriendList().size();i++){
			FriendVO fv =flv.getFriendList().get(i);
			String friendId = fv.getFriendId();
			UserPO up = ums.findUser(friendId);
			if(up.isLogin()){
				fList.getFriendList().add(fv);
			}
		}
		return fList;
	}

}
