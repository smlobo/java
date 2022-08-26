package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/BookListAdd"}, asyncSupported = true)
public class BookListAdd extends HttpServlet {
	
	private void badRating(HttpServletRequest request, HttpServletResponse response, String s) 
			throws ServletException, IOException {
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
			throws ServletException, IOException {

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

		AsyncContext asyncContext = request.startAsync();

		asyncContext.start(new BookListAddRunner(asyncContext, bookName, rating));
	}

	private class BookListAddRunner implements Runnable {
		private AsyncContext asyncContext;
		private String bookName;
		private int rating;

		public BookListAddRunner(AsyncContext asyncContext, String bookName, int rating) {
			this.asyncContext = asyncContext;
			this.bookName = bookName;
			this.rating = rating;
		}

		@Override
		public void run() {
			PrintWriter out = null;
			try {
				out = asyncContext.getResponse().getWriter();
			}
			catch (IOException e) {}

			// Set the app server and app name
			String serverInfo = asyncContext.getRequest().getServletContext().getServerInfo();
			String application = serverInfo.substring(0, Math.min(serverInfo.length(), 26)) + "; " +
					((HttpServletRequest) asyncContext.getRequest()).getContextPath();

			// Add to the database
			DatabaseLogic.addRow(out, bookName, rating, application);

			// HTML header
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Async Book List Add</title>");
			out.println("<style>table, th, td { border: 1px solid black; }</style>");
			out.println("</head>");
			out.println("<body style=\"background-color:lightsteelblue;\">");
			out.println("<h1>" + "Async Added Book: " + bookName + "</h1>");
			Date d = new Date();
			out.println("<h2>" + "New Book List Contents " + d.toString() + " :</h2>");

			// Execute SQL query
			ResultSet resultSet = DatabaseLogic.query(out, "");

			// Print SQL results to a HTML table
			try {
				out.println("<h3>Table: " + resultSet.getMetaData().getTableName(1) + "</h3>");
				out.println("<table><tr>");
				for  (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					out.print("<th>" + resultSet.getMetaData().getColumnName(i) + "</th>");
				}
				out.println("</tr>");
				while (resultSet.next()) {
					out.println("<tr>");
					for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++) {
						out.println("<td>" + resultSet.getString(resultSet.getMetaData().getColumnName(i)) +
								"</td>");
					}
					out.println("</tr>");
				}
			}
			catch (Exception e) {
				out.println("Caught exception 5");
			}
			out.println("</table>");

			// Link to go back to the main page
			out.println("<br><form action=\"" + ((HttpServletRequest) asyncContext.getRequest()).getContextPath() +
					"\"> <input type=\"submit\" value=\"Add Another?\" />");

			out.println("</body>");
			out.println("</html>");

			asyncContext.complete();
		}
	}

	public void destroy() {
		// Close the database connection
		DatabaseLogic.close();
	}
}
