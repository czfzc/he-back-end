/*
 Navicat Premium Data Transfer

 Source Server         : hour
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : hour

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 27/04/2019 21:01:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_inf
-- ----------------------------
DROP TABLE IF EXISTS `admin_inf`;
CREATE TABLE `admin_inf`  (
  `admin_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sms_code` int(6) NOT NULL,
  `mainkey` int(10) NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_inf
-- ----------------------------
INSERT INTO `admin_inf` VALUES ('17709201921', '71266a572d22ecc98e7b4b4e35fba105', 'czf', '115115', 123456, 105, b'1');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1000065);

-- ----------------------------
-- Table structure for school_building
-- ----------------------------
DROP TABLE IF EXISTS `school_building`;
CREATE TABLE `school_building`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mainkey` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school_building
-- ----------------------------
INSERT INTO `school_building` VALUES ('123', '逸夫楼', '1,5', 0);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `room_num` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `build_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addition` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `is_default` tinyint(1) NOT NULL DEFAULT 0,
  `mainkey` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_building`(`build_id`) USING BTREE,
  INDEX `user_inf_user_id_fk1`(`user_id`) USING BTREE,
  INDEX `id`(`id`, `user_id`) USING BTREE,
  CONSTRAINT `school_building` FOREIGN KEY (`build_id`) REFERENCES `school_building` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_inf_user_id_fk1` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('123', '17709201921', '王红欣', '17709205870', '446', '123', 'no', b'1', 0, 0);
INSERT INTO `user_address` VALUES ('2a6e0499d5ce4388bd741e178fcb409f', '17709201921', '曹八', '17800001122', 'b3', '123', '无', b'1', 1, 1000006);
INSERT INTO `user_address` VALUES ('6a4e089dc2af49818999e2c5f5b3dd3a', '17709201921', '曹八', '17800001122', 'b3', '123', '', b'1', 0, 1000008);

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf`  (
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `session_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mainkey` int(10) NOT NULL DEFAULT 0,
  `mysession` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`mainkey`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `open_id`(`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES ('17709201921', '10101011', '12221223', 5, '115115');
INSERT INTO `user_inf` VALUES ('aac', 'bbb', 'ccc', 6, '');
INSERT INTO `user_inf` VALUES ('17709201922', '10123912', '27614612', 8, '');

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL DEFAULT 0.00,
  `time` datetime(0) NOT NULL,
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `mainkey` int(10) NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `payed`(`payed`) USING BTREE,
  INDEX `abled`(`abled`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `payed_2`(`payed`, `order_id`) USING BTREE,
  INDEX `order_id`(`order_id`, `abled`) USING BTREE,
  INDEX `abled_2`(`abled`, `order_id`) USING BTREE,
  INDEX `order_id_2`(`order_id`) USING BTREE,
  INDEX `order_id_3`(`order_id`, `user_id`) USING BTREE,
  CONSTRAINT `user_inf_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_order
-- ----------------------------
INSERT INTO `user_order` VALUES ('37badee1657e4df9a77a542a469aaae0', '17709201921', '127.0.1.1', 0.00, '2019-04-27 06:10:37', 0, b'1', 1000041);
INSERT INTO `user_order` VALUES ('8f3b7a3e4f3c45c0b1f4cb6f03683ff0', '17709201921', '127.0.1.1', 2.00, '2019-04-27 06:18:59', 3, b'1', 1000048);
INSERT INTO `user_order` VALUES ('b346751d1cef49b18f1037aafd479dd1', '17709201921', '127.0.1.1', 2.00, '2019-04-27 06:15:50', 0, b'1', 1000044);
INSERT INTO `user_order` VALUES ('c4bf2d53ccfe441189c1aed28cd97c9e', '17709201921', '127.0.1.1', 2.00, '2019-04-27 06:24:56', 0, b'1', 1000052);
INSERT INTO `user_order` VALUES ('f7f47791cc9e4ba488b3d66be79d7e8e', '17709201921', '127.0.1.1', 2.00, '2019-04-27 19:27:49', 0, b'1', 1000056);
INSERT INTO `user_order` VALUES ('f92ab7cef53d4be6b67952271cde1271', '17709201921', '127.0.1.1', 2.00, '2019-04-27 19:28:44', 0, b'1', 1000060);

-- ----------------------------
-- Table structure for user_proxy_express
-- ----------------------------
DROP TABLE IF EXISTS `user_proxy_express`;
CREATE TABLE `user_proxy_express`  (
  `express_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `preorder_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL,
  `sms_content` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信内容',
  `receive_code` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mainkey` int(10) NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `status` int(1) NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`express_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_address_id_fk`(`address_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_preorder_id_fk`(`preorder_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_preorder_id_fk2`(`preorder_id`, `user_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_abled`(`preorder_id`, `abled`) USING BTREE,
  CONSTRAINT `user_proxy_express_preorder_address_id_fk` FOREIGN KEY (`address_id`) REFERENCES `user_proxy_express_preorder` (`address_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_proxy_express_preorder_preorder_id_fk2` FOREIGN KEY (`preorder_id`, `user_id`) REFERENCES `user_proxy_express_preorder` (`id`, `user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_proxy_express_preorder_abled` FOREIGN KEY (`preorder_id`, `abled`) REFERENCES `user_proxy_express_preorder` (`id`, `abled`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_proxy_express
-- ----------------------------
INSERT INTO `user_proxy_express` VALUES ('01094183d6464d4aa00d39b785dd6a03', '1851360938a14b6e854e154f394ae72d', '123', 'czf2', '18681805870', 1.00, '请去南门领快递，凭号码435312', '435312', 1000047, '17709201921', '2019-04-27 06:15:50', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('42ee1aa3707e489e9783046b0ac3899c', '12486e440551422faa23bb6ceec15729', '123', 'czf2', '18681805870', 1.00, '请去南门领快递，凭号码435312', '435312', 1000055, '17709201921', '2019-04-27 06:24:56', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('55221cf5752f4295adb6c7bd036abf34', '12486e440551422faa23bb6ceec15729', '123', 'czf', '17709201921', 1.00, '请去东门领快递，凭号码128743', '128743', 1000054, '17709201921', '2019-04-27 06:24:56', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('5a7118418ffd4f8db1e780e3e8da94e9', 'b10a40cf4f9f423a84bcd94582dfd9a8', '123', 'czf', '17709201921', 1.00, '请去东门领快递，凭号码128743', '128743', 1000050, '17709201921', '2019-04-27 06:18:59', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('5c1fc8b0df6f4f87b4bf27f7a9a40029', '4da16319f6644d50a87c7e52f8828cd6', '123', 'czf', '17709201921', 1.00, '请去东门领快递，凭号码128743', '128743', 1000058, '17709201921', '2019-04-27 19:27:49', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('648c5e16aea94656ab84ba404544bd01', '1851360938a14b6e854e154f394ae72d', '123', 'czf', '17709201921', 1.00, '请去东门领快递，凭号码128743', '128743', 1000046, '17709201921', '2019-04-27 06:15:50', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('82b2b47216d8462484ba62fae03ef185', '1c9f45570c624f96b8c3f7dd838433dd', '123', 'czf2', '18681805870', 1.00, '请去南门领快递，凭号码435312', '435312', 1000063, '17709201921', '2019-04-27 19:28:44', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('9c9b370192aa4ab493985c5acb1e17bb', 'b10a40cf4f9f423a84bcd94582dfd9a8', '123', 'czf2', '18681805870', 1.00, '请去南门领快递，凭号码435312', '435312', 1000051, '17709201921', '2019-04-27 06:18:59', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('ed2d85c0cf8f4e26a5a5a27e0e4b9b56', '4da16319f6644d50a87c7e52f8828cd6', '123', 'czf2', '18681805870', 1.00, '请去南门领快递，凭号码435312', '435312', 1000059, '17709201921', '2019-04-27 19:27:49', 0, b'1');
INSERT INTO `user_proxy_express` VALUES ('f18e62f7dcf24bd9837667958d7e7d4a', '1c9f45570c624f96b8c3f7dd838433dd', '123', 'czf', '17709201921', 1.00, '请去东门领快递，凭号码128743', '128743', 1000062, '17709201921', '2019-04-27 19:28:44', 0, b'1');

-- ----------------------------
-- Table structure for user_proxy_express_preorder
-- ----------------------------
DROP TABLE IF EXISTS `user_proxy_express_preorder`;
CREATE TABLE `user_proxy_express_preorder`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL,
  `address_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `service_id` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `mainkey` int(10) NOT NULL,
  `send_method_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_order_user_id_fk`(`user_id`) USING BTREE,
  INDEX `address_id`(`address_id`) USING BTREE,
  INDEX `user_order_payed_fk`(`payed`, `order_id`) USING BTREE,
  INDEX `user_order_abled_fk`(`order_id`, `abled`) USING BTREE,
  INDEX `user_address_id_fk`(`address_id`, `user_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `user_service_id`(`service_id`) USING BTREE,
  INDEX `user_send_method_id`(`send_method_id`, `service_id`) USING BTREE,
  INDEX `id_2`(`id`, `user_id`) USING BTREE,
  INDEX `id_3`(`id`, `abled`) USING BTREE,
  CONSTRAINT `user_address_id_fk` FOREIGN KEY (`address_id`, `user_id`) REFERENCES `user_address` (`id`, `user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_order_abled_fk` FOREIGN KEY (`order_id`, `abled`) REFERENCES `user_order` (`order_id`, `abled`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_order_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `user_order` (`order_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_order_payed_fk` FOREIGN KEY (`payed`, `order_id`) REFERENCES `user_order` (`payed`, `order_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_order_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_order` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_send_method_id` FOREIGN KEY (`send_method_id`, `service_id`) REFERENCES `user_send_method` (`id`, `service_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `user_service_id` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_proxy_express_preorder
-- ----------------------------
INSERT INTO `user_proxy_express_preorder` VALUES ('12486e440551422faa23bb6ceec15729', 2.00, '123', '2019-04-27 06:24:56', 'c4bf2d53ccfe441189c1aed28cd97c9e', '17709201921', 1, 0, 0, b'1', 1000053, 1);
INSERT INTO `user_proxy_express_preorder` VALUES ('1851360938a14b6e854e154f394ae72d', 2.00, '123', '2019-04-27 06:15:50', 'b346751d1cef49b18f1037aafd479dd1', '17709201921', 1, 0, 0, b'1', 1000045, 1);
INSERT INTO `user_proxy_express_preorder` VALUES ('1c9f45570c624f96b8c3f7dd838433dd', 2.00, '123', '2019-04-27 19:28:44', 'f92ab7cef53d4be6b67952271cde1271', '17709201921', 1, 0, 0, b'1', 1000061, 1);
INSERT INTO `user_proxy_express_preorder` VALUES ('4da16319f6644d50a87c7e52f8828cd6', 2.00, '123', '2019-04-27 19:27:49', 'f7f47791cc9e4ba488b3d66be79d7e8e', '17709201921', 1, 0, 0, b'1', 1000057, 1);
INSERT INTO `user_proxy_express_preorder` VALUES ('b10a40cf4f9f423a84bcd94582dfd9a8', 2.00, '123', '2019-04-27 06:18:59', '8f3b7a3e4f3c45c0b1f4cb6f03683ff0', '17709201921', 1, 0, 3, b'1', 1000049, 1);
INSERT INTO `user_proxy_express_preorder` VALUES ('eae4563400724e269d9bb32429c6fc6f', 0.00, '123', '2019-04-27 06:10:37', '37badee1657e4df9a77a542a469aaae0', '17709201921', 1, 0, 0, b'1', 1000042, 1);

-- ----------------------------
-- Table structure for user_refund
-- ----------------------------
DROP TABLE IF EXISTS `user_refund`;
CREATE TABLE `user_refund`  (
  `refund_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `reason` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `refused` bit(1) NOT NULL,
  `abled` bit(1) NOT NULL,
  `succeed` bit(1) NOT NULL,
  `mainkey` int(10) NOT NULL,
  PRIMARY KEY (`refund_id`) USING BTREE,
  INDEX `user_order_id_fk3`(`order_id`, `user_id`) USING BTREE,
  CONSTRAINT `user_order_id_fk3` FOREIGN KEY (`order_id`, `user_id`) REFERENCES `user_order` (`order_id`, `user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_refund
-- ----------------------------
INSERT INTO `user_refund` VALUES ('21fc0c4a04c2448c89ad4069c79d11ef', '8f3b7a3e4f3c45c0b1f4cb6f03683ff0', '17709201921', '2019-04-27 20:56:37', '没有任何理由', b'0', b'1', b'1', 1000064);

-- ----------------------------
-- Table structure for user_send_method
-- ----------------------------
DROP TABLE IF EXISTS `user_send_method`;
CREATE TABLE `user_send_method`  (
  `id` int(11) NOT NULL,
  `value` char(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '配送方式内容',
  `service_id` int(11) NOT NULL,
  `mainkey` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `service_id`) USING BTREE,
  INDEX `user_service_id2`(`service_id`) USING BTREE,
  CONSTRAINT `user_service_id2` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_send_method
-- ----------------------------
INSERT INTO `user_send_method` VALUES (1, '12', 1, 0);

-- ----------------------------
-- Table structure for user_service
-- ----------------------------
DROP TABLE IF EXISTS `user_service`;
CREATE TABLE `user_service`  (
  `id` int(11) NOT NULL,
  `model_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `comment` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `mainkey` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_service
-- ----------------------------
INSERT INTO `user_service` VALUES (1, 'express', 'express', 0);

SET FOREIGN_KEY_CHECKS = 1;
