-- Drugs
INSERT INTO drugs (id, name, package_size, pzn) VALUES 
(1, 'Thyronajod 75 Henning', 100, '06882248'),
(2, 'Thyronajod 100 Henning', 100, '06882277'),
(3, 'Aspirin Plus C', 10, '1406632'),

(4, 'DIGIMERCK MINOR 0.07, TAB', 100, ''),
(5, 'ESCITALOPRAM ABZ 10MG, FTA', 100, ''),
(6, 'AMLODIPIN AAA 5MG TABL, TAB', 100, ''),
(7, 'RAMILICH 5MG TABLETTEN, TAB', 100, ''),
(8, 'METOHEXA-SUCC 47.5MG, RET', 100, ''),
(9, 'HCT DEXCEL 25MG, TAB', 100, ''),

(10, 'L. THYROXIN WINTHROP 150UG, TAB', 100, ''),
(11, 'VALSACOR COP 80/12.5MG, FTA', 100, ''),
(12, 'TORASEMID AL 10MG TABL, TAB', 100, ''),
(13, 'PRADAXA 110MG, HKP', 100, ''),
(14, 'PROCORALAN 5MG FILMTABL, FTA', 100, ''),
(15, 'ALLOPURINAL ABZ 100MG TABL', 100, ''),
(16, 'NOVAINSULFON LICHTENSTEIN 500MG', 100, ''),
(17, 'PREDNISOLON ACIS 10MG', 100, ''),
(18, 'PANTOPRAZOL ARISTO 20MG TABL', 100, ''),
(19, 'METOPROLOL_RATIOPHARM SUCCINAT 47,5MG TABL', 100, '');

-- Patients
INSERT INTO patients (id, first_name, last_name) VALUES 
(1, 'Inge', 'S.'), 
(2, 'Rolf', 'S.'), 
(3, 'Birgitt', 'S.'), 
(4, 'Dieter', 'M.'); 

-- INGE
INSERT INTO drugbox (id, dayly_intake, inventory_amount, inventory_date, drug_id, patient_id) VALUES
(4, 1.0, 50, '2018-02-01', 4, 1),
(5, 0.5, 45, '2018-02-01', 5, 1),
(6, 1.0, 40, '2018-02-01', 6, 1),
(7, 2.0, 45, '2018-02-01', 7, 1),
(8, 2.0, 30, '2018-02-01', 8, 1),
(9, 0.5, 50, '2018-02-01', 9, 1),

-- ROLF
(10, 1.0, 50, '2018-02-01', 10, 2),
(11, 0.5, 50, '2018-02-01', 11, 2),
(12, 3.0, 50, '2018-02-01', 12, 2),
(13, 2.0, 50, '2018-02-01', 13, 2),
(14, 2.0, 50, '2018-02-01', 14, 2),
(15, 2.0, 50, '2018-04-04', 15, 2),
(16, 2.0, 50, '2018-04-04', 16, 2),
(17, 1.0, 50, '2018-04-04', 17, 2),
(18, 0.5, 50, '2018-04-04', 18, 2),
(19, 0.5, 50, '2018-04-04', 19, 2),

-- DIETER
(1, 1.0, 75, '2018-02-01', 1, 4),

-- BIRGITT
(2, 1.0, 15, '2018-02-01', 2, 3);
