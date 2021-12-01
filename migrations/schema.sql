DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  `fk_payment_id` int(11) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT INTO `subscription` (`id`, `type`, `fk_payment_id`, `reference`) VALUES ('1', 'weekly', NULL, 'sub-aaa-bbb-ccc');
INSERT INTO `subscription` (`id`, `type`, `fk_payment_id`, `reference`) VALUES ('2', 'daily', NULL, 'sub-ddd-fff-ggg');
INSERT INTO `subscription` (`id`, `type`, `fk_payment_id`, `reference`) VALUES ('3', 'weekly', NULL, 'sub-xxx-yyy-zzz');
