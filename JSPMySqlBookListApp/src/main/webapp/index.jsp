<%@ page import = "java.util.*"%>

<html>
    <head>
        <title>JSP MySQL Access Web Application</title>
    </head>
    <body style="background-color:darkkhaki;">
        <h1>JSP MySQL Access Web Application</h1>

        <c:set var="date"/>
        <%
            pageContext.setAttribute("date", (new Date()).toString());
        %>

        <h3>It is: ${date}</h3>

        <p>Enter the row number you want to retrieve from the test.book_list table:</p>
        <form action = "BookListAccess.jsp" method = "GET">
            Book List ID: <input type = "number" name = "book_list_id">
            <input type = "submit" value = "Submit">
        </form>
        <br>
        <p>Add a new book to the table:</p>
        <form action = "BookListAdd.jsp" method = "POST">
            Name: <input type = "text" name = "book_name">
            Rating: <input type = "number" name = "rating">
            <input type = "submit" value = "Add">
        </form>
        <br>
        <p>Delete a book to the table:</p>
        <form action = "BookListDelete" method = "POST">
            ID: <input type = "number" name = "id">
            <input type = "submit" value = "Delete">
        </form>
        <br>
        <form action = "BookListDelete" method = "POST">
            <input type = "hidden" name = "id" value = "-1">
            <input type = "submit" value = "Delete Last Book">
        </form>
        <br>
    </body>
</html>
