CREATE TABLE discipline (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255)
);

CREATE TABLE course (
    id UUID PRIMARY KEY DEFAULT gen_random_uuidd(),
    course_number INTEGER NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE student (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    student_number VARCHAR(255) UNIQUE,
    email VARCHAR(255)
);

CREATE TABLE examiner (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    course_id UUID REFERENCES course(id)
);

CREATE TABLE examiner_discipline (
    examiner_id UUID NOT NULL REFERENCES examiner(id),
    discipline_id UUID NOT NULL REFERENCES discipline(id)
);

CREATE TABLE submission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    submitted_date DATE,
    status VARCHAR(255)
                CHECK (status IN ('UPLOADED', 'AI_GRADED', 'REVIEWED', 'REJECTED'))
    title VARCHAR(255),
    file_name VARCHAR(255),
    student_id UUID REFERENCES student(id),
    examiner_id UUID REFERENCES examiner(id)
);

CREATE TABLE rubric_scores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(id),
    criterion VARCHAR(255),
    ai_feedback VARCHAR(255),
    score INTEGER NOT NULL,
    graded_date DATE,
    submission_id UUID REFERENCES submission(id)
);