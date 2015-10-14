package view;

import controller.LoginEventListener;
import controller.LoginEventListener.TypeUser;
import controller.LoadVideoEventListener;
import controller.CheckVideoEventListener;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.Test;

import model.Customer;
import model.ObjectEvent;
import model.Transaction;
import model.TransactionCopy;
import model.Video;
import model.Video.Categories;
import model.VideoCopy;
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
		String username = this.getUserInputByShowingMessage("Username : ");
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
					this.presentOption();
					
				//add video
				} else if (inputFunction.equals("2")) {					
					String inputTitle = this.getUserInputByShowingMessage("Title : ");
					String inputRentalCharge = this.getUserInputByShowingMessage("RentalCharge : ");
					String inputCat = this.getUserInputByShowingMessage("Category[1:Comedy, 2:Action, 3:Family, 4:Drama] : ");
					Categories category;
					//Comedy, Action, Family, Drama
					if (inputCat.equals("1"))
						category = Categories.Comedy;
					else if (inputCat.equals("2"))
						category = Categories.Action;
					else if (inputCat.equals("3"))
						category = Categories.Family;
					else
						category = Categories.Drama;
					
					String inputPeriod = this.getUserInputByShowingMessage("Period : ");
					String inputYear = this.getUserInputByShowingMessage("Year release : ");
					String inputOverdueCharge = this.getUserInputByShowingMessage("Overdue charge : ");
					String inputNoOfCopy = this.getUserInputByShowingMessage("No. of copy : ");
					
					ObjectEvent event = this._loadVideoBL.addVideo(inputTitle, 
							Float.parseFloat(inputRentalCharge),
							category,  
							Integer.parseInt(inputPeriod),  
							inputYear, 
							Float.parseFloat(inputOverdueCharge), 
							inputNoOfCopy);
					System.out.println(event.resultMessage);
					this.presentOption();
				
				//checkout video
				} else if (inputFunction.equals("3")) {
					List<Transaction> unrevieweds = this._checkVideo.getWaitingReviewTransactions();
					for (Transaction transaction : unrevieweds) {
						System.out.println(transaction);
						if (transaction.getVideoCopyIDs() != null) {
							ObjectEvent event = this._checkVideo.checkoutVideo(transaction, 
									this._loadVideoBL.getListOfVideoCopys(), 
									this._loadVideoBL.getListOfVideos());
							System.out.println(event.resultMessage);
						}
					}
					this.presentOption();
					
				//check return video
				} else if (inputFunction.equals("4")) {
					String custName = this.getUserInputByShowingMessage("Return video by customer name : ");
					String transcopyID = this.getUserInputByShowingMessage("Return video by transacCopyID : ");
					
					TransactionCopy transCopy = this._checkVideo.getTransactionCopy(transcopyID);
					Transaction trans = this._checkVideo.getTransaction(String.valueOf(transCopy.getTransacID()));
					VideoCopy videoCopy = this._loadVideoBL.getVideoCopy(String.valueOf(transCopy.getVideoCopyID()));
					Video video = this._loadVideoBL.getVideo(String.valueOf(videoCopy.getVideoID()));
					
					String dateReturn = this.getUserInputByShowingMessage("No. of day rent : ");
					String damaged = this.getUserInputByShowingMessage("Damage[1:NO, 2:YES] : ");
					
					ObjectEvent objEvent = this._checkVideo.returnVideo(this._loginBL.getCustomerWithName(custName), 
							trans, 
							transCopy, 
							video, 
							videoCopy, 
							dateReturn, 
							damaged);
					System.out.println(objEvent.resultMessage);
					this.presentOption();
						
				//show all video
				} else if (inputFunction.equals("5")) {
					this.showListOfVideos(this._loadVideoBL.getListOfVideos());
					this.presentOption();
					
				//communicate with users
				} else if (inputFunction.equals("6")) {
					String inputTrans = this.getUserInputByShowingMessage("Email to transactionID : ");
					Transaction transac = this._checkVideo.getTransaction(inputTrans);
					if (transac != null) {
						Customer cust = this._loginBL.getCustomerWithID(transac.getCustID());
						ObjectEvent objEvent = this._checkVideo.emailCustomer(cust, 
								transac, 
								this._loadVideoBL.getListOfVideoCopys(), 
								this._loadVideoBL.getListOfVideos());
						System.out.println(objEvent.resultMessage);
					}
					this.presentOption();
				}
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
					if (Integer.parseInt(inputNoOfVideoRent) > 5) {
						System.out.println("5 movies are limited for rent and reserve.");
						this.presentOption();
						return;
					}
					String listOfVideo = "";
					String listOfVideoReserve = "";
					for (int i = 0; i < Integer.parseInt(inputNoOfVideoRent); i++) {
						String inputVideoID = this.getUserInputByShowingMessage("Which videoID : ");
						ObjectEvent objEvent =  this._loadVideoBL.getVideoCopyFromVideoID(inputVideoID);
						Video video = (Video)objEvent.objResult;
						if (objEvent.resultMessage.equals(" unavailable")) {
							String inputReserve = this.getUserInputByShowingMessage("Do you want to reserve?[1:NO, 2:YES] : ");
							if (inputReserve.equals("2"))
								listOfVideoReserve += String.valueOf(video.getVideoCopy().getCopyID()) + ",";								
						
						} else if (objEvent.isSuccessful) {
							boolean isRent = this._loadVideoBL.rentVideo(video);
							if (isRent) {
								System.out.println(video.getTitle() + " is rent");
								listOfVideo += String.valueOf(video.getVideoCopy().getCopyID()) + ",";
							}
						
						} else {
							i--;
							System.out.println(video.getTitle() + objEvent.resultMessage);
						}
					}
					if (listOfVideo != null && listOfVideo.length() > 1)
						listOfVideo = listOfVideo.substring(0, listOfVideo.length() - 1);
						
					if (listOfVideoReserve != null && listOfVideoReserve.length() > 1) 
						listOfVideoReserve = listOfVideoReserve.substring(0, listOfVideoReserve.length() - 1);
					
					if (this._checkVideo.createTransaction(this._loginBL.getCurrentCustomer(), listOfVideo, listOfVideoReserve))
						System.out.println("Rent successfully");

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
