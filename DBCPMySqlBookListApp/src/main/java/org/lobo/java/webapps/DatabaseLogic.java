package org.lobo.java.webapps;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DatabaseLogic {
	private static DataSource dataSource;

	public static void init() {
		if (dataSource != null)
			return;

		String dbServer = System.getenv("MYSQL_SERVER");
		if (dbServer == null) {
			dbServer = "localhost";
		}
		dataSource = TestBasicDataSource.setupDataSource("jdbc:mysql://" + dbServer +
				"/test?user=smlobo&password=welcome");
	}

	public static void destroy() {
		if (dataSource == null)
			return;

		try {
			TestBasicDataSource.shutdownDataSource(dataSource);
		} catch (SQLException throwables) {
			System.out.println("Exception while shutting down data source: " + throwables);
			throwables.printStackTrace();
		}
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
	
	public static void query(PrintWriter out, String rID) {
		try {
			// Get a connection from the DataSource
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement;
			if (isNumeric(rID)) {
				preparedStatement = connection.prepareStatement
						("select * from test.book_list where id = " + rID);
				// Show requested ID
				out.println("<h3>Requested id: " + rID + "</h3>");
			}
			else {
				preparedStatement = connection.prepareStatement("select * from test.book_list");
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			// Print DBCP stats
			TestBasicDataSource.printDataSourceStats(out, dataSource);

			// Write the HTML table with the resultSet data
			HTMLTable.generateTable(out, resultSet);

			if (resultSet != null)
				resultSet.close();
			preparedStatement.close();
			connection.close();
		}
		catch (SQLException e) {
        	out.println("Caught exception - bad query: " + e);
		}
	}

	public static void addRow(PrintWriter out, String book, int rating, String application) {
		// Current date
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		try {
			// Get a connection from the DataSource
			Connection connection = dataSource.getConnection();

			PreparedStatement pStatement = connection.prepareStatement
					("insert into test.book_list (name, rating, date_added, application) " +
						"values (?, ?, ?, ?)");
			pStatement.setString(1,  book);
			pStatement.setInt(2,  rating);
			pStatement.setString(3, timestamp.toString());
			pStatement.setString(4, application);
			pStatement.executeUpdate();

			pStatement.close();
			connection.close();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad insert: " + e);
		}
	}

	public static String deleteRow(PrintWriter out, int id) {
		String bookName = null;
		try {
			// Get a connection from the DataSource
			Connection connection = dataSource.getConnection();

			// Get the name of the book being deleted
			PreparedStatement pStatement = connection.prepareStatement
					("select name from test.book_list where id = " + id);
			ResultSet resultSet = pStatement.executeQuery();

			// If empty - invalid id
			if (!resultSet.next())
				return bookName;

			bookName = resultSet.getString("name");

			// Actually delete the row
			pStatement = connection.prepareStatement
					("delete from test.book_list where id = " + id);
			pStatement.executeUpdate();

			pStatement.close();
			connection.close();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad delete: " + e);
		}
		return bookName;
	}

	public static String deleteLastRow(PrintWriter out) {
		String bookName = null;
		try {
			// Get a connection from the DataSource
			Connection connection = dataSource.getConnection();

			// Get the name of the book being deleted
			PreparedStatement pStatement = connection.prepareStatement
					("select id, name from test.book_list");
			ResultSet resultSet = pStatement.executeQuery();

			// Get the last row name & ID
			String id = null;
			while (resultSet.next()) {
				bookName = resultSet.getString("name");
				id = resultSet.getString("id");
			}

			// Delete the last row
			pStatement = connection.prepareStatement
					("delete from test.book_list where id = " + id);
			pStatement.executeUpdate();

			pStatement.close();
			connection.close();
		}
		catch (Exception e) {
        	out.println("Caught exception - bad delete last: " + e);
		}
		return bookName;
	}

}
