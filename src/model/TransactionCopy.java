package model;

import java.util.Date;

public class TransactionCopy {
	private Date dateDue;
	private Date dateReturn;
	private float rentalCharge;
	private boolean active;
	public Date getDateDue() {
		return dateDue;
	}
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}
	public Date getDateReturn() {
		return dateReturn;
	}
	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}
	public float getRentalCharge() {
		return rentalCharge;
	}
	public void setRentalCharge(float rentalCharge) {
		this.rentalCharge = rentalCharge;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
