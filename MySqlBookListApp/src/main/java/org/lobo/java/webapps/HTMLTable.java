package org.lobo.java.webapps;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class HTMLTable {
    static void generateTable(PrintWriter out, ResultSet resultSet) {
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
            out.println("Caught exception generating HTML table: " + e);
        }
        out.println("</table>");
    }
}
