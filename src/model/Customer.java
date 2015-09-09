package model;

public class Customer {
	private int customerID;
	private String name;
	private String address;
	private String email;
	private int telNo;
	private boolean rating = false;
	
	public void init(int aCustID, String aName, String aAddress, String aEmail, int aTel, boolean aRating) {
		this.name = aName;
		this.address = aAddress;
		this.email = aEmail;
		this.telNo = aTel;
		this.rating = aRating;
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
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

	public int getTelNo() {
		return telNo;
	}

	public void setTelNo(int telNo) {
		this.telNo = telNo;
	}

	public boolean isRating() {
		return rating;
	}

	public void setRating(boolean rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "name : " + name + "\n" + "admin : " + (rating? "Yes" : "No") + "\n" + "address : " + address + "\n" + "email : " + email + "\n" + "tel : " + Integer.toString(telNo);	
	}
}
