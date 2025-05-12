CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO users (id, full_name, email, password) VALUES (uuid_generate_v4(), 'Emily Johnson', 'admin@test.com', '{bcrypt}$2a$10$MNcPxMvP.E8.JcrNskHeSOOw0q8SOl/ppplPxZIA73qyQS3A0jHCy');

INSERT INTO document (id, title, parent_id) VALUES ('b3ed8df1-e269-4ffc-a1fa-7c8f7f90863d', 'Note 1', null),
