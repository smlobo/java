package org.lobo.java.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.Date;

@RestController
public class BookListDelete {
	
	private String badId(StringBuilder stringBuilder, String id) {
		// HTML header
		stringBuilder.append("<html>");
		stringBuilder.append("<head>");
		stringBuilder.append("<title>Bad ID for delete</title>");
		stringBuilder.append("</head>");
		stringBuilder.append("<body style=\"background-color:lightgreen;\">");

		stringBuilder.append("<h3>Bad ID entered: " + id + "</h3>");
		stringBuilder.append("<h3>Book id must be a number, and a valid index.</h3>");

		// Link to go back to the main page
		stringBuilder.append("<br><form action=\"/\"> <input type=\"submit\"value=\"Back\" />");

		stringBuilder.append("</body>");
		stringBuilder.append("</html>");

		return stringBuilder.toString();
	}

	@RequestMapping("/BookListDelete")
	public String delete(@RequestParam("id") String idStr) {
		StringBuilder returnString = new StringBuilder();

		// Get input parameter
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			return badId(returnString, idStr);
		}

		// Check if the book id exists
		String bookName = DatabaseLogic.deleteRow(returnString, id);
		if (bookName == null) {
			return badId(returnString, idStr);
		}

		// HTML header
		returnString.append("<html>");
		returnString.append("<head>");
		returnString.append("<title>Spring Boot Book List Delete</title>");
		returnString.append("<style>table, th, td { border: 1px solid black; }</style>");
		returnString.append("</head>");
		returnString.append("<body style=\"background-color:lightgreen;\">");
		returnString.append("<h1>" + "Spring Boot Deleted Book: " + bookName + "</h1>");
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
		returnString.append("<br><form action=\"/\"> <input type=\"submit\"value=\"Delete Another?\" />");

		returnString.append("</body>");
		returnString.append("</html>");

		return returnString.toString();
	}
}
