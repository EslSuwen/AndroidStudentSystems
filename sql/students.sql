/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : students

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 15/01/2020 18:33:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_1
-- ----------------------------
DROP TABLE IF EXISTS `admin_1`;
CREATE TABLE `admin_1` (
  `uname` varchar(20) NOT NULL,
  `pwd` varchar(6) NOT NULL,
  PRIMARY KEY (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of admin_1
-- ----------------------------
BEGIN;
INSERT INTO `admin_1` VALUES ('admin', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for student_2
-- ----------------------------
DROP TABLE IF EXISTS `student_2`;
CREATE TABLE `student_2` (
  `stu_uname` varchar(20) NOT NULL,
  `stu_pwd` varchar(12) NOT NULL,
  `stu_name` varchar(10) NOT NULL,
  `stu_gender` varchar(5) DEFAULT 'ÄÐ',
  `math` decimal(4,1) DEFAULT '0.0',
  `chinese` decimal(4,1) DEFAULT '0.0',
  `english` decimal(4,1) DEFAULT '0.0',
  `Dep` varchar(20) DEFAULT NULL,
  `institute` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`stu_uname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student_2
-- ----------------------------
BEGIN;
INSERT INTO `student_2` VALUES ('10101', '123', '学生1', 'm', 89.0, 63.0, 62.0, 'cs', 'com');
INSERT INTO `student_2` VALUES ('10102', '123', '学生3', 'm', 66.0, 75.0, 88.0, '计算机科学与技术', '信息科学与工程');
INSERT INTO `student_2` VALUES ('10104', '123', '学生4', 'f', 66.0, 75.0, 88.0, '物联网', '信息科学与工程');
INSERT INTO `student_2` VALUES ('10105', '123', '学生5', 'm', 66.0, 75.0, 88.0, '英语', '外语');
INSERT INTO `student_2` VALUES ('10106', '123', '学生6', 'f', 66.0, 75.0, 88.0, '计算机科学与技术', '信息科学与工程');
INSERT INTO `student_2` VALUES ('10107', '123', '学生7', 'm', 66.0, 75.0, 88.0, '计算机科学与技术', '信息科学与工程');
INSERT INTO `student_2` VALUES ('123', '123', '123', 'm', 0.0, 0.0, 0.0, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
