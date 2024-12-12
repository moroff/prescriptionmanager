-- Drugs
INSERT INTO drugs (name, package_size, pzn) VALUES 
('Thyronajod 75 Henning', 100, '06882248'),
('Thyronajod 100 Henning', 100, '06882277'),
('Aspirin Plus C', 10, '1406632'),

('DIGIMERCK MINOR 0.07, TAB', 100, ''),
('ESCITALOPRAM ABZ 10MG, FTA', 100, ''),
('AMLODIPIN AAA 5MG TABL, TAB', 100, ''),
('RAMILICH 5MG TABLETTEN, TAB', 100, ''),
('METOHEXA-SUCC 47.5MG, RET', 100, ''),
('HCT DEXCEL 25MG, TAB', 100, ''),

('L. THYROXIN WINTHROP 150UG, TAB', 100, ''),
('VALSACOR COP 80/12.5MG, FTA', 100, ''),
('TORASEMID AL 10MG TABL, TAB', 100, ''),
('PRADAXA 110MG, HKP', 100, ''),
('PROCORALAN 5MG FILMTABL, FTA', 100, ''),
('ALLOPURINAL ABZ 100MG TABL', 100, ''),
('NOVAINSULFON LICHTENSTEIN 500MG', 100, ''),
('PREDNISOLON ACIS 10MG', 100, ''),
('PANTOPRAZOL ARISTO 20MG TABL', 100, ''),
('METOPROLOL_RATIOPHARM SUCCINAT 47,5MG TABL', 100, '');

-- Patients
INSERT INTO patients (first_name, last_name) VALUES 
('Inge', 'S.'), 
('Rolf', 'S.'), 
('Birgitt', 'S.'), 
('Dieter', 'M.'); 

-- INGE
INSERT INTO drugbox (dayly_intake, inventory_amount, inventory_date, drug_id, patient_id) VALUES
(1.0, 50, '2018-02-01', 4, 1),
(0.5, 45, '2018-02-01', 5, 1),
(1.0, 40, '2018-02-01', 6, 1),
(2.0, 45, '2018-02-01', 7, 1),
(2.0, 30, '2018-02-01', 8, 1),
(0.5, 50, '2018-02-01', 9, 1),

-- ROLF
(1.0, 50, '2018-02-01', 10, 2),
(0.5, 50, '2018-02-01', 11, 2),
(3.0, 50, '2018-02-01', 12, 2),
(2.0, 50, '2018-02-01', 13, 2),
(2.0, 50, '2018-02-01', 14, 2),
(2.0, 50, '2018-04-04', 15, 2),
(2.0, 50, '2018-04-04', 16, 2),
(1.0, 50, '2018-04-04', 17, 2),
(0.5, 50, '2018-04-04', 18, 2),
(0.5, 50, '2018-04-04', 19, 2),

-- DIETER
(1.0, 75, '2018-02-01', 1, 4),

-- BIRGITT
(1.0, 15, '2018-02-01', 2, 3);
