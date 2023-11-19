CREATE TABLE IF NOT EXISTS author (
  id VARCHAR(50) PRIMARY KEY,
  authorName VARCHAR(255) NOT NULL,
  sex VARCHAR(1) CHECK (sex IN ('M', 'F')) NOT NULL
);