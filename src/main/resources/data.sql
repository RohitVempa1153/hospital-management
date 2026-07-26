-- INSERT INTO patient (name, gender, birth_date, email, blood_group) 
-- VALUES 
-- ('Aarav Sharma', 'MALE', '1990-05-10', 'aarav.sharma@example.com', 'O_POSITIVE'),
-- ('Diya Patel', 'FEMALE', '1995-08-20', 'diya.patel@example.com', 'A_POSITIVE'),
-- ('Dishant Verma', 'MALE', '1988-03-15', 'dishant.verma@example.com', 'A_POSITIVE'),
-- ('Neha Iyer', 'FEMALE', '1992-12-01', 'neha.iyer@example.com', 'AB_POSITIVE'),
-- ('Kabir Singh', 'MALE', '1993-07-11', 'kabir.singh@example.com', 'O_POSITIVE');

-- INSERT INTO doctor (name, specialization, email)
-- VALUES
--     ('Dr. Rakesh Mehta', 'Cardiology', 'rakesh.mehta@example.com'),
--     ('Dr. Sneha Kapoor', 'Dermatology', 'sneha.kapoor@example.com'),
--     ('Dr. Arjun Nair', 'Orthopedics', 'arjun.nair@example.com');

-- INSERT INTO appointment (appointment_time, reason, doctor_id, patient_id)
-- VALUES
--   ('2025-07-01 10:30:00', 'General Checkup', 1, 2),
--   ('2025-07-02 11:00:00', 'Skin Rash', 2, 2),
--   ('2025-07-03 09:45:00', 'Knee Pain', 3, 3),
--   ('2025-07-04 14:00:00', 'Follow-up Visit', 1, 1),
--   ('2025-07-05 16:15:00', 'Consultation', 1, 4),
--   ('2025-07-06 08:30:00', 'Allergy Treatment', 2, 5);



-- =====================================================================
-- DUMMY SEED DATA for Hospital Management Application
-- =====================================================================
-- IMPORTANT: Table/column names below follow Hibernate's DEFAULT naming
-- convention based on your entities. Please verify against your actual
-- generated schema (enable `spring.jpa.show-sql=true` or run with
-- ddl-auto=update once and inspect logs) before running this, especially:
--   - app_user_roles          (from @ElementCollection Set<RoleType>)
--   - doctor.user_id          (PK, from @MapsId)
--   - patient.user_id         (PK, from @MapsId)
--   - my_dpt_doctors          (explicitly named via @JoinTable — safe)
--
-- All passwords below are BCrypt hashes for the plaintext "password123".
-- Replace with your own if your BCryptPasswordEncoder strength differs.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. USERS (app_user)
-- ---------------------------------------------------------------------
-- 1 admin, 3 doctors, 6 patients = 10 users total

