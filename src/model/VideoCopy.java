package model;

import java.util.Date;

public class VideoCopy {
	private int unitID;
	private Date datePurchased;
	private boolean damaged;
	private boolean availale;
	private int noOfTimeRented;
	public int getUnitID() {
		return unitID;
	}
	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}
	public Date getDatePurchased() {
		return datePurchased;
	}
	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}
	public boolean isAvailale() {
		return availale;
	}
	public void setAvailale(boolean availale) {
		this.availale = availale;
	}
	public boolean isDamaged() {
		return damaged;
	}
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	public int getNoOfTimeRented() {
		return noOfTimeRented;
	}
	public void setNoOfTimeRented(int noOfTimeRented) {
		this.noOfTimeRented = noOfTimeRented;
	}
}
