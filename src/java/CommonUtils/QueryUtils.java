package CommonUtils;

import DataBase.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryUtils {

    private static DBConfig dbConfig;

    public QueryUtils() {
        dbConfig = new DBConfig();
    }

    public static Connection getDBConnection() {
        return dbConfig.getConnection();
    }

    public static boolean validateLogin(String username, String password) {
        PreparedStatement statement = null;
        String query = "Select username, firstname, lastname, role from user where lower(username) = lower(?) and lower(password) = lower(?)";
        try {
            statement = getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                SessionUtils.setUserName(username);
                SessionUtils.setFirstName(result.getString("firstname"));
                SessionUtils.setLastName(result.getString("lastname"));
                SessionUtils.setRole(result.getString("role"));
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
}
