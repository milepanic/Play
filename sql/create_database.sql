DROP SCHEMA IF EXISTS play;
CREATE SCHEMA play DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE play;

CREATE TABLE users (
	id INT AUTO_INCREMENT,
	username VARCHAR(10) NOT NULL,
	password VARCHAR(120) NOT NULL,
	firstname VARCHAR(120),
	lastname VARCHAR(120),
	email VARCHAR(120) NOT NULL,
	description VARCHAR(1000),
	registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	banned BOOLEAN DEFAULT false,
	role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
	deleted BOOLEAN DEFAULT false,
	PRIMARY KEY(id)
);

INSERT INTO users (username, password, email, role) VALUES 
		('admin', 'admin', 'admin@admin.com', 'ADMIN'),
		('user', 'admin', 'user@user.com', 'USER'),
		('user2', 'admin', 'user2@user.com', 'USER'),
		('user3', 'admin', 'user3@user.com', 'USER'),
		('user4', 'admin', 'user4@user.com', 'USER');
	
CREATE TABLE videos (
	id INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	url VARCHAR(100) NOT NULL,
	thumbnail VARCHAR(100) NOT NULL,
	description VARCHAR(1000),
	visibility ENUM('PUBLIC', 'UNLISTED', 'PRIVATE') NOT NULL DEFAULT 'PUBLIC',
	commentable BOOLEAN DEFAULT true,
	voteable BOOLEAN DEFAULT true,
	blocked BOOLEAN DEFAULT false,
	views INT NOT NULL DEFAULT 0,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	user_id INT NOT NULL,
	deleted BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO videos (name, url, thumbnail, description, visibility, user_id) VALUES 
	('Marshall Jefferson - Floating', 'https://www.youtube.com/embed/EGuiXTUQeH4', 'https://img.youtube.com/vi/EGuiXTUQeH4/0.jpg', '', 'PUBLIC', 1),
	('Dave Brubeck - Take Five', 'https://www.youtube.com/embed/vmDDOFXSgAs', 'https://img.youtube.com/vi/vmDDOFXSgAs/0.jpg', '', 'UNLISTED', 1),
	('Ryo Fukui - Scenery 1976 (FULL ALBUM)', 'https://www.youtube.com/embed/Hrr3dp7zRQY', 'https://img.youtube.com/vi/Hrr3dp7zRQY/0.jpg', '', 'PRIVATE', 1),
	('The Doors - L. A. Woman', 'https://www.youtube.com/embed/JskztPPSJwY', 'https://img.youtube.com/vi/JskztPPSJwY/0.jpg', '', 'PUBLIC', 1),
	('Wu-Tang Clan - Forever FULL ALBUM', 'https://www.youtube.com/embed/5CzsXvAZ6R4', 'https://img.youtube.com/vi/5CzsXvAZ6R4/0.jpg', '', 'PUBLIC', 1),
	('Bob Marley - I Shot The Sheriff', 'https://www.youtube.com/embed/2XiYUYcpsT4', 'https://img.youtube.com/vi/2XiYUYcpsT4/0.jpg', '', 'PUBLIC', 1),
	('Motorhead - Ace Of Spades', 'https://www.youtube.com/embed/vcf7DnHi54g', 'https://img.youtube.com/vi/vcf7DnHi54g/0.jpg', '*NOTE* its for entertainment purposes only', 'UNLISTED', 2),
	('Marko Nastic - Smekerica Kulijana', 'https://www.youtube.com/embed/gM-QEcuDY8I', 'https://img.youtube.com/vi/gM-QEcuDY8I/0.jpg', '', 'PUBLIC', 2),
	('WRC - ADAC Rallye Deutschland 2016', 'https://www.youtube.com/embed/ZQ7XIGVCwsA', 'https://img.youtube.com/vi/ZQ7XIGVCwsA/0.jpg', 'FIA World Rally Championship - ADAC Rallye Deutschland 2016', 'PUBLIC', 2),
	('Eric B. & Rakim - Paid In Full', 'https://www.youtube.com/embed/E7t8eoA_1jQ', 'https://img.youtube.com/vi/E7t8eoA_1jQ/0.jpg', '', 'PUBLIC', 3),
	('Carl Cox - Fantasee', 'https://www.youtube.com/embed/-Ndw3lp0D7E', 'https://img.youtube.com/vi/-Ndw3lp0D7E/0.jpg', 'Carl Cox - Fantasee 
Written and produced by Carl Cox, Davide Carbone and Josh Abrahams ', 'PUBLIC', 4),
	('Kavinsky - Pacific Coast Highway', 'https://www.youtube.com/embed/-5FKNViujeM', 'https://img.youtube.com/vi/-5FKNViujeM/0.jpg', 'Buy the vinyl - support the author', 'PRIVATE', 4);
	
CREATE TABLE comments (
	id INT AUTO_INCREMENT,
	text TEXT NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	user_id INT NOT NULL,
	video_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (video_id) REFERENCES videos(id)
);

INSERT INTO comments (text, user_id, video_id) VALUES
	 ('Komentar PRVI', 1, 1),
	 ('D R U G I ', 2, 1),
	 ('KOOOOMEEENTARRR', 1, 1),
	 ('ssss wwwww eeeee fffff', 3, 1),
	 ('Ko je postavio ovaj komentar', 4, 1),
	 ('Dobar video! ', 5, 1),
	 ('AHOY', 1, 2),
	 ('POZDRAV SVIMA', 3, 2),
	 ('Have a nice day', 1, 2),
	 ('Go Go Go! Roger That!', 2, 2),
	 ('Very nice', 3, 3),
	 ('Good job mate', 3, 2),
	 ('Komentarrr', 3, 1),
	 ('AHOY', 3, 2);
	
CREATE TABLE follow (
	follower_id INT,
	user_id INT,
	FOREIGN KEY (follower_id) REFERENCES users(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO follow VALUES 
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 1),
	(3, 1),
	(3, 2),
	(4, 2),
	(3, 4),
	(1, 5),
	(2, 5);
	
CREATE TABLE votes (
	id INT AUTO_INCREMENT,
	user_id INT,
	voteable_id INT,
	voteable_type VARCHAR(30) NOT NULL,
	vote BOOLEAN NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO votes (user_id, voteable_id, voteable_type, vote) VALUES
	(1, 1, 'comment', 0),
	(1, 2, 'comment', 0),
	(1, 3, 'comment', 0),
	(1, 4, 'comment', 1),
	(1, 5, 'comment', 0),
	(1, 6, 'comment', 1),
	(1, 7, 'comment', 0),
	(1, 8, 'comment', 1),
	(1, 9, 'comment', 1),
	(1, 10, 'comment', 0),
	(1, 11, 'comment', 1),
	(1, 12, 'comment', 0),
	(1, 13, 'comment', 1),
	(1, 14, 'comment', 0),
	(2, 1, 'comment', 0),
	(2, 2, 'comment', 1),
	(2, 3, 'comment', 1),
	(2, 4, 'comment', 0),
	(2, 5, 'comment', 1),
	(2, 6, 'comment', 0),
	(2, 7, 'comment', 1),
	(2, 8, 'comment', 0),
	(2, 9, 'comment', 0),
	(2, 10, 'comment', 1),
	(2, 11, 'comment', 0),
	(2, 12, 'comment', 1),
	(2, 13, 'comment', 0),
	(2, 14, 'comment', 1),
	(3, 1, 'comment', 1),
	(3, 2, 'comment', 1),
	(3, 3, 'comment', 1),
	(3, 4, 'comment', 1),
	(3, 5, 'comment', 1),
	(3, 6, 'comment', 1),
	(3, 7, 'comment', 0),
	(3, 8, 'comment', 0),
	(3, 9, 'comment', 0),
	(3, 10, 'comment', 0),
	(3, 11, 'comment', 0),
	(3, 12, 'comment', 0),
	(3, 13, 'comment', 1),
	(3, 14, 'comment', 0),
	(4, 1, 'comment', 0),
	(4, 2, 'comment', 1),
	(4, 3, 'comment', 0),
	(4, 4, 'comment', 1),
	(4, 5, 'comment', 0),
	(4, 6, 'comment', 1),
	(4, 7, 'comment', 1),
	(4, 8, 'comment', 0),
	(4, 9, 'comment', 0),
	(4, 10, 'comment', 1),
	(4, 11, 'comment', 0),
	(4, 12, 'comment', 1),
	(4, 13, 'comment', 1),
	(4, 14, 'comment', 0);
