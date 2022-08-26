package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookListDelete")
public class BookListDelete extends DataSourceHttpServlet {
	
	private void badId(HttpServletRequest request, HttpServletResponse response, String s) 
			throws IOException {
		// Set response content type
		response.setContentType("text/html");

		// HTML header
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>DBCP Bad ID for delete</title>");
		out.println("</head>");
		out.println("<body style=\"background-color:dodgerblue;\">");

		out.println("<h3>DBCP Bad ID entered: " + s + "</h3>");
		out.println("<h3>Book id must be a number, and a valid index.</h3>");

		// Link to go back to the main page
		out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
				"value=\"Back\" />");

		out.println("</body>");
		out.println("</html>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// Create a new session
		request.getSession();

		// Get input parameter
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			badId(request, response, request.getParameter("id"));
			return;
		}
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Check if the book id exists
		String bookName;
		// Negative id implies delete the last row
		if (id < 0)
			bookName = DatabaseLogic.deleteLastRow(out);
		else
			bookName = DatabaseLogic.deleteRow(out, id);
		if (bookName == null) {
			badId(request, response, Integer.toString(id));
			return;
		}

		// HTML header
		out.println("<html>");
		out.println("<head>");
		out.println("<title>DBCP Book List Delete</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:dodgerblue;\">");
		out.println("<h1>" + "DBCP Deleted Book: " + bookName + "</h1>");
		Date d = new Date();
		out.println("<h2>" + "New Book List Contents " + d.toString() + " :</h2>");

		// Execute SQL query
		DatabaseLogic.query(out, "");

		// Link to go back to the main page
		out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
			"value=\"Delete Another?\" />");

		out.println("</body>");
		out.println("</html>");
	}
}
