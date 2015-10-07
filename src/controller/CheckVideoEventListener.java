package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.SQLAdapter;
import model.ObjectEvent;
import model.Transaction;
import model.TransactionCopy;
import model.Video;
import model.Customer;
import model.VideoCopy;

public class CheckVideoEventListener implements ActionListener {
	private Map<String, Transaction> _mTransaction = new HashMap<String, Transaction>();
	private Map<String, TransactionCopy> _mTransactionCopy = new HashMap<String, TransactionCopy>();
	public CheckVideoEventListener() {
		super();
		this.loadTransactions(SQLAdapter.getInstance());
		this.loadTransactionCopys(SQLAdapter.getInstance());
	}
	
	public Map<String, Transaction> getTransactions() {
		return this._mTransaction;
	}
	
	public Map<String, TransactionCopy> getTransactionCopys() {
		return this._mTransactionCopy;
	}
	
	public Transaction getTransaction(String aTransactionID) {
		if (this._mTransaction.containsKey(aTransactionID))
			return this._mTransaction.get(aTransactionID);
		return null;
	}
	
	public TransactionCopy getTransactionCopy(String aCopyID) {
		if (this._mTransactionCopy.containsKey(aCopyID))
			return this._mTransactionCopy.get(aCopyID);
		return null;
	}
	
