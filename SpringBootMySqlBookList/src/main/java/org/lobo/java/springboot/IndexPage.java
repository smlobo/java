package org.lobo.java.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexPage {

    @RequestMapping("/")
    public String index() {
        StringBuilder returnString = new StringBuilder();

        returnString.append("<html>");
        returnString.append("<head>");
        returnString.append("<title>Spring Boot MySQL Access Web Application</title>");
        returnString.append("</head>");
        returnString.append("<body style=\"background-color:lightgreen;\">");
        Date d = new Date();
        returnString.append("<h1>Spring Boot MySQL Access Web Application</h1>");
        returnString.append("<br>");
        returnString.append("<p>Enter the row number you want to retrieve from the test.book_list table:</p>");
        returnString.append("<form action = \"BookListQuery\" method = \"GET\">");
        returnString.append("                Book List ID: <input type = \"number\" name = \"book_list_id\">");
        returnString.append("    <input type = \"submit\" value = \"Submit\">");
        returnString.append("</form>");
        returnString.append("<br>");
        returnString.append("<p>Add a new book to the table:</p>");
        returnString.append("<form action = \"BookListAdd\" method = \"POST\">");
        returnString.append("                Name: <input type = \"text\" name = \"book_name\">");
        returnString.append("                Rating: <input type = \"number\" name = \"rating\">");
        returnString.append("    <input type = \"submit\" value = \"Add\">");
        returnString.append("</form>");
        returnString.append("<br>");
        returnString.append("<p>Delete a book to the table:</p>");
        returnString.append("<form action = \"BookListDelete\" method = \"POST\">");
        returnString.append("                ID: <input type = \"number\" name = \"id\">");
        returnString.append("    <input type = \"submit\" value = \"Delete\">");
        returnString.append("</form>");
        returnString.append("<p><a href=\"BookListDeleteLast\"><input type=button value='Delete Last Book'></a>");
        returnString.append("<br>");
        returnString.append("</body>");
        returnString.append("</html>");

        return returnString.toString();
    }
}
