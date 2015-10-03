package model;

import java.util.Date;

public class Transaction {
	private int transactionID;
	protected Date rentalDate;
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public Date getRentalDate() {
		return rentalDate;
	}
	
	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}
}
