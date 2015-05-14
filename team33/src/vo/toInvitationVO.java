package vo;

public class toInvitationVO {

	String fromId;
	String fromName;
	String gameType;
	String toId;
	
	public toInvitationVO(String fromId,String fromName,String gameType,String toId){
		this.fromId = fromId;
		this.fromName = fromName;
		this.gameType = gameType;
		this.toId = toId;
	}
	
	public String getFromId(){
		return fromId;
	}
	public void setFromId(String fromId){
		this.fromId = fromId;
	}
	public String getFromName(){
		return fromName;
	}
	public void setFromName(){
		this.fromName = fromName;
	}
	
	public String getGameType(){
		return gameType;
	}
	public void setGameType(String gameType){
		this.gameType = gameType;
	}
	
	public String getToId(){
		return toId;
	}
	public void setToId(String toId){
		this.toId = toId;
	}
	
}
