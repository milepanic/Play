DROP SCHEMA IF EXISTS play;
CREATE SCHEMA play DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE play;

CREATE TABLE users (
	id INT AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(120) NOT NULL,
	firstname VARCHAR(120),
	lastname VARCHAR(120),
	email VARCHAR(120) NOT NULL,
	description VARCHAR(1000),
	registered_at TIMESTAMP,
	banned BOOLEAN DEFAULT false,
	role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
	PRIMARY KEY(id)
);

INSERT INTO users (username, password, email, role) VALUES 
		('admin', 'admin', 'admin@admin.com', 'ADMIN'),
		('user', 'admin', 'user@user.com', 'USER'),
		('user2', 'admin', 'user2@user.com', 'USER'),
		('user3', 'admin', 'user3@user.com', 'USER');
	
CREATE TABLE videos (
	id INT AUTO_INCREMENT,
	name VARCHAR(200) NOT NULL,
	url VARCHAR(100) NOT NULL,
	thumbnail VARCHAR(100) NOT NULL,
	description VARCHAR(1000),
	visibility ENUM('PUBLIC', 'UNLISTED', 'PRIVATE') NOT NULL DEFAULT 'PUBLIC',
	commentable BOOLEAN DEFAULT true,
	voteable BOOLEAN DEFAULT true,
	blocked BOOLEAN DEFAULT false,
	views INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP,
	user_id INT NOT NULL,
	deleted BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO videos (name, url, thumbnail, user_id) VALUES 
	('Marshall Jefferson - Floating', 'https://www.youtube.com/embed/EGuiXTUQeH4', 'https://img.youtube.com/vi/EGuiXTUQeH4/0.jpg', 1),
	('Dave Brubeck - Take Five', 'https://www.youtube.com/embed/vmDDOFXSgAs', 'https://img.youtube.com/vi/vmDDOFXSgAs/0.jpg', 2),
	('Ryo Fukui - Scenery 1976 (FULL ALBUM)', 'https://www.youtube.com/embed/Hrr3dp7zRQY', 'https://img.youtube.com/vi/Hrr3dp7zRQY/0.jpg', 3),
	('Blues Brothers - Sweet Home Chicago', 'https://www.youtube.com/embed/79vCiXg3njY', 'https://img.youtube.com/vi/79vCiXg3njY/0.jpg', 4);
	
CREATE TABLE comments (
	id INT AUTO_INCREMENT,
	text TEXT NOT NULL,
	created_at TIMESTAMP,
	user_id INT NOT NULL,
	video_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (video_id) REFERENCES videos(id)
);

INSERT INTO comments (text, user_id, video_id)
	VALUES ('Naci todor postavio ovaj komentar', 1, 1);
	
CREATE TABLE follow (
	follower_id INT,
	user_id INT,
	FOREIGN KEY (follower_id) REFERENCES users(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);
	
CREATE TABLE votes (
	id INT AUTO_INCREMENT,
	user_id INT,
	voteable_id INT,
	voteable_type VARCHAR(30) NOT NULL,
	vote BOOLEAN NOT NULL,
	created_at TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);
