package org.lobo.java.webapps;

import java.io.PrintWriter;
import java.util.List;

public class BookTable {
    static void bookTable(PrintWriter out, List<Book> books) {
        // Print the table header
        out.println("<table><tr>");
        out.print("<th>ID</th><th>Name</th><th>Rating</th><th>Date Added</th><th>Application</th>");
        out.println("</tr>");

        // Print SQL results to a HTML table
        for (Book book : books) {
            out.println("<tr>");
            out.println("<td>" + book.id + "</td>");
            out.println("<td>" + book.name + "</td>");
            out.println("<td>" + book.rating + "</td>");
            out.println("<td>" + book.date_added + "</td>");
            out.println("<td>" + book.application + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

    }
}
