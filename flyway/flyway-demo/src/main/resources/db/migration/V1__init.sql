DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT,
    `username` varchar(100) NOT NULL,
    `phone`    varchar(11)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `user`
VALUES (1, '张三', '12342318394'),
       (2, '李四', '14938274838'),
       (3, '王五', '12938473838'),
       (4, '马六', '12138473823');
