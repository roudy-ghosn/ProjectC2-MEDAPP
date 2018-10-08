package CommonUtils;

import BusinessObjects.Patient;
import DataBase.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static DBConfig dbConfig;
    private static PreparedStatement statement;

    public QueryUtils() {
        statement = null;
        dbConfig = new DBConfig();
    }

    public static Connection getDBConnection() {
        return dbConfig.getConnection();
    }

    public static boolean validateLogin(String username, String password) {
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

    public static List<Patient> getPatientsList(String filter) {
        List<Patient> patientsList = new ArrayList<Patient>();
        String query = "Select username, firstname, lastname, role from user";
        try {
            statement = getDBConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Patient patient = new Patient();
                patient.setName(result.getString("username"));
                patientsList.add(patient);
            }
        } catch (SQLException ex) {
        }
        return patientsList;
    }
}
