package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12260042", "sql12260042", "kPxQzPneMT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
