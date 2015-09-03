package view;

import controller.LoginEventListener;
import controller.LoginEventListener.TypeUser;

import java.util.Scanner;

import javax.swing.JFrame;

public class VideoSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	private Scanner scannerInput;
	
	public VideoSystem() {
		validateUser();
	}
	
	private void validateUser() {
		String username = this.showUserInputWith("Username :");
		String password = this.showUserInputWith("Password : ");
		
		//validating username && password
		LoginEventListener loginBL = new LoginEventListener(this);
		loginBL.typeUser = loginBL.verifyLogin(username, password);
		if (loginBL.typeUser == TypeUser.TypeUserAdmin) {
			System.out.println("show admin page");
		} else if (loginBL.typeUser == TypeUser.TypeUserClerk) {
			System.out.println("show clerk page");
		} else if (loginBL.typeUser == TypeUser.TypeUserCustomer) {
			System.out.println("show customer page");
		} else {
			System.out.println("no user");
			validateUser();
		}
	}
	
	private String showUserInputWith(String aMessage) {
		//initialize scanner class for entire process in order to make sure that the performance will not be used uncessary
		String input;
		try {
			if (scannerInput == null)
				scannerInput = new Scanner(System.in);
				
			System.out.print(aMessage);
			input = scannerInput.next();
			return input;
		}
		catch (NumberFormatException aEx) {
			throw (aEx);
		}
	}
}
