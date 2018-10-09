package CommonUtils;

import BusinessObjects.Patient;
import BusinessObjects.Person;
import BusinessObjects.Role;
import BusinessObjects.User;
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
        String query = "select Roles.Role_description role, Persons.Person_firstName firstname, Persons.Person_lastName lastname "
                       + "from Users, Persons, Roles "
                      + "where lower(Users.User_userName) = lower(?) "
                        + "and lower(Users.User_passowrd) = lower(?) "
                        + "and Users.Person_id = Persons.Person_id "
                        + "and Users.Role_id = Roles.Role_id";
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
            ex.printStackTrace();
            return false;
        }
        return false;
    }
    
    /* Manage Users */

    public static List<User> getUsersList(String filter) {
        List<User> usersList = new ArrayList<User>();
        String query = "select Users.User_id, Users.User_userName, Users.User_creationDate, Roles.Role_id, Persons.Person_id "
                       + "from Users, Persons, Roles "
                      + "where lower(Users.User_userName) like lower('%" + (filter != null ? filter : "") + "%') "
                        + "and Users.Person_id = Persons.Person_id "
                        + "and Users.Role_id = Roles.Role_id";
        try {
            statement = getDBConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setId(result.getString("User_id"));
                user.setUsername(result.getString("User_userName"));
                user.setCreationDate(result.getDate("User_creationDate"));
                user.setRole(getRolesList(result.getString("Role_id")).get(0));
                user.setPerson(getPersonDetails(result.getString("Person_id")));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersList;
    }
    
    public static void deleteUser(String userId){
        String query = "delete from Users where User_id = ?";
        try {
            statement = getDBConnection().prepareStatement(query);
            statement.setString(1, userId);
            statement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /* Manage Persons */
    
    public static Person getPersonDetails(String filter){
        Person person = new Person();
        String query = "select Person_id, Person_firstName, Person_lastName, Person_age, Person_phoneNumber"
                   + ", Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender"
                   + ", Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode "
                       + "from Persons where Persons.Person_id = ?";
        try {
            statement = getDBConnection().prepareStatement(query);
            statement.setString(1, filter);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                person.setId(result.getString("Person_id"));
                person.setAddress(result.getString("Person_address"));
                person.setAge(result.getString("Person_age"));
                person.setCountry(result.getString("Person_country"));
                person.setEmail(result.getString("Person_email"));
                person.setFatherName(result.getString("Person_fatherName"));
                person.setFirstName(result.getString("Person_firstName"));
                person.setHasChildren("O".equals(result.getString("Person_hasChildren")) ? true : false);
                person.setIsMaried("O".equals(result.getString("Person_isMarried")) ? true : false);
                person.setLastName(result.getString("Person_lastName"));
                person.setMotherName(result.getString("Person_motherName"));
                person.setPhoneNumber(result.getString("Person_phoneNumber"));
                person.setRegion(result.getString("Person_region"));
                person.setZipCode(result.getString("Person_zipCode"));
                person.setGender(result.getString("Person_gender"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return person;
    }
    
    /* Manage Roles */

    public static List<Role> getRolesList(String filter) {
        List<Role> rolesList = new ArrayList<Role>();
        String query = "select Role_id, Role_description from Roles where ( (Roles.Role_id = ? and ? is not null ) or ? is null)";
        try {
            statement = getDBConnection().prepareStatement(query);
            statement.setString(1, filter);
            statement.setString(2, filter);
            statement.setString(3, filter);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Role role = new Role();
                role.setId(result.getString("Role_id"));
                role.setDescription(result.getString("Role_description"));
                rolesList.add(role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rolesList;
    }
}
