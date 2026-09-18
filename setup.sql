DROP DATABASE IF EXISTS cms;
CREATE DATABASE cms;
USE cms;

CREATE TABLE doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Sample doctors
INSERT INTO doctor (name, specialty, email, password, phone) VALUES
('Dr. Alice Tran', 'Cardiology', 'alice.tran@clinic.com', 'hashed_pw_1', '0901111111'),
('Dr. Ben Nguyen', 'Dermatology', 'ben.nguyen@clinic.com', 'hashed_pw_2', '0902222222'),
('Dr. Chi Pham', 'Pediatrics', 'chi.pham@clinic.com', 'hashed_pw_3', '0903333333');

-- Sample patients (7, so we can show "exactly 5 records")
INSERT INTO patient (name, email, password, phone, address) VALUES
('Nguyen Van A', 'a@example.com', 'hashed_pw_a', '0911111111', 'HCMC'),
('Tran Thi B', 'b@example.com', 'hashed_pw_b', '0922222222', 'Hanoi'),
('Le Van C', 'c@example.com', 'hashed_pw_c', '0933333333', 'Da Nang'),
('Pham Thi D', 'd@example.com', 'hashed_pw_d', '0944444444', 'HCMC'),
('Hoang Van E', 'e@example.com', 'hashed_pw_e', '0955555555', 'Hue'),
('Vo Thi F', 'f@example.com', 'hashed_pw_f', '0966666666', 'HCMC'),
('Dang Van G', 'g@example.com', 'hashed_pw_g', '0977777777', 'Can Tho');

-- Sample appointments across several months
INSERT INTO appointment (doctor_id, patient_id, appointment_time, status) VALUES
(1, 1, '2026-09-19 09:00:00', 'SCHEDULED'),
(1, 2, '2026-09-19 10:00:00', 'SCHEDULED'),
(1, 3, '2026-08-05 09:00:00', 'COMPLETED'),
(2, 4, '2026-09-19 11:00:00', 'SCHEDULED'),
(2, 5, '2026-07-15 09:00:00', 'COMPLETED'),
(3, 6, '2026-09-19 14:00:00', 'SCHEDULED'),
(3, 7, '2026-09-01 09:00:00', 'COMPLETED'),
(1, 4, '2026-09-01 15:00:00', 'COMPLETED');

DELIMITER //

-- Q21: daily appointment report for a given doctor
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN p_doctor_id BIGINT, IN p_date DATE)
BEGIN
    SELECT a.id, p.name AS patient_name, a.appointment_time, a.status
    FROM appointment a
    JOIN patient p ON a.patient_id = p.id
    WHERE a.doctor_id = p_doctor_id
      AND DATE(a.appointment_time) = p_date
    ORDER BY a.appointment_time;
END //

-- Q22: doctor with the most patients in a given month/year
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN p_month INT, IN p_year INT)
BEGIN
    SELECT d.id AS doctor_id, d.name AS doctor_name, COUNT(DISTINCT a.patient_id) AS patient_count
    FROM appointment a
    JOIN doctor d ON a.doctor_id = d.id
    WHERE MONTH(a.appointment_time) = p_month
      AND YEAR(a.appointment_time) = p_year
    GROUP BY d.id, d.name
    ORDER BY patient_count DESC
    LIMIT 1;
END //

-- Q23: doctor with the most patients in a given year
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN p_year INT)
BEGIN
    SELECT d.id AS doctor_id, d.name AS doctor_name, COUNT(DISTINCT a.patient_id) AS patient_count
    FROM appointment a
    JOIN doctor d ON a.doctor_id = d.id
    WHERE YEAR(a.appointment_time) = p_year
    GROUP BY d.id, d.name
    ORDER BY patient_count DESC
    LIMIT 1;
END //

DELIMITER ;
