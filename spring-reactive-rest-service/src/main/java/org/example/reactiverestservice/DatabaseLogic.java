package org.example.reactiverestservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	public static Country queryIncomeGroup(String incomeGroup) {
		Country country = null;
		try {
			ResultSet resultSet;
			PreparedStatement pStatement;

			// One time initialize
			if (connect == null)
				connect = DriverManager.getConnection(dbUrl);

			// Get a random row
			if (incomeGroup == null) {
				pStatement = connect.prepareStatement
						("select shortname, currencyunit, region from country_list order by rand() limit 1");
			}
			// Get a random movie from the requested genre
			else {
				pStatement = connect.prepareStatement
						("select shortname, currencyunit, region from country_list where incomegroup like '" +
								incomeGroup + "' order by rand() limit 1");
			}
			resultSet = pStatement.executeQuery();
			resultSet.next();
			country = new Country(resultSet.getString("shortname"),
					resultSet.getString("currencyunit"), resultSet.getString("region"));
		}
		catch (Exception e) {
        	System.out.println("Caught exception - bad query - " + e);
		}

		// TODO artificial slowdown
		try {
			Thread.sleep(100);
		}
		catch (Exception e) {}

		return country;
	}

}
