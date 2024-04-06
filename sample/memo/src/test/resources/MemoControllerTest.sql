DELETE FROM `memo`;
DELETE FROM `user`;

INSERT INTO `user` (id, name, email_address) VALUES (1, 'tanaka', 'tanaka@example.com');
INSERT INTO `user` (id, name, email_address) VALUES (2, 'sato', 'sato@example.com');

INSERT INTO `memo` (id, body, user_id) VALUES (1, 'takoyaki', 1);
INSERT INTO `memo` (id, body, user_id) VALUES (2, 'okonomiyaki', 2);
