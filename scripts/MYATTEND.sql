CREATE USER MYATTEND_DEV IDENTIFIED BY MYATTEND;
GRANT CONNECT TO MYATTEND_DEV;
GRANT CREATE SESSION TO MYATTEND_DEV;
GRANT CREATE TABLE TO MYATTEND_DEV;
GRANT UNLIMITED TABLESPACE TO MYATTEND_DEV;
GRANT CREATE SEQUENCE TO MYATTEND_DEV;
GRANT CREATE PROCEDURE TO MYATTEND_DEV;
GRANT CREATE TRIGGER TO MYATTEND_DEV;
COMMIT;


-- Drop existing tables if they exist
DECLARE
    v_sql VARCHAR2(1000);
BEGIN
    FOR table_name IN (
        SELECT table_name
        FROM user_tables
        WHERE table_name IN (
            'MA_ATTENDANCES', 'MA_COURSES_STUDENTS', 'MA_CLASSES', 
            'MA_COURSES', 'MA_STUDENTS', 'MA_LECTURERS', 
            'MA_TOKENS', 'MA_USERS', 'MA_ROLES', 'MA_STATS', 'MA_SESSIONS_SEMESTER'
        )
    ) LOOP
        BEGIN
            v_sql := 'DROP TABLE ' || table_name.table_name || ' CASCADE CONSTRAINTS';
            EXECUTE IMMEDIATE v_sql;
            DBMS_OUTPUT.PUT_LINE('Dropped table: ' || table_name.table_name);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error dropping table: ' || table_name.table_name || ' - ' || SQLERRM);
        END;
    END LOOP;
END;


-- Create roles table
CREATE TABLE ma_roles(
    id NUMBER(3) NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_roles_pk PRIMARY KEY(id)
);

-- Create stats table

CREATE TABLE ma_sessions_semester(
    id VARCHAR(50) NOT NULL,
    session_name VARCHAR(200) NOT NULL,
    session_used VARCHAR(1) DEFAULT 'N',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_sessions_semester_pk PRIMARY KEY(id)
);

-- Create users table
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
    CONSTRAINT ma_users_pk PRIMARY KEY(id,email),
    CONSTRAINT ma_users_fk1 FOREIGN KEY(role_id) REFERENCES ma_roles(id) ON DELETE SET NULL
);

-- Create tokens table
CREATE TABLE ma_tokens(
    id VARCHAR(255) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid NUMBER(1) DEFAULT 1,
    user_id NUMBER(11),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_tokens_pk PRIMARY KEY(id),
    CONSTRAINT ma_tokens_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE SET NULL
);

