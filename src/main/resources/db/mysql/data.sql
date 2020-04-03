INSERT IGNORE INTO drugs (id, name, package_size, pzn, version) VALUES (1, 'Thyronajod 75 Henning', 100, '06882248', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version) VALUES (2, 'Thyronajod 100 Henning', 100, '06882277', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version) VALUES (3, 'Aspirin Plus C', 10, '1406632', null);

INSERT IGNORE INTO drugs (id, name, package_size, pzn, version) VALUES (4, 'DIGIMERCK MINOR 0.07, TAB', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (5, 'ESCITALOPRAM ABZ 10MG, FTA', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (6, 'AMLODIPIN AAA 5MG TABL, TAB', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (7, 'RAMILICH 5MG TABLETTEN, TAB', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (8, 'METOHEXA-SUCC 47.5MG, RET', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (9, 'HCT DEXCEL 25MG, TAB', 100, '', null);

INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (10, 'L. THYROXIN WINTHROP 150UG, TAB', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (11, 'VALSACOR COP 80/12.5MG, FTA', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (12, 'TORASEMID AL 10MG TABL, TAB', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (13, 'PRADAXA 110MG, HKP', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (14, 'PROCORALAN 5MG FILMTABL, FTA', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (16, 'NOVAINSULFON LICHTENSTEIN 500MG', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (17, 'PREDNISOLON ACIS 10MG', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (18, 'PANTOPRAZOL ARISTO 20MG TABL', 100, '', null);
INSERT IGNORE INTO drugs (id, name, package_size, pzn, version)  VALUES (19, 'METOPROLOL_RATIOPHARM SUCCINAT 47,5MG TABL', 100, '', null);

INSERT IGNORE INTO patients (id, first_name, last_name, city, house_number, street, zip, telephone, version) VALUE (1, 'Inge', 'S.', '', '', '', '', '', null); 
INSERT IGNORE INTO patients (id, first_name, last_name, city, house_number, street, zip, telephone, version) VALUE (2, 'Rolf', 'S.', '', '', '', '', '', null); 
INSERT IGNORE INTO patients (id, first_name, last_name, city, house_number, street, zip, telephone, version) VALUE (3, 'Birgitt', 'S.', '', '', '', '', '', null); 
INSERT IGNORE INTO patients (id, first_name, last_name, city, house_number, street, zip, telephone, version) VALUE (4, 'Dieter', 'M.', '', '', '', '', '', null); 

-- INGE
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (4, 1.0, 50, '2018-02-01', 4, 1, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (5, 0.5, 45, '2018-02-01', 5, 1, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (6, 1.0, 40, '2018-02-01', 6, 1, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (7, 2.0, 45, '2018-02-01', 7, 1, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (8, 2.0, 30, '2018-02-01', 8, 1, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (9, 0.5, 50, '2018-02-01', 9, 1, null);

-- ROLF
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (10, 1.0, 50, '2018-02-01', 10, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (11, 0.5, 50, '2018-02-01', 11, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (12, 3.0, 50, '2018-02-01', 12, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (13, 2.0, 50, '2018-02-01', 13, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (14, 2.0, 50, '2018-02-01', 14, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (15, 2.0, 50, '2018-04-04', 15, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (16, 2.0, 50, '2018-04-04', 16, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (17, 1.0, 50, '2018-04-04', 17, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (18, 0.5, 50, '2018-04-04', 18, 2, null);
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (19, 0.5, 50, '2018-04-04', 19, 2, null);

-- DIETER
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (1, 1.0, 75, '2018-02-01', 1, 4, null);

-- BIRGITT
INSERT IGNORE INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id, version) VALUE (2, 1.0, 15, '2018-02-01', 2, 3, null);

-- PRESET VERSION TIMESTAMP
UPDATE drugs SET version=CURRENT_TIMESTAMP() WHERE version is null;
UPDATE drugbox SET version=CURRENT_TIMESTAMP() WHERE version is null;
UPDATE patients SET version=CURRENT_TIMESTAMP() WHERE version is null;
UPDATE therapy SET version=CURRENT_TIMESTAMP() WHERE version is null;
UPDATE therapy_appointment SET version=CURRENT_TIMESTAMP() WHERE version is null;
UPDATE therapy_prescription SET version=CURRENT_TIMESTAMP() WHERE version is null;
