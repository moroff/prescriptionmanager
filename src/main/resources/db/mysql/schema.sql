CREATE DATABASE IF NOT EXISTS prescriptionmngr;

ALTER DATABASE prescriptionmngr
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON prescriptionmngr.* TO pc@localhost IDENTIFIED BY 'pc';

USE prescriptionmngr;


-- table drugbox
CREATE TABLE drugbox (
        id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        dayly_intake DOUBLE(22) DEFAULT 1.0,
        inventory_amount DOUBLE(22) DEFAULT NULL,
        inventory_date DATE(10) DEFAULT NULL,
        drug_id INT(10) NOT NULL,
        patient_id INT(10) NOT NULL
);
-- table drugs
CREATE TABLE drugs (
        id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(80) NOT NULL ,
        package_size INT(10) DEFAULT NULL,
        pzn VARCHAR(8) DEFAULT NULL
);
-- table patients
CREATE TABLE patients (
        id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(255) NOT NULL ,
        last_name VARCHAR(255) NOT NULL ,
        city VARCHAR(255) DEFAULT NULL,
        house_number VARCHAR(255) DEFAULT NULL,
        street VARCHAR(255) DEFAULT NULL,
        zip VARCHAR(255) DEFAULT NULL,
        telephone VARCHAR(255) DEFAULT NULL
);

