package org.lobo.jetty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseLogic {
    // Use a single connection
    private static Connection connect;
    private static final String dbUrl;

    static {
        String dbServer = System.getenv("MYSQL_SERVER");
        if (dbServer == null) {
            dbServer = "localhost";
        }
        dbUrl = "jdbc:mysql://" + dbServer + "/test?user=smlobo&password=welcome";
    }

    // Return 'n' random names from the 'baby_names' table
    protected static List<String> randomNames(int n) {
        LinkedList<String> list = new LinkedList<>();

        try {
            if (connect == null)
                connect = DriverManager.getConnection(dbUrl);
            PreparedStatement pStatement = connect.prepareStatement("select * from test.baby_names order by " +
                    "rand() limit " + n);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                //logger.info("Name: " + resultSet.getString("name"));
                list.add(resultSet.getString("name"));
            }
        }
        catch (Exception e) {
            Logger.getLogger("database").severe("Caught exception querying for " + n + " random names");
        }
        return list;
    }
}
