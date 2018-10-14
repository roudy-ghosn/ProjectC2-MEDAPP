package CommonUtils;

import BusinessObjects.Appointment;
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
import java.sql.Date;
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
        String query = "select Roles.Role_description role, Persons.Person_firstName firstname, Persons.Person_lastName lastname, Persons.Person_id "
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
                SessionUtils.setRole(result.getString("role"));
                SessionUtils.setLastName(result.getString("lastname"));
                SessionUtils.setFirstName(result.getString("firstname"));
                SessionUtils.setLoggedPersonId(result.getString("Person_id"));
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
        String query = "select Person_id, Person_firstName, Person_lastName, Person_dob, Person_phoneNumber"
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
                person.setDateOfBirth(result.getDate("Person_dob"));
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

    public static void deleteDoctor(String doctorId) {
        String query = "delete from Persons where Person_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctorId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertDoctor(Doctor doctor) {
        String query = "Insert into Persons(Person_id, Person_firstName, Person_lastName, Person_dob"
                + ", Person_phoneNumber, Person_email, Person_address, Person_country, Person_region"
                + ", Person_type, Doctor_specialty) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'M', ?)";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, UUID.randomUUID().hashCode());
            statement.setString(2, doctor.getFirstName());
            statement.setString(3, doctor.getLastName());
            statement.setDate(4, new Date(doctor.getDateOfBirth().getTime()));
            statement.setString(5, doctor.getPhoneNumber());
            statement.setString(6, doctor.getEmail());
            statement.setString(7, doctor.getAddress());
            statement.setString(8, doctor.getCountry());
            statement.setString(9, doctor.getRegion());
            statement.setString(10, doctor.getSpecialty());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateDoctor(Doctor doctor) {
        String query = "Update Persons set Person_firstName = ?, Person_lastName = ?, Person_dob = ?"
                + ", Person_phoneNumber = ?, Person_email = ?, Person_address = ?, Person_country = ?"
                + ", Person_region = ?, Doctor_specialty = ? Where Person_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setDate(3, new Date(doctor.getDateOfBirth().getTime()));
            statement.setString(4, doctor.getPhoneNumber());
            statement.setString(5, doctor.getEmail());
            statement.setString(6, doctor.getAddress());
            statement.setString(7, doctor.getCountry());
            statement.setString(8, doctor.getRegion());
            statement.setString(9, doctor.getSpecialty());
            statement.setString(10, doctor.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
        String query = "select MedicalFile_id, Doctor_id, MedicalFile_creationDate, MedicalFile_file, MedicalFile_note "
                + " from MedicalFile where Patient_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, patientId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                file.setId(result.getString("MedicalFile_id"));
                List<Doctor> doctors = getDoctorList(result.getString("Doctor_id"));
                if (doctors != null && doctors.size() > 0) {
                    file.setDoctor(doctors.get(0));
                }
                file.setNotes(result.getString("MedicalFile_note"));
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
                report.setId(result.getString("Report_id"));
                report.setDate(result.getDate("Report_date"));
                report.setNotes(result.getString("Report_note"));
                report.setTitle(result.getString("Report_titre"));
                report.setDisease(result.getString("Disease_id"));
                report.setComments(result.getString("Report_comments"));
                report.setTreatment(result.getString("Report_treatment"));
                report.setDiagnosis(result.getString("Report_diagnosis"));
                report.setDescription(result.getString("Report_description"));
                List<Doctor> doctors = getDoctorList(result.getString("Doctor_id"));
                if (doctors != null && doctors.size() > 0) {
                    report.setDoctor(doctors.get(0));
                }
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
        String query = "select Person_id, Person_firstName, Person_lastName, Person_dob, Person_phoneNumber"
                + ", Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender"
                + ", Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode "
                + ", Person_ln, Person_lg, Doctor_specialty "
                + "from Persons "
                + "where ((Person_id = ? and ? is not null) or ? is null) "
                + "and Person_type = 'M'";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctorId);
            statement.setString(2, doctorId);
            statement.setString(3, doctorId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(result.getString("Person_id"));
                doctor.setLg(result.getString("Person_lg"));
                doctor.setLn(result.getString("Person_ln"));
                doctor.setEmail(result.getString("Person_email"));
                doctor.setGender(result.getString("Person_gender"));
                doctor.setRegion(result.getString("Person_region"));
                doctor.setDateOfBirth(result.getDate("Person_dob"));
                doctor.setZipCode(result.getString("Person_zipCode"));
                doctor.setAddress(result.getString("Person_address"));
                doctor.setCountry(result.getString("Person_country"));
                doctor.setLastName(result.getString("Person_lastName"));
                doctor.setFirstName(result.getString("Person_firstName"));
                doctor.setSpecialty(result.getString("Doctor_specialty"));
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
    public static List<Patient> getPatientList(String patientId) {
        List<Patient> patientList = new ArrayList<Patient>();
        String query = "select Person_id, Person_firstName, Person_lastName, Person_dob, Person_phoneNumber"
                + ", Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender"
                + ", Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode, Patient_respObsv "
                + "from Persons "
                + "where ((Person_id = ? and ? is not null) or ? is null) "
                + "and Person_type = 'P'";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, patientId);
            statement.setString(2, patientId);
            statement.setString(3, patientId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Patient patient = new Patient();
                patient.setId(result.getString("Person_id"));
                patient.setEmail(result.getString("Person_email"));
                patient.setGender(result.getString("Person_gender"));
                patient.setRegion(result.getString("Person_region"));
                patient.setDateOfBirth(result.getDate("Person_dob"));
                patient.setZipCode(result.getString("Person_zipCode"));
                patient.setAddress(result.getString("Person_address"));
                patient.setCountry(result.getString("Person_country"));
                patient.setLastName(result.getString("Person_lastName"));
                patient.setFirstName(result.getString("Person_firstName"));
                patient.setMotherName(result.getString("Person_motherName"));
                patient.setFatherName(result.getString("Person_fatherName"));
                patient.setPhoneNumber(result.getString("Person_phoneNumber"));
                patient.setIsMaried("O".equals(result.getString("Person_isMarried")) ? true : false);
                patient.setHasChildren("O".equals(result.getString("Person_hasChildren")) ? true : false);
                patient.setResponsibleObserver(result.getString("Patient_respObsv"));
                patientList.add(patient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return patientList;
    }

    public static List<Appointment> getAppointmentList(String doctorId){
        List<Appointment> appointmentList = new ArrayList<Appointment>();
         String query = "select Appoitment_id, Doctor_id, Patient_id, Appoitment_date, Appoitment_time, Appoitment_note "
                + "from Appointment "
                + "where (Doctor_id = ? "
                + "and Appoitment_date = '" + java.time.LocalDate.now() + "')";
         try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctorId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(result.getString("Appoitment_id"));
                List<Doctor> doctors = getDoctorList(result.getString("Doctor_id"));
                if (doctors != null && doctors.size() > 0) {
                    appointment.setDoctor(doctors.get(0));
                }
                List<Patient> patients = getPatientList(result.getString("Patient_id"));
                if (patients != null && patients.size() > 0) {
                    appointment.setPatient(patients.get(0));
                }
                appointment.setDate(result.getDate("Appoitment_date"));
                appointment.setTime(result.getString("Appoitment_time"));
                appointment.setNotes(result.getString("Appoitment_note"));
                appointmentList.add(appointment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         System.out.println(appointmentList);
        return appointmentList;
    }    
}
