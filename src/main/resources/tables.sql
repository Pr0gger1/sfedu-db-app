CREATE TABLE IF NOT EXISTS faculties (
    id SERIAL PRIMARY KEY,
    faculty_name VARCHAR(128) UNIQUE NOT NULL,
    address VARCHAR(256) UNIQUE NOT NULL,
    phone BIGINT, email VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS teachers (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(64) NOT NULL,
    salary REAL DEFAULT 0 NOT NULL,
    specialization VARCHAR(32) NOT NULL,
    faculty_id INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL,
    birthday DATE DEFAULT NOW() NOT NULL,
    phone BIGINT
);

CREATE TABLE IF NOT EXISTS directions (
    id SERIAL PRIMARY KEY,
    faculty_id INTEGER REFERENCES faculties (id) ON DELETE SET NULL,
    direction_name VARCHAR(64) NOT NULL,
    head INTEGER REFERENCES teachers (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS subjects (
    id SERIAL PRIMARY KEY,
    subject_name VARCHAR(64),
    faculty_id INTEGER REFERENCES faculties (id) ON DELETE SET NULL,
    direction_id INTEGER REFERENCES directions (id) ON DELETE CASCADE,
    teacher_id INTEGER REFERENCES teachers (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    faculty_id INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL,
    group_name VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(64) NOT NULL,
    course SMALLINT DEFAULT 1 NOT NULL,
    direction_id INTEGER REFERENCES directions (id) ON DELETE CASCADE NOT NULL,
    faculty_id INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL,
    birthday DATE DEFAULT NOW() NOT NULL,
    scholarship REAL DEFAULT 0,
    phone BIGINT
);

CREATE TABLE IF NOT EXISTS marks (
    id SERIAL PRIMARY KEY,
    student_id INTEGER REFERENCES students (id) ON DELETE CASCADE NOT NULL,
    subject_id INTEGER REFERENCES subjects (id) ON DELETE CASCADE NOT NULL,
    mark SMALLINT NOT NULL,
    year SMALLINT NOT NULL
);
