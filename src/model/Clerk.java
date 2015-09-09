package model;

public class Clerk {
	private boolean admin = false;
	private String name;
	private String address;
	private String email;
	private int tel;
	
	public void init(String aName, String aAddress, String aEmail, int aTel, boolean aIsAdmin) {
		this.name = aName;
		this.address = aAddress;
		this.email = aEmail;
		this.tel = aTel;
		this.admin = aIsAdmin;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public String toString() {
		return "name : " + name + "\n" + "admin : " + (admin? "Yes" : "No") + "\n" + "address : " + address + "\n" + "email : " + email + "\n" + "tel : " + Integer.toString(tel);
	}
}
