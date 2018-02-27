INSERT INTO drugs (id, name, package_size, pzn) VALUES (1, 'Thyronajod 75 Henning', 100, '06882248') ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size, pzn) VALUES (2, 'Thyronajod 100 Henning', 100, '06882277') ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size, pzn) VALUES (3, 'Aspirin Plus C', 10, '1406632') ON CONFLICT DO NOTHING;

INSERT INTO drugs (id, name, package_size) VALUES (4, 'DIGIMERCK MINOR 0.07, TAB', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (5, 'ESCITALOPRAM ABZ 10MG, FTA', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (6, 'AMLODIPIN AAA 5MG TABL, TAB', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (7, 'RAMILICH 5MG TABLETTEN, TAB', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (8, 'METOHEXA-SUCC 47.5MG, RET', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (9, 'HCT DEXCEL 25MG, TAB', 100) ON CONFLICT DO NOTHING;

INSERT INTO drugs (id, name, package_size) VALUES (10, 'L. THYROXIN WINTHROP 150UG, TAB', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (11, 'VALSACOR COP 80/12.5MG, FTA', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (12, 'TORASEMID AL 10MG TABL, TAB', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (13, 'PRADAXA 110MG, HKP', 100) ON CONFLICT DO NOTHING;
INSERT INTO drugs (id, name, package_size) VALUES (14, 'PROCORALAN 5MG FILMTABL, FTA', 100) ON CONFLICT DO NOTHING;

INSERT INTO patients (id, first_name, last_name) VALUE (1, 'Inge', 'S.') ON CONFLICT DO NOTHING; 
INSERT INTO patients (id, first_name, last_name) VALUE (2, 'Rolf', 'S.') ON CONFLICT DO NOTHING; 
INSERT INTO patients (id, first_name, last_name) VALUE (3, 'Birgitt', 'S.') ON CONFLICT DO NOTHING; 
INSERT INTO patients (id, first_name, last_name) VALUE (4, 'Dieter', 'M.') ON CONFLICT DO NOTHING; 

-- INGE
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (4, 1.0, 50, '2018-02-01', 4, 1) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (5, 0.5, 45, '2018-02-01', 5, 1) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (6, 1.0, 40, '2018-02-01', 6, 1) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (7, 2.0, 45, '2018-02-01', 7, 1) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (8, 2.0, 30, '2018-02-01', 8, 1) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (9, 0.5, 50, '2018-02-01', 9, 1) ON CONFLICT DO NOTHING;

-- ROLF
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (10, 1.0, 50, '2018-02-01', 10, 2) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (11, 0.5, 50, '2018-02-01', 11, 2) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (12, 3.0, 50, '2018-02-01', 12, 2) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (13, 2.0, 50, '2018-02-01', 13, 2) ON CONFLICT DO NOTHING;
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (14, 2.0, 50, '2018-02-01', 14, 2) ON CONFLICT DO NOTHING;

-- DIETER
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (1, 1.0, 75, '2018-02-01', 1, 4) ON CONFLICT DO NOTHING;

-- BIRGITT
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id)  VALUE (2, 1.0, 15, '2018-02-01', 2, 3) ON CONFLICT DO NOTHING;
