/* PERSONS */

INSERT INTO Persons(Person_id, Person_firstName, Person_lastName, Person_age, Person_phoneNumber, Person_email, Person_fatherName, Person_motherName, Person_isMarried, Person_gender, Person_hasChildren, Person_address, Person_country, Person_region, Person_zipCode)
     VALUES ();

DELETE FROM Persons WHERE Person_id = '';

UPDATE Persons SET ... WHERE Person_id = '';

/* PATIENTS */

INSERT INTO Patient(Patient_id, Person_id, Patient_note, Patient_responsibleObserver)
     VALUES ();

DELETE FROM Patient WHERE Patient_id = '';

UPDATE Patient SET ... WHERE Patient_id = '';

/* DOCTORS */

INSERT INTO Doctors(Doctor_id, Specialty_id, Person_id, Doctor_note, Doctor_ln, Doctor_lg)
     VALUES ();

DELETE FROM Doctors WHERE Doctor_id = '';

UPDATE Doctors SET ... WHERE Doctor_id = '';

/* MEDICALFILE */

INSERT INTO MedicalFile(MedicalFile_id, Patient_id, Doctor_id, MedicalFile_creationDate, MedicalFile_file, MedicalFile_note)
     VALUES ();

DELETE FROM MedicalFile WHERE MedicalFile_id = '';

UPDATE MedicalFile SET ... WHERE MedicalFile_id = '';

/* REPORT */

INSERT INTO Report(Report_id, MedicalFile_id, Patient_id, Doctor_id, Report_titre, Report_description, Report_attachment, Report_note, Report_diagnosis, Report_treatment, Report_date, Report_comments, Disease_id)
     VALUES ();

DELETE FROM Report WHERE Report_id = '';

UPDATE Report SET ... WHERE Report_id = '';

/* DISEASES */

INSERT INTO Diseases(Disease_id, Disease_description, Disease_type, Disease_note)
     VALUES ();

DELETE FROM Diseases WHERE Disease_id = '';

UPDATE Diseases SET ... WHERE Disease_id = '';

/* ROLES */

INSERT INTO Roles(Role_id, Role_description)
     VALUES ();

DELETE FROM Roles WHERE Role_id = '';

UPDATE Roles SET ... WHERE Role_id = '';

/* USERS */

INSERT INTO Users(User_id, Person_id, User_userName, User_passowrd, User_creationDate, Role_id)
     VALUES ();

DELETE FROM Users WHERE User_id = '';

UPDATE Users SET ... WHERE User_id = '';

/* SPECIALTY */

INSERT INTO Specialty(Specialty_id, Specialty_description, Specialty_note)
     VALUES ();

DELETE FROM Specialty WHERE Specialty_id = '';

UPDATE Specialty SET ... WHERE Specialty_id = '';