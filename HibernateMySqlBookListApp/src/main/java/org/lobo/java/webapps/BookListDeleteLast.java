package org.lobo.java.webapps;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/BookListDeleteLast")
public class BookListDeleteLast extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Create a new session
		request.getSession();

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Delete the last row
		Book book = BookDao.removeLastBook();

		// HTML header
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hibernate Book List Delete Last</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:skyblue;\">");
		out.println("<h1>" + "Hibernate Deleted Last Book: [" + book.id + "] " + book.name + "</h1>");
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
