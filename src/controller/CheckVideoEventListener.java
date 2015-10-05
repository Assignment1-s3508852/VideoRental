package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.SQLAdapter;
import model.Clerk;
import model.Transaction;
import model.Video;
import model.Customer;

public class CheckVideoEventListener implements ActionListener {
	private Map<String, Transaction> _mTransaction = new HashMap<String, Transaction>();
	
	public CheckVideoEventListener() {
		super();
		this.loadTransaction(SQLAdapter.getInstance());
	}
	
	public boolean checkoutVideo() {
		return true;
	}
	
	public boolean checkReturnVideo() {
		return true;
	}
	
	public boolean rentVideo(Video aVideo) {		
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();

		int copyID = aVideo.getVideoCopy().getCopyID();
		List<Object> objects = new ArrayList<Object>();
		objects.add(false);
		objects.add(copyID);
		if (sqlAdapter.updateTable("UPDATE VIDEO_COPY SET available = ? WHERE copyID = ?", objects))
			return true;
		return false;
	}
	
	public boolean createTransaction(Customer aCustomer, Video aVideo) {
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(this._mTransaction.size() + 1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		objects.add(dateFormat.format(date));
		objects.add(aCustomer.getCustomerID());		
		objects.add(false);
			if (sqlAdapter.insertIntoTable("INSERT INTO TRANSACTION VALUES (?, ?, ?, ?)", objects))
				return true;		
		return false;
	}
	
	public boolean returnVideo() {
		return true;
	}
	
	private void loadTransaction(SQLAdapter aSQLAdapter) {
		this._mTransaction.clear();

		String[] mappingTransaction = {"transacID", "rentaldate", "custID", "reviewed"};
		
		ArrayList<Map<String, String>> dataList = aSQLAdapter.getData(mappingTransaction, "select * from TRANSACTION");
		for (Map<String, String> mapTransaction : dataList) {
			Transaction transaction = new Transaction();
			transaction.init(
					Integer.parseInt(mapTransaction.get("transacID")), 
					mapTransaction.get("rentaldate"), 
					Integer.parseInt(mapTransaction.get("custID")), 
					mapTransaction.get("reviewed"));
			if (transaction != null)
				this._mTransaction.put(String.valueOf(transaction.getTransacID()), transaction);
		}
		System.out.println(this._mTransaction);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
