DROP TABLE IF EXISTS t_Album;
DROP TABLE IF EXISTS t_LogEntry;

CREATE TABLE t_Album(
    isrc varchar(255) PRIMARY KEY,
    title varchar(255),
    description varchar(255), 
    year_released varchar(255), 
    artist_first_name varchar(255), 
	artist_last_name varchar(255), 
	cover LONGBLOB,
	media_type varchar(255)
)ENGINE=InnoDB;

CREATE TABLE t_LogEntry(
	id int PRIMARY KEY AUTO_INCREMENT,
	change_type varchar(255),
	isrc varchar(255) NOT NULL,
	time_stamp date
)ENGINE=InnoDB;