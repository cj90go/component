/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : lock

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 05/09/2020 15:18:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `amount` decimal(10, 0) NOT NULL COMMENT '账户余额',
  `version` int(11) NULL DEFAULT 1 COMMENT '版本号字段',
  `active` tinyint(11) NULL DEFAULT 1 COMMENT '是否有效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户账户唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (1, 1, -1960, 2, 1);
INSERT INTO `user_account` VALUES (2, 1002, 40, 8, 1);
INSERT INTO `user_account` VALUES (3, 1003, 40, 1, 1);

-- ----------------------------
-- Table structure for user_account_record
-- ----------------------------
DROP TABLE IF EXISTS `user_account_record`;
CREATE TABLE `user_account_record`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '记录表主键id',
  `account_id` int(11) NOT NULL COMMENT '账户表主键id',
  `money` decimal(10, 0) NOT NULL COMMENT '体现成功时记录的金额',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_account_record
-- ----------------------------
INSERT INTO `user_account_record` VALUES (00000000047, 3, 80, '2020-09-03 21:06:52');
INSERT INTO `user_account_record` VALUES (00000000048, 3, 80, '2020-09-03 21:06:52');

SET FOREIGN_KEY_CHECKS = 1;
