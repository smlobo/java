package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookListModify")
public class BookListModify extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// Create a new session
		request.getSession();

		// Get input parameter
		String sqlKeyword = request.getParameter("sqlkeyword");
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// HTML header
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Book List Modify</title>");
		out.println("</head>");
		out.println("<body style=\"background-color:lightblue;\">");
		out.println("<h1>" + "Modified Table with <font color=\"red\">" + sqlKeyword + 
				"</font></h1>");
		Date d = new Date();
		out.println("<h2>" + "On " + d.toString() + "</h2>");

		// Modify the database
		DatabaseLogic.doModify(out, sqlKeyword, getServletContext());

		// Link to go back to the main page
		out.println("<br><a href=\"" + request.getContextPath() + "\">Back</a>");

		out.println("</body>");
		out.println("</html>");
	}

	public void destroy() {
		// Close the database connection
		DatabaseLogic.close();
	}
}
