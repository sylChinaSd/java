-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.6.17 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 msp 的数据库结构
CREATE DATABASE IF NOT EXISTS `msp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `msp`;


-- 导出  表 msp.msp_authorities 结构
CREATE TABLE IF NOT EXISTS `msp_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(150) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `msp_users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  msp.msp_authorities 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `msp_authorities` DISABLE KEYS */;
INSERT INTO `msp_authorities` (`username`, `authority`) VALUES
	('manager', 'ROLE_MANAGER'),
	('root', 'ROLE_ADMIN'),
	('user', 'ROLE_USER');
/*!40000 ALTER TABLE `msp_authorities` ENABLE KEYS */;


-- 导出  表 msp.msp_product 结构
CREATE TABLE IF NOT EXISTS `msp_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_type_id` int(11) DEFAULT NULL,
  `pname` varchar(150) DEFAULT NULL,
  `remark` varchar(150) DEFAULT NULL,
  `pdesc` varchar(300) DEFAULT NULL,
  `rule_param` varchar(600) DEFAULT NULL,
  `pack_list` varchar(300) DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  `discount` decimal(8,2) DEFAULT '1.00',
  `stock` int(11) DEFAULT '0',
  `index_seq` int(4) DEFAULT '0',
  `c_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `m_pic` varchar(100) DEFAULT NULL,
  `pic1` varchar(100) DEFAULT NULL,
  `pic2` varchar(100) DEFAULT NULL,
  `pic3` varchar(100) DEFAULT NULL,
  `pic4` varchar(100) DEFAULT NULL,
  `pcondi` int(2) DEFAULT '0',
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- 正在导出表  msp.msp_product 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `msp_product` DISABLE KEYS */;
INSERT INTO `msp_product` (`id`, `shop_id`, `user_id`, `product_type_id`, `pname`, `remark`, `pdesc`, `rule_param`, `pack_list`, `price`, `discount`, `stock`, `index_seq`, `c_time`, `m_pic`, `pic1`, `pic2`, `pic3`, `pic4`, `pcondi`, `version`) VALUES
	(4, NULL, NULL, NULL, '富士苹果', '一箱12斤', '', '', '一箱，6个', 36.00, 0.85, 3, 0, '2016-11-18 17:49:24', 'resources/images/products/4/mPic.jpg', NULL, NULL, NULL, NULL, 1, 146),
	(15, NULL, NULL, NULL, '莱阳梨', '', '一盒6个', '一盒6个', '', 26.00, 1.00, 87, NULL, NULL, 'resources/images/products/15/mPic.jpg', NULL, NULL, NULL, NULL, 1, 9),
	(16, NULL, NULL, NULL, '水蜜桃', '', '', '一盒5个', '一盒5个', 16.00, 0.95, 11, NULL, NULL, 'resources/images/products/16/mPic.jpg', NULL, NULL, NULL, NULL, 0, 9),
	(17, NULL, NULL, NULL, '新鲜荔枝', '', '', '一盒2斤', '', 21.00, 0.95, 0, NULL, NULL, 'resources/images/products/17/mPic.jpg', NULL, NULL, NULL, NULL, 1, 33),
	(18, NULL, NULL, NULL, '进口香蕉', '', '', '', '10个', 9.90, 1.00, 142, NULL, NULL, 'resources/images/products/18/mPic.jpg', NULL, NULL, NULL, NULL, 1, 9),
	(19, NULL, NULL, NULL, '黄岩蜜桔', '', '', '', '3斤，约15个', 12.50, 0.85, 40, NULL, NULL, 'resources/images/products/19/mPic.jpg', NULL, NULL, NULL, NULL, 1, 2),
	(20, NULL, NULL, NULL, '国产菠萝', '', '', '', '2个，约3斤', 12.00, 0.90, 256, NULL, NULL, 'resources/images/products/20/mPic.jpg', NULL, NULL, NULL, NULL, 1, 1),
	(21, NULL, NULL, NULL, '山东石榴', '', '', '5个', '一盒5个', 16.50, 1.00, 35, NULL, NULL, 'resources/images/products/21/mPic.jpg', NULL, NULL, NULL, NULL, 1, 1),
	(22, NULL, NULL, NULL, '四川辣椒', '', '', '', '1斤', 18.00, 0.90, 69, NULL, NULL, '', NULL, NULL, NULL, NULL, 1, 0),
	(23, NULL, NULL, NULL, '西红柿', '', '', '', '6个，2.5斤', 6.00, 0.90, 662, NULL, NULL, 'resources/images/products/23/mPic.jpg', NULL, NULL, NULL, NULL, 1, 2),
	(24, NULL, NULL, NULL, '测试', NULL, NULL, NULL, NULL, NULL, 1.00, 0, 0, '2017-11-23 16:57:17', NULL, NULL, NULL, NULL, NULL, 0, NULL),
	(25, NULL, NULL, NULL, '测试', NULL, NULL, NULL, NULL, NULL, 1.00, 0, 0, '2017-11-24 11:13:16', NULL, NULL, NULL, NULL, NULL, 0, NULL);
