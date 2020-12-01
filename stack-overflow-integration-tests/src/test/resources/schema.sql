CREATE SCHEMA IF NOT EXISTS `stackoverflow` ;
USE stackoverflow;
DROP TABLE IF EXISTS comment, answer, question, account;


CREATE TABLE `stackoverflow`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(128) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `reputation` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `stackoverflow`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `view_count` INT NULL DEFAULT 0,
  `vote_count` INT NULL DEFAULT 0,
  `created` DATETIME NULL DEFAULT NOW(),
  `updated` DATETIME NULL DEFAULT NOW(),
  `status` VARCHAR(11) NULL,
  `closing_remark` VARCHAR(45) NULL,
  `author_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_author_id_in_question`
    FOREIGN KEY (`author_id`)
    REFERENCES `stackoverflow`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `stackoverflow`.`answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `answer_text` TEXT NOT NULL,
  `accepted` TINYINT(1) NULL DEFAULT 0,
  `vote_count` INT NULL DEFAULT 0,
  `flag_count` INT NULL DEFAULT 0,
  `created` DATETIME NULL DEFAULT NOW(),
  `author_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_author_id_in_answer`
    FOREIGN KEY (`author_id`)
    REFERENCES `stackoverflow`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_id_in_answer`
    FOREIGN KEY (`question_id`)
    REFERENCES `stackoverflow`.`question` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `stackoverflow`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `created` DATETIME NULL DEFAULT NOW(),
  `flag_count` INT NULL DEFAULT 0,
  `vote_count` INT NULL DEFAULT 0,
  `author_id` INT NOT NULL,
  `answer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_author_id_in_comment`
    FOREIGN KEY (`author_id`)
    REFERENCES `stackoverflow`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_answer_id_in_comment`
    FOREIGN KEY (`answer_id`)
    REFERENCES `stackoverflow`.`answer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);