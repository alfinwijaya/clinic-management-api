CREATE TABLE IF NOT EXISTS patients(
    username VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100),
    phone_number INT
);

CREATE TABLE IF NOT EXISTS medic_records(
    id VARCHAR(100) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    diagnosis VARCHAR(255) NOT NULL,
    medical_prescription VARCHAR(255) NOT NULL,
    treatment_suggestion VARCHAR(255) NOT NULL,
    FOREIGN KEY fk_patients_medic_records (username) REFERENCES patients (username)
);