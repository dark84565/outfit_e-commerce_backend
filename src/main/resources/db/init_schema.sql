CREATE DATABASE if not exists `cnc`;

USE cnc;

CREATE TABLE if not exists `user`
(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `username` VARCHAR(32) NOT NULL,
    `email` VARCHAR(32) UNIQUE NOT NULL,
    `uuid` VARCHAR(256) UNIQUE NOT NULL,
    `password` VARCHAR(256) NOT NULL
);

CREATE TABLE if not exists  `post`
(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `description` VARCHAR(512) NOT NULL,
    `user_uuid` VARCHAR(256) NOT NULL,
    FOREIGN KEY (`user_uuid`) REFERENCES `user`(`uuid`)
);

CREATE TABLE if not exists `photo`
(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `path` VARCHAR(300) NOT NULL,
    `post_id` BIGINT NOT NULL,
    FOREIGN KEY (`post_id`) REFERENCES `post`(`id`)
);

CREATE TABLE if not exists `tag`
(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tag` VARCHAR(32) NOT NULL
);

CREATE TABLE if not exists `post_tag`
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY AUTO_INCREMENT,
	`post_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    FOREIGN KEY (`post_id`) REFERENCES post(`id`),
    FOREIGN KEY (`tag_id`) REFERENCES tag(`id`)
);

CREATE TABLE if not exists `key_pair`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_uuid` VARCHAR(256) NOT NULL,
    `private_key` VARCHAR(4096) NOT NULL,
    `public_key` VARCHAR(1024) NOT NULL,
    FOREIGN KEY (`user_uuid`) REFERENCES user(`uuid`)
);
