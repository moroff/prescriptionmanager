
-- table drugbox
DROP TABLE drugbox IF EXISTS;
CREATE TABLE drugbox (
        id INTEGER IDENTITY PRIMARY KEY,
        dayly_intake DOUBLE(22) DEFAULT 1.0,
        inventory_amount DOUBLE(22) DEFAULT NULL,
        inventory_date DATE(10) DEFAULT NULL,
        drug_id INT(10) NOT NULL,
        patient_id INT(10) NOT NULL
);
--CREATE unique index dbi_drug_patient ON drugbox (patient_id, drug_id);

-- table drugs
DROP TABLE drugs IF EXISTS;
CREATE TABLE drugs (
        id INTEGER IDENTITY PRIMARY KEY,
        name VARCHAR(80) NOT NULL ,
        package_size INT(10) DEFAULT NULL,
        pzn VARCHAR(8) DEFAULT NULL
);
CREATE INDEX drugs_name ON drugs (name);

-- table patients
DROP TABLE patients IF EXISTS;
CREATE TABLE patients (
        id INTEGER IDENTITY PRIMARY KEY,
        first_name VARCHAR(255) NOT NULL ,
        last_name VARCHAR(255) NOT NULL ,
        city VARCHAR(255) DEFAULT NULL,
        house_number VARCHAR(255) DEFAULT NULL,
        street VARCHAR(255) DEFAULT NULL,
        zip VARCHAR(255) DEFAULT NULL,
        telephone VARCHAR(255) DEFAULT NULL
);
