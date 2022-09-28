<%@ page import = "java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
 
<html>
    <head>
        <title>JSP Book List Add</title>
    </head>

    <body style="background-color:darkkhaki;">

        <h1>JSP Book List Add</h1>

        <c:set var="dbServer" value="localhost"/>
<%--        <%--%>
<%--            String dbServer = System.getenv("MYSQL_SERVER");--%>
<%--            if (dbServer != null) {--%>
<%--                pageContext.setAttribute("dbServer", System.getenv("MYSQL_SERVER"));--%>
<%--            }--%>
<%--        %>--%>

        Using DB Server: <b>${dbServer}</b>
        <br>

        <sql:setDataSource var = "testds" driver = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://${dbServer}/test"
            user = "smlobo"  password = "welcome"/>

        <c:set var="sqlTimeStamp"/>
        <%
            java.util.Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            pageContext.setAttribute("sqlTimeStamp", timestamp.toString());
        %>

        <c:set var="appName"/>
        <%
            String name = request.getServletContext().getServerInfo() + 
                "; JSPMySqlBookList";
            pageContext.setAttribute("appName", name);
        %>

        Adding: 
        <b>${param.book_name}, ${param.rating}, ${sqlTimeStamp}, ${appName}</b>
        <br><br>

        <sql:update dataSource = "${testds}" var = "result">
            insert into book_list (name, rating, date_added, application) 
            values ("${param.book_name}", "${param.rating}", "${sqlTimeStamp}", 
            "${appName}");
        </sql:update>

        <sql:query dataSource = "${testds}" var = "result">
            SELECT * from book_list;
        </sql:query>
        <table border = "1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Rating</th>
                <th>Date Added</th>
                <th>Application</th>
            </tr>

            <c:forEach var = "row" items = "${result.rows}">
                <tr>
                    <td><c:out value = "${row.id}"/></td>
                    <td><c:out value = "${row.name}"/></td>
                    <td><c:out value = "${row.rating}"/></td>
                    <td><c:out value = "${row.date_added}"/></td>
                    <td><c:out value = "${row.application}"/></td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
