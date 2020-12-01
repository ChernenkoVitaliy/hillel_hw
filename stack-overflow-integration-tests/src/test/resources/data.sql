INSERT INTO `stackoverflow`.`account` (`password`, `account_status`, `name`, `email`, `phone`, `reputation`) VALUES ('password_1', 'ACTIVE', 'Test Name1', 'test_name1@some.com', '(123) 321 45 67', '0');
INSERT INTO `stackoverflow`.`account` (`password`, `account_status`, `name`, `email`, `phone`, `reputation`) VALUES ('password_2', 'BLOCKED', 'Test Name2', 'test_name2@some.com', '(123) 321 45 68', '0');
INSERT INTO `stackoverflow`.`account` (`password`, `account_status`, `name`, `email`, `phone`, `reputation`) VALUES ('password_3', 'BANNED', 'Test Name3', 'test_name3@some.com', '(123) 321 45 69', '0');


INSERT INTO `stackoverflow`.`question` (`title`, `description`, `author_id`) VALUES ('Some question title 1', 'Some question description 1', '1');
INSERT INTO `stackoverflow`.`question` (`title`, `description`, `author_id`) VALUES ('Some question title 2', 'Some question description 2', '2');
INSERT INTO `stackoverflow`.`question` (`title`, `description`, `author_id`) VALUES ('Some question title 3', 'Some question description 3', '3');


INSERT INTO `stackoverflow`.`answer` (`answer_text`, `author_id`, `question_id`) VALUES ('Some answer text 1', '1', '1');
INSERT INTO `stackoverflow`.`answer` (`answer_text`, `author_id`, `question_id`) VALUES ('Some answer text 2', '2', '2');
INSERT INTO `stackoverflow`.`answer` (`answer_text`, `author_id`, `question_id`) VALUES ('Some answer text 3', '3', '3');


INSERT INTO `stackoverflow`.`comment` (`text`, `author_id`, `answer_id`) VALUES ('Some comment text 1', '1', '1');
INSERT INTO `stackoverflow`.`comment` (`text`, `author_id`, `answer_id`) VALUES ('Some comment text 2', '2', '2');
INSERT INTO `stackoverflow`.`comment` (`text`, `author_id`, `answer_id`) VALUES ('Some comment text 3', '3', '3');