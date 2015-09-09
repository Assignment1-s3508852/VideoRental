package controller;

import model.Customer;
import model.Clerk;
import view.VideoSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginEventListener implements ActionListener {
	VideoSystem _videoSystem;
	private Map<String, Customer> _mCustomer = new HashMap<String, Customer>();
	private Map<String, Clerk> _mClerk = new HashMap<String, Clerk>();
	
	private Customer _currentCustomer;
	private Clerk _currentClerk;
	
	public enum TypeUser {
		TypeUserCustomer, TypeUserClerk, TypeUserAdmin, TypeUserNone;
	}
	
	public TypeUser typeUser;
	
	public LoginEventListener(VideoSystem aSystem) {
		super();
		this._videoSystem = aSystem;
		
		//for testing
		//need to get this information from database
		Clerk admin = new Clerk();
		admin.init("champ_admin", "18 Footscray", "admin@student.rmit.edu.au", 0406124465, true);
		this._mClerk.put(admin.getName(), admin);
		
		Clerk notAdmin = new Clerk();
		notAdmin.init("champ_noAdmin", "18 Footscray", "not_admin@student.rmit.edu.au", 0406124465, false);
		this._mClerk.put(notAdmin.getName(), notAdmin);
		
		Customer customer = new Customer();
		customer.init(1, "champ_customer", "18 Footscray", "customer@student.rmit.edu.au", 0406124465, true);
		this._mCustomer.put(customer.getName(), customer);
	}
	
	public TypeUser verifyLogin(String aUsername, String aPassword) {
		String pass_ = "123";
//		String verifyUser;		
		if (this._mCustomer.containsKey(aUsername)) {
			this._currentCustomer = this._mCustomer.get(aUsername);
			if (aPassword.equals(pass_)) {
				return TypeUser.TypeUserCustomer;
			}
		}
		if (this._mClerk.containsKey(aUsername)) {
			if (aPassword.equals(pass_)) {
				this._currentClerk = this._mClerk.get(aUsername);
				return this._currentClerk.isAdmin()? TypeUser.TypeUserAdmin : TypeUser.TypeUserClerk; 
			} 
		}
		return TypeUser.TypeUserNone;
	}
	
	public Clerk getCurrentClerk() {
		return this._currentClerk;
	}
	
	public Customer getCurrentCustomer() {
		return this._currentCustomer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
