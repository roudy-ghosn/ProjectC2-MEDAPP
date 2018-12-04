package DataBase;

import Constants.Constants;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + Constants.db_host + ":" + Constants.db_port + "/" + Constants.db_username, Constants.db_username, Constants.db_password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