/*!40000 ALTER TABLE `msp_product` ENABLE KEYS */;


-- 导出  表 msp.msp_resources 结构
CREATE TABLE IF NOT EXISTS `msp_resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `rpath` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- 正在导出表  msp.msp_resources 的数据：~18 rows (大约)
/*!40000 ALTER TABLE `msp_resources` DISABLE KEYS */;
INSERT INTO `msp_resources` (`id`, `role_name`, `name`, `rpath`) VALUES
	(1, 'ROLE_ADMIN', '商品管理', '/product/grid'),
	(4, 'ROLE_USER', '商品列表', '/product/list'),
	(5, 'ROLE_ADMIN', '系统设置', '/config'),
	(6, 'ROLE_MANAGER', '商品管理', '/product/grid'),
	(7, 'ROLE_MANAGER', '商品列表', '/product/list'),
	(8, 'ROLE_USER', '购物车', '/shoppingcart'),
	(9, 'ROLE_ADMIN', '购物车', '/shoppingcart'),
	(10, 'ROLE_ADMIN', '商品列表', '/product/list'),
	(11, 'ROLE_ADMIN', '订单查询', '/order/query'),
	(12, 'ROLE_USER', '订单查询', '/order/query'),
	(13, 'ROLE_ADMIN', '订单管理', '/order/manage/query'),
	(14, 'ROLE_MANAGER', '订单管理', '/order/manage/query'),
	(15, 'ROLE_ADMIN', '统计分析', '/order/manage/analysis'),
	(16, 'ROLE_MANAGER', '统计分析', '/order/manage/analysis'),
	(17, 'ROLE_ADMIN', '我的收藏', '/concern'),
	(18, 'ROLE_USER', '我的收藏', '/concern'),
	(19, 'ROLE_ADMIN', '收货地址', '/address'),
	(20, 'ROLE_USER', '收货地址', '/address');
/*!40000 ALTER TABLE `msp_resources` ENABLE KEYS */;


-- 导出  表 msp.msp_users 结构
CREATE TABLE IF NOT EXISTS `msp_users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(88) NOT NULL,
  `enabled` int(2) DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  msp.msp_users 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `msp_users` DISABLE KEYS */;
INSERT INTO `msp_users` (`username`, `password`, `enabled`) VALUES
	('manager', '$2a$11$5G39N/IEf7Y5nutCkVjJ4upr5VGnCg7yssJ7JFxFU0yB17582Zy0O', NULL),
	('root', '$2a$11$ejZ.6dmEI5hDgnVfIhORxuH44WkkEjJCsVxNH2oB8mSxHD2zYfMcO', NULL),
	('user', '$2a$11$MotL0eO4NSXaUyyMNKXNJeiQzJYPHpMb3lb6khNkqV7gBDss8R0CO', NULL);
/*!40000 ALTER TABLE `msp_users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
