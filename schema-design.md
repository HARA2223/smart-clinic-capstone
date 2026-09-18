# Smart Clinic Management System — Database Schema Design

## MySQL (relational) — patients, doctors, appointments

### Table: `doctor`
| Column      | Type          | Constraints                     |
|-------------|---------------|----------------------------------|
| id          | BIGINT        | PRIMARY KEY, AUTO_INCREMENT      |
| name        | VARCHAR(100)  | NOT NULL                         |
| specialty   | VARCHAR(100)  | NOT NULL                         |
| email       | VARCHAR(150)  | NOT NULL, UNIQUE                 |
| password    | VARCHAR(255)  | NOT NULL (stored hashed)         |
| phone       | VARCHAR(20)   | NOT NULL                         |

### Table: `doctor_available_times`
| Column         | Type         | Constraints                                   |
|----------------|--------------|------------------------------------------------|
| doctor_id      | BIGINT       | FK -> doctor(id) ON DELETE CASCADE             |
| available_time | VARCHAR(20)  | e.g. "09:00-10:00"                              |

### Table: `patient`
| Column   | Type          | Constraints                |
|----------|---------------|------------------------------|
| id       | BIGINT        | PRIMARY KEY, AUTO_INCREMENT  |
| name     | VARCHAR(100)  | NOT NULL                     |
| email    | VARCHAR(150)  | NOT NULL, UNIQUE             |
| password | VARCHAR(255)  | NOT NULL (stored hashed)     |
| phone    | VARCHAR(20)   | NOT NULL                     |
| address  | VARCHAR(255)  |                               |

### Table: `appointment`
| Column           | Type          | Constraints                              |
|------------------|---------------|--------------------------------------------|
| id               | BIGINT        | PRIMARY KEY, AUTO_INCREMENT                |
| doctor_id        | BIGINT        | FK -> doctor(id), NOT NULL                 |
| patient_id       | BIGINT        | FK -> patient(id), NOT NULL                |
| appointment_time | DATETIME      | NOT NULL                                   |
| status           | VARCHAR(20)   | NOT NULL (SCHEDULED / COMPLETED / CANCELLED)|

### Table: `admin`
| Column   | Type          | Constraints                |
|----------|---------------|------------------------------|
| id       | BIGINT        | PRIMARY KEY, AUTO_INCREMENT  |
| username | VARCHAR(100)  | NOT NULL, UNIQUE             |
| password | VARCHAR(255)  | NOT NULL (stored hashed)     |

**Relationships**
- `appointment.doctor_id` → `doctor.id` (many appointments per doctor)
- `appointment.patient_id` → `patient.id` (many appointments per patient)
- `doctor_available_times.doctor_id` → `doctor.id` (one doctor, many time slots)

## MongoDB (NoSQL) — prescriptions

Prescriptions are stored as flexible documents because the list of
medications and notes per prescription varies in shape and doesn't need
relational joins.

```json
{
  "_id": "ObjectId",
  "appointmentId": 101,
  "patientName": "Jane Doe",
  "medications": ["Amoxicillin 500mg", "Ibuprofen 200mg"],
  "doctorNotes": "Take after meals for 7 days."
}
```
