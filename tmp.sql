show databases;

use placement_db;

select * from assessment_students;
select * from jobs;
show tables;
select * from applications; -- where status='TEST_SCHEDULED';
ALTER TABLE applications CHANGE COLUMN rank application_rank INT;

select * from students;
select * from applications;
-- drop table applications;

select * from jobs;

update jobs set eligible_departments = 'Computer Science' where job_id=3;

select * from users;

select * from resumes;
select * from applications ;
SELECT *
FROM applications;
-- WHERE status = 'SHORTLISTED';

ALTER TABLE applications
MODIFY COLUMN status ENUM(
    'APPLIED',
    'UNDER_REVIEW',
    'SHORTLISTED_FOR_TEST',
    'TEST_SCHEDULED',
    'SHORTLISTED',
    'INTERVIEW_SCHEDULED',
    'SELECTED',
    'REJECTED',
    'WITHDRAWN'
) NOT NULL;

SET SQL_SAFE_UPDATES = 0;
DESCRIBE applications;

UPDATE applications
SET status = 'SHORTLISTED_FOR_TEST'
WHERE status = 'TEST_SCHEDULED';
update applications set status='APPLIED';

SET SQL_SAFE_UPDATES = 1;