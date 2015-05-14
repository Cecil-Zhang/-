package vo;

import java.util.ArrayList;

public class InvitationVO {
	String fromId;
	String gameType;
	ArrayList<String> toIdList;
	public InvitationVO(String fromId,String gametype,ArrayList<String> toIdList) {
		super();
		this.fromId = fromId;
		this.gameType = gametype;
		this.toIdList = toIdList ;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getGameType(){
		return gameType;
	}
	public void setGameType(String gametype){
		this.gameType = gametype;
	}
	public ArrayList<String> getToIdList() {
		return toIdList;
	}
	public void setToIdList(ArrayList<String> list) {
		this.toIdList = list;
	}
	
}
