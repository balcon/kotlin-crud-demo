INSERT INTO author (id, name, country)
VALUES (1, 'F. Dostoevsky', 'Russia'),
       (2, 'J. Steinbeck', 'United States'),
       (3, 'K. Marx', 'Germany');

INSERT INTO book (id, title, writing_year, author_id)
VALUES (10, 'The Karamazov Brothers', 1879, 1),
       (11, 'Crime and Punishment ', 1866, 1),
       (12, 'The Grapes of Wrath', 1939, 2),
       (13, 'Of Mice and Men', 1937, 2),
       (14, 'Das Kapital', 1867, 3);
