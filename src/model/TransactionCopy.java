package model;

import java.util.Date;

public class TransactionCopy {
	//| copyID | dateDue | status | dateReturn | rentalCharged |
	private Date dateDue;
	private Date dateReturn;
	private float rentalCharge;
	private char status;
	
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
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
}
