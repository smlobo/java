package org.lobo.java.webapps;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.PrintWriter;
import java.sql.SQLException;

public class TestBasicDataSource {
    public static DataSource setupDataSource(String connectURI) {
         BasicDataSource ds = new BasicDataSource();
         ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
         ds.setUrl(connectURI);
         return ds;
    }

    public static void printDataSourceStats(PrintWriter out, DataSource ds) {
        out.println("<p style=\"color:white;\">DBCP Status:");
        BasicDataSource bds = (BasicDataSource) ds;
        out.println("Active: " + bds.getNumActive() + "; ");
        out.println("Idle: " + bds.getNumIdle() + "</p>\n");
    }

    public static void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }
}
