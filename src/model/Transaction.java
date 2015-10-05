package model;

public class Transaction {
	private int transacID;
	protected String rentaldate;
	protected int custID;
	private boolean reviewed;
	
	public void init(int aTransacID, String aRentaldate, int aCustID, String aReviewed) {
		this.transacID = aTransacID;
		this.rentaldate = aRentaldate;
		this.custID = aCustID;
		this.reviewed = aReviewed.equals("0")? false : true;
	}
	
	public int getTransacID() {
		return transacID;
	}
	public void setTransacID(int transacID) {
		this.transacID = transacID;
	}
	public boolean isReviewed() {
		return reviewed;
	}
	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
	public String getRentaldate() {
		return this.rentaldate;
	}
	public void setRentaldate(String aRentaldate) {
		this.rentaldate = aRentaldate;
	}
	public int getCustID() {
		return this.custID;
	}
	public void setCustID(int aCustID) {
		this.custID = aCustID;
	}
}
