-- ==========================================================
-- Quiz App Database Schema (Final Version)
-- Author: Sasidhar
-- ==========================================================

-- 1️⃣ Create Database
CREATE DATABASE IF NOT EXISTS quiz_app;
USE quiz_app;

-- 2️⃣ Users Table
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,  -- store hashed password
  full_name VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3️⃣ Quizzes Table
CREATE TABLE IF NOT EXISTS quizzes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  duration_minutes INT NOT NULL,  -- timer (e.g., 10 mins)
  total_marks INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4️⃣ Questions Table
CREATE TABLE IF NOT EXISTS questions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_id INT NOT NULL,
  question_text TEXT NOT NULL,
  choice_a VARCHAR(500),
  choice_b VARCHAR(500),
  choice_c VARCHAR(500),
  choice_d VARCHAR(500),
  correct_choice CHAR(1) NOT NULL,   -- 'A', 'B', 'C', 'D'
  marks INT DEFAULT 1,
  FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

-- 5️⃣ Results Table
CREATE TABLE IF NOT EXISTS results (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  quiz_id INT NOT NULL,
  score INT NOT NULL,
  max_score INT NOT NULL,
  started_at TIMESTAMP,
  finished_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

-- 6️⃣ Result Details Table
CREATE TABLE IF NOT EXISTS result_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  result_id INT NOT NULL,
  question_id INT NOT NULL,
  selected_choice CHAR(1),
  is_correct BOOLEAN,
  marks_obtained INT,
  FOREIGN KEY (result_id) REFERENCES results(id) ON DELETE CASCADE,
  FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- ==========================================================
-- ✅ Sample Data for Testing
-- ==========================================================

-- Users (you can register via UI too)
INSERT INTO users (username, password_hash, full_name)
VALUES ('testuser', 'password123', 'Test User')
ON DUPLICATE KEY UPDATE username=username;

-- Quiz Metadata
INSERT INTO quizzes (title, duration_minutes, total_marks)
VALUES ('Java Basics Quiz', 5, 5)
ON DUPLICATE KEY UPDATE title=title;

-- Questions for Java Basics Quiz
INSERT INTO questions (quiz_id, question_text, choice_a, choice_b, choice_c, choice_d, correct_choice, marks)
VALUES 
(1, 'What is JVM?', 'Java Virtual Machine', 'Java Variable Memory', 'Joint Virtual Memory', 'None', 'A', 1),
(1, 'Which keyword is used to inherit a class in Java?', 'this', 'super', 'extends', 'import', 'C', 1),
(1, 'Which method is the entry point for any Java program?', 'start()', 'main()', 'run()', 'execute()', 'B', 1),
(1, 'Which of the following is not a Java primitive type?', 'int', 'float', 'boolean', 'string', 'D', 1),
(1, 'Which package contains the Scanner class?', 'java.io', 'java.util', 'java.net', 'java.text', 'B', 1);
