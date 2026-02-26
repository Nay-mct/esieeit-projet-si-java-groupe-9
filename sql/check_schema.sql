-- TP 4.1 - MySQL schema inspection helpers

USE project_si_db;

-- 1) Tables
SHOW TABLES;

-- 2) Columns and constraints
DESCRIBE users;
DESCRIBE projects;
DESCRIBE tasks;
DESCRIBE comments;

-- 3) Foreign keys
SELECT
  tc.TABLE_NAME,
  kcu.COLUMN_NAME,
  kcu.REFERENCED_TABLE_NAME,
  kcu.REFERENCED_COLUMN_NAME,
  tc.CONSTRAINT_NAME
FROM information_schema.TABLE_CONSTRAINTS tc
JOIN information_schema.KEY_COLUMN_USAGE kcu
  ON tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME
  AND tc.TABLE_SCHEMA = kcu.TABLE_SCHEMA
WHERE tc.CONSTRAINT_TYPE = 'FOREIGN KEY'
  AND tc.TABLE_SCHEMA = 'project_si_db'
ORDER BY tc.TABLE_NAME, tc.CONSTRAINT_NAME;
