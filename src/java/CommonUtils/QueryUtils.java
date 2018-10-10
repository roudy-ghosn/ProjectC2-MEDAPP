package CommonUtils;

import BusinessObjects.Disease;
import BusinessObjects.Doctor;
import BusinessObjects.MedicalFile;
import BusinessObjects.Patient;
import BusinessObjects.Person;
import BusinessObjects.Report;
import BusinessObjects.Role;
import BusinessObjects.User;
import DataBase.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QueryUtils {

    private static PreparedStatement statement;
    private static DBConfig dbConfig = new DBConfig();
    private static Connection dbConnection = dbConfig.getConnection();

    public static boolean validateLogin(String username, String password) {
        String query = "select Roles.Role_description role, Persons.Person_firstName firstname, Persons.Person_lastName lastname "
                + "from Users, Persons, Roles "
                + "where lower(Users.User_userName) = lower(?) "
                + "and lower(Users.User_passowrd) = lower(?) "
                + "and Users.Person_id = Persons.Person_id "
                + "and Users.Role_id = Roles.Role_id";
        try {
            statement = dbConnection.prepareStatement(query);
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
        String query = "select Users.User_id, Users.User_userName, Users.User_passowrd, Users.User_creationDate, Roles.Role_id, Persons.Person_id "
                + "from Users, Persons, Roles "
                + "where lower(Users.User_userName) like lower('%" + (filter != null ? filter : "") + "%') "
                + "and Users.Person_id = Persons.Person_id "
                + "and Users.Role_id = Roles.Role_id";
        try {
            statement = dbConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setId(result.getString("User_id"));
                user.setRole(result.getString("Role_id"));
                user.setPerson(result.getString("Person_id"));
                user.setUsername(result.getString("User_userName"));
                user.setPassword(result.getString("User_passowrd"));
                user.setCreationDate(result.getDate("User_creationDate"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersList;
    }

    public static void deleteUser(String userId) {
        String query = "delete from Users where User_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertUser(User user) {
        String query = "Insert into Users(User_id, Person_id, User_userName, User_passowrd, User_creationDate, Role_id)"
                + "     values (?, ?, ?, ?, now(), ?)";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, user.getPerson());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        String query = "Update Users set Person_id = ?, User_userName = ?, User_passowrd = ?, Role_id = ? WHERE User_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getPerson());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /* Manage Persons */
    public static List<Person> getPersonList(String filter) {
        List<Person> personsList = new ArrayList<Person>();
        String query = "select Person_id, Person_firstName, Person_lastName, Person_age, Person_phoneNumber"
                + ", Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender"
                + ", Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode "
                + "from Persons "
                + "where ((Person_id = ? and ? is not null ) or ? is null)";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, filter);
            statement.setString(2, filter);
            statement.setString(3, filter);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Person person = new Person();
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
                personsList.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return personsList;
    }

    /* Manage Roles */
    public static List<Role> getRolesList(String filter) {
        List<Role> rolesList = new ArrayList<Role>();
        String query = "select Role_id, Role_description from Roles where ( (Roles.Role_id = ? and ? is not null ) or ? is null)";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, filter);
            statement.setString(2, filter);
            statement.setString(3, filter);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
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

    /* Manage Medical File */
    public static MedicalFile getPatientMedicalFile(String patientId) {
        MedicalFile file = new MedicalFile();
        String query = "select MedicalFile_id, Doctor_id, MedicalFile_creationDate, MedicalFile_file, MedicalFile_note"
                + "from MedicalFile where Patient_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, patientId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                file.setId(result.getString("MedicalFile_id"));
                file.setNotes(result.getString("MedicalFile_note"));
                List<Doctor> doctors = getDoctorList(result.getString("Doctor_id"));
                if (doctors != null && doctors.size() > 0) {
                    file.setDoctor(doctors.get(0));
                }
                file.setReports(getMedicalFileListOfReports(file.getId()));
                file.setCreationDate(result.getDate("MedicalFile_creationDate"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    public static List<Report> getMedicalFileListOfReports(String fileId) {
        List<Report> reportList = new ArrayList<Report>();
        String query = "select Report_id, Doctor_id, Report_titre, Report_description, "
                + "Report_attachment, Report_note, Report_diagnosis, Report_treatment, "
                + "Report_date, Report_comments, Disease_id "
                + "from Report where MedicalFile_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, fileId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Report report = new Report();
                report.setComments(result.getString("Report_comments"));
                report.setDate(result.getDate("Report_date"));
                report.setDescription(result.getString("Report_description"));
                report.setDiagnosis(result.getString("Report_diagnosis"));
                List<Disease> diseases = getDiseaseList(result.getString("Disease_id"));
                if (diseases != null && diseases.size() > 0) {
                    report.setDisease(diseases.get(0));
                }
                List<Doctor> doctors = getDoctorList(result.getString("Doctor_id"));
                if (doctors != null && doctors.size() > 0) {
                    report.setDoctor(doctors.get(0));
                }
                report.setId(result.getString("Report_id"));
                report.setNotes(result.getString("Report_note"));
                report.setTitle(result.getString("Report_titre"));
                report.setTreatment(result.getString("Report_treatment"));
                reportList.add(report);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reportList;
    }

    public static List<Disease> getDiseaseList(String diseaseId) {
        List<Disease> diseasesList = new ArrayList<Disease>();
        String query = "select Disease_id, Disease_description, Disease_type, Disease_note "
                + "from Diseases "
                + "where ( (Disease_id = ? and ? is not null ) or ? is null)";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, diseaseId);
            statement.setString(2, diseaseId);
            statement.setString(3, diseaseId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Disease disease = new Disease();
                disease.setId(result.getString("Disease_id"));
                disease.setType(result.getString("Disease_type"));
                disease.setNote(result.getString("Disease_note"));
                disease.setDescription(result.getString("Disease_description"));
                diseasesList.add(disease);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return diseasesList;
    }

    public static List<Doctor> getDoctorList(String doctorId) {
        List<Doctor> doctorsList = new ArrayList<Doctor>();
        String query = "select Person_id, Person_firstName, Person_lastName, Person_age, Person_phoneNumber"
                + ", Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender"
                + ", Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode "
                + ", Person_ln, Person_lg, Person_specialty "
                + "from Persons "
                + "where ((Person_id = ? and ? is not null) or ? is null) "
                + "and Person_type = 'D'";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctorId);
            statement.setString(1, doctorId);
            statement.setString(1, doctorId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(result.getString("Person_id"));
                doctor.setLg(result.getString("Person_lg"));
                doctor.setLn(result.getString("Person_ln"));
                doctor.setAge(result.getString("Person_age"));
                doctor.setEmail(result.getString("Person_email"));
                doctor.setGender(result.getString("Person_gender"));
                doctor.setRegion(result.getString("Person_region"));
                doctor.setZipCode(result.getString("Person_zipCode"));
                doctor.setAddress(result.getString("Person_address"));
                doctor.setCountry(result.getString("Person_country"));
                doctor.setLastName(result.getString("Person_lastName"));
                doctor.setFirstName(result.getString("Person_firstName"));
                doctor.setSpecialty(result.getString("Person_specialty"));
                doctor.setMotherName(result.getString("Person_motherName"));
                doctor.setFatherName(result.getString("Person_fatherName"));
                doctor.setPhoneNumber(result.getString("Person_phoneNumber"));
                doctor.setIsMaried("O".equals(result.getString("Person_isMarried")) ? true : false);
                doctor.setHasChildren("O".equals(result.getString("Person_hasChildren")) ? true : false);
                doctorsList.add(doctor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doctorsList;
    }
}
