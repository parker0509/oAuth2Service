-- User 테이블 생성
CREATE TABLE IF NOT EXISTS examplesdb.user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    picture VARCHAR(255),
    gender VARCHAR(10),
    age VARCHAR(10),
    CONSTRAINT unique_email UNIQUE (email)  -- 이메일 고유 제약 추가
);

-- recruitments 테이블 생성
CREATE TABLE IF NOT EXISTS examplesdb.recruitments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    category VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