-- Create lecturers table
CREATE TABLE ma_lecturers(
    user_id NUMBER(11) NOT NULL UNIQUE,
    lect_id NUMBER(11) NOT NULL UNIQUE,
    supervisor_id NUMBER(11),
    start_date DATE NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    salary NUMBER(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_lecturers_pk PRIMARY KEY(user_id, lect_id),
    CONSTRAINT ma_lecturers_fk1 FOREIGN KEY(user_id) REFERENCES ma_users(id) ON DELETE CASCADE
);
ALTER TABLE ma_lecturers ADD CONSTRAINT ma_lecturers_fk2 FOREIGN KEY(supervisor_id) REFERENCES ma_lecturers(user_id) ON DELETE SET NULL;

-- Create students table
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

-- Create courses table
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

-- Create classes table
CREATE TABLE ma_classes(
    id VARCHAR(50) NOT NULL UNIQUE,
    course_id VARCHAR(50) NOT NULL,
    class_desc VARCHAR(255) NOT NULL,
    class_date DATE NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    venue VARCHAR(255),
    session_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT ma_classes_pk PRIMARY KEY(id, session_id),
    CONSTRAINT ma_classes_fk1 FOREIGN KEY(course_id) REFERENCES ma_courses(id) ON DELETE CASCADE,
    CONSTRAINT MA_CLASSES_FK_SESS FOREIGN KEY (session_id) REFERENCES ma_sessions_semester(id) ON DELETE CASCADE
);

-- Create stats table
CREATE TABLE ma_stats(
    id VARCHAR(11) NOT NULL,
    stats_desc VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_stats_pk PRIMARY KEY(id)
);

-- Create attendances table
CREATE TABLE ma_attendances(
    id VARCHAR(50) NOT NULL,
    class_id VARCHAR(50) NOT NULL,
    stud_id NUMBER(11) NOT NULL,
    status VARCHAR(11) NOT NULL,
    attend_date DATE,
    attend_time TIMESTAMP,
    session_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_attendances_pk PRIMARY KEY(id, session_id),
    CONSTRAINT ma_attendances_fk1 FOREIGN KEY(class_id) REFERENCES ma_classes(id) ON DELETE CASCADE,
    CONSTRAINT ma_attendances_fk2 FOREIGN KEY(stud_id) REFERENCES ma_students(user_id) ON DELETE CASCADE,
    CONSTRAINT ma_attendances_fk3 FOREIGN KEY(status) REFERENCES ma_stats(id) ON DELETE CASCADE,
    CONSTRAINT MA_ATTENDANCES_FK_SESS FOREIGN KEY (session_id) REFERENCES ma_sessions_semester(id) ON DELETE CASCADE
);

-- Create courses_students table
CREATE TABLE ma_courses_students(
    stud_id NUMBER(11) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    session_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ma_courses_students_pk PRIMARY KEY(stud_id, course_id, session_id),
    CONSTRAINT ma_courses_students_fk1 FOREIGN KEY(stud_id) REFERENCES ma_students(user_id) ON DELETE CASCADE,
    CONSTRAINT ma_courses_students_fk2 FOREIGN KEY(course_id) REFERENCES ma_courses(id) ON DELETE CASCADE,
    CONSTRAINT ma_courses_FK_SESS FOREIGN KEY (session_id) REFERENCES ma_sessions_semester(id) ON DELETE CASCADE
);


CREATE SEQUENCE uidseq INCREMENT BY 1 START WITH 100000 MINVALUE 100000 MAXVALUE 99999999999999 CYCLE CACHE 10


-- Create function
CREATE OR REPLACE FUNCTION getCurrentTimestamp
RETURN varchar
IS tms varchar(50);
BEGIN
    SELECT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDhh24missff3') INTO tms FROM DUAL;
    RETURN (tms);
END;


DROP TRIGGER INSERT_ATTENDANCE;
COMMIT;

-- Insert roles and stats
INSERT INTO ma_roles(id, role_name) VALUES(1, 'ADMIN');
INSERT INTO ma_roles(id, role_name) VALUES(2, 'LECTURER');
INSERT INTO ma_roles(id, role_name) VALUES(3, 'STUDENT');

INSERT INTO ma_stats(id, stats_desc) VALUES('AB', 'ABSENT');
INSERT INTO ma_stats(id, stats_desc) VALUES('C', 'ATTENDED');

SELECT * FROM MA_USERS mu 


COMMIT;

insert into ma_users (id,email,username,full_name,password,gender,birth_date,profile_pic,role_id) 
values(1,'admin@gmail.com','1000000','ADMINISTRATOR','$argon2id$v=19$m=2000,t=10,p=1$vhDYiK4T2RfO538qZWQQ9w$g15Pt/PMGsJ1QXYKl3GZS7p6EqJvVrc0fR3R5dcoxD4','M',to_date('2029-01-01', 'YYYY-MM-DD'),'/images/profile/default.png',1)

UPDATE ma_users SET PROFILE_PIC = '/images/profile/default.png' WHERE id = 1

insert into ma_classes (id,course_id,class_desc,class_date,start_time,end_time,venue,created_at,updated_at) values('05b90524-bf7b-4649-8aad-cc300693d5f3','98c0f162-3be3-4dbe-afcb-8f113249ffc0','Seminar 1','2024-12-10', '2024-12-10 21:47:00.000','2024-12-10 21:00:00.000','Google Meet','2024-12-10 21:48:01.998','2024-12-10 21:48:01.998')


COMMIT;

