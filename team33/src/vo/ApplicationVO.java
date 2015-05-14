package vo;

public class ApplicationVO {

	String fromId;
	String fromName;
	String toId;
	String toName;
	
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFromName(){
		return fromName;
	}
	public void setFromName(String fromName){
		this.fromName = fromName;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;

	}
	public String getToName(){
		return toName;
	}
	public void setToName(){
		this.toName = toName;
	}
	
	public ApplicationVO(String fromId, String fromName,String toId,String toName) {
		super();
		this.fromId = fromId;
		this.fromName = fromName;
		this.toId = toId;
		this.toName = toName;

	}
}
