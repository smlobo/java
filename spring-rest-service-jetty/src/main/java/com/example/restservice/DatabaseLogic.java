package com.example.restservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseLogic {
	// Use a single connection
	private static Connection connect;
//	private static int count;
	private static final String dbUrl;

	static {
		String dbServer = System.getenv("MYSQL_SERVER");
		if (dbServer == null) {
			dbServer = "localhost";
		}
		dbUrl = "jdbc:mysql://" + dbServer + "/test?user=smlobo&password=welcome";
	}

	public static Movie queryGenre(String genre) {
		Movie movie = null;
		try {
			ResultSet resultSet;
			PreparedStatement pStatement;

			// One time initialize
			if (connect == null) {
				connect = DriverManager.getConnection(dbUrl);
//				pStatement = connect.prepareStatement("select count(*) from movie_list");
//				resultSet = pStatement.executeQuery();
//				resultSet.next();
//				count = resultSet.getInt("count(*)");
			}

			// Get a random row
			if (genre == null) {
				pStatement = connect.prepareStatement
						("select title, release_date from movie_list order by rand() limit 1");
			}
			// Get a random movie from the requested genre
			else {
				pStatement = connect.prepareStatement
						("select title, release_date from movie_list where genres like '%" + genre + "%' " +
								"order by rand() limit 1");
			}
			resultSet = pStatement.executeQuery();
			resultSet.next();
			movie = new Movie(resultSet.getString("title"),
					resultSet.getString("release_date"));
		}
		catch (Exception e) {
        	System.out.println("Caught exception - bad query - " + e);
		}

		// TODO artificial slowdown
		try {
			Thread.sleep(100);
		}
		catch (Exception e) {}

		return movie;
	}

}
