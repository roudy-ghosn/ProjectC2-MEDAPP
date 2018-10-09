CREATE TABLE Persons (
    Person_id int NOT NULL PRIMARY KEY,
    Person_firstName varchar(255) NOT NULL,
    Person_lastName varchar(255) NOT NULL,
    Person_age int,
    Person_phoneNumber int,
    Person_email varchar(250),
    Person_fatherName varchar(250),
    Person_motherName varchar(250),
    Person_numSec int,
    Person_isMarried varchar(1) NOT NULL,
    Person_gender char(1) NOT NULL,
    Person_hasChildren varchar(1),
    Person_address varchar(500),
    Person_country varchar(250),
    Person_region varchar(250),
    Person_zipCode varchar(250)
); 

CREATE TABLE Patient (
    Patient_id int NOT NULL PRIMARY KEY,
    Person_id int NOT NULL,
    Patient_note varchar(500),
    Patient_responsibleObserver int
); 

ALTER TABLE Patient
ADD FOREIGN KEY (Person_id) REFERENCES Persons(Person_id);

ALTER TABLE Patient
ADD FOREIGN KEY (Patient_responsibleObserver) REFERENCES Doctors(Doctor_id);


CREATE TABLE Doctors (
    Doctor_id int NOT NULL PRIMARY KEY,
    Specialty_id int ,
    Person_id int NOT NULL,
    Doctor_note varchar(500),
    Doctor_ln varchar(50),
    Doctor_lg varchar(50)
); 

ALTER TABLE Doctors
ADD FOREIGN KEY (Person_id) REFERENCES Persons(Person_id);

ALTER TABLE Doctors
ADD FOREIGN KEY (Specialty_id) REFERENCES Specialty(Specialty_id);

CREATE TABLE MedicalFile (
    MedicalFile_id int NOT NULL PRIMARY KEY,
    Patient_id int,
    Doctor_id int,
    MedicalFile_creationDate date,
    MedicalFile_file varbinary(10000),
    MedicalFile_note varchar(500)
); 

ALTER TABLE MedicalFile
ADD FOREIGN KEY (Patient_id) REFERENCES Patient(Patient_id);

ALTER TABLE MedicalFile
ADD FOREIGN KEY (Doctor_id) REFERENCES Doctors(Doctor_id);

CREATE TABLE Report (
    Report_id int NOT NULL PRIMARY KEY,
    MedicalFile_id int NOT NULL,
    Patient_id int NOT NULL,
    Doctor_id int NOT NULL,
    Report_titre varchar(100),
    Report_description varchar(10000),
    Report_attachment varchar(10000),
    Report_note varchar(10000),
    Report_diagnosis varchar(10000),
    Report_treatment varchar(10000),
    Report_date date,
    Report_comments varchar(10000),
    Disease_id int
); 

ALTER TABLE Report
ADD FOREIGN KEY (MedicalFile_id) REFERENCES MedicalFile(MedicalFile_id);

ALTER TABLE Report
ADD FOREIGN KEY (Patient_id) REFERENCES Patient(Patient_id);

ALTER TABLE Report
ADD FOREIGN KEY (Doctor_id) REFERENCES Doctors(Doctor_id);

ALTER TABLE Report
ADD FOREIGN KEY (Disease_id) REFERENCES Diseases(Disease_id);


CREATE TABLE Diseases (
    Disease_id int NOT NULL PRIMARY KEY,
    Disease_description varchar(10000),
    Disease_type varchar(10000),
    Disease_note varchar(10000)
); 

CREATE TABLE Roles (
    Role_id int NOT NULL PRIMARY KEY,
    Role_description varchar(10000)
);

CREATE TABLE Users (
    User_id int NOT NULL PRIMARY KEY,
    Person_id int NOT NULL,
    User_userName varchar(25) NOT NULL,
    User_passowrd varchar(25),
    User_creationDate date,
    Role_id int
); 

ALTER TABLE Users
ADD FOREIGN KEY (Person_id) REFERENCES Persons(Person_id);

ALTER TABLE Users
ADD FOREIGN KEY (Role_id) REFERENCES Roles(Role_id);

CREATE TABLE Specialty (
    Specialty_id int NOT NULL PRIMARY KEY,
    Specialty_description varchar(500),
    Specialty_note varchar(500)
);
