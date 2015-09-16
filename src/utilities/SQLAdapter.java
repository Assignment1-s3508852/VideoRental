package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLAdapter {
	
	static String host = "jdbc:oracle:thin:@emu.cs.rmit.edu.au:1521/GENERAL";
	static String username = "s3521070";
	static String password = "tFQUPwsW";
	
	public void sqlConnect() throws ClassNotFoundException {
		Connection connection = null;
		Class.forName("org.postgresql.Driver");

		try {

			connection = DriverManager.getConnection(
					host, 
					username,
					password);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