	public ObjectEvent checkoutVideo(Transaction aTransaction, Map<String, VideoCopy> aVideoCopys, Map<String, Video> aVideos) {
		ObjectEvent event = new ObjectEvent();
		event.isSuccessful = false;
		event.resultMessage = "";
		
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();
		int transacID = aTransaction.getTransacID();
		List<Object> objForTrans = new ArrayList<Object>();
		objForTrans.add(true);
		objForTrans.add(transacID);
		if (sqlAdapter.updateTable("UPDATE TRANSACTION SET reviewed = ? WHERE transacID = ?", objForTrans)) {				
			event.isSuccessful = true;
			
			String[] listOfCopyID = aTransaction.getVideoIDs().split(",");
			for (String copyID : listOfCopyID) {
				List<Object> objForTransCopy = new ArrayList<Object>();
				objForTransCopy.add(this._mTransactionCopy.size() + 1);
				
				VideoCopy copy = aVideoCopys.get(copyID);
				Video video = aVideos.get(String.valueOf(copy.getVideoID()));
				
				String sRentalDate = aTransaction.getRentaldate();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date dateDue = null;
				
				int returnDate = video.getRentPeriod();
				try {
					Date rentalDate = format.parse(sRentalDate);
					dateDue = new Date(rentalDate.getTime() + (1000 * 60 * 60 * 24 * returnDate));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				objForTransCopy.add(format.format(dateDue)); 
				objForTransCopy.add(true);
				objForTransCopy.add(format.format(dateDue));				
				objForTransCopy.add(0);
				objForTransCopy.add(aTransaction.getTransacID());
				objForTransCopy.add(copyID);
				
				String coMessage = "";
				if (sqlAdapter.insertIntoTable("INSERT INTO TRANSACTION_COPY VALUES (?, ?, ?, ?, ?, ?, ?)", objForTransCopy)) {
					coMessage = video.getTitle() + "is checkouted successfully.\n";
					this.loadTransactionCopys(sqlAdapter);
				} else {
					coMessage = video.getTitle() + "is checkouted unsuccessfully.\n";
				}
				event.resultMessage = event.resultMessage + coMessage;
			}	
			return event;
		} else {
			event.resultMessage = "Transaction is update unsuccessfully.";
		}
		return event;
	}
	
	public boolean checkReturnVideo() {
		return true;
	}
		
	public boolean createTransaction(Customer aCustomer, String aListOfVideoID) {
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();

		List<Object> objects = new ArrayList<Object>();
		objects.add(this._mTransaction.size() + 1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		objects.add(dateFormat.format(date));
		objects.add(aCustomer.getCustomerID());		
		objects.add(false);
		objects.add(aListOfVideoID);
		if (sqlAdapter.insertIntoTable("INSERT INTO TRANSACTION VALUES (?, ?, ?, ?, ?)", objects)) {
			this.loadTransactions(sqlAdapter);
			return true;		
		}
		return false;
	}
	
	public ObjectEvent returnVideo(Customer aCustomer, Transaction aTrans, TransactionCopy aTransacCopy, Video aVideo, VideoCopy aVideoCopy, String aNoOfReturn, String aDamaged) {
		float charge = aVideo.getRentalCharge();
		//check if rental overdue
		int allowDay = aVideo.getRentPeriod();
		int totalRent = Integer.parseInt(aNoOfReturn);
		int dayOver = totalRent - allowDay;
		if (dayOver > 0) {
			charge += dayOver * aVideo.getOverdueCharge();
		}

		//discount for premium user
		if (aCustomer.isRating()) {
			charge = (float) (charge * 0.90); 
		}
				
		ObjectEvent event = new ObjectEvent(); 
		event.isSuccessful = false;
		event.resultMessage = "";
		
		SQLAdapter adapter = SQLAdapter.getInstance();
		//check if damage
		if (aDamaged.equals("2")) {
			//update SQL 
			List<Object> objUpdate = new ArrayList<Object>();
			objUpdate.add(aVideoCopy.getCopyID());
			if (adapter.updateTable("UPDATE VIDEO_COPY SET damage = 1 WHERE copyID = ?", objUpdate))
				event.resultMessage = "item is updated as damaged\n";
		}
		
		//update sql
		List<Object> objUpdate = new ArrayList<Object>();
		objUpdate.add(true);
		objUpdate.add(aVideoCopy.getNoOfRent() + 1);
		objUpdate.add(aVideoCopy.getCopyID());
		
		String videoName = aVideo.getTitle();
		if (adapter.updateTable("UPDATE VIDEO_COPY SET available = ?, noOfRent = ? WHERE copyID = ?", objUpdate)) {
			
			List<Object> objTransCopyUpdate = new ArrayList<Object>();
			String sRentalDate = aTrans.getRentaldate();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dateReturn = null;
			
			int returnDate = totalRent;
			try {
				Date rentalDate = format.parse(sRentalDate);
				dateReturn = new Date(rentalDate.getTime() + (1000 * 60 * 60 * 24 * returnDate));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			objTransCopyUpdate.add(dateReturn);
			objTransCopyUpdate.add(charge);
			objTransCopyUpdate.add(aTransacCopy.getCopyID());
			if (adapter.updateTable("UPDATE TRANSACTION_COPY SET dateReturn = ?, rentalCharged = ? WHERE copyID = ?", objTransCopyUpdate)) {
				event.isSuccessful = true;
				event.resultMessage += videoName + " is return\nRental change is " + charge;
			}
		}
		return event;
	}
	
	public List<Transaction> getWaitingReviewTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (Map.Entry<String, Transaction> entry : this._mTransaction.entrySet()) {
			Transaction transaction = entry.getValue();
			if (!transaction.isReviewed()) 
				transactions.add(transaction);
		}
		return transactions;
	}
	
	private void loadTransactions(SQLAdapter aSQLAdapter) {
		this._mTransaction.clear();

		String[] mappingTransaction = {"transacID", "rentaldate", "custID", "reviewed", "videoIDs"};
		
		ArrayList<Map<String, String>> dataList = aSQLAdapter.getData(mappingTransaction, "select * from TRANSACTION");
		for (Map<String, String> mapTransaction : dataList) {
			Transaction transaction = new Transaction();
			transaction.init(
					Integer.parseInt(mapTransaction.get("transacID")), 
					mapTransaction.get("rentaldate"), 
					Integer.parseInt(mapTransaction.get("custID")), 
					mapTransaction.get("reviewed"), 
					mapTransaction.get("videoIDs"));
			if (transaction != null)
				this._mTransaction.put(String.valueOf(transaction.getTransacID()), transaction);
		}
		System.out.println(this._mTransaction);
	}
	
	private void loadTransactionCopys(SQLAdapter aSQLAdapter) {
		this._mTransactionCopy.clear();

		String[] mappingTransaction = {"copyID", "dateDue", "status", "dateReturn", "rentalCharged", "transacID", "videoCopyID"};
		
		ArrayList<Map<String, String>> dataList = aSQLAdapter.getData(mappingTransaction, "select * from TRANSACTION_COPY");
		for (Map<String, String> mapTransactionCopy : dataList) {
			TransactionCopy transCopy = new TransactionCopy();
			transCopy.init(
					Integer.parseInt(mapTransactionCopy.get("copyID")), 
					mapTransactionCopy.get("dateDue"), 
					mapTransactionCopy.get("dateReturn"),  
					mapTransactionCopy.get("rentalCharged"), 
					mapTransactionCopy.get("status"), 
					Integer.parseInt(mapTransactionCopy.get("transacID")), 
					Integer.parseInt(mapTransactionCopy.get("videoCopyID")));
			if (transCopy != null)
				this._mTransactionCopy.put(String.valueOf(transCopy.getCopyID()), transCopy);
		}
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
