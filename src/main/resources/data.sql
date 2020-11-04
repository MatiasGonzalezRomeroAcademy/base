INSERT INTO PROJECTS (name, description) VALUES
  ('Academy', 'Learning how to implement a web app using spring boot'),
  ('Project 2', 'Description 2'),
  ('Project 3', 'Description 3'),
  ('Project 4', 'Description 4');
 
INSERT INTO PROJECT_DETAILS (project_id, created_by) VALUES
  (1, 'Admin'),
  (2, 'Admin 2'),
  (3, 'Admin 3'),
  (4, 'Admin 4');
  
INSERT INTO TASKS (project_id, description) VALUES
  (1, 'Description 1'),
  (1, 'Description 2'),
  (1, 'Description 3'),
  (1, 'Description 4');
  
INSERT INTO TAGS (name) VALUES
  ('Tag 1'),
  ('Tag 2'),
  ('Tag 3'),
  ('Tag 4');
  
INSERT INTO PROJECT_TAGS (project_id, tag_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4);
  