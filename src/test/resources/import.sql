CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO task_status (status_id, name, color, status_order) VALUES
(uuid_generate_v1(), 'Backlog', '#FF2147', -1),
(uuid_generate_v1(), 'To Do', '#E5E5E5', 0),
(uuid_generate_v1(), 'In Progress', '#5E94FF', 1),
(uuid_generate_v1(), 'Completed', '#339900', 2);

INSERT INTO task_priority (priority_id, title, precedence, color) VALUES
(uuid_generate_v1(), 'Urgent', 0, '#EC3A48'),
(uuid_generate_v1(), 'High', 1, '#FAA116'),
(uuid_generate_v1(), 'Medium', 2, '#31B791'),
(uuid_generate_v1(), 'Low', 3, '#E5E5E5');

-- INSERT INTO project (project_id, project_name, slug, description, total_task_count) VALUES
-- (uuid_generate_v1(), 'Windows Mobile Phone', 'WMP', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Curabitur pretium congue egestas eu praesent amet. Arcu finibus condimentum; magnis hendrerit malesuada interdum habitant.', 2),
-- (uuid_generate_v1(), 'Android Mobile Phone', 'AMP', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Curabitur pretium congue egestas eu praesent amet. Arcu finibus condimentum; magnis hendrerit malesuada interdum habitant.', 0),
-- (uuid_generate_v1(), 'Open Dataset Search', 'ODS', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Curabitur pretium congue egestas eu praesent amet. Arcu finibus condimentum; magnis hendrerit malesuada interdum habitant.', 0),
-- (uuid_generate_v1(), 'Open Source Data Explorer', 'OSDE', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Curabitur pretium congue egestas eu praesent amet. Arcu finibus condimentum; magnis hendrerit malesuada interdum habitant.', 0),
-- (uuid_generate_v1(), 'TensorFlow', 'TSF', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Curabitur pretium congue egestas eu praesent amet. Arcu finibus condimentum; magnis hendrerit malesuada interdum habitant.', 0);
--
-- INSERT INTO task (id, task_id, title, description, estimates, project_id, status, priority, created_at, updated_at) VALUES
-- (uuid_generate_v1(), 1, 'Lorem ipsum odor amet, consectetuer adipiscing elit.', 'lala', floor(random() * 10 + 1)::int, (SELECT project_id FROM project WHERE slug = 'WMP'), (SELECT status_id FROM task_status WHERE name = 'Backlog'), (SELECT priority_id from task_priority where title = 'Urgent'), CURRENT_DATE, CURRENT_DATE),
-- (uuid_generate_v1(), 2, 'Lorem ipsum odor amet, consectetuer adipiscing elit.', 'lolo', floor(random() * 10 + 1)::int, (SELECT project_id FROM project WHERE slug = 'WMP'), (SELECT status_id FROM task_status WHERE name = 'In Progress'), (SELECT priority_id FROM task_priority WHERE title = 'High'), CURRENT_DATE, CURRENT_DATE),
-- (uuid_generate_v1(), 1, 'Lorem ipsum odor amet, consectetuer adipiscing elit.', 'Task 1', floor(random() * 10 + 1)::int, (SELECT project_id FROM project WHERE slug = 'AMP'), (SELECT status_id FROM task_status WHERE name = 'Completed'), (SELECT priority_id FROM task_priority WHERE title = 'Low'), CURRENT_DATE, CURRENT_DATE);
--
-- INSERT INTO task_tag (tag_id, title, project_id, color) VALUES
-- (uuid_generate_v1(), 'Tag1', (SELECT project_id FROM project WHERE slug = 'WMP'), '#FF0000'),
-- (uuid_generate_v1(), 'Tag2', (SELECT project_id FROM project WHERE slug = 'WMP'), '#FFA500'),
-- (uuid_generate_v1(), 'Tag3', (SELECT project_id FROM project WHERE slug = 'WMP'), '#FFFF00'),
-- (uuid_generate_v1(), 'Tag4', (SELECT project_id FROM project WHERE slug = 'WMP'), '#008000'),
-- (uuid_generate_v1(), 'Tag5', (SELECT project_id FROM project WHERE slug = 'WMP'), '#0000FF'),
-- (uuid_generate_v1(), 'Tag6', (SELECT project_id FROM project WHERE slug = 'WMP'), '#4B0082'),
-- (uuid_generate_v1(), 'Tag7', (SELECT project_id FROM project WHERE slug = 'WMP'), '#EE82EE');
