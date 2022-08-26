package org.lobo.java.webapps;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/BookListQuery"}, asyncSupported = true)
public class BookListAccess extends HttpServlet {
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// Set response content type
		response.setContentType("text/html");

		final AsyncContext asyncContext = request.startAsync();

		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				PrintWriter out = null;
				try {
					out = asyncContext.getResponse().getWriter();
				}
				catch (IOException e) {}

				// HTML header
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Async Book List Content</title>");
				out.println("<style>table, th, td { border: 1px solid black; }</style>");
				out.println("</head>");
				out.println("<body style=\"background-color:lightsteelblue;\">");
				Date d = new Date();
				out.println("<h1>" + "Async Book List Contents " + d.toString() + "</h1>");

				// Execute SQL query
				ResultSet resultSet = DatabaseLogic.query(out, asyncContext.getRequest().getParameter("book_list_id"));

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
						"\"> <input type=\"submit\" value=\"Back\" />");

				out.println("</body>");
				out.println("</html>");

				asyncContext.complete();
			}
		});
	}

	public void destroy() {
		// Close the database connection
		DatabaseLogic.close();
	}
}
