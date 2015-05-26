package connection;

import java.sql.*;

public class OracleJDBC {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;

    public static Connection makeConection() {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            //e.printStackTrace();
            return makeConection();
        }
        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "TW", "TW");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            // e.printStackTrace();
            return makeConection();
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("You have failed to make connection!");
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ResultSetMetaData metaData) {
        this.metaData = metaData;
    }


}