package org.lobo.java.webapps;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletContext;

public class DatabaseLogic {
	// Use a single connection
	private static Connection connect;
	private static final String dbUrl;

	static {
		String dbServer = System.getenv("MYSQL_SERVER");
		if (dbServer == null) {
			dbServer = "localhost";
		}
		dbUrl = "jdbc:mysql://" + dbServer + "/test?user=smlobo&password=welcome";
	}
	
	private static boolean isNumeric(String str)
	{
		if (str == null || "".equals(str))
			return false;
		
	    for (char c : str.toCharArray()) {
	        if (!Character.isDigit(c)) 
	        	return false;
	    }
	    return true;
	}
	
	public static ResultSet query(PrintWriter out, String rID) {
		ResultSet resultSet = null;
		try {
			// Needed to load the mysql jdbc driver into the classpath
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);
			PreparedStatement pStatement = null;
			if (isNumeric(rID)) {
				pStatement = connect.prepareStatement
						("select * from test.book_list where id = " + rID);
			}
			else {
				pStatement = connect.prepareStatement("select * from test.book_list");
			}
			resultSet = pStatement.executeQuery();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad query");
		}
		return resultSet;
	}

	public static void addRow(PrintWriter out, String book, int rating, String application) {
		// Current date
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		//out.println("Adding: " + book + ", " + rating + ", " + date + ", " + timestamp + application);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);

			PreparedStatement pStatement = connect.prepareStatement
					("insert into test.book_list (name, rating, date_added, application) " + 
						"values (?, ?, ?, ?)");
			pStatement.setString(1,  book);
			pStatement.setInt(2,  rating);
			pStatement.setString(3, timestamp.toString());
			pStatement.setString(4, application);
			pStatement.executeUpdate();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad insert: " + e);
		}
	}

	public static String deleteRow(PrintWriter out, int id) {
		String bookName = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);

			// Get the name of the book being deleted
			PreparedStatement pStatement = connect.prepareStatement
					("select name from test.book_list where id = " + id);
			ResultSet resultSet = pStatement.executeQuery();
			
			// If empty - invalid id
			if (!resultSet.next())
				return bookName;
			
			bookName = resultSet.getString("name");
			
			// Actually delete the row
			pStatement = connect.prepareStatement
					("delete from test.book_list where id = " + id);
			pStatement.executeUpdate();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad delete: " + e);
		}
		return bookName;
	}

	public static String deleteLastRow(PrintWriter out) {
		String bookName = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);

			// Get the name of the book being deleted
			PreparedStatement pStatement = connect.prepareStatement
					("select id, name from test.book_list");
			ResultSet resultSet = pStatement.executeQuery();
			
			// Get the last row name & ID
			String id = null;
			while (resultSet.next()) {
				bookName = resultSet.getString("name");
				id = resultSet.getString("id");
			}
			
			// Delete the last row
			pStatement = connect.prepareStatement
					("delete from test.book_list where id = " + id);
			pStatement.executeUpdate();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad delete last: " + e);
		}
		return bookName;
	}

	public static void doModify(PrintWriter out, String task, 
			ServletContext context) {

		try {
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);
		}
		catch (Exception e) {
        	context.log("Modify executeQuery() failed with {" + e + "}");
        	out.println("Caught exception - bad modify3: " + e.toString() + "<br>");
		}


		Statement statement = null;
		try {
			statement = connect.createStatement();
		}
		catch (SQLException sqlE) {
			context.log("Modify createStatement() failed with {" + sqlE + "}");
        	out.println("Caught exception - bad modify2: " + sqlE.toString() + "<br>");
		}
		
		try {
			if (task.equals("grant"))
				statement.executeQuery("grant select on test.book_list to smlobo");
			else if (task.equals("revoke"))
				statement.executeQuery("revoke select on test.book_list from smlobo");
			else if (task.equals("select"))
				statement.executeQuery("select user from mysql.user");
		}
		catch (Exception e) {
        	context.log("Modify executeQuery() failed with {" + e + "}");
        	out.println("Caught exception - bad modify: " + e.toString() + "<br>");
		}
	}

	public static void close() {
		try {
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} catch (SQLException throwables) {
			System.out.println("Caught exception while closing the DB connection: " + throwables);
			throwables.printStackTrace();
		}
	}
}
