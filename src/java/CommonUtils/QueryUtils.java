package CommonUtils;

import Beans.HomePageBean;
import BusinessObjects.Appointment;
import BusinessObjects.Disease;
import BusinessObjects.Doctor;
import BusinessObjects.MedicalFile;
import BusinessObjects.Patient;
import BusinessObjects.Person;
import BusinessObjects.Report;
import BusinessObjects.Role;
import BusinessObjects.User;
import Constants.Constants;
import DataBase.DBConfig;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JOptionPane;

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
                + "where ( lower(Users.User_userName) like lower('%" + (filter != null ? filter : "") + "%') "
                + " or lower(Users.User_id) like lower('%" + (filter != null ? filter : "") + "%') ) "
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

            while (result.next()) {
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

    public static void deletePerson(String personId) {
        String query = "delete from Persons where Person_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, personId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertDoctor(Doctor doctor) {
        String query = "Insert into Persons(Person_id, Person_firstName, Person_lastName, Person_dob"
                + ", Person_phoneNumber, Person_email, Person_address, Person_country, Person_fatherName"
                + ", Person_motherName, Person_isMarried, Person_hasChildren, Person_gender, Person_zipCode"
                + ", Person_ln, Person_lg, Person_region, Doctor_specialty, Person_type)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'M');";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, UUID.randomUUID().hashCode());
            statement.setString(2, doctor.getFirstName());
            statement.setString(3, doctor.getLastName());
            if (doctor.getDateOfBirth() != null) {
                statement.setDate(4, new Date(doctor.getDateOfBirth().getTime()));
            } else {
                statement.setDate(4, null);
            }
            statement.setString(5, doctor.getPhoneNumber());
            statement.setString(6, doctor.getEmail());
            statement.setString(7, doctor.getAddress());
            statement.setString(8, doctor.getCountry());
            statement.setString(9, doctor.getFatherName());
            statement.setString(10, doctor.getMotherName());
            statement.setString(11, (doctor.isIsMaried() ? "O" : "N"));
            statement.setString(12, (doctor.isHasChildren() ? "O" : "N"));
            statement.setString(13, doctor.getGender());
            statement.setString(14, doctor.getZipCode());
            statement.setString(15, doctor.getLn());
            statement.setString(16, doctor.getLg());
            statement.setString(17, doctor.getRegion());
            statement.setString(18, doctor.getSpecialty());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateDoctor(Doctor doctor) {
        String query = "Update Persons set Person_firstName = ?, Person_lastName = ?, Person_dob = ?"
                + ", Person_phoneNumber = ?, Person_email = ?, Person_address = ?, Person_country = ?"
                + ", Person_fatherName = ?, Person_motherName = ?, Person_isMarried = ?"
                + ", Person_hasChildren = ?, Person_gender = ?, Person_zipCode = ?, Person_ln = ?"
                + ", Person_lg = ?, Person_region = ?, Doctor_specialty = ? Where Person_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            if (doctor.getDateOfBirth() != null) {
                statement.setDate(3, new Date(doctor.getDateOfBirth().getTime()));
            } else {
                statement.setDate(3, null);
            }
            statement.setString(4, doctor.getPhoneNumber());
            statement.setString(5, doctor.getEmail());
            statement.setString(6, doctor.getAddress());
            statement.setString(7, doctor.getCountry());
            statement.setString(8, doctor.getFatherName());
            statement.setString(9, doctor.getMotherName());
            statement.setString(10, (doctor.isIsMaried() ? "O" : "N"));
            statement.setString(11, (doctor.isHasChildren() ? "O" : "N"));
            statement.setString(12, doctor.getGender());
            statement.setString(13, doctor.getLn());
            statement.setString(14, doctor.getLg());
            statement.setString(15, doctor.getZipCode());
            statement.setString(16, doctor.getRegion());
            statement.setString(17, doctor.getSpecialty());
            statement.setString(18, doctor.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertPatient(Patient patient) {
        String query = "Insert into Persons(Person_id, Person_firstName, Person_lastName, Person_dob"
                + ", Person_phoneNumber, Person_email, Person_address, Person_country, Person_fatherName"
                + ", Person_motherName, Person_isMarried, Person_hasChildren, Person_gender, Person_zipCode"
                + ", Person_region, Patient_respObsv, Person_type)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'P');";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, UUID.randomUUID().hashCode());
            statement.setString(2, patient.getFirstName());
            statement.setString(3, patient.getLastName());
            if (patient.getDateOfBirth() != null) {
                statement.setDate(4, new Date(patient.getDateOfBirth().getTime()));
            } else {
                statement.setDate(4, null);
            }
            statement.setString(5, patient.getPhoneNumber());
            statement.setString(6, patient.getEmail());
            statement.setString(7, patient.getAddress());
            statement.setString(8, patient.getCountry());
            statement.setString(9, patient.getFatherName());
            statement.setString(10, patient.getMotherName());
            statement.setString(11, (patient.isIsMaried() ? "O" : "N"));
            statement.setString(12, (patient.isHasChildren() ? "O" : "N"));
            statement.setString(13, patient.getGender());
            statement.setString(14, patient.getZipCode());
            statement.setString(15, patient.getRegion());
            statement.setString(16, patient.getResponsibleObserver());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updatePatient(Patient patient) {
        String query = "Update Persons set Person_firstName = ?, Person_lastName = ?, Person_dob = ?"
                + ", Person_phoneNumber = ?, Person_email = ?, Person_address = ?, Person_country = ?"
                + ", Person_fatherName = ?, Person_motherName = ?, Person_isMarried = ?"
                + ", Person_hasChildren = ?, Person_gender = ?, Person_zipCode = ?, Patient_respObsv = ?"
                + ", Person_region = ? Where Person_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            if (patient.getDateOfBirth() != null) {
                statement.setDate(3, new Date(patient.getDateOfBirth().getTime()));
            } else {
                statement.setDate(3, null);
            }
            statement.setString(4, patient.getPhoneNumber());
            statement.setString(5, patient.getEmail());
            statement.setString(6, patient.getAddress());
            statement.setString(7, patient.getCountry());
            statement.setString(8, patient.getFatherName());
            statement.setString(9, patient.getMotherName());
            statement.setString(10, (patient.isIsMaried() ? "O" : "N"));
            statement.setString(11, (patient.isHasChildren() ? "O" : "N"));
            statement.setString(12, patient.getGender());
            statement.setString(13, patient.getZipCode());
            statement.setString(14, patient.getResponsibleObserver());
            statement.setString(15, patient.getRegion());
            statement.setString(16, patient.getId());
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

    public static Report getReport(String reportId) {
        Report report = new Report();
        String query = "select Report_id, Doctor_id, Report_titre, Report_description, "
                + "Report_attachment, Report_note, Report_diagnosis, Report_treatment, "
                + "Report_date, Report_comments, Disease_id "
                + "from Report where Report_id = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, reportId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return report;
    }

    public static void insertReport(Report report) {
        String query = "Insert into Report(Report_id, Doctor_id, Report_titre, Report_description, "
                + "Report_attachment, Report_note, Report_diagnosis, Report_treatment, "
                + "Report_date, Report_comments, Disease_id "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, UUID.randomUUID().hashCode());
            statement.setString(2, report.getDoctor().getId());
            statement.setString(3, report.getTitle());
            statement.setString(4, report.getDescription());
            statement.setString(5, report.getAttachment());
            statement.setString(6, report.getNotes());
            statement.setString(7, report.getDiagnosis());
            statement.setString(8, report.getTreatment());
            if (report.getDate() != null) {
                statement.setDate(9, new Date(report.getDate().getTime()));
            } else {
                statement.setDate(9, null);
            }
            statement.setString(10, report.getComments());
            statement.setString(11, report.getDisease());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateReport(Report report) {
        String query = "Update Report set Report_titre = ?, Report_description = ?, "
                + "Report_attachment = ?, Report_note = ?, Report_diagnosis = ?, Report_treatment = ?, "
                + "Report_date = ?, Report_comments = ?, Disease_id = ? "
                + "Where Report_id = ?";

        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, report.getTitle());
            statement.setString(2, report.getDescription());
            statement.setString(3, report.getAttachment());
            statement.setString(4, report.getNotes());
            statement.setString(5, report.getDiagnosis());
            statement.setString(6, report.getTreatment());
            if (report.getDate() != null) {
                statement.setDate(7, new Date(report.getDate().getTime()));
            } else {
                statement.setDate(7, null);
            }
            statement.setString(8, report.getComments());
            statement.setString(9, report.getDisease());
            statement.setString(10, report.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    public static List<Appointment> getAllAppointments(String doctorId) {
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        String query = "select Appoitment_id, Doctor_id, Patient_id, Appoitment_date, Appoitment_time, Appoitment_note "
                + "from Appointment "
                + "where (Doctor_id = ? "
                + ")";
        //and Appoitment_date = '" + java.time.LocalDate.now() + "'
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
        return appointmentList;
    }
    
    public static List<Appointment> getAppointmentList(String doctorId) {
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
        return appointmentList;
    }

    /* DataBase Backup Procedure */
    public static String[] Backupdbtosql() {
        try {
            /* Return the files location and backup success/failure */
            String[] result = new String[2];

            /* Getting path to the Jar file being executed */
            CodeSource codeSource = DBConfig.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            /* Creating Path Constraints for folder saving */
            String folderPath = jarDir + "\\backup";

            /* Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();

            /* Creating Path Constraints for backup saving */
            String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";
            result[0] = savePath;

            /* Used to create a cmd command*/
            String executeCmd = Constants.mysqldump_dir + " --column-statistics=0 -h " + Constants.db_host + " -u "
                    + Constants.db_username + " -p" + Constants.db_password + " --add-drop-database -B "
                    + Constants.db_name + " -r " + savePath;

            /* Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /* processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                result[1] = "success";
            } else {
                result[1] = "failure";
            }
            return result;
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /* Charts Data */
    public static String[] getPatientsDistributionByGender() {
        String[] results = new String[2];
        String query = "SELECT m.male maleCount, f.female femaleCount"
                     + "  FROM (SELECT COUNT(*) male FROM Persons m WHERE Person_gender = 'M') m"
                          + ", (SELECT COUNT(*) female FROM Persons WHERE Person_gender = 'F') f";
        try {
            statement = dbConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                results[0] = result.getString("maleCount");
                results[1] = result.getString("femaleCount");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }
    
    public static Map<String, String[]> getDiseaseDistribution(String year) {
        Map<String, String[]> results = new HashMap<String, String[]>();
        String query = "SELECT Report.Disease_id, Diseases.Disease_description, count(*) counter "
                + "FROM Report, Diseases "
                + "Where Report.Disease_id = Diseases.Disease_id "
                + "  And YEAR(Report.Report_date) = ? "
                + "Group by Disease_id";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, year);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String[] disease = new String[2];
                disease[0] = result.getString("Disease_description");
                disease[1] = result.getString("counter");
                results.put(result.getString("Disease_id"), disease);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    public static Map<String, String[]> getUsersDistributionForPast5Years(String year) {
        Map<String, String[]> results = new HashMap<String, String[]>();
        String query = "Select year(Users.User_creationDate) creationDate, count(*) counter"
                     + "  From Users"
                     + " Where YEAR(Users.User_creationDate) <= ? "
                    + " Group By year(Users.User_creationDate)"
                     + " Limit 5";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, year);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String[] disease = new String[2];
                disease[0] = result.getString("creationDate");
                disease[1] = result.getString("counter");
                results.put(result.getString("creationDate"), disease);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }
}