INSERT INTO app_user (id, provider_id, provider_type, username, password) VALUES
(1, NULL, 'EMAIL', 'admin@hospital.com',        '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(2, NULL, 'EMAIL', 'dr.sharma@hospital.com',     '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(3, NULL, 'EMAIL', 'dr.mehta@hospital.com',      '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(4, NULL, 'EMAIL', 'dr.iyer@hospital.com',       '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(5, NULL, 'EMAIL', 'rahul.verma@mail.com',       '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(6, NULL, 'EMAIL', 'priya.nair@mail.com',        '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(7, NULL, 'EMAIL', 'arjun.singh@mail.com',       '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(8, NULL, 'EMAIL', 'sneha.reddy@mail.com',       '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(9, NULL, 'EMAIL', 'kabir.khan@mail.com',        '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k'),
(10, NULL, 'EMAIL', 'ananya.das@mail.com',       '$2a$10$7QJ8mZ1z9Xw2vB3nK4pL5eYh6tR7sU8vW9xZ0aB1cD2eF3gH4iJ5k');

-- ---------------------------------------------------------------------
-- 2. USER ROLES (app_user_roles) — from @ElementCollection Set<RoleType>
-- ---------------------------------------------------------------------

INSERT INTO app_user_roles (user_id, roles) VALUES
(1, 'ADMIN'),
(2, 'DOCTOR'),
(3, 'DOCTOR'),
(4, 'DOCTOR'),
(5, 'PATIENT'),
(6, 'PATIENT'),
(7, 'PATIENT'),
(8, 'PATIENT'),
(9, 'PATIENT'),
(10, 'PATIENT');

-- ---------------------------------------------------------------------
-- 3. DOCTORS — id/user_id shared with app_user via @MapsId
-- ---------------------------------------------------------------------

INSERT INTO doctor (user_id, name, specialization, email, created_at) VALUES
(2, 'Dr. Anil Sharma',  'Cardiology',    'dr.sharma@hospital.com', CURRENT_TIMESTAMP),
(3, 'Dr. Kavita Mehta', 'Dermatology',   'dr.mehta@hospital.com',  CURRENT_TIMESTAMP),
(4, 'Dr. Rohan Iyer',   'Orthopedics',   'dr.iyer@hospital.com',   CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------
-- 4. PATIENTS — id/user_id shared with app_user via @MapsId
-- ---------------------------------------------------------------------

INSERT INTO patient (user_id, name, birth_date, email, gender, created_at, blood_group, insurance_id) VALUES
(5,  'Rahul Verma', '1990-04-12', 'rahul.verma@mail.com', 'MALE',   CURRENT_TIMESTAMP, 'O_POSITIVE',  NULL),
(6,  'Priya Nair',  '1988-11-02', 'priya.nair@mail.com',  'FEMALE', CURRENT_TIMESTAMP, 'A_POSITIVE',  NULL),
(7,  'Arjun Singh', '1995-07-23', 'arjun.singh@mail.com', 'MALE',   CURRENT_TIMESTAMP, 'B_NEGATIVE',  NULL),
(8,  'Sneha Reddy', '1992-01-30', 'sneha.reddy@mail.com', 'FEMALE', CURRENT_TIMESTAMP, 'AB_POSITIVE', NULL),
(9,  'Kabir Khan',  '1985-09-15', 'kabir.khan@mail.com',  'MALE',   CURRENT_TIMESTAMP, 'O_NEGATIVE',  NULL),
(10, 'Ananya Das',  '1998-03-08', 'ananya.das@mail.com',  'FEMALE', CURRENT_TIMESTAMP, 'A_NEGATIVE',  NULL);

-- ---------------------------------------------------------------------
-- 5. APPOINTMENTS
-- ---------------------------------------------------------------------
-- status is a plain String field in your entity — using common values
-- like SCHEDULED / COMPLETED / CANCELLED as an illustrative convention.

INSERT INTO appointment (id, appointment_time, reason, status, patient_id, doctor_id) VALUES
(1, '2026-08-01 10:00:00', 'Chest pain and shortness of breath', 'SCHEDULED', 5, 2),
(2, '2026-08-01 11:00:00', 'Routine skin check',                 'SCHEDULED', 6, 3),
(3, '2026-08-02 09:30:00', 'Knee pain after running',            'SCHEDULED', 7, 4),
(4, '2026-07-20 14:00:00', 'Follow-up on blood pressure',        'COMPLETED', 8, 2),
(5, '2026-07-18 16:00:00', 'Acne treatment consultation',        'COMPLETED', 9, 3),
(6, '2026-07-15 10:30:00', 'Fracture follow-up',                 'CANCELLED', 10, 4),
(7, '2026-08-05 12:00:00', 'General cardiac checkup',            'SCHEDULED', 6, 2),
(8, '2026-08-06 15:00:00', 'Persistent rash',                    'SCHEDULED', 7, 3);

-- ---------------------------------------------------------------------
-- 6. Reset auto-increment / identity sequences (adjust per your DB)
-- ---------------------------------------------------------------------
-- PostgreSQL example (uncomment and run if using Postgres):
-- SELECT setval(pg_get_serial_sequence('app_user', 'id'), (SELECT MAX(id) FROM app_user));
-- SELECT setval(pg_get_serial_sequence('appointment', 'id'), (SELECT MAX(id) FROM appointment));
--
-- MySQL example (uncomment and run if using MySQL):
-- ALTER TABLE app_user AUTO_INCREMENT = 11;
-- ALTER TABLE appointment AUTO_INCREMENT = 9;