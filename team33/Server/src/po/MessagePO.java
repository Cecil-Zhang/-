package po;

import java.io.Serializable;

public class MessagePO implements Serializable{
	boolean type;
	String fromId;
	String toId;
	boolean ifRead;
	boolean reply;
	String content;
	public MessagePO(Boolean t,String fId,String tId, boolean ifRead,
			boolean reply,String content) {
		super();
		this.type = t;
		this.fromId = fId;
		this.toId = tId;
		this.ifRead = ifRead;	
		this.reply = reply;
		this.content = content;
	}
	public boolean isType(){
		return type;
	}
	public void setType(boolean t){
		this.type = t;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String id) {
		this.fromId = id;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String id) {
		this.toId = id;
	}
	public boolean isIfRead() {
		return ifRead;
	}
	public void setIfRead(boolean ifRead) {
		this.ifRead = ifRead;
	}
	public void setReply(boolean reply){
		this.reply = reply;
	}
	public boolean isReply(){
		return reply;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
		
	}
}
