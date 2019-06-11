/*
 Navicat Premium Data Transfer

 Source Server         : mypc
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : hour

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 10/06/2019 09:01:40
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
  `school_card_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_inf
-- ----------------------------
INSERT INTO `admin_inf` VALUES ('1', '6512BD43D9CAA6E02C990B0A82652DCA', 'ccc', '51df9b92899eee0a7ef9e92005c36561', 1111, 111, b'1', '11', '2019-06-09 13:03:40');
INSERT INTO `admin_inf` VALUES ('17709201921', '71266a572d22ecc98e7b4b4e35fba105', 'czf', 'f61e3562306f77c32a60258be793948a', 123456, 105, b'1', '', '2019-06-03 00:13:28');

-- ----------------------------
-- Table structure for express_admin
-- ----------------------------
DROP TABLE IF EXISTS `express_admin`;
CREATE TABLE `express_admin`  (
  `express_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `express_point` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payed` int(11) NOT NULL,
  `phone_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `preorder_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `receive_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sender_admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sms_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `total_fee` double NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`express_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `hibernate_sequence` VALUES (1000465);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `room_num` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `build_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addition` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `is_default` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_building`(`build_id`) USING BTREE,
  INDEX `user_inf_user_id_fk1`(`user_id`) USING BTREE,
  INDEX `id`(`id`, `user_id`) USING BTREE,
  CONSTRAINT `school_building` FOREIGN KEY (`build_id`) REFERENCES `user_dest_building` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_inf_user_id_fk1` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('ff8081816b3ad2a4016b3ad3f6d20000', '17709201921', '1', '1', '1', '123', '', b'1', b'1');

-- ----------------------------
-- Table structure for user_dest_building
-- ----------------------------
DROP TABLE IF EXISTS `user_dest_building`;
CREATE TABLE `user_dest_building`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dest_building
-- ----------------------------
INSERT INTO `user_dest_building` VALUES ('123', '三舍A区', '[1,5]', b'0');
INSERT INTO `user_dest_building` VALUES ('124', '三舍B区', '[1,4]', b'1');
INSERT INTO `user_dest_building` VALUES ('125', '三舍C区', '[2,3]', b'1');
INSERT INTO `user_dest_building` VALUES ('2c9080846b16a315016b1bc5f5540014', '二舍', '[1,1]', b'1');
INSERT INTO `user_dest_building` VALUES ('ff8081816aedd0c7016aedd34fa60000', '四舍', '[2,9]', b'1');

-- ----------------------------
-- Table structure for user_express
-- ----------------------------
DROP TABLE IF EXISTS `user_express`;
CREATE TABLE `user_express`  (
  `express_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `preorder_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL,
  `sms_content` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信内容',
  `receive_code` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `status` int(1) NOT NULL,
  `abled` bit(1) NOT NULL,
  `express_point_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `size_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `send_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sender_admin_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`express_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_address_id_fk`(`address_id`) USING BTREE,
  INDEX `user_proxy_express_preorder_preorder_id_fk`(`preorder_id`) USING BTREE,
  INDEX `user_express_point_id`(`express_point_id`) USING BTREE,
  INDEX `express_size_fk2`(`size_id`) USING BTREE,
  INDEX `user_preorder_abled`(`preorder_id`, `abled`, `address_id`, `user_id`) USING BTREE,
  INDEX `user_preorder_preorder_id_fk2`(`preorder_id`, `user_id`) USING BTREE,
  INDEX `user_preorder_fk`(`preorder_id`, `abled`, `address_id`, `user_id`, `payed`) USING BTREE,
  CONSTRAINT `express_size_fk2` FOREIGN KEY (`size_id`) REFERENCES `user_express_size` (`size_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_express_point_id` FOREIGN KEY (`express_point_id`) REFERENCES `user_express_point` (`express_point_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `user_preorder_fk` FOREIGN KEY (`preorder_id`, `abled`, `address_id`, `user_id`, `payed`) REFERENCES `user_preorder` (`id`, `abled`, `address_id`, `user_id`, `payed`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_express
-- ----------------------------
INSERT INTO `user_express` VALUES ('ff8081816b3ad2a4016b3ad450de0003', 'ff8081816b3ad2a4016b3ad450cd0002', 'ff8081816b3ad2a4016b3ad3f6d20000', '1', '1', 0.00, '【近邻宝】请用微信或密码75142638到东大游泳馆东侧五四体育场东南角3号柜S11箱取件。询13066696859', '75142638', '17709201921', '2019-06-09 14:02:01', 0, b'1', '111122', '0', '2', NULL, 1, NULL);

-- ----------------------------
-- Table structure for user_express_card
-- ----------------------------
DROP TABLE IF EXISTS `user_express_card`;
CREATE TABLE `user_express_card`  (
  `card_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `end_time` datetime(0) NOT NULL,
  `service_id` int(11) NOT NULL,
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `last_times` int(5) NOT NULL DEFAULT 0,
  PRIMARY KEY (`card_id`) USING BTREE,
  INDEX `product_id_fk`(`product_id`) USING BTREE,
  INDEX `service_id_fk`(`service_id`) USING BTREE,
  INDEX `user_id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `user_more_product` (`product_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `service_id_fk` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_express_point
-- ----------------------------
DROP TABLE IF EXISTS `user_express_point`;
CREATE TABLE `user_express_point`  (
  `express_point_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sms_temp` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code_format` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`express_point_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_express_point
-- ----------------------------
INSERT INTO `user_express_point` VALUES ('111122', '[1,4]', '近邻宝', '【近邻宝】请用微信或密码63178402到东大游泳馆东侧五四体育场东南角3号柜C27箱取件。询13066696859\r\n', 'nnnnnnnn', b'1');
INSERT INTO `user_express_point` VALUES ('112211', '[1,2]', '菜鸟驿站-百世', '【菜鸟驿站】您的百世快递包裹到东大校内七舍北侧菜鸟驿站，请17:30前凭1-1-6200及时取，询19904053317\r\n', 'n-n-nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('221111', '[2,2]', '京东物流', '【京东物流】货号5916您好，您的快递到东北大学京东派（小北门西30米），营业时间早9:00-晚6点，电话13309816129\r\n', 'nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('2c9080846b16a315016b1bc34bf30012', '[1,1]', '综合楼EMS', '【东大后勤服务中心】您好，您有一个EMS包裹，请到综合楼113A领取。领取时间：周一至周五上午8:00-12:00,下午14:00-17:00。周六、周日上午8:30-12:00，咨询电话：83687360内线：87360', ' nnnnnnn', b'1');
INSERT INTO `user_express_point` VALUES ('2c9080846b16a315016b1bc437170013', '[1,7]', '菜鸟驿站-韵达', '【菜鸟驿站】您的6月2日韵达包裹等您很久了，请凭（8-8-7077）尽快到东大小北门左拐直行50米菜鸟驿站领取，谢谢理解', 'n-n-nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('2c9080846b3a9a11016b3a9f86c40003', '[1,3]', '菜鸟驿站-天天', '【菜鸟驿站】您的天天包裹到东大小北门左拐直行50米菜鸟驿站，请17:30前凭1-1-7913及时取，询19904053317', 'n-n-nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('ff8081816aed55c8016aeda1fbc20000', '[2,5]', '顺丰速运', '您好！我是顺丰快递员，因由于，您所在地址无法提供派件服务，特请您移步至东北大学小北门菊记超市右转顺丰快递，货号4410，烦请18:30前携带有效证件取件，如您现在不方便取件请及时与我联系，联系电话18540218053，感谢您使用顺丰快递！【顺丰速运】', 'nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('ff8081816aeda51f016aeda74dcb0000', '[3,5]', '妈妈驿站-圆通', '【妈妈驿站】取货码96-013，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', 'nn-nnn', b'1');

-- ----------------------------
-- Table structure for user_express_price
-- ----------------------------
DROP TABLE IF EXISTS `user_express_price`;
CREATE TABLE `user_express_price`  (
  `mainkey` int(11) NOT NULL,
  `dest_building_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `express_point_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` double(10, 2) NOT NULL,
  `size_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `send_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `build_id_fk`(`dest_building_id`) USING BTREE,
  INDEX `express_point_id_fk`(`express_point_id`) USING BTREE,
  INDEX `express_size_id_fk`(`size_id`) USING BTREE,
  INDEX `send_method_id_fk`(`send_method_id`) USING BTREE,
  CONSTRAINT `build_id_fk` FOREIGN KEY (`dest_building_id`) REFERENCES `user_dest_building` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `express_point_id_fk` FOREIGN KEY (`express_point_id`) REFERENCES `user_express_point` (`express_point_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `express_size_id_fk` FOREIGN KEY (`size_id`) REFERENCES `user_express_size` (`size_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `send_method_id_fk` FOREIGN KEY (`send_method_id`) REFERENCES `user_send_method` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_express_price
-- ----------------------------
INSERT INTO `user_express_price` VALUES (1457, '125', '221111', 2.00, '1', '2');
INSERT INTO `user_express_price` VALUES (1458, '125', '221111', 1.00, '0', '2');
INSERT INTO `user_express_price` VALUES (1456, '125', '112211', 1.00, '0', '2');
INSERT INTO `user_express_price` VALUES (1000458, '125', 'ff8081816aeda51f016aeda74dcb0000', 2.00, '1', '2');
INSERT INTO `user_express_price` VALUES (1000459, '125', 'ff8081816aeda51f016aeda74dcb0000', 1.00, '0', '2');
INSERT INTO `user_express_price` VALUES (1000460, '125', '112211', 2.00, '1', '2');
INSERT INTO `user_express_price` VALUES (1000461, 'ff8081816aedd0c7016aedd34fa60000', 'ff8081816aeda51f016aeda74dcb0000', 1.00, '0', '2');
INSERT INTO `user_express_price` VALUES (1000462, 'ff8081816aedd0c7016aedd34fa60000', 'ff8081816aeda51f016aeda74dcb0000', 2.00, '1', '2');
INSERT INTO `user_express_price` VALUES (1000463, 'ff8081816aedd0c7016aedd34fa60000', '221111', 1.00, '0', '2');
INSERT INTO `user_express_price` VALUES (1000464, 'ff8081816aedd0c7016aedd34fa60000', '221111', 2.00, '1', '2');

-- ----------------------------
-- Table structure for user_express_size
-- ----------------------------
DROP TABLE IF EXISTS `user_express_size`;
CREATE TABLE `user_express_size`  (
  `size_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `size_name` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`size_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_express_size
-- ----------------------------
INSERT INTO `user_express_size` VALUES ('0', '小件（2千克以内）', b'1');
INSERT INTO `user_express_size` VALUES ('1', '中件（2-8千克）', b'1');
INSERT INTO `user_express_size` VALUES ('2', '大件（8千克以上）', b'1');

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf`  (
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gzh_open_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `union_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mysession` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  `main_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `voucher_geted` bit(1) NOT NULL DEFAULT b'0',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `headimgurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(11) NOT NULL,
  PRIMARY KEY (`main_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `open_id`(`open_id`) USING BTREE,
  INDEX `user_id_2`(`user_id`, `main_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES ('17709201921', 'ohaRd5aqy7TOAi0Fh11HfQmhqUIA', 'oq0CD1rxkyMUSR_lS5JklPSPYPQc', 'o3nMJ1jGLU8vlPawSp4ZQR_sd5kU', 'L27Gcqp/nxEX13d4E8biNQ==', '91bc5a995151341ca17f086be36a33fd', b'1', '2019-06-09 13:55:27', 'ff8081816b3ac89c016b3acdca510000', b'1', '西安', '中国', 'http://thirdwx.qlogo.cn/mmopen/k9riaLOS1S9nlXkeXVTtf7ZpQQ6KlyxY3tQIeWID0qpJZzX8FaHqMibBShQlmY7YVIWurEx0ZPEicKiaVlibcmwWskicCCJmSPmoMv/132', 'zh_CN', 'Anonymous', '陕西', 1);
INSERT INTO `user_inf` VALUES (NULL, NULL, 'oq0CD1jDbkwnqrcTqlN9VVw2dlOI', 'o3nMJ1oWOucUVjz9asCJdfMrRV5g', NULL, NULL, b'1', NULL, 'ff8081816b3add9d016b3ae580f20000', b'1', '徐州', '中国', 'http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLDv74WKb2k7ic46piaICic90xJMjZPfoVZVRahhFsGQlc47yFxgCXJt06hoTSTrDyeEELqzoHv5Tsp8Q/132', 'zh_CN', '兎飯飯', '江苏', 1);
INSERT INTO `user_inf` VALUES (NULL, NULL, 'oq0CD1ulqahUsqAa-FTvxbtfQEdo', 'o3nMJ1tKcyUc6ZKWQbbLct4fBbPg', NULL, NULL, b'1', NULL, 'ff8081816b3add9d016b3ae6ea030003', b'0', '本溪', '中国', 'http://thirdwx.qlogo.cn/mmopen/ibVbYRtzkaicR1BISeUNaue8oFic6fauYU1jbmkmvH4sOjBCfticCmLLrtGWZnXlDtQZ66YW4HN3lpO75e3pXmb04z4R5ON8GnZV/132', 'zh_CN', '沛公', '辽宁', 1);

-- ----------------------------
-- Table structure for user_more_product
-- ----------------------------
DROP TABLE IF EXISTS `user_more_product`;
CREATE TABLE `user_more_product`  (
  `product_name` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sum` double(255, 2) NOT NULL,
  `abled` bit(1) NOT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_more_product
-- ----------------------------
INSERT INTO `user_more_product` VALUES ('快递代取服务', '09201111', 0.00, b'1');
INSERT INTO `user_more_product` VALUES ('快递代取周免单卡', '09201921', 0.01, b'1');

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL DEFAULT 0.00,
  `time` datetime(0) NOT NULL,
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `prepay_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `payed`(`payed`) USING BTREE,
  INDEX `abled`(`abled`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `payed_2`(`payed`, `order_id`) USING BTREE,
  INDEX `order_id`(`order_id`, `abled`) USING BTREE,
  INDEX `abled_2`(`abled`, `order_id`) USING BTREE,
  INDEX `order_id_2`(`order_id`) USING BTREE,
  INDEX `order_id_3`(`order_id`, `user_id`) USING BTREE,
  INDEX `order_id_4`(`order_id`, `abled`, `user_id`) USING BTREE,
  INDEX `order_id_5`(`order_id`, `abled`, `user_id`, `payed`) USING BTREE,
  INDEX `order_id_6`(`order_id`, `user_id`, `payed`) USING BTREE,
  CONSTRAINT `user_inf_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_order
-- ----------------------------
INSERT INTO `user_order` VALUES ('ff8081816b3ad2a4016b3ad450b10001', '17709201921', '113.140.147.202', 0.00, '2019-06-09 14:02:01', 1, b'1', NULL);

-- ----------------------------
-- Table structure for user_preorder
-- ----------------------------
DROP TABLE IF EXISTS `user_preorder`;
CREATE TABLE `user_preorder`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_fee` double(10, 2) NOT NULL,
  `address_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `service_id` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `send_method_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_order_user_id_fk`(`user_id`) USING BTREE,
  INDEX `address_id`(`address_id`) USING BTREE,
  INDEX `user_order_payed_fk`(`payed`, `order_id`) USING BTREE,
  INDEX `user_order_abled_fk`(`order_id`, `abled`) USING BTREE,
  INDEX `user_address_id_fk`(`address_id`, `user_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `user_send_method_id`(`send_method_id`, `service_id`) USING BTREE,
  INDEX `id_2`(`id`, `user_id`) USING BTREE,
  INDEX `id_3`(`id`, `abled`) USING BTREE,
  INDEX `payed`(`payed`, `id`) USING BTREE,
  INDEX `user_send_method_id_fk1`(`service_id`, `send_method_id`) USING BTREE,
  INDEX `user_order_fk`(`order_id`, `abled`, `user_id`, `payed`) USING BTREE,
  INDEX `id_4`(`id`, `abled`, `address_id`, `user_id`) USING BTREE,
  INDEX `user_more_product_id_fk`(`product_id`) USING BTREE,
  INDEX `id_5`(`id`, `abled`, `address_id`, `user_id`, `payed`) USING BTREE,
  CONSTRAINT `user_address_id_fk` FOREIGN KEY (`address_id`, `user_id`) REFERENCES `user_address` (`id`, `user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_more_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `user_more_product` (`product_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_order_fk` FOREIGN KEY (`order_id`, `abled`, `user_id`, `payed`) REFERENCES `user_order` (`order_id`, `abled`, `user_id`, `payed`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_send_method_id_fk1` FOREIGN KEY (`service_id`, `send_method_id`) REFERENCES `user_send_method` (`service_id`, `id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_service_id` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_preorder
-- ----------------------------
INSERT INTO `user_preorder` VALUES ('ff8081816b3ad2a4016b3ad450cd0002', 0.00, 'ff8081816b3ad2a4016b3ad3f6d20000', '2019-06-09 14:02:01', 'ff8081816b3ad2a4016b3ad450b10001', '17709201921', 1, 0, 1, b'1', '2', '09201111');

-- ----------------------------
-- Table structure for user_print
-- ----------------------------
DROP TABLE IF EXISTS `user_print`;
CREATE TABLE `user_print`  (
  `preorder_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `callback_url` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_url` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `send_method_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sender_admin_id` int(11) NULL DEFAULT NULL,
  `payed` bit(1) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `page_num` int(5) NOT NULL,
  `page_size_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `page_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mainkey` int(11) NOT NULL,
  PRIMARY KEY (`preorder_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `payed` tinyint(1) NOT NULL DEFAULT 0,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`refund_id`) USING BTREE,
  INDEX `user_order_id_fk4`(`order_id`, `user_id`, `payed`) USING BTREE,
  CONSTRAINT `user_order_id_fk4` FOREIGN KEY (`order_id`, `user_id`, `payed`) REFERENCES `user_order` (`order_id`, `user_id`, `payed`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_send_method
-- ----------------------------
DROP TABLE IF EXISTS `user_send_method`;
CREATE TABLE `user_send_method`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配送方式内容',
  `service_id` int(11) NOT NULL,
  `type_str` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `service_id`) USING BTREE,
  INDEX `user_service_id2`(`service_id`) USING BTREE,
  CONSTRAINT `user_service_id2` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_send_method
-- ----------------------------
INSERT INTO `user_send_method` VALUES ('1', '12:00左右送达', 1, 'dd12', b'0');
INSERT INTO `user_send_method` VALUES ('2', '18:00左右送达', 1, 'dd12', b'1');
INSERT INTO `user_send_method` VALUES ('3', '2小时内送达', 1, 'ds2', b'0');
INSERT INTO `user_send_method` VALUES ('4', '0小时内送达', 9, 'ds0', b'1');

-- ----------------------------
-- Table structure for user_service
-- ----------------------------
DROP TABLE IF EXISTS `user_service`;
CREATE TABLE `user_service`  (
  `id` int(11) NOT NULL,
  `model_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mainkey` int(11) NOT NULL,
  `show` bit(1) NOT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `protrol` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_service
-- ----------------------------
INSERT INTO `user_service` VALUES (1, 'express_proxy', '快递代取', 589, b'1', b'1', '东大一小时外送外包服务条款\r\n版本：0.5\r\n发布/生效日期：2019年5月17日生效\r\n\r\n欢迎您使用东大一小时外送外包服务！\r\n\r\n您在使用东大一小时外送外包服务（下称“本服务”）前，应当仔细阅读本《东大一小时外送外包服务条款》（下称“本条款”）中的全部规则。如果您通过使用微信小程序统一登陆或者网站等其他客户端登陆或使用本服务的，即视为您已经阅读并且自愿接受本条款及服务规则所有内容的约束。所有服务规则视为本条款不可分割的一部分，与本条款具有同等的法律效力。\r\n\r\n本条款的签约方为沈阳市少一小时网络服务部和其下相关平台的实际运营主体（以下简称“少一小时网络”或“东大一小时外送外包服务平台”或“东大一小时”或“小程序”或“我们”），以及使用本服务以及相关服务的实际使用人（以下简称“用户”或“您”），本条款是您与少一小时网络就本服务达成交易所订立的服务条款，具有正式书面合同的效力。\r\n\r\n您理解并同意，少一小时网络可能会不定期的对于本服务条款进行修补，更新。本条款以及之后的版本均符合相关法律法规以及东北大学相关校内规定。\r\n\r\n定义\r\n    1. 外送外包服务\r\n外送外包服务是东大一小时推出的在东北大学校园（现仅支持南湖校区）内方便东大师生的一项有偿低价服务，现在上线的有校内代取快递服务。\r\n    2. 订单\r\n在您每次在本小程序交易时弹出微信支付框时系统会自动采集您的订单信息并且进行自动存档，此时订单状态为未支付状态。当您成功支付完毕后此订单状态会自动变为已支付，此时则默认我们可以正常根据您的订单内容的要求开始为您进行本服务。\r\n3.子订单\r\n在您每次正常下单前，可以选择多项服务作为子订单系统自动生成。当您下单时，多条子订单会被合并成为订单统一支付。每类子订单代表一种外送外包服务。\r\n4.代金券\r\n我们会不定期向用户通过线下或者线上以实物或者电子券的方式发送一种可以按次或者按折扣为用户面单或者打折的优惠卷，这里称代金券。\r\n5.快递代取服务\r\n快递代取服务是东大一小时微信小程序提供的外送外包服务其中的一项，用户可自主在小程序里填写快递暂存点发来的短信或手动输入快递取件码，选择快递暂存点和用户住址等信息，在成功下订单后便可享受快递送上门的服务。\r\n\r\n\r\n    一． 用户账号\r\n\r\n根据微信小程序开发要求，微信正常用户在首次登陆东大一小时需要手动在弹出的要求获取用户信息的选项卡选择同意，并且在首次使用服务时阅读本服务条款并且选择同意。若均选择同意则默认同意东大一小时小程序获取用户信息并且将相关信息进行存档和自动注册处理，此时您便可以作为东大一小时的合法用户与东大一小时进行正常的交易服务活动。当用户第二次及以后开启小程序时便会自动登陆而无需注册便可以正常使用。\r\n\r\n    二． 用户管理\r\n\r\n    1. 您应知悉，在您正常使用东大一小时小程序的本服务时，系统可能采集用户信息以便于您正常使用本服务。您进一步同意，即表示东大一小时小程序可以在一定限度内在第三方平台合理使用您的用户信息，包括但不限于用户账号信息，地址信息，交易信息等。\r\n    2. 您应当在相关法律法规以及《东北大学校规》规定范围内合理合法的使用本小程序，我们有权根据您的违规或不当使用本小程序或者其他方式要求我们提供超出我们能力范围以及相关规定之外的服务，在您下单后驳回您的订单。\r\n    3. 您有权选择订阅或退订本服务为正常通知您消息所发送的短信或者微信公众号推送。\r\n    4. 您不得在东大一小时微信小程序外对任何个人或者团体组织转卖，溢价折价销售本平台提供的任何服务以及有代金券性质的电子物品或实物。\r\n    5. 若您违反本条款，少一小时网络有权采取一切必要的措施，包括但不限于暂停或终止您通过东大一小时账号使用东大一小时外送外包服务，并且终止您的已生效订单，给少一小时网络造成损失的，您应负全部赔偿责任，包括但不限于财产损害赔偿，名誉损害赔偿及诉讼费，律师费，公证费，交通费等因维权而产生的合理费用。少一小时网络有权按照本条款的相关规定对您的行为进行处理。\r\n    6. 根据《中华人民共和国网络安全法》规定，用户不得非法入侵东大一小时微信小程序后台服务器，以及利用软件漏洞非法牟利，或者做其他任何侵犯东大一小时权益，财产以及名誉的事情，若经核实发现，我们将勒令用户将软件或服务器恢复原样，恢复名誉权，赔偿损失，我们将采取一切必要的措施维护东大一小时的权益。\r\n\r\n\r\n三.   	外送外包服务规则\r\n\r\n1.  鉴于校内外送外包服务的特殊性以及针对的对象的特殊性，少一小时网络将根据《中华人民共和国电子商务法》等法律法规的要求，对外送外包服务中所规定的客体进行审慎审核，对于主体所承担的义务和具有的权利进行限定，对于在外送外包服务中所产生的任何服务信息，交易信息在发生错误时及时更正，并且存档三年以上。除法律法规强制性规定外，少一小时网络将尽到核实客体的义务，并不代表少一小时将对客体的真伪性，合法性，安全性提供任何形式的明示或默示的担保，声明和承诺，亦不对因此所导致的您的任何损失承担任何责任，但少一小时网络在其中存在重大过失的除外。如果您在使用东大一小时相关服务时发现相关人员，商品或服务信息违反法律规定，您可及时向我们或者有关机关举报或投诉，少一小时网络将在收到您的举报或投诉后采取相应的核实与处理措施。\r\n2.  您同意少一小时网络有权采取必要的技术手段和管理措施保障东大一小时外送外包服务的正常运行，提供必要，可靠的交易环境和交易服务，维护正常交易秩序。您通过东大一小时小程序交易服务时应遵守本条款以及相关服务规则的规定，不得干扰东大一小时的正常运营，破坏团购秩序，亦不得以任何手段干扰东大一小时的正常运营或干扰其他用户使用东大一小时服务。\r\n3.  您同意，您通过东大一小时所进行的任何交易应出于真实的消费目的，不得以转售等商业目的进行团购交易。\r\n4.  您知悉并同意，东大一小时仅为信息发布平台，并非用户购买的具体商品/服务的生产者，销售者。\r\n5.  退款，赔偿规则\r\n	5.1  快递代取赔偿\r\n		5.1.1  鉴于东大一小时平台快递代取服务特殊性，少一小时网络对任意正常使用东大一小时的用户提供一定价值范围内的快递损坏，丢失采取全额，部分赔付的规定。对超时送达的快递对用户造成损失的，经过友好协商后按照用户的意愿赔付。\r\n		5.1.2  由于非人为自然原因导致的快递丢失，损坏的，东大一小时不为其提供赔付服务。由于用户自己的不当操作等过失导致的快递丢失，损坏的，东大一小时不为其提供赔付服务。\r\n		5.1.3  东大一小时平台将设专门人员接收用户的赔付请求并且管理和调查用户的快递在运送过程中的损坏，丢失。用户如果申请赔付请求，需要用户向相关工作人员提供可以核实快递件价格或者确定快递损坏事实的截图，订单号，运单号以及实物等真实的证据信息。若用户的快递损坏并非快递代取工作人员在工作时的疏忽或不当操作，我们将拒绝退款或者进行赔付工作。若用户向赔付工作的工作人员提供虚假信息，企图获得不正当赔付，经核实确认的，我们将拒绝退款或者进行赔付工作，并且将该用户拉入少一小时网络服务诚信黑名单，且拒绝用户再次使用少一小时网络服务产品。对于用户已获得的不正当赔付款，经核实确认的，我们将采取友好协商第一，法律诉讼第二的原则对该用户进行告知。\r\n		5.1.4  对于快递实际物品价值（不含税，快递费）小于十元人民币的用户，我们为其提供全额赔偿并且退还代取快递服务费用。\r\n		5.1.5  对于快递实际物品价值（不含税，快递费）小于一百元且不小于的老快递代取月卡会员用户，我们为其提供实际物品价值15-40%的赔付，普通用户提供5-20%的赔付。\r\n		5.1.6  对于快递实际物品价值（不含税，快递费）小于五百元且不小于一百元的老快递代取月卡会员的用户，我们为其提供实际物品价值 5-15%的赔付，普通用户提供3-10%的赔付。\r\n	5.2  快递代取退款\r\n		5.2.1  凡是在东大一小时小程序下正常注册及使用的用户，均有权对已支付的款项进行退款申请操作，经相关工作人员核实通过后方可退款，退款实际到达用户微信账户的时间以微信处理退款的时间为准。\r\n5.2.2  东大一小时有相关的退款工作人员处理用户的退款工作，我们将驳回所有正常服务完毕的退款。凡是用户提交的理由符合依据的，我们都将从速通过退款请求。\r\n\r\n四.  知识产权及其他权利\r\n\r\n1.  少一小时网络及东大一小时所展示的各运营系统均由少一小时网络自主开发，运营提供技术支持，并对东大一小时外送外包服务的开发和运营过程中产生的所有数据和信息等享有全部权利。东大一小时提供各项服务时所依托软件著作权，专利权，所使用的各项商标，商业形象，商业标识，技术诀窍，其著作权与商标权及其他各项相关权利均归少一小时网络所有。\r\n\r\n五.  争议解决\r\n1.  如您在本平台所购买的商品/服务所产生任何问题而引起的争议，您应与我们根据本协议内容的约定确定各自的权利义务，承担各自责任，解决争议。\r\n2.  若您因本条款内容或其执行发生任何其他争议，各方应尽力友好协商解决；协商不成时，任何一方均可向本条款签订地有管辖权的人民法院提起诉讼。\r\n3.  因您使用东大一小时外送外包服务而引起或与之相关的一切争议，权利主张或其他事项，均使用中华人民共和国大陆地区法律，并且排除一切冲突法规定的适用。');
INSERT INTO `user_service` VALUES (9, 'express_month_card', '快递代取月卡购买', 1064, b'0', b'1', '东大一小时外送外包服务条款\r\n版本：0.5\r\n发布/生效日期：2019年5月17日生效\r\n\r\n欢迎您使用东大一小时外送外包服务！\r\n\r\n您在使用东大一小时外送外包服务（下称“本服务”）前，应当仔细阅读本《东大一小时外送外包服务条款》（下称“本条款”）中的全部规则。如果您通过使用微信小程序统一登陆或者网站等其他客户端登陆或使用本服务的，即视为您已经阅读并且自愿接受本条款及服务规则所有内容的约束。所有服务规则视为本条款不可分割的一部分，与本条款具有同等的法律效力。\r\n\r\n本条款的签约方为沈阳市少一小时网络服务部和其下相关平台的实际运营主体（以下简称“少一小时网络”或“东大一小时外送外包服务平台”或“东大一小时”或“小程序”或“我们”），以及使用本服务以及相关服务的实际使用人（以下简称“用户”或“您”），本条款是您与少一小时网络就本服务达成交易所订立的服务条款，具有正式书面合同的效力。\r\n\r\n您理解并同意，少一小时网络可能会不定期的对于本服务条款进行修补，更新。本条款以及之后的版本均符合相关法律法规以及东北大学相关校内规定。\r\n\r\n定义\r\n    1. 外送外包服务\r\n外送外包服务是东大一小时推出的在东北大学校园（现仅支持南湖校区）内方便东大师生的一项有偿低价服务，现在上线的有校内代取快递服务。\r\n    2. 订单\r\n在您每次在本小程序交易时弹出微信支付框时系统会自动采集您的订单信息并且进行自动存档，此时订单状态为未支付状态。当您成功支付完毕后此订单状态会自动变为已支付，此时则默认我们可以正常根据您的订单内容的要求开始为您进行本服务。\r\n3.子订单\r\n在您每次正常下单前，可以选择多项服务作为子订单系统自动生成。当您下单时，多条子订单会被合并成为订单统一支付。每类子订单代表一种外送外包服务。\r\n4.代金券\r\n我们会不定期向用户通过线下或者线上以实物或者电子券的方式发送一种可以按次或者按折扣为用户面单或者打折的优惠卷，这里称代金券。\r\n5.快递代取服务\r\n快递代取服务是东大一小时微信小程序提供的外送外包服务其中的一项，用户可自主在小程序里填写快递暂存点发来的短信或手动输入快递取件码，选择快递暂存点和用户住址等信息，在成功下订单后便可享受快递送上门的服务。\r\n\r\n\r\n    一． 用户账号\r\n\r\n根据微信小程序开发要求，微信正常用户在首次登陆东大一小时需要手动在弹出的要求获取用户信息的选项卡选择同意，并且在首次使用服务时阅读本服务条款并且选择同意。若均选择同意则默认同意东大一小时小程序获取用户信息并且将相关信息进行存档和自动注册处理，此时您便可以作为东大一小时的合法用户与东大一小时进行正常的交易服务活动。当用户第二次及以后开启小程序时便会自动登陆而无需注册便可以正常使用。\r\n\r\n    二． 用户管理\r\n\r\n    1. 您应知悉，在您正常使用东大一小时小程序的本服务时，系统可能采集用户信息以便于您正常使用本服务。您进一步同意，即表示东大一小时小程序可以在一定限度内在第三方平台合理使用您的用户信息，包括但不限于用户账号信息，地址信息，交易信息等。\r\n    2. 您应当在相关法律法规以及《东北大学校规》规定范围内合理合法的使用本小程序，我们有权根据您的违规或不当使用本小程序或者其他方式要求我们提供超出我们能力范围以及相关规定之外的服务，在您下单后驳回您的订单。\r\n    3. 您有权选择订阅或退订本服务为正常通知您消息所发送的短信或者微信公众号推送。\r\n    4. 您不得在东大一小时微信小程序外对任何个人或者团体组织转卖，溢价折价销售本平台提供的任何服务以及有代金券性质的电子物品或实物。\r\n    5. 若您违反本条款，少一小时网络有权采取一切必要的措施，包括但不限于暂停或终止您通过东大一小时账号使用东大一小时外送外包服务，并且终止您的已生效订单，给少一小时网络造成损失的，您应负全部赔偿责任，包括但不限于财产损害赔偿，名誉损害赔偿及诉讼费，律师费，公证费，交通费等因维权而产生的合理费用。少一小时网络有权按照本条款的相关规定对您的行为进行处理。\r\n    6. 根据《中华人民共和国网络安全法》规定，用户不得非法入侵东大一小时微信小程序后台服务器，以及利用软件漏洞非法牟利，或者做其他任何侵犯东大一小时权益，财产以及名誉的事情，若经核实发现，我们将勒令用户将软件或服务器恢复原样，恢复名誉权，赔偿损失，我们将采取一切必要的措施维护东大一小时的权益。\r\n\r\n\r\n三.   	外送外包服务规则\r\n\r\n1.  鉴于校内外送外包服务的特殊性以及针对的对象的特殊性，少一小时网络将根据《中华人民共和国电子商务法》等法律法规的要求，对外送外包服务中所规定的客体进行审慎审核，对于主体所承担的义务和具有的权利进行限定，对于在外送外包服务中所产生的任何服务信息，交易信息在发生错误时及时更正，并且存档三年以上。除法律法规强制性规定外，少一小时网络将尽到核实客体的义务，并不代表少一小时将对客体的真伪性，合法性，安全性提供任何形式的明示或默示的担保，声明和承诺，亦不对因此所导致的您的任何损失承担任何责任，但少一小时网络在其中存在重大过失的除外。如果您在使用东大一小时相关服务时发现相关人员，商品或服务信息违反法律规定，您可及时向我们或者有关机关举报或投诉，少一小时网络将在收到您的举报或投诉后采取相应的核实与处理措施。\r\n2.  您同意少一小时网络有权采取必要的技术手段和管理措施保障东大一小时外送外包服务的正常运行，提供必要，可靠的交易环境和交易服务，维护正常交易秩序。您通过东大一小时小程序交易服务时应遵守本条款以及相关服务规则的规定，不得干扰东大一小时的正常运营，破坏团购秩序，亦不得以任何手段干扰东大一小时的正常运营或干扰其他用户使用东大一小时服务。\r\n3.  您同意，您通过东大一小时所进行的任何交易应出于真实的消费目的，不得以转售等商业目的进行团购交易。\r\n4.  您知悉并同意，东大一小时仅为信息发布平台，并非用户购买的具体商品/服务的生产者，销售者。\r\n5.  退款，赔偿规则\r\n	5.1  快递代取赔偿\r\n		5.1.1  鉴于东大一小时平台快递代取服务特殊性，少一小时网络对任意正常使用东大一小时的用户提供一定价值范围内的快递损坏，丢失采取全额，部分赔付的规定。对超时送达的快递对用户造成损失的，经过友好协商后按照用户的意愿赔付。\r\n		5.1.2  由于非人为自然原因导致的快递丢失，损坏的，东大一小时不为其提供赔付服务。由于用户自己的不当操作等过失导致的快递丢失，损坏的，东大一小时不为其提供赔付服务。\r\n		5.1.3  东大一小时平台将设专门人员接收用户的赔付请求并且管理和调查用户的快递在运送过程中的损坏，丢失。用户如果申请赔付请求，需要用户向相关工作人员提供可以核实快递件价格或者确定快递损坏事实的截图，订单号，运单号以及实物等真实的证据信息。若用户的快递损坏并非快递代取工作人员在工作时的疏忽或不当操作，我们将拒绝退款或者进行赔付工作。若用户向赔付工作的工作人员提供虚假信息，企图获得不正当赔付，经核实确认的，我们将拒绝退款或者进行赔付工作，并且将该用户拉入少一小时网络服务诚信黑名单，且拒绝用户再次使用少一小时网络服务产品。对于用户已获得的不正当赔付款，经核实确认的，我们将采取友好协商第一，法律诉讼第二的原则对该用户进行告知。\r\n		5.1.4  对于快递实际物品价值（不含税，快递费）小于十元人民币的用户，我们为其提供全额赔偿并且退还代取快递服务费用。\r\n		5.1.5  对于快递实际物品价值（不含税，快递费）小于一百元且不小于的老快递代取月卡会员用户，我们为其提供实际物品价值15-40%的赔付，普通用户提供5-20%的赔付。\r\n		5.1.6  对于快递实际物品价值（不含税，快递费）小于五百元且不小于一百元的老快递代取月卡会员的用户，我们为其提供实际物品价值 5-15%的赔付，普通用户提供3-10%的赔付。\r\n	5.2  快递代取退款\r\n		5.2.1  凡是在东大一小时小程序下正常注册及使用的用户，均有权对已支付的款项进行退款申请操作，经相关工作人员核实通过后方可退款，退款实际到达用户微信账户的时间以微信处理退款的时间为准。\r\n5.2.2  东大一小时有相关的退款工作人员处理用户的退款工作，我们将驳回所有正常服务完毕的退款。凡是用户提交的理由符合依据的，我们都将从速通过退款请求。\r\n\r\n四.  知识产权及其他权利\r\n\r\n1.  少一小时网络及东大一小时所展示的各运营系统均由少一小时网络自主开发，运营提供技术支持，并对东大一小时外送外包服务的开发和运营过程中产生的所有数据和信息等享有全部权利。东大一小时提供各项服务时所依托软件著作权，专利权，所使用的各项商标，商业形象，商业标识，技术诀窍，其著作权与商标权及其他各项相关权利均归少一小时网络所有。\r\n\r\n五.  争议解决\r\n1.  如您在本平台所购买的商品/服务所产生任何问题而引起的争议，您应与我们根据本协议内容的约定确定各自的权利义务，承担各自责任，解决争议。\r\n2.  若您因本条款内容或其执行发生任何其他争议，各方应尽力友好协商解决；协商不成时，任何一方均可向本条款签订地有管辖权的人民法院提起诉讼。\r\n3.  因您使用东大一小时外送外包服务而引起或与之相关的一切争议，权利主张或其他事项，均使用中华人民共和国大陆地区法律，并且排除一切冲突法规定的适用。');

-- ----------------------------
-- Table structure for user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `user_voucher`;
CREATE TABLE `user_voucher`  (
  `card_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abled` bit(1) NOT NULL,
  `used` bit(1) NOT NULL DEFAULT b'1',
  `check_time` datetime(0) NULL DEFAULT NULL,
  `service_id` int(11) NOT NULL DEFAULT 0,
  `user_main_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`card_id`) USING BTREE,
  INDEX `user_voucher_tyoe_abled`(`type_id`, `abled`) USING BTREE,
  INDEX `user_voucher_type_id_fk`(`type_id`, `service_id`) USING BTREE,
  INDEX `user_id_fk3`(`user_main_id`) USING BTREE,
  CONSTRAINT `user_main_id_fk6` FOREIGN KEY (`user_main_id`) REFERENCES `user_inf` (`main_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `user_voucher_tyoe_abled` FOREIGN KEY (`type_id`, `abled`) REFERENCES `user_voucher_type` (`type_id`, `abled`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_voucher_type_id_fk` FOREIGN KEY (`type_id`, `service_id`) REFERENCES `user_voucher_type` (`type_id`, `service_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_voucher
-- ----------------------------
INSERT INTO `user_voucher` VALUES ('ff8081816b3ac89c016b3acdca6c0001', '123123', '快递代取劵', b'1', b'1', '2019-06-09 13:54:54', 1, 'ff8081816b3ac89c016b3acdca510000');
INSERT INTO `user_voucher` VALUES ('ff8081816b3ac89c016b3acdca700002', '123123', '快递代取劵', b'1', b'0', '2019-06-09 13:54:54', 1, 'ff8081816b3ac89c016b3acdca510000');
INSERT INTO `user_voucher` VALUES ('ff8081816b3add9d016b3ae581170001', '123123', '快递代取劵', b'1', b'0', '2019-06-09 14:20:48', 1, 'ff8081816b3add9d016b3ae580f20000');
INSERT INTO `user_voucher` VALUES ('ff8081816b3add9d016b3ae5811c0002', '123123', '快递代取劵', b'1', b'0', '2019-06-09 14:20:48', 1, 'ff8081816b3add9d016b3ae580f20000');

-- ----------------------------
-- Table structure for user_voucher_type
-- ----------------------------
DROP TABLE IF EXISTS `user_voucher_type`;
CREATE TABLE `user_voucher_type`  (
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `abled` bit(1) NOT NULL,
  `remain` int(10) NOT NULL,
  `service_id` int(11) NOT NULL,
  `mainkey` int(11) NOT NULL,
  PRIMARY KEY (`mainkey`) USING BTREE,
  INDEX `type_id`(`type_id`, `abled`) USING BTREE,
  INDEX `service_fk3`(`service_id`) USING BTREE,
  INDEX `type_id_2`(`type_id`) USING BTREE,
  INDEX `type_id_3`(`type_id`, `service_id`) USING BTREE,
  CONSTRAINT `service_fk3` FOREIGN KEY (`service_id`) REFERENCES `user_service` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_voucher_type
-- ----------------------------
INSERT INTO `user_voucher_type` VALUES ('123123', '快递代取免单劵', '2019-07-31 16:18:59.000000', b'1', 0, 1, 0);
INSERT INTO `user_voucher_type` VALUES ('124125', '订餐代金卷', '2019-06-29 12:59:42.000000', b'1', 0, 9, 1);
INSERT INTO `user_voucher_type` VALUES ('124127', '任意代金卷', '2019-06-06 13:03:06.000000', b'1', 0, 9, 2);

-- ----------------------------
-- Table structure for voucher_group
-- ----------------------------
DROP TABLE IF EXISTS `voucher_group`;
CREATE TABLE `voucher_group`  (
  `type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `check_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wexin_token
-- ----------------------------
DROP TABLE IF EXISTS `wexin_token`;
CREATE TABLE `wexin_token`  (
  `access_token` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `appid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `mainkey` int(10) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wexin_token
-- ----------------------------
INSERT INTO `wexin_token` VALUES ('22_0e29unbD9wxaDnzfSSQyj0E3gW1o0IwNB2bww8MA1RQPWuaA5iwptAkOLdk5274zZaBeeHh9gy-h8eF70T0p43xjz4Wpq4da_rxSr0JNTK91whBzd-1PyJ4g7VekABvax3G-dKkTSTrTyQ2sRYUeAIAUBX', 'wx30bce2f4251d660a', '2019-06-09 14:09:03.249000', 1221);
INSERT INTO `wexin_token` VALUES ('22_QWTrwZymG-A8tYm6n8xpF_W2laXtjk-BxQLg2cg7q5rrkcilZGCqcwdcYq9feavKTFdpkOdnnsFPSBf4pNEgiGrVrG195v0QBErH4UAPf35p2Wh45K50u3eZa4JecdP_4WOu20jLW4igXizKALReAGAJUA', 'wxad0e430bbecf97ef', '2019-06-09 15:24:53.325000', 1219);

SET FOREIGN_KEY_CHECKS = 1;
