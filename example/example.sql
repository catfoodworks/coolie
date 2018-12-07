CREATE DATABASE `coolie` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL,
  `name` varchar(16) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `gender` varchar(8) NOT NULL DEFAULT '',
  `account` varchar(16) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `remark` varchar(128) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `user_type` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `acount_UNIQUE` (`account`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
