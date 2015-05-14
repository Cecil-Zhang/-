package po;

import java.io.Serializable;

public class AddressPO implements Serializable {
	String id;
	String address;
	public AddressPO(String id, String address) {
		super();
		this.id = id;
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
