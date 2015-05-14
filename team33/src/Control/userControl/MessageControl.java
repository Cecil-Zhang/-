package Control.userControl;

import java.util.ArrayList;
import java.util.Date;

import Control.friendControl.FriendControl;
import Control.friendControl.FriendControlService;
import LogicService.IPHelper;
import LogicService.ViewRMI;
import Model.friendModel.FriendModel;
import Model.friendModel.FriendModelService;
import Model.userModel.MessageModel;
import Model.userModel.MessageModelService;
import Model.userModel.UserModel;
import Model.userModel.UserModelService;
import View.MainFrame;
import View.MessageFrame;
import po.AddressPO;
import po.MessagePO;
import po.UserPO;
import vo.ApplicationVO;
import vo.InvitationVO;
import vo.UserVO;
import vo.toInvitationVO;

public class MessageControl implements MessageControlService {

	MessageModelService mms = new MessageModel();
	UserModelService ums = new UserModel();
	FriendModelService fms = new FriendModel();

	public void sendApplication(ApplicationVO appVo) {

		if(ums.findUser(appVo.getToId())==null){
			MessageFrame.show("不存在该名字用户");
		}
		if (fms.isFriend(appVo.getFromId(), appVo.getToId())) {
			MessageFrame.show("该玩家已是您的好友");
			return;
		}
		if(appVo.getToId().equals(appVo.getFromId())){
			MessageFrame.show("你卖萌呢！");
		}
		MessagePO mp = new MessagePO(false, appVo.getFromId(), appVo.getToId(),
				false, false, "");
		mms.saveMessage(mp);
		MessageFrame.show("好友申请已发送");
		AddressPO ap = ums.getIp(mp.getToId());
		if (ap == null) {
		} else {
			String ipString = ap.getAddress();
			ViewRMI vrs = new ViewRMI(ipString);
			vrs.newMess();
		}
	}

	public boolean sendInvitation(InvitationVO iv) {
		
		String id = iv.getFromId();
		if(mms.getInvitations(id).size()!=0){
			MessageFrame.show("你已在等待游戏队列中，不可重复邀请");
			return false;
		}
		ArrayList<String> listId = iv.getToIdList();
		
		for (int i = 0; i < listId.size(); i++) {
			MessagePO mp = new MessagePO(true, iv.getFromId(), listId.get(i),
					false, false, iv.getGameType());
			mms.saveMessage(mp);
			AddressPO ap = ums.getIp(mp.getToId());
			if (ap == null) {
			} else {
				String ipString = ap.getAddress();
				ViewRMI vrs = new ViewRMI(ipString);
				vrs.newMess();
			}
		}
		long time = (new Date()).getTime();
		while (true) {
			boolean answer = true;
			long nowTime = (new Date()).getTime();
			if ((nowTime - time) > 60000) {
				MessageFrame.show("等待时间超时，请重新开始游戏");
				mms.deleteInvitations(id);
				return false;
			} else {
				ArrayList<MessagePO> mpList = mms.getInvitations(id);
				for (int i = 0; i < mpList.size(); i++) {
					if (mpList.get(i).isIfRead()) {
						if (mpList.get(i).isReply()) {
							continue;																																																																																																																				
						} else {
							MessageFrame.show("有好友拒绝游戏邀请，请重新开始游戏");
							for (int x = 0; x < mpList.size(); x++) {
								if (mpList.get(x).isIfRead()) {
									AddressPO ap = ums.getIp(mpList.get(x)
											.getToId());
									if (ap == null) {
									} else {
										String ipString = ap.getAddress();
										ViewRMI vrs = new ViewRMI(ipString);
										vrs.show("该游戏已经取消");
									}
								}
							}
							mms.deleteInvitations(id);
							return false;
						}
					} else {
						answer = false;
						break;
					}
				}
				if (answer) {
					MessageFrame.show("游戏即将开始，请做好准备---");
					for (int i = 0; i < listId.size(); i++) {
						AddressPO ap = ums.getIp(listId.get(i));
						if (ap == null) {
						} else {
							String ipString = ap.getAddress();
							ViewRMI vrs = new ViewRMI(ipString);
							vrs.show("游戏即将开始，请做好准备---");
						}
					}
					mms.deleteInvitations(id);
					return true;
				}
			}
		}

	}

	public void replyApplication(ApplicationVO aVo, boolean reply) {
		MessagePO mp = new MessagePO(false, aVo.getToId(), aVo.getFromId(),
				true, reply, "");
		mms.deleteMessage(mp);
		if (reply) {
			FriendControlService fcs = new FriendControl();
			UserVO uv1 = fcs.findUser(aVo.getFromId());
			UserVO uv2 = fcs.findUser(aVo.getToId());
			fcs.addFriend(uv1.getId(), uv2);
			fcs.addFriend(uv2.getId(), uv1);
			AddressPO ap = ums.getIp(aVo.getToId());
			if (ap == null) {
			} else {
				String ip = ap.getAddress();
				ViewRMI vr = new ViewRMI(ip);
				String name = ums.findUser(aVo.getFromId()).getUserName();
				vr.show(name + "已成为您的好友");
			}
		} else {
			AddressPO ap = ums.getIp(aVo.getToId());
			if (ap == null) {
			} else {
				String ip = ap.getAddress();
				ViewRMI vr = new ViewRMI(ip);
				String name = ums.findUser(aVo.getFromId()).getUserName();
				vr.show(name + "拒绝添加好友");
			}
		}
	}

	public void replyInvitation(String fromId, String toId, boolean reply) {
		MessagePO mp = new MessagePO(true, toId, fromId, false, reply, "");
		ArrayList<MessagePO> list = mms.getMessage(fromId); 
		for(int i=0;i<list.size();i++){
		   
			if(mp.getFromId().equals(toId)&&mp.isType()){
			 mp = list.get(i);	
			 break;
			}
		}
		mp.setReply(reply);
		mp.setIfRead(true);
		mms.updateMessage(mp);

	}

	public void drawBackInvitat(String id) {

	}

	public ArrayList<ApplicationVO> getApplicationVO(String id) {
		ArrayList<ApplicationVO> list = new ArrayList<ApplicationVO>();
		ArrayList<MessagePO> list2 = mms.getMessage(id);
		for (int i = 0; i < list2.size(); i++) {
			MessagePO mp = list2.get(i);
			if ((!mp.isType()) & (!mp.isIfRead())) {
				UserPO up1 = ums.findUser(mp.getFromId());
				UserPO up2 = ums.findUser(mp.getToId());
				list.add(new ApplicationVO(up1.getUserId(), up1.getUserName(),
						up2.getUserId(), up2.getUserName()));
			}
		}
		return list;
	}

	public ArrayList<toInvitationVO> getInvitationVO(String id) {
		ArrayList<toInvitationVO> list = new ArrayList<toInvitationVO>();
		ArrayList<MessagePO> list2 = mms.getMessage(id);
		for (int i = 0; i < list2.size(); i++) {
			MessagePO mp = list2.get(i);
			if (!mp.isType()) {
				continue;
			}
			UserPO up1 = ums.findUser(mp.getFromId());
			list.add(new toInvitationVO(up1.getUserId(), up1.getUserName(), mp
					.getContent(), mp.getToId()));
		}
		return list;
	}
}
