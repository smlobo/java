package org.lobo.java.webapps;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/BookListDelete")
public class BookListDelete extends HttpServlet {
	
	private void badId(HttpServletRequest request, HttpServletResponse response, String s) throws IOException {
		// Set response content type
		response.setContentType("text/html");

		// HTML header
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Bad ID for delete</title>");
		out.println("</head>");
		out.println("<body style=\"background-color:skyblue;\">");

		out.println("<h3>Hibernate Bad ID entered: " + s + "</h3>");
		out.println("<h3>Book id must be a number, and a valid index.</h3>");

		// Link to go back to the main page
		out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
				"value=\"Back\" />");

		out.println("</body>");
		out.println("</html>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
		Book book = BookDao.getBook(id);
		if (book == null) {
			badId(request, response, Integer.toString(id));
			return;
		}

		// Delete it
		BookDao.removeBook(book);

		// HTML header
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hibernate Book List Delete</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:skyblue;\">");
		out.println("<h1>" + "Hibernate Deleted Book: [" + id + "] " + book.name + "</h1>");
		Date d = new Date();
		out.println("<h2>" + "New Book List Contents " + d.toString() + " :</h2>");

		BookTable.bookTable(out, BookDao.getBooks(""));

		// Link to go back to the main page
		out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
			"value=\"Delete Another?\" />");

		out.println("</body>");
		out.println("</html>");
	}
}
