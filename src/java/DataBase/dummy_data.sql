/* PERSONS */

INSERT INTO Persons(Person_id, Person_firstName, Person_lastName, Person_age, Person_phoneNumber, Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender, Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode)
     VALUES (UUID(), 'Roudy', 'Ghosn', 23, '70 461 559', 'roudyghosn@gmail.com', 'Elias', 'Salam', 'N', 'M', '', '', '', '', '');

/* PATIENTS */

INSERT INTO Patient(Patient_id, Person_id, Patient_note, Patient_responsibleObserver)
     VALUES ();

/* DOCTORS */

INSERT INTO Doctors(Doctor_id, Specialty_id, Person_id, Doctor_note, Doctor_ln, Doctor_lg)
     VALUES ();

/* MEDICALFILE */

INSERT INTO MedicalFile(MedicalFile_id, Patient_id, Doctor_id, MedicalFile_creationDate, MedicalFile_file, MedicalFile_note)
     VALUES ();

/* REPORT */

INSERT INTO Report(Report_id, MedicalFile_id, Patient_id, Doctor_id, Report_titre, Report_description, Report_attachment, Report_note, Report_diagnosis, Report_treatment, Report_date, Report_comments, Disease_id)
     VALUES ();

/* DISEASES */

INSERT INTO Diseases(Disease_id, Disease_description, Disease_type, Disease_note)
     VALUES ();

/* ROLES */

INSERT INTO Roles(Role_id, Role_description)
     VALUES (1, 'Administrator');
INSERT INTO Roles(Role_id, Role_description)
     VALUES (2, 'Doctor');
INSERT INTO Roles(Role_id, Role_description)
     VALUES (3, 'Patient');

/* USERS */

INSERT INTO Users(User_id, Person_id, User_userName, User_passowrd, User_creationDate, Role_id)
     VALUES (UUID(), '2147483647', 'admin', 'admin', NOW(), (select role_id from Roles where role_description = 'Administrator'));

/* SPECIALTY */

INSERT INTO Specialty(Specialty_id, Specialty_description, Specialty_note)
     VALUES ();