package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookListQuery")
public class BookListAccess extends DataSourceHttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// Get input parameter
		String rID = request.getParameter("book_list_id");
		
		// Set response content type
		response.setContentType("text/html");

		// HTML header
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>DBCP Book List Content</title>");
		out.println("<style>table, th, td { border: 1px solid black; }</style>");
		out.println("</head>");
		out.println("<body style=\"background-color:dodgerblue;\">");
		Date d = new Date();
		out.println("<h1>" + "DBCP Book List Contents " + d.toString() + "</h1>");

		// Execute SQL query
		DatabaseLogic.query(out, rID);

        // Link to go back to the main page
        out.println("<br><form action=\"" + request.getContextPath() + "\"> <input type=\"submit\"" + 
        		"value=\"Back\" />");

		out.println("</body>");
		out.println("</html>");
	}
}
