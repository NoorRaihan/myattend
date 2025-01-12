CREATE USER MYATTEND IDENTIFIED BY MYATTEND;
GRANT CONNECT TO MYATTEND;
GRANT CREATE SESSION TO MYATTEND;
GRANT CREATE TABLE TO MYATTEND;
GRANT UNLIMITED TABLESPACE TO MYATTEND;
GRANT CREATE SEQUENCE TO MYATTEND;
GRANT CREATE PROCEDURE TO MYATTEND;
GRANT CREATE TRIGGER TO MYATTEND;
COMMIT;

DROP TABLE ma_roles;
CREATE TABLE ma_roles(
    id NUMBER(3) NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_roles_pk PRIMARY KEY(id) ENABLE
);

DROP TABLE ma_users;
CREATE TABLE ma_users (
    id NUMBER(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender CHAR(1) NOT NULL,
    birth_date DATE NOT NULL,
    profile_pic VARCHAR(255),
    role_id NUMBER(3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_users_pk PRIMARY KEY(id,email) ENABLE,
    CONSTRAINT ma_users_fk1 FOREIGN KEY(role_id) REFERENCES ma_roles(id) ON DELETE SET NULL
);

DROP TABLE ma_tokens;
CREATE TABLE ma_tokens(
    id VARCHAR(255) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid NUMBER(1) DEFAULT 1,
    user_id NUMBER(11),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_tokens_pk PRIMARY KEY(id) ENABLE,
    CONSTRAINT ma_tokens_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE SET NULL
);

DROP TABLE ma_lecturers;
CREATE TABLE ma_lecturers (
    user_id NUMBER(11) NOT NULL UNIQUE,
    lect_id NUMBER(11) NOT NULL UNIQUE,ma_users
    supervisor_id NUMBER(11),
    start_date date NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    salary NUMBER(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_lecturers_pk PRIMARY KEY(user_id, lect_id),
    CONSTRAINT ma_lecturers_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE CASCADE
);
ALTER TABLE ma_lecturers ADD CONSTRAINT ma_lecturers_fk2 FOREIGN KEY(supervisor_id) REFERENCES ma_lecturers(user_id) ON DELETE SET NULL;


DROP TABLE ma_students;
CREATE TABLE ma_students(
    user_id NUMBER(11) NOT NULL UNIQUE,
    stud_id NUMBER(11) NOT NULL UNIQUE,
    program VARCHAR(100) NOT NULL,
    intake VARCHAR(100) NOT NULL,
    semester NUMBER(2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_students_pk PRIMARY KEY(user_id, stud_id),
    CONSTRAINT ma_students_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE CASCADE
);

DROP TABLE ma_courses;
CREATE TABLE ma_courses(
    id VARCHAR(50) NOT NULL UNIQUE,
    course_code VARCHAR(10) NOT NULL,
    user_id NUMBER(11) NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    credit_hour NUMBER(10,2) NOT NULL,
    color VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT ma_courses_pk PRIMARY KEY(id, course_code),
    CONSTRAINT ma_courses_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE SET NULL
);

DROP TABLE ma_classes;
CREATE TABLE ma_classes(
    id VARCHAR(50) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    class_desc VARCHAR(255) NOT NULL,
    class_date DATE NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    venue VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT ma_classes_pk PRIMARY KEY(id),
    CONSTRAINT ma_classes_fk1 FOREIGN KEY(course_id) REFERENCES ma_courses(id) ON DELETE CASCADE
);

DROP TABLE ma_stats;
CREATE TABLE ma_stats(
    id VARCHAR(11) NOT NULL,
    stats_desc VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_stats_pk PRIMARY KEY(id)
);

DROP TABLE ma_attendances;
CREATE TABLE ma_attendances(
    id VARCHAR(50) NOT NULL,
    class_id VARCHAR(50) NOT NULL,
    stud_id NUMBER(11) NOT NULL,
    status VARCHAR(11) NOT NULL,
    attend_date DATE,
    attend_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_attendances_pk PRIMARY KEY(id),
    CONSTRAINT ma_attendances_fk1 FOREIGN KEY(class_id) REFERENCES ma_classes(id) ON DELETE CASCADE,
    CONSTRAINT ma_attendances_fk2 FOREIGN KEY(stud_id) REFERENCES ma_students(user_id) ON DELETE CASCADE,
    CONSTRAINT ma_attendances_fk3 FOREIGN KEY(status) REFERENCES ma_stats(id) ON DELETE CASCADE
);

DROP TABLE ma_courses_students;
CREATE TABLE ma_courses_students(
    stud_id NUMBER(11) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_courses_students_pk PRIMARY KEY(stud_id, course_id),
    CONSTRAINT ma_courses_students_fk1 FOREIGN KEY(stud_id) REFERENCES ma_students(user_id) ON DELETE CASCADE,
    CONSTRAINT ma_courses_students_fk2 FOREIGN KEY(course_id) REFERENCES ma_courses(id) ON DELETE CASCADE
);

CREATE SEQUENCE uidseq
    INCREMENT BY 1
    START WITH 100000
    MINVALUE 100000
    CYCLE
    CACHE 10;

CREATE OR REPLACE FUNCTION getCurrentTimestamp
RETURN varchar
IS tms varchar(50);
BEGIN
    SELECT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDhh24missff3') INTO tms FROM DUAL;
    RETURN (tms);
END;

CREATE OR REPLACE TRIGGER insert_attendance
AFTER INSERT ON ma_classes
FOR EACH ROW
BEGIN
    INSERT INTO ma_attendances (id, stud_id, class_id, status)
    SELECT GETCURRENTTIMESTAMP(), stud_id, :new.id, 'AB' FROM ma_courses_students WHERE course_id = :new.id;
END;

COMMIT;


INSERT INTO ma_roles(id, role_name) VALUES(1, 'ADMIN');
INSERT INTO ma_roles(id, role_name) VALUES(2, 'LECTURER');
INSERT INTO ma_roles(id, role_name) VALUES(3, 'STUDENT');

INSERT INTO ma_stats(id, stats_desc) VALUES('AB', 'ABSENT');
INSERT INTO ma_stats(id, stats_desc) VALUES('C', 'ATTENDED');
COMMIT;

insert into ma_users (id,email,username,full_name,password,gender,birth_date,profile_pic,role_id) 
values(1,'admin@gmail.com','1000000','ADMINISTRATOR','$argon2id$v=19$m=2000,t=10,p=1$vhDYiK4T2RfO538qZWQQ9w$g15Pt/PMGsJ1QXYKl3GZS7p6EqJvVrc0fR3R5dcoxD4','M',to_date('2029-01-01', 'YYYY-MM-DD'),'images/profile/default.png',1)

COMMIT;