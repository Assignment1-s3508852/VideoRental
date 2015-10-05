package view;

import controller.LoginEventListener;
import controller.LoginEventListener.TypeUser;
import controller.LoadVideoEventListener;
import controller.CheckVideoEventListener;

import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.Test;

import model.Customer;
import model.ObjectEvent;
import model.Video;
import model.Video.Categories;
import utilities.SQLAdapter;

public class VideoSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	private Scanner scannerInput;
	
	private LoginEventListener _loginBL = null;
	private LoadVideoEventListener _loadVideoBL = null; 
	private CheckVideoEventListener _checkVideo = null;
	
	TypeUser _typeUser;
	
	@Test
	public void testLogin() {
		System.out.println("testLogin");

//		String testUsername = "champ_customer123";
//		String testPassword = "123";
//		
//		assertEquals("invalid user!!!", 
//				this._loginBL.verifyLogin(testUsername, testPassword), 
//				this._loginBL.verifyLogin("champ_customer", "123"));
	}
	
//	@Test 
//	public void testShowInformationOfAdmin() {
//		System.out.println("testShowInformationOfAdmin");
//		
//		String testInput = "1";
//		String inputOption = this.getUserInputByShowingMessage("*****Option*****\n1 = information, 2 = function : ");
//		assertEquals("wrong input!!!", 
//				testInput, 
//				inputOption);		
//	}
//	
//	@Test
//	public void testSearchVideo() {
//		System.out.println("testSearchVideo");
//		
//		int numVideo = 1;
//		String inputKeyword = this.getUserInputByShowingMessage("Keyword : ");
//		
//		assertEquals("wrong number of video", 
//				numVideo, 
//				this._loadVideoBL.getSearchByKeywordListOfVideos(inputKeyword).size());
//	}
//	
//	@Test
//	public void testSearchCatogoryVideo() {
//		System.out.println("testSearchCatogoryVideo");
//		
//		int numVideo = 1;
//		String inputCategory = this.getUserInputByShowingMessage("*****Categories*****\n1 = Comedy\n2 = Action\n3 = Family\n4 = Drama\n");
//		assertEquals("wrong number of video", 
//				numVideo, 
//				this._loadVideoBL.getSearchByCategoryListOfVideos(Categories.values()[Integer.valueOf(inputCategory) - 1]).size());
//	}
	
	public VideoSystem() {
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();
		try {
			if (sqlAdapter.sqlConnect()) {
				_loginBL = new LoginEventListener(this);
				_loadVideoBL = new LoadVideoEventListener(this);
				_checkVideo = new CheckVideoEventListener();
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		
		}

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
			
		} else if (_typeUser == TypeUser.TypeUserIncorrectPassoword) {
			System.out.println("Incorrect password!!!");
			validateUser();
		
		} else {
			System.out.println("Invalid username!!!");
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
				String promptFunction = "*****Function*****\n1 = Add customer\n2 = Add video\n3 = Checkout video\n4 = Check return video\n5 = Show all videos\n";
				if (_typeUser == TypeUser.TypeUserAdmin)
					promptFunction += "6 = Communicate with customer\n";
				
				String inputFunction = this.getUserInputByShowingMessage(promptFunction);
				
				//add customer
				if (inputFunction.equals("1")) {
					//insert into VIDEO values (001, 'Batman', 1.00, 'Adventure', 7, 2000, 5.00);
					//CustID | name    | address  | email | Custel | rating | password
					String inputName = this.getUserInputByShowingMessage("Name : ");
					String inputAddress = this.getUserInputByShowingMessage("Address : ");
					String inputEmail = this.getUserInputByShowingMessage("Email : ");
					String inputTel = this.getUserInputByShowingMessage("Tel : ");
					String inputRating = this.getUserInputByShowingMessage("Rating[1:Standard, 2:Premium] : ");
					String inputPassword = this.getUserInputByShowingMessage("Password : ");
					String inputConfirmPassword = this.getUserInputByShowingMessage("Re-Password : ");
					if (inputPassword.equals(inputConfirmPassword)) {
						if (this._loginBL.addCustomer(inputName, inputAddress, inputEmail, inputTel, inputRating, inputPassword)) {							
							System.out.println("Add customer successfully!!!");
							if (inputRating.equals("1"))
								System.out.println("Standard type -- $50 must be pay for joining fee");
							else
								System.out.println("Premium type -- 10% will be discount for every movie rental");
						} else {
							System.out.println("Add customer unsuccessfully!!!");
						}
					}
					
				//add video
				} else if (inputFunction.equals("2")) {
					//Assign to Frank					
					
//					String inputTitle = this.getUserInputByShowingMessage("Title : ");
//					String inputRentalCharge = this.getUserInputByShowingMessage("RentalCharge : ");
//					String inputCat = this.getUserInputByShowingMessage("Category[1:Comedy, 2:Action, 3:Family, 4:Drama] : ");
//					Categories category;
//					//Comedy, Action, Family, Drama
//					if (inputCat.equals("1"))
//						category = Categories.Comedy;
//					else if (inputCat.equals("2"))
//						category = Categories.Action;
//					else if (inputCat.equals("3"))
//						category = Categories.Family;
//					else
//						category = Categories.Drama;
//					
//					String inputPeriod = this.getUserInputByShowingMessage("Period : ");
//					String inputYear = this.getUserInputByShowingMessage("Year release : ");
//					String inputOverdueCharge = this.getUserInputByShowingMessage("Overdue charge : ");
//					
//					boolean isAdded = this._loadVideoBL.addVideo(inputTitle, 
//							Float.parseFloat(inputRentalCharge),
//							category,  
//							Integer.parseInt(inputPeriod),  
//							inputYear, 
//							Float.parseFloat(inputOverdueCharge), 
//							null);
//					if (isAdded)
//						System.out.println("Add Successfully");
//					
//					this.presentOption();
				
				//check out video
				} else if (inputFunction.equals("3")) {
				//Assign to Frank					

					
				//check return video
				} else if (inputFunction.equals("4")) {
				//Assign to Matt
					
				//show all video
				} else if (inputFunction.equals("5")) {
					this.showListOfVideos(this._loadVideoBL.getListOfVideos());
					this.presentOption();
					
				//communicate with users
				} else if (inputFunction.equals("6"))
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
				
				//Show all videos
				if (inputFunction.equals("1")) {
					this.showListOfVideos(this._loadVideoBL.getListOfVideos());
					this.presentOption();
					
				//Search by title
				} else if (inputFunction.equals("2")) {
					String inputKeyword = this.getUserInputByShowingMessage("Search by keyword : ");
					if (inputKeyword.length() > 0) {
						this.showListOfVideos(this._loadVideoBL.getSearchByKeywordListOfVideos(inputKeyword));
					}
					this.presentOption();
				
				//Search by category
				} else if (inputFunction.equals("3")) {
					String inputCategory = this.getUserInputByShowingMessage("*****Categories*****\n1 = Comedy\n2 = Action\n3 = Family\n4 = Drama\n");
					this.showListOfVideos(this._loadVideoBL.getSearchByCategoryListOfVideos(Categories.values()[Integer.valueOf(inputCategory) - 1]));
					this.presentOption();
				
				//Rent video
				} else if (inputFunction.equals("4")) {
					String inputNoOfVideoRent = this.getUserInputByShowingMessage("No of video rent : ");
					for (int i = 0; i < Integer.parseInt(inputNoOfVideoRent); i++) {
						String inputVideoID = this.getUserInputByShowingMessage("Which videoID : ");
						ObjectEvent objEvent =  this._loadVideoBL.getVideoCopyFromVideoID(inputVideoID);
						if (objEvent.isSuccessful) {
							Video video = (Video)objEvent.objResult;
							boolean isRent = this._checkVideo.rentVideo(video);
							if (isRent) {
								//create transaction with uniqueID (custID)
								Customer customer = this._loginBL.getCurrentCustomer();
								boolean isTransactionCreated = this._checkVideo.createTransaction(customer, video);								
								if (isTransactionCreated)
									System.out.println("rent succesfully");						
							}
						} else {
							System.out.println(objEvent.resultMessage);
						}
					}
					this.presentOption();
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
			input = scannerInput.nextLine();
			return input;
		}
		catch (NumberFormatException aEx) {
			throw (aEx);
		}
	}
}
