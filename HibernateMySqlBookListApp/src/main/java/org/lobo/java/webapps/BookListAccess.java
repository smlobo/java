package org.lobo.java.webapps;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet("/BookListQuery")
public class BookListAccess extends HttpServlet {
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Get input parameter
		String rID = request.getParameter("book_list_id");
		
		// Set response content type
		response.setContentType("text/html");

		// HTML header
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hibernate Book List Content</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:skyblue;\">");
		Date d = new Date();
		out.println("<h1>" + "Hibernate Book List Contents " + d.toString() + "</h1>");
		out.println("<h3>Requested id: " + rID + "</h3>");

		// Get a List of books from Hibernate
		List<Book> books = BookDao.getBooks(rID);
		BookTable.bookTable(out, books);

        // Link to go back to the main page
        out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
        		"value=\"Back\" />");

		out.println("</body>");
		out.println("</html>");
	}
}
