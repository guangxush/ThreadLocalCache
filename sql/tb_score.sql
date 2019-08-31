/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : university

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 31/08/2019 21:42:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_score
-- ----------------------------
DROP TABLE IF EXISTS `tb_score`;
CREATE TABLE `tb_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(7) NOT NULL,
  `class_id` varchar(10) NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `score` int(4) NOT NULL,
  `date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_score
-- ----------------------------
BEGIN;
INSERT INTO `tb_score` VALUES (1, '12345', '1', 'English', 98, '2019-08-31 20:18:47');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
