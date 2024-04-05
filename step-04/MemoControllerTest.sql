DELETE FROM `memo`;
DELETE FROM `user`;

INSERT INTO `user` (id, name, email_address) VALUES (1, 'User1', 'user1@example.com');
INSERT INTO `user` (id, name, email_address) VALUES (2, 'User2', 'user2@example.com');

INSERT INTO `memo` (id, body, user_id) VALUES (1, 'First memo', 1);
INSERT INTO `memo` (id, body, user_id) VALUES (2, 'Second memo', 2);
