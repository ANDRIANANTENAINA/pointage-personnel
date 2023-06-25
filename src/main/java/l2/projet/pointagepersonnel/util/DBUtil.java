package l2.projet.pointagepersonnel.util;

import java.sql.*;
public class DBUtil {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static Connection conn = null;
    private static final String connStr = "jdbc:postgresql://localhost/pointage";
    private static final String username = "base";
    private static final String password = "Falinirina!0205";
    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Postgres Driver?");
            e.printStackTrace();
            throw e;
        }
        System.out.println("Postgres JDBC Driver Registered!");

        try {
            conn = DriverManager.getConnection(connStr, username, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            return resultSet;
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        }
//        finally {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            dbDisconnect();
//        }
    }

    //Update/Insert/Delete
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }
}
