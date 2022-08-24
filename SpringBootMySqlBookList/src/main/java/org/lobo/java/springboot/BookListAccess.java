package org.lobo.java.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.Date;

@RestController
public class BookListAccess {

    @RequestMapping("/BookListQuery")
    public String query(@RequestParam("book_list_id") String rID) {
        StringBuilder returnString = new StringBuilder();

        returnString.append("<html>");
        returnString.append("<head>");
        returnString.append("<title>Spring Boot Book List Content</title>");
        returnString.append("<style>table, th, td { border: 1px solid black; }</style>");
        returnString.append("</head>");
        returnString.append("<body style=\"background-color:lightgreen;\">");
        Date d = new Date();
        returnString.append("<h1>" + "Spring Boot Book List Contents " + d.toString() + "</h1>");

        // Execute SQL query
        ResultSet resultSet = DatabaseLogic.query(returnString, rID);

        // Print SQL results to a HTML table
        try {
            returnString.append("<h3>Table: " + resultSet.getMetaData().getTableName(1) + "</h3>");
            returnString.append("<table><tr>");
            for  (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                returnString.append("<th>" + resultSet.getMetaData().getColumnName(i) + "</th>");
            }
            returnString.append("</tr>");
            while (resultSet.next()) {
                returnString.append("<tr>");
                for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++) {
                    returnString.append("<td>" + resultSet.getString(resultSet.getMetaData().getColumnName(i)) +
                            "</td>");
                }
                returnString.append("</tr>");
            }
        }
        catch (Exception e) {
            returnString.append("Caught exception 5");
        }
        returnString.append("</table>");

        // Link to go back to the main page
        returnString.append("<br><form action=\"/\"> <input type=\"submit\"value=\"Back\" />");

        returnString.append("</body>");
        returnString.append("</html>");

        return returnString.toString();
    }
}
