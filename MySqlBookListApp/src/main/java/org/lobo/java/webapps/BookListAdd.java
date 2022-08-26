package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookListAdd")
public class BookListAdd extends HttpServlet {
	
	private void badRating(HttpServletRequest request, HttpServletResponse response, String s) 
			throws IOException {
		// Set response content type
		response.setContentType("text/html");

		// HTML header
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Bad rating input</title>");
		out.println("</head>");
		out.println("<body style=\"background-color:lightblue;\">");

		out.println("<h3>Bad rating entered: " + s + "</h3>");
		out.println("<h3>Rating must be a number between 1 & 10.</h3>");

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
		String bookName = request.getParameter("book_name");
		int rating = 0;
		try {
			rating = Integer.parseInt(request.getParameter("rating"));
		} catch (NumberFormatException e) {
			badRating(request, response, request.getParameter("rating"));
			return;
		}
		if (rating < 1 || rating > 10) {
			badRating(request, response, Integer.toString(rating));
			return;
		}
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Set the app server and app name
		String serverInfo = request.getServletContext().getServerInfo();
		String application = serverInfo.substring(0, Math.min(serverInfo.length(), 26)) + "; " + 
				request.getContextPath();
		
		// Add to the database
		DatabaseLogic.addRow(out, bookName, rating, application);

		// HTML header
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Book List Add</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:lightblue;\">");
		out.println("<h1>" + "Added Book: " + bookName + "</h1>");
		Date d = new Date();
		out.println("<h2>" + "New Book List Contents " + d.toString() + " :</h2>");

		// Execute SQL query
		ResultSet resultSet = DatabaseLogic.query(out, "");
		HTMLTable.generateTable(out, resultSet);

		// Link to go back to the main page
		out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
			"value=\"Add Another?\" />");

		out.println("</body>");
		out.println("</html>");
	}

	public void destroy() {
		// Close the database connection
		DatabaseLogic.close();
	}
}
