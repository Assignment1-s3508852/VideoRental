package model;

import java.util.Date;

public class VideoCopy {
	private int copyID;
	private String datePurchase;
	private boolean damage;
	private boolean availale;
	private int noOfRent;

	public void init(int aCopyID, String aDatePurchase, boolean aDamage, boolean aAvailale, int aNoOfRent) {
		this.copyID = aCopyID;
		this.datePurchase = aDatePurchase;
		this.damage = aDamage;
		this.availale = aAvailale;
		this.noOfRent = aNoOfRent;
	}

	public int getCopyID() {
		return copyID;
	}

	public void setCopyID(int copyID) {
		this.copyID = copyID;
	}

	public String getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	public boolean isDamage() {
		return damage;
	}

	public void setDamage(boolean damage) {
		this.damage = damage;
	}

	public boolean isAvailale() {
		return availale;
	}

	public void setAvailale(boolean availale) {
		this.availale = availale;
	}

	public int getNoOfRent() {
		return noOfRent;
	}

	public void setNoOfRent(int noOfRent) {
		this.noOfRent = noOfRent;
	}
}
