package org.lobo.java.springboot;

import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.Date;

@RestController
public class BookListAdd {
	private String badRating(StringBuilder returnString, String s) {
		// HTML header
		returnString.append("<html>");
		returnString.append("<head>");
		returnString.append("<title>Bad rating input</title>");
		returnString.append("</head>");
		returnString.append("<body style=\"background-color:lightgreen;\">");

		returnString.append("<h3>Bad rating entered: " + s + "</h3>");
		returnString.append("<h3>Rating must be a number between 1 & 10.</h3>");

		// Link to go back to the main page
		returnString.append("<br><form action=\"/\"> <input type=\"submit\"" +
				"value=\"Back\" />");

		returnString.append("</body>");
		returnString.append("</html>");

		return returnString.toString();
	}

	@RequestMapping("/BookListAdd")
	public String add(@RequestParam("book_name") String bookName, @RequestParam("rating") String ratingStr) {
		StringBuilder returnString = new StringBuilder();

		int rating = 0;
		try {
			rating = Integer.parseInt(ratingStr);
		} catch (NumberFormatException e) {
			return badRating(returnString, ratingStr);
		}
		if (rating < 1 || rating > 10) {
			return badRating(returnString, ratingStr);
		}

		// Get the Spring version
		String application = getClass().getPackage().getImplementationTitle() + ":" +
				getClass().getPackage().getImplementationVersion() + " {Spring: " + SpringVersion.getVersion() + "}";
		// Add to the database
		DatabaseLogic.addRow(returnString, bookName, rating, application);

		// HTML header
		returnString.append("<html>");
		returnString.append("<head>");
		returnString.append("<title>Spring Boot Book List Add</title>");
		returnString.append("<style>table, th, td { border: 1px solid black; }</style>");
		returnString.append("</head>");
		returnString.append("<body style=\"background-color:lightgreen;\">");
		returnString.append("<h1>" + "Spring Boot Added Book: " + bookName + "</h1>");
		returnString.append("<h2>" + "Spring Version: " + application + "</h2>");
		Date d = new Date();
		returnString.append("<h2>" + "New Book List Contents " + d.toString() + " :</h2>");

		// Execute SQL query
		ResultSet resultSet = DatabaseLogic.query(returnString, null);

		// Print SQL results to a HTML table
		try {
			returnString.append("<h3>Table: " + resultSet.getMetaData().getTableName(1) + "</h3>");
			returnString.append("<table><tr>");
			for  (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				returnString.append("<th>" + resultSet.getMetaData().getColumnName(i) + "</th>");
			}
			returnString.append("</tr>");
			while (resultSet.next()) {
				returnString.append("<tr>");
				for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++) {
					returnString.append("<td>" + resultSet.getString(resultSet.getMetaData().getColumnName(i)) + 
							"</td>");
				}
				returnString.append("</tr>");
			}
		}
		catch (Exception e) {
			returnString.append("Caught exception 5");
		}
		returnString.append("</table>");
		
		// Link to go back to the main page
		returnString.append("<br><form action=\"/\"> <input type=\"submit\"value=\"Add Another?\" />");

		returnString.append("</body>");
		returnString.append("</html>");

		return returnString.toString();
	}
}
