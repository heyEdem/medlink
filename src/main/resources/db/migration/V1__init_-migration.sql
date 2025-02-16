CREATE TABLE appointment
(
    id                  UUID NOT NULL,
    appointment_doctor  UUID,
    appointment_patient UUID,
    date                DATE,
    start_time          TIMESTAMP WITHOUT TIME ZONE,
    end_time            TIMESTAMP WITHOUT TIME ZONE,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    status              VARCHAR(255),
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

CREATE TABLE availability
(
    id             UUID NOT NULL,
    doctor_user_id UUID,
    start_time     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_time       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_availability PRIMARY KEY (id)
);

CREATE TABLE otp
(
    id         UUID         NOT NULL,
    email      VARCHAR(255) NOT NULL,
    code       VARCHAR(255) NOT NULL,
    type       VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    expiry     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_otp PRIMARY KEY (id)
);

CREATE TABLE review
(
    id              UUID    NOT NULL,
    rating          INTEGER NOT NULL,
    appointment_id  UUID,
    doctor_user_id  UUID,
    patient_user_id UUID,
    comment         TEXT,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE users
(
    user_id          UUID                  NOT NULL,
    email            VARCHAR(255)          NOT NULL,
    name             VARCHAR(255)          NOT NULL,
    role             VARCHAR(255),
    password         VARCHAR(255)          NOT NULL,
    contact          VARCHAR(255),
    profile_picture  VARCHAR(255),
    clinic           VARCHAR(255),
    specialty        VARCHAR(255),
    qualification    VARCHAR(255),
    bio              TEXT,
    dob              TIMESTAMP WITHOUT TIME ZONE,
    emergency_number VARCHAR(255),
    medical_history  TEXT,
    is_verified      BOOLEAN DEFAULT FALSE NOT NULL,
    is_deleted       BOOLEAN DEFAULT FALSE NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    deleted_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE user_appointments
(
    user_user_id    UUID NOT NULL,
    appointments_id UUID NOT NULL,
    CONSTRAINT pk_user_appointments PRIMARY KEY (user_user_id, appointments_id)
);

ALTER TABLE otp
    ADD CONSTRAINT uc_otp_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE appointment
    ADD CONSTRAINT fk_appointment_on_doctor FOREIGN KEY (appointment_doctor) REFERENCES users (user_id);

ALTER TABLE appointment
    ADD CONSTRAINT fk_appointment_on_patient FOREIGN KEY (appointment_patient) REFERENCES users (user_id);

ALTER TABLE availability
    ADD CONSTRAINT fk_availability_on_doctor FOREIGN KEY (doctor_user_id) REFERENCES users (user_id);

ALTER TABLE review
    ADD CONSTRAINT fk_review_on_appointment FOREIGN KEY (appointment_id) REFERENCES appointment (id);

ALTER TABLE review
    ADD CONSTRAINT fk_review_on_doctor FOREIGN KEY (doctor_user_id) REFERENCES users (user_id);

ALTER TABLE review
    ADD CONSTRAINT fk_review_on_patient FOREIGN KEY (patient_user_id) REFERENCES users (user_id);

ALTER TABLE user_appointments
    ADD CONSTRAINT fk_user_appointments_on_appointment FOREIGN KEY (appointments_id) REFERENCES appointment (id);

ALTER TABLE user_appointments
    ADD CONSTRAINT fk_user_appointments_on_user FOREIGN KEY (user_user_id) REFERENCES users (user_id);
