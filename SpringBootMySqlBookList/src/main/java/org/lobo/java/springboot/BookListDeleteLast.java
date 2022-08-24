package org.lobo.java.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.Date;

@RestController
public class BookListDeleteLast {

	@RequestMapping("/BookListDeleteLast")
	public String deleteLast() {
		StringBuilder returnString = new StringBuilder();
		
		// Check if the book id exists
		String bookName = DatabaseLogic.deleteLastRow(returnString);

		// HTML header
		returnString.append("<html>");
		returnString.append("<head>");
		returnString.append("<title>Spring Boot Book List Delete Last</title>");
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
		returnString.append("<br><form action=\"/\"> <input type=\"submit\"value=\"Back\" />");

		returnString.append("</body>");
		returnString.append("</html>");

		return returnString.toString();
	}
}
