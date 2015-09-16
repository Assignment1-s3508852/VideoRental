package view;

import static org.junit.Assert.assertEquals;
import controller.LoginEventListener;
import controller.LoginEventListener.TypeUser;
import controller.LoadVideoEventListener;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.Test;

import model.Video;
import model.Video.Categories;
import utilities.SQLAdapter;

public class VideoSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	private Scanner scannerInput;
	
	private LoginEventListener _loginBL = new LoginEventListener(this);
	private LoadVideoEventListener _loadVideoBL = new LoadVideoEventListener(this);
	
	TypeUser _typeUser;
	
	@Test
	public void testLogin() {
		System.out.println("testLogin");

		String testUsername = "champ_customer123";
		String testPassword = "123";
		
		assertEquals("invalid user!!!", 
				this._loginBL.verifyLogin(testUsername, testPassword), 
				this._loginBL.verifyLogin("champ_customer", "123"));
	}
	
	@Test 
	public void testShowInformationOfAdmin() {
		System.out.println("testShowInformationOfAdmin");
		
		String testInput = "1";
		String inputOption = this.getUserInputByShowingMessage("*****Option*****\n1 = information, 2 = function : ");
		assertEquals("wrong input!!!", 
				testInput, 
				inputOption);		
	}
	
	@Test
	public void testSearchVideo() {
		System.out.println("testSearchVideo");
		
		int numVideo = 1;
		String inputKeyword = this.getUserInputByShowingMessage("Keyword : ");
		
		assertEquals("wrong number of video", 
				numVideo, 
				this._loadVideoBL.getSearchByKeywordListOfVideos(inputKeyword).size());
	}
	
	@Test
	public void testSearchCatogoryVideo() {
		System.out.println("testSearchCatogoryVideo");
		
		int numVideo = 1;
		String inputCategory = this.getUserInputByShowingMessage("*****Categories*****\n1 = Comedy\n2 = Action\n3 = Family\n4 = Drama\n");
		assertEquals("wrong number of video", 
				numVideo, 
				this._loadVideoBL.getSearchByCategoryListOfVideos(Categories.values()[Integer.valueOf(inputCategory) - 1]).size());
	}
	
	public VideoSystem() {
//		SQLAdapter sqlAdapter = new SQLAdapter();
//		sqlAdapter.sqlConnect();

//		this.testLogin();
//		this.testShowInformationOfAdmin();
//		this.testSearchVideo();
//		this.testSearchCatogoryVideo();
		validateUser();
		presentOption();
	}
		
	private void validateUser() {
		String username = this.getUserInputByShowingMessage("Username :");
		String password = this.getUserInputByShowingMessage("Password : ");
		
		//validating username && password		
		_typeUser = this._loginBL.verifyLogin(username, password);
		if (_typeUser == TypeUser.TypeUserAdmin) { 
			System.out.println("show admin page");
			
		} else if (_typeUser == TypeUser.TypeUserClerk) {
			System.out.println("show clerk page");
		
		} else if (_typeUser == TypeUser.TypeUserCustomer) {
			System.out.println("show customer page");
			
		} else {
			System.out.println("no user");
			validateUser();
		}
	}
	
	void presentOption() {
		String inputOption = this.getUserInputByShowingMessage("*****Option*****\n1 = information, 2 = function : ");

		//Admin || Clerk
		//1 : show detail, 2 : show function 
		if (_typeUser == TypeUser.TypeUserAdmin || _typeUser == TypeUser.TypeUserClerk) {
			if (inputOption.equals("1")) {
				System.out.println(this._loginBL.getCurrentClerk().toString());
			} else if (inputOption.equals("2")) {
				String promptFunction = "*****Function*****\n1 = Add customer\n2 = Add video\n3 = Checkout video\n4 = Check return video\n";
				if (_typeUser == TypeUser.TypeUserAdmin)
					promptFunction += "5 = Communicate with customer";
				
				String inputFunction = this.getUserInputByShowingMessage(promptFunction);
				System.out.println(inputFunction);
			}
		}
		
		//Customer
		//1 : show detail, 2 : show function 
		else if (_typeUser == TypeUser.TypeUserCustomer) {
			if (inputOption.equals("1")) {
				System.out.println(this._loginBL.getCurrentCustomer().toString());
			
			} else if (inputOption.equals("2")) {
				String promptFunction = "*****Function*****\n1 = Show videos\n2 = Search by title\n3 = Search by category\n4 = Rent video\n";
				String inputFunction = this.getUserInputByShowingMessage(promptFunction);
				
				if (inputFunction.equals("1")) {
					this.showListOfVideos(this._loadVideoBL.getListOfVideos());
					this.presentOption();
					
				} else if (inputFunction.equals("2")) {
					String inputKeyword = this.getUserInputByShowingMessage("Search by keyword : ");
					if (inputKeyword.length() > 0) {
						this.showListOfVideos(this._loadVideoBL.getSearchByKeywordListOfVideos(inputKeyword));
					}
					this.presentOption();
				
				} else if (inputFunction.equals("3")) {
					String inputCategory = this.getUserInputByShowingMessage("*****Categories*****\n1 = Comedy\n2 = Action\n3 = Family\n4 = Drama\n");
					this.showListOfVideos(this._loadVideoBL.getSearchByCategoryListOfVideos(Categories.values()[Integer.valueOf(inputCategory) - 1]));
					this.presentOption();
				
				} else if (inputFunction.equals("4")) {
					String inputVideoID = this.getUserInputByShowingMessage("Which videoID : ");
					System.out.println(inputVideoID);
				}
			}
		}
	}
	
	private void showListOfVideos(Map<String, Video> aVideos) {
		for (Map.Entry<String, Video> entry : aVideos.entrySet()) {
		    Video value = entry.getValue();
		    
		    System.out.println(value.toString() + "\n");
		}
	}
	
	private String getUserInputByShowingMessage(String aMessage) {
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
