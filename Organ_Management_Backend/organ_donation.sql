DROP DATABASE IF EXISTS organ_manager;
CREATE DATABASE organ_manager;
use organ_manager;

CREATE TABLE user(
    user_id INTEGER PRIMARY KEY auto_increment,
    username varchar(100) UNIQUE NOT NULL,
    password char(68) NOT NULL
);

CREATE TABLE role (
    role_id INTEGER NOT NULL auto_increment,
    role_name varchar(100) NOT NULL,
    constraint pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE patient_info (
    patient_id INTEGER auto_increment,
    user_id INTEGER NOT NULL,
    pname varchar(200) NOT NULL,
    gender varchar(20) DEFAULT NULL,
    age INTEGER NOT NULL,
    blood_type varchar(5) NOT NULL,
    mobile_number varchar(100) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (patient_id)
);

CREATE TABLE donor (
    donor_id INTEGER auto_increment,
    patient_id INTEGER NOT NULL,
    organ_donated varchar(50) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (donor_id)
);

CREATE TABLE recipient (
    rec_id INTEGER auto_increment,
    patient_id INTEGER NOT NULL,
    organ_requested varchar(50) NOT NULL,
    priority INTEGER NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (rec_id)
);

CREATE TABLE doctor_info (
    doctor_id INTEGER auto_increment,
    user_id INTEGER NOT NULL,
    doctor_name varchar(200) NOT NULL,
    speciality varchar(200) NOT NULL,
    phone varchar(50) NOT NULL,
    constraint pk_doctorInfo PRIMARY KEY (doctor_id)
);

CREATE TABLE matches (
    match_id INTEGER auto_increment,
    donor_id INTEGER NOT NULL,
    rec_id INTEGER NOT NULL,
    completed INTEGER,
    constraint pk_matches PRIMARY KEY (match_id)
);

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_userRoles
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE user_roles
ADD CONSTRAINT fk_role_userRoles
FOREIGN KEY (role_id) REFERENCES role(role_id);

ALTER TABLE doctor_info
ADD CONSTRAINT fk_doctorInfo
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE patient_info
ADD CONSTRAINT fk_patientInfo
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE donor
ADD CONSTRAINT fk_donorInfo
FOREIGN KEY (patient_id) REFERENCES patient_info(patient_id);

ALTER TABLE recipient
ADD CONSTRAINT fk_recInfo
FOREIGN KEY (patient_id) REFERENCES patient_info(patient_id);

ALTER TABLE matches
ADD CONSTRAINT fk_donorId
FOREIGN KEY (donor_id) REFERENCES donor(donor_id);

ALTER TABLE matches
ADD CONSTRAINT fk_recId
FOREIGN KEY (rec_id) REFERENCES recipient(rec_id);

INSERT INTO role values (1,'ADMIN'),(2,'DOCTOR'),(3,'USER');
INSERT INTO user values (1,"admin","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
-- test123

INSERT INTO user values (2,"doctor1","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values (3,"doctor2","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values (4,"doctor3","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");

INSERT INTO user values (5,"sankalp","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values (6,"siddharth","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values (7,"charan","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values (8,"prachoday","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");

INSERT INTO user_roles values (1,1);
INSERT INTO user_roles values (2,2);
INSERT INTO user_roles values (3,2);
INSERT INTO user_roles values (4,2);
INSERT INTO user_roles values (5,3);
INSERT INTO user_roles values (6,3);
INSERT INTO user_roles values (7,3);
INSERT INTO user_roles values (8,3);

INSERT INTO doctor_info values (1,2,"John","Respiratory diseases","9988776655");
INSERT INTO doctor_info values (2,3,"Chris","Cardiovascular","9769769768");
INSERT INTO doctor_info values (3,4,"Chris","Surgeon","9769769768");
INSERT INTO patient_info values (1,5,"Sankalp","M",20,"B+","1010101010");
INSERT INTO patient_info values (2,6,"Siddharth","M",40,"A-","3203203202");
INSERT INTO patient_info values (3,7,"Charan","M",26,"B+","9999999999");
INSERT INTO patient_info values (4,8,"Prachoday","M",32,"O+","8880008880");

INSERT INTO donor values (1,1,"Kidney");
INSERT INTO donor values (2,2,"Liver");

INSERT INTO recipient values (1,3,"Liver",7);
INSERT INTO recipient values (2,3,"Kidney",10);
INSERT INTO recipient values (3,4,"Eyes",3);

INSERT INTO matches values (1,1,2,0);