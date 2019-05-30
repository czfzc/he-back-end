/*
 Navicat Premium Data Transfer

 Source Server         : hour
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : hour

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 29/05/2019 23:54:57
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
INSERT INTO `admin_inf` VALUES ('17709201921', '71266a572d22ecc98e7b4b4e35fba105', 'czf', '8eca6a12f6cd010af380d4754e12a124', 123456, 105, b'1', '', '2019-05-29 21:55:36');

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
INSERT INTO `hibernate_sequence` VALUES (1000460);

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
  `addition` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
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
INSERT INTO `user_address` VALUES ('ff8081816ade8470016adeafbf7a000c', '17709201921', '曹子帆', '17709201921', '446', '123', '放心', b'0', b'0');
INSERT INTO `user_address` VALUES ('ff8081816adec5d2016adec817c10000', '17709201921', '操一二', '46469794', '378', '123', '哈哈', b'0', b'0');
INSERT INTO `user_address` VALUES ('ff8081816ae8ebbb016ae9b10ff90000', '17709201921', '曹子帆', '17709201921', '446', '123', '', b'1', b'1');
INSERT INTO `user_address` VALUES ('ff8081816af1b89a016af1bf0a080004', '17709201921', '赵逼博', '17678464949', '544', 'ff8081816aedd0c7016aedd34fa60000', '没有啦', b'1', b'0');
INSERT INTO `user_address` VALUES ('ff8081816af1b89a016af1ce11160014', '15714086571', '赵', '15714089875', '456', '123', '', b'1', b'0');
INSERT INTO `user_address` VALUES ('ff8081816af1b89a016af1ce27160015', '15714086571', '赵', '15714089875', '456', '123', '', b'1', b'1');
INSERT INTO `user_address` VALUES ('ff8081816afe78e7016afebb456d000f', '17379858863', 'fdsf', 'jkljkj', '802', '123', 'undefined', b'1', b'0');
INSERT INTO `user_address` VALUES ('ff8081816afe78e7016afece47450011', '17804142688', '李沛霖', '17804142688', '510', 'ff8081816aedd0c7016aedd34fa60000', 'nmsl', b'1', b'1');
INSERT INTO `user_address` VALUES ('ff8081816b00e95a016b00fb33a1000f', '17379858863', '程子丘', '17379858863', '101', '123', '红红火火恍恍惚惚', b'1', b'1');
INSERT INTO `user_address` VALUES ('ff8081816b01101c016b01192c10000d', '17172220333', '周楷', '17172220333', '434', 'ff8081816aedd0c7016aedd34fa60000', '', b'1', b'1');

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
INSERT INTO `user_dest_building` VALUES ('123', '逸夫楼', '[1,5]', b'1');
INSERT INTO `user_dest_building` VALUES ('124', '何事礼', '[1,4]', b'1');
INSERT INTO `user_dest_building` VALUES ('125', '大成', '[2,3]', b'1');
INSERT INTO `user_dest_building` VALUES ('ff8081816aedd0c7016aedd34fa60000', '三舍C区', '[2,9]', b'1');

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
INSERT INTO `user_express` VALUES ('ff8081816afe2adb016afe2ed2fe0002', 'ff8081816afe2adb016afe2ed2ee0001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 19:24:03', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe2adb016afe2f60e70005', 'ff8081816afe2adb016afe2f60dd0004', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 19:24:39', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 0);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe849cf90002', 'ff8081816afe78e7016afe849cdf0001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 20:57:45', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe8534480005', 'ff8081816afe78e7016afe8534300004', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 20:58:24', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe85346d0006', 'ff8081816afe78e7016afe8534300004', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 20:58:24', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe872f27000b', 'ff8081816afe78e7016afe872f0a000a', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 21:00:33', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe872f37000c', 'ff8081816afe78e7016afe872f0a000a', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 21:00:33', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe872f5a000d', 'ff8081816afe78e7016afe872f0a000a', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 21:00:33', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '0', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afe872f68000e', 'ff8081816afe78e7016afe872f0a000a', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-28 21:00:34', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afecf09730014', 'ff8081816afe78e7016afecf09640013', 'ff8081816afe78e7016afece47450011', '李沛霖', '17804142688', 0.01, '【京东物流】货号7859您好，京东，您的快递到东北大学京东派了（小北门西30米），营业时间早9:00-晚6点，电话13309816129', '7859', '17804142688', '2019-05-28 22:19:02', 0, b'1', '221111', '1', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816afe78e7016afed27bdf001a', 'ff8081816afe78e7016afed27bd00019', 'ff8081816afe78e7016afece47450011', '李沛霖', '17804142688', 0.00, '【京东物流】货号7859您好，京东，您的快递到东北大学京东派了（小北门西30米），营业时间早9:00-晚6点，电话13309816129', '7859', '17804142688', '2019-05-28 22:22:48', 0, b'1', '221111', '0', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816b00e95a016b00f3f0610006', 'ff8081816b00e95a016b00f3f04b0005', 'ff8081816afe78e7016afebb456d000f', 'fdsf', 'jkljkj', 0.01, '', '啦啦啦', '17379858863', '2019-05-29 08:18:35', 0, b'1', '111122', '1', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816b013d95016b0224d2700002', 'ff8081816b013d95016b0224d2690001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-29 13:51:36', 2, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b013d95016b0224d2730003', 'ff8081816b013d95016b0224d2690001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子龙', '17709201922', 0.01, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-29 13:51:36', 0, b'1', 'ff8081816aeda51f016aeda74dcb0000', '0', '2', NULL, 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b0228954a0002', 'ff8081816b022807016b0228953d0001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-29 13:55:43', 2, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b022895530003', 'ff8081816b022807016b0228953d0001', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '【妈妈驿站】取货码96-014，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', '96-014', '17709201921', '2019-05-29 13:55:43', 2, b'1', 'ff8081816aeda51f016aeda74dcb0000', '2', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b035614cc0006', 'ff8081816b022807016b035614c30005', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '', '23432', '17709201921', '2019-05-29 19:25:02', 2, b'1', '111122', '0', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b035615020007', 'ff8081816b022807016b035614c30005', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '', '43243', '17709201921', '2019-05-29 19:25:02', 2, b'1', '111122', '1', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b035615050008', 'ff8081816b022807016b035614c30005', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.00, '', '324432', '17709201921', '2019-05-29 19:25:02', 2, b'1', '111122', '1', '2', '17709201921', 1);
INSERT INTO `user_express` VALUES ('ff8081816b022807016b035615080009', 'ff8081816b022807016b035614c30005', 'ff8081816ae8ebbb016ae9b10ff90000', '曹子帆', '17709201921', 0.01, '', '234', '17709201921', '2019-05-29 19:25:02', 1, b'1', '111122', '1', '2', '17709201921', 1);

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
-- Records of user_express_card
-- ----------------------------
INSERT INTO `user_express_card` VALUES ('ff8081816af1b89a016af1cfe8e8001d', '15714086571', '2019-06-02 09:44:56', 1, '09201921', b'1', 0);
INSERT INTO `user_express_card` VALUES ('ff8081816afe78e7016afecfc37e0017', '17804142688', '2019-06-27 22:19:50', 1, '09201921', b'1', 20);
INSERT INTO `user_express_card` VALUES ('ff8081816b00e95a016b00f1a8880003', '17379858863', '2019-06-28 08:16:06', 1, '09201921', b'1', 20);
INSERT INTO `user_express_card` VALUES ('ff8081816b01101c016b0114e729000a', '17172220333', '2019-06-28 08:54:36', 1, '09201921', b'1', 20);
INSERT INTO `user_express_card` VALUES ('ff8081816b013d95016b02254ec50006', '17709201921', '2019-06-28 23:20:41', 1, '09201921', b'1', 20);

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
INSERT INTO `user_express_point` VALUES ('112211', '[1,2]', '菜鸟驿站', '【菜鸟驿站】您的百世快递包裹到东大校内七舍北侧菜鸟驿站，请17:30前凭1-1-6200及时取，询19904053317\r\n', 'n-n-nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('221111', '[2,2]', '京东物流', '【京东物流】货号5916您好，您的快递到东北大学京东派（小北门西30米），营业时间早9:00-晚6点，电话13309816129\r\n', 'nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('ff8081816aed55c8016aeda1fbc20000', '[2,5]', '顺丰速运', '您好！我是顺丰快递员，因由于，您所在地址无法提供派件服务，特请您移步至东北大学小北门菊记超市右转顺丰快递，货号4410，烦请18:30前携带有效证件取件，如您现在不方便取件请及时与我联系，联系电话18540218053，感谢您使用顺丰快递！【顺丰速运】', 'nnnn', b'1');
INSERT INTO `user_express_point` VALUES ('ff8081816aeda51f016aeda74dcb0000', '[3,5]', '妈妈驿站', '【妈妈驿站】取货码96-013，您有圆通快递包裹，已到东大八舍B区东面圆通妈妈驿站，营业时间08:00-19:00', 'nn-nnn', b'1');

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
INSERT INTO `user_express_price` VALUES (1457, '123', '111122', 0.17, '1', '3');
INSERT INTO `user_express_price` VALUES (1458, '124', '221111', 0.35, '1', '2');
INSERT INTO `user_express_price` VALUES (1456, '125', '112211', 0.19, '0', '3');
INSERT INTO `user_express_price` VALUES (1000458, '124', '111122', 0.01, '1', '2');
INSERT INTO `user_express_price` VALUES (1000459, '124', '112211', 0.07, '2', '3');

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
INSERT INTO `user_express_size` VALUES ('0', '小', b'0');
INSERT INTO `user_express_size` VALUES ('1', '中', b'0');
INSERT INTO `user_express_size` VALUES ('2', '大（500g以上）', b'0');

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf`  (
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `session_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mysession` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abled` bit(1) NOT NULL DEFAULT b'1',
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  `main_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `voucher_geted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`open_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `open_id`(`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES ('17804142688', 'owdWH5A3c4lzVSmPIevEnoZjNpgU', 'uT2WeERq7kITUdqrmWnohg==', 'b49bb9db36353b45a8366a214524e361', b'1', '2019-05-28 22:16:49', 'ff8081816afe78e7016afecc91d80010', b'0');
INSERT INTO `user_inf` VALUES ('18804028235', 'owdWH5AjnV-xY9-_xkq98ElhGyBs', 'p/aP2uK3J4omFDCdpNgMIg==', '74fcadfe330612f7a781e14cf20a6e19', b'1', '2019-05-24 12:33:35', 'ff8081816ae81c76016ae81d63290000', b'0');
INSERT INTO `user_inf` VALUES ('17709201921', 'owdWH5DUOjonvHzlVHwP_sdJvj1c', 'pnoYzF7gAySbz2vE19m24w==', '345e664b98d07d80ba828817252bc0b5', b'1', '2019-05-22 16:36:01', 'ff8081816ade8470016adeaec59e000b', b'1');
INSERT INTO `user_inf` VALUES ('17172220333', 'owdWH5FlfVZPDRkOiyEbillNNtEI', '35mCNm/6Wokws4AXvOavbg==', '25d35d6efcb66014534923c5a3db6085', b'1', '2019-05-29 08:38:53', 'ff8081816b00e95a016b0106736d0015', b'0');
INSERT INTO `user_inf` VALUES ('17379858863', 'owdWH5FlY7RZVE6-NAJ7uNJla5K8', '7eVQDCkgazQaZQj/4S7jow==', '6749a114a06850b7e76b0f20a85913f0', b'1', '2019-05-24 15:40:04', 'ff8081816ae8b01f016ae8c836e10004', b'0');
INSERT INTO `user_inf` VALUES ('15714086571', 'owdWH5JvaW4H08bji9quKv3JXHPA', 'Ha6Xly8X53vzzd/7MMiUew==', 'b1bae02a2a1d3e418a89b437917c8cb7', b'1', '2019-05-24 13:35:05', 'ff8081816ae822d3016ae855d73a0020', b'0');

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
  `ip` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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
INSERT INTO `user_order` VALUES ('ff8081816ae86d9b016ae8a733720000', '17709201921', '106.13.141.127', 0.01, '2019-05-24 15:03:53', 2, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816ae8b01f016ae8b816ee0000', '17709201921', '106.13.141.127', 0.01, '2019-05-24 15:22:20', 4, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816ae8ebbb016ae9b244200001', '17709201921', '106.13.141.127', 0.01, '2019-05-24 19:55:35', 2, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aedd0c7016aedd6766d0001', '17709201921', '106.13.141.127', 0.01, '2019-05-25 15:13:36', 1, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb148016aefb1c4f20000', '17709201921', '106.13.141.127', 0.01, '2019-05-25 23:52:46', 1, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb148016aefb3d9a10003', '17709201921', '106.13.141.127', 0.01, '2019-05-25 23:55:02', 1, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb4c7016aefb4e1ba0000', '17709201921', '106.13.141.127', 0.01, '2019-05-25 23:56:10', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb4c7016aefb570640002', '17709201921', '106.13.141.127', 0.01, '2019-05-25 23:56:47', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb4c7016aefb727200004', '17709201921', '106.13.141.127', 0.01, '2019-05-25 23:58:39', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb873016aefb934dd0000', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:00:53', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb873016aefb951950002', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:01:01', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb873016aefb95a2c0004', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:01:03', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefb873016aefbc020d0006', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:03:57', 0, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefe126016aefe13d610000', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:44:37', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816aefe126016aefe1a1380002', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:45:03', 1, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefe126016aefe36a030004', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:47:00', 1, b'1', '');
INSERT INTO `user_order` VALUES ('ff8081816aefe5e3016aefe5ff150000', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:49:49', 1, b'1', 'wx260049495633015ca381028f6737271500');
INSERT INTO `user_order` VALUES ('ff8081816aefe5e3016aefe5ff4f0002', '17709201921', '106.13.141.127', 0.01, '2019-05-26 00:49:49', 1, b'1', 'wx260049495594873ee3abd0a85105556900');
INSERT INTO `user_order` VALUES ('ff8081816aeff38f016aeff7c1100000', '17709201921', '106.13.141.127', 0.01, '2019-05-26 01:09:13', 1, b'1', 'wx26010913522108cdc200013b4741412400');
INSERT INTO `user_order` VALUES ('ff8081816af0052e016af00592ba0000', '17709201921', '106.13.141.127', 0.01, '2019-05-26 01:24:18', 1, b'1', 'wx260124190894113dfc1dcccc5135377200');
INSERT INTO `user_order` VALUES ('ff8081816af0052e016af00867010002', '17709201921', '106.13.141.127', 0.01, '2019-05-26 01:27:24', 1, b'1', 'wx260127243476443fbe9f246f6633371900');
INSERT INTO `user_order` VALUES ('ff8081816af0052e016af009bf590004', '17709201921', '106.13.141.127', 0.00, '2019-05-26 01:28:52', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af00cc0016af00d3f640000', '17709201921', '106.13.141.127', 0.00, '2019-05-26 01:32:41', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af00cc0016af00e755b0003', '17709201921', '106.13.141.127', 0.01, '2019-05-26 01:34:01', 1, b'1', 'wx26013401358718b67c600fa92321068900');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1bd99190000', '17709201921', '106.13.141.127', 0.02, '2019-05-26 09:24:56', 1, b'1', 'wx260924563556488c4068acee9898058700');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1bfafed0005', '17709201921', '106.13.141.127', 0.01, '2019-05-26 09:27:13', 1, b'1', 'wx26092713111268c3e79e74dc9741251500');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cb75200008', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:04', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cb84540009', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:08', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cb8cb0000a', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:10', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cba988000b', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:17', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cbafeb000c', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:19', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cc0786000d', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:41', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cc11e4000e', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:44', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cc4890000f', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:40:58', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cc4de50010', '17709201921', '106.13.141.127', 0.01, '2019-05-26 09:40:59', 1, b'1', 'wx260941000699002778e632c41832002500');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cc9d1d0012', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:41:20', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cca7e20013', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:41:23', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1ce8f1a0016', '15714086571', '106.13.141.127', 0.01, '2019-05-26 09:43:27', 1, b'1', 'wx260943277418179e602f89fd8320511100');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cf64610019', '15714086571', '106.13.141.127', 0.01, '2019-05-26 09:44:22', 0, b'1', 'wx26094422340808b3430fa3800151779000');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1cfa44c001b', '15714086571', '106.13.141.127', 0.01, '2019-05-26 09:44:38', 1, b'1', 'wx260944387010347ffbb4eda29065548200');
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1d07a4b001e', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:45:33', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af1b89a016af1d0d25f0021', '15714086571', '106.13.141.127', 0.00, '2019-05-26 09:45:56', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4e68b430000', '17709201921', '127.0.1.1', 0.00, '2019-05-27 00:08:31', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4e718010002', '17709201921', '127.0.1.1', 0.03, '2019-05-27 00:09:07', 0, b'1', 'wx27000907935661ab439f72fe2053796900');
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4e7a67c0007', '17709201921', '127.0.1.1', 0.03, '2019-05-27 00:09:43', 0, b'1', 'wx27000944032530304efda4ba2054925500');
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4e8cac4000c', '17709201921', '127.0.1.1', 0.00, '2019-05-27 00:10:58', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4e999de000f', '17709201921', '127.0.1.1', 0.02, '2019-05-27 00:11:51', 0, b'1', 'wx270011518942961c9b60196a8997572400');
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4eab05b0013', '17709201921', '127.0.1.1', 0.02, '2019-05-27 00:13:02', 0, b'1', 'wx27001303110110002093cb4d2681237100');
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4ebff600017', '17709201921', '127.0.1.1', 0.02, '2019-05-27 00:14:28', 0, b'1', 'wx270014289497651a9d1ff36b1531623900');
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4ed1e72001b', '17709201921', '127.0.1.1', 0.00, '2019-05-27 00:15:42', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af4def0016af4edfd68001f', '17709201921', '127.0.1.1', 0.01, '2019-05-27 00:16:39', 0, b'1', 'wx270016400728467a3d6d26a19069954200');
INSERT INTO `user_order` VALUES ('ff8081816af5045e016af8a109e00007', '17709201921', '127.0.1.1', 0.01, '2019-05-27 17:31:05', 1, b'1', 'wx27173105014688cfa8fb5eba6429693600');
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af9525fa70000', '17379858863', '127.0.1.1', 0.00, '2019-05-27 20:44:46', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af958cae90001', '17709201921', '127.0.1.1', 0.01, '2019-05-27 20:51:47', 1, b'1', 'wx272051481072972542e88f163803535400');
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af95915810003', '17379858863', '127.0.1.1', 0.00, '2019-05-27 20:52:06', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af959171d0004', '17379858863', '127.0.1.1', 0.00, '2019-05-27 20:52:07', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af9591b640005', '17379858863', '127.0.1.1', 0.00, '2019-05-27 20:52:08', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af95a04a90006', '17709201921', '127.0.1.1', 0.01, '2019-05-27 20:53:07', 1, b'1', 'wx27205308379301f12f1048d96444296000');
INSERT INTO `user_order` VALUES ('ff8081816af9412e016af9848b7f0008', '17709201921', '127.0.1.1', 0.00, '2019-05-27 21:39:34', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afd9484016afd9539b30000', '17709201921', '127.0.1.1', 0.00, '2019-05-28 16:36:16', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afd9484016afd9749a10003', '17709201921', '127.0.1.1', 0.00, '2019-05-28 16:38:32', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afd9484016afd9bc1610006', '17709201921', '127.0.1.1', 0.00, '2019-05-28 16:43:24', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afd9484016afdae17b30009', '17709201921', '127.0.1.1', 0.00, '2019-05-28 17:03:26', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afd9484016afdae86cb000c', '17709201921', '127.0.1.1', 0.00, '2019-05-28 17:03:55', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afdb1d0016afdb22f5a0000', '17709201921', '127.0.1.1', 0.00, '2019-05-28 17:07:54', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afdb708016afdb7b0fa0000', '17709201921', '127.0.1.1', 0.01, '2019-05-28 17:13:55', 1, b'1', 'wx2817135598941881b6ae41539075062000');
INSERT INTO `user_order` VALUES ('ff8081816afdb708016afdb861530003', '17709201921', '127.0.1.1', 0.01, '2019-05-28 17:14:40', 0, b'1', 'wx28171440771192568b6c7c151636744900');
INSERT INTO `user_order` VALUES ('ff8081816afe2adb016afe2ed2d70000', '17709201921', '127.0.1.1', 0.01, '2019-05-28 19:24:03', 1, b'1', 'wx28192403308881fe28c438863844843300');
INSERT INTO `user_order` VALUES ('ff8081816afe2adb016afe2f60d30003', '17709201921', '127.0.1.1', 0.01, '2019-05-28 19:24:39', 0, b'1', 'wx28192439591776326c6f46d56935564800');
INSERT INTO `user_order` VALUES ('ff8081816afe3df2016afe3e42bb0000', '17709201921', '127.0.1.1', 0.01, '2019-05-28 19:40:54', 1, b'1', 'wx28194054991729d8f2191ad73815904800');
INSERT INTO `user_order` VALUES ('ff8081816afe3df2016afe401c4f0002', '17709201921', '127.0.1.1', 0.01, '2019-05-28 19:42:56', 1, b'1', 'wx281942561608820023965e6c2405386100');
INSERT INTO `user_order` VALUES ('ff8081816afe3df2016afe40aabc0005', '17709201921', '127.0.1.1', 0.06, '2019-05-28 19:43:32', 1, b'1', 'wx28194332607265034995d7163198269500');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afe849ccc0000', '17709201921', '127.0.1.1', 0.00, '2019-05-28 20:57:45', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afe8534260003', '17709201921', '127.0.1.1', 0.01, '2019-05-28 20:58:24', 1, b'1', 'wx28205824128982b2651d29975392465600');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afe8577500007', '17709201921', '127.0.1.1', 0.01, '2019-05-28 20:58:41', 1, b'1', 'wx282058412091716e7dadef408898294500');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afe872efe0009', '17709201921', '127.0.1.1', 0.01, '2019-05-28 21:00:33', 1, b'1', 'wx28210033823694c3c84607309616383600');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afecf09510012', '17804142688', '127.0.1.1', 0.01, '2019-05-28 22:19:02', 1, b'1', 'wx282219028796507390b9d56f6827332400');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afecfa87b0015', '17804142688', '127.0.1.1', 0.06, '2019-05-28 22:19:43', 1, b'1', 'wx282219434948450833a394960675882600');
INSERT INTO `user_order` VALUES ('ff8081816afe78e7016afed27bca0018', '17804142688', '127.0.1.1', 0.00, '2019-05-28 22:22:48', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b00f18a110001', '17379858863', '127.0.1.1', 0.06, '2019-05-29 08:15:58', 1, b'1', 'wx290815592158380b4906e9116910523700');
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b00f3f03d0004', '17379858863', '127.0.1.1', 0.01, '2019-05-29 08:18:35', 1, b'1', 'wx29081836418421b61f0261c08209883300');
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b00f9cadb000d', '17709201921', '127.0.1.1', 0.01, '2019-05-29 08:24:59', 1, b'1', 'wx2908245925811770f4214f997104749600');
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b0102b2030011', '17709201921', '127.0.1.1', 0.01, '2019-05-29 08:34:42', 1, b'1', 'wx29083442771047e13145fca32689900700');
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b01069acf0016', '17172220333', '127.0.1.1', 0.00, '2019-05-29 08:38:59', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b010784020017', '17709201921', '127.0.1.1', 0.06, '2019-05-29 08:39:58', 0, b'1', 'wx2908395861280742ca942d699835833200');
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b010c69360019', '17172220333', '127.0.1.1', 0.00, '2019-05-29 08:45:19', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816b00e95a016b010cab5f001a', '17172220333', '127.0.1.1', 0.00, '2019-05-29 08:45:36', 0, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b0110940b0000', '17172220333', '127.0.1.1', 0.06, '2019-05-29 08:49:52', 0, b'1', 'wx29084952937608b72b9014bb8548788700');
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b011192f10002', '17172220333', '127.0.1.1', 0.06, '2019-05-29 08:50:57', 0, b'1', 'wx29085057818842db1f1a5a6a3951939800');
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b01131b8b0004', '17172220333', '127.0.1.1', 0.06, '2019-05-29 08:52:38', 0, b'1', 'wx29085238338507fb8f7680761112637400');
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b0114999f0006', '17172220333', '127.0.1.1', 0.06, '2019-05-29 08:54:16', 0, b'1', 'wx290854161792733d510979da6531085600');
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b0114cd320008', '17172220333', '127.0.1.1', 0.06, '2019-05-29 08:54:29', 1, b'1', 'wx290854293372732a334a6d659894829000');
INSERT INTO `user_order` VALUES ('ff8081816b01101c016b011872ee000b', '17172220333', '127.0.1.1', 0.01, '2019-05-29 08:58:28', 0, b'1', 'wx29085828358068f798dc43320615787200');
INSERT INTO `user_order` VALUES ('ff8081816b013d95016b0224d2510000', '17709201921', '127.0.1.1', 0.02, '2019-05-29 13:51:36', 1, b'1', 'wx29135137437202a82658a2dd0104289900');
INSERT INTO `user_order` VALUES ('ff8081816b013d95016b022537780004', '17709201921', '127.0.1.1', 0.06, '2019-05-29 13:52:02', 1, b'1', 'wx291352023334220a3f6a62782356741100');
INSERT INTO `user_order` VALUES ('ff8081816b022807016b022895250000', '17709201921', '127.0.1.1', 0.00, '2019-05-29 13:55:42', 1, b'1', NULL);
INSERT INTO `user_order` VALUES ('ff8081816b022807016b035614c00004', '17709201921', '127.0.1.1', 0.01, '2019-05-29 19:25:02', 1, b'1', 'wx291925038571809bf53f45731071476600');
INSERT INTO `user_order` VALUES ('ff8081816b022807016b042db7e9000a', '17709201921', '127.0.1.1', 0.01, '2019-05-29 23:20:34', 1, b'1', 'wx29232035203917f7d9efd8363128281200');

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
INSERT INTO `user_preorder` VALUES ('ff8081816ae86d9b016ae8a733920001', 0.01, 'ff8081816adec5d2016adec817c10000', '2019-05-24 15:03:53', 'ff8081816ae86d9b016ae8a733720000', '17709201921', 1, 0, 2, b'1', '2', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816ae8b01f016ae8b817050001', 0.01, 'ff8081816adec5d2016adec817c10000', '2019-05-24 15:22:20', 'ff8081816ae8b01f016ae8b816ee0000', '17709201921', 1, 0, 4, b'1', '2', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816ae8ebbb016ae9b244290002', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-24 19:55:35', 'ff8081816ae8ebbb016ae9b244200001', '17709201921', 1, 0, 2, b'1', '3', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aedd0c7016aedd6767d0002', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-25 15:13:36', 'ff8081816aedd0c7016aedd6766d0001', '17709201921', 1, 0, 1, b'1', '2', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb148016aefb1c5060001', 0.01, NULL, '2019-05-25 23:52:46', 'ff8081816aefb148016aefb1c4f20000', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb148016aefb3d9c00004', 0.01, NULL, '2019-05-25 23:55:02', 'ff8081816aefb148016aefb3d9a10003', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb4c7016aefb4e1ef0001', 0.01, NULL, '2019-05-25 23:56:10', 'ff8081816aefb4c7016aefb4e1ba0000', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb4c7016aefb5707e0003', 0.01, NULL, '2019-05-25 23:56:47', 'ff8081816aefb4c7016aefb570640002', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb4c7016aefb7273c0005', 0.01, NULL, '2019-05-25 23:58:39', 'ff8081816aefb4c7016aefb727200004', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb873016aefb935060001', 0.01, NULL, '2019-05-26 00:00:54', 'ff8081816aefb873016aefb934dd0000', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb873016aefb951aa0003', 0.01, NULL, '2019-05-26 00:01:01', 'ff8081816aefb873016aefb951950002', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb873016aefb95a370005', 0.01, NULL, '2019-05-26 00:01:03', 'ff8081816aefb873016aefb95a2c0004', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefb873016aefbc02290007', 0.01, NULL, '2019-05-26 00:03:57', 'ff8081816aefb873016aefbc020d0006', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefe126016aefe13d960001', 0.01, NULL, '2019-05-26 00:44:37', 'ff8081816aefe126016aefe13d610000', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefe126016aefe1a14f0003', 0.01, NULL, '2019-05-26 00:45:03', 'ff8081816aefe126016aefe1a1380002', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefe126016aefe36a1d0005', 0.01, NULL, '2019-05-26 00:47:00', 'ff8081816aefe126016aefe36a030004', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefe5e3016aefe5ff4b0001', 0.01, NULL, '2019-05-26 00:49:49', 'ff8081816aefe5e3016aefe5ff150000', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aefe5e3016aefe5ff600003', 0.01, NULL, '2019-05-26 00:49:49', 'ff8081816aefe5e3016aefe5ff4f0002', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816aeff38f016aeff7c1280001', 0.01, NULL, '2019-05-26 01:09:13', 'ff8081816aeff38f016aeff7c1100000', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af0052e016af00592cf0001', 0.01, NULL, '2019-05-26 01:24:18', 'ff8081816af0052e016af00592ba0000', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af0052e016af008671b0003', 0.01, NULL, '2019-05-26 01:27:24', 'ff8081816af0052e016af00867010002', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af0052e016af009bf680005', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-26 01:28:52', 'ff8081816af0052e016af009bf590004', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af00cc0016af00d3f850001', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-26 01:32:41', 'ff8081816af00cc0016af00d3f640000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af00cc0016af00e75690004', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-26 01:34:01', 'ff8081816af00cc0016af00e755b0003', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1bd99240001', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-26 09:24:56', 'ff8081816af1b89a016af1bd99190000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1bfb0030006', 0.01, NULL, '2019-05-26 09:27:13', 'ff8081816af1b89a016af1bfafed0005', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1cc4df50011', 0.01, NULL, '2019-05-26 09:41:00', 'ff8081816af1b89a016af1cc4de50010', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1ce8f260017', 0.01, 'ff8081816af1b89a016af1ce27160015', '2019-05-26 09:43:27', 'ff8081816af1b89a016af1ce8f1a0016', '15714086571', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1cf6472001a', 0.01, NULL, '2019-05-26 09:44:22', 'ff8081816af1b89a016af1cf64610019', '15714086571', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1cfa45d001c', 0.01, NULL, '2019-05-26 09:44:38', 'ff8081816af1b89a016af1cfa44c001b', '15714086571', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1d07a57001f', 0.00, 'ff8081816af1b89a016af1ce27160015', '2019-05-26 09:45:33', 'ff8081816af1b89a016af1d07a4b001e', '15714086571', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af1b89a016af1d0d2650022', 0.00, 'ff8081816af1b89a016af1ce27160015', '2019-05-26 09:45:56', 'ff8081816af1b89a016af1d0d25f0021', '15714086571', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e7180a0003', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:09:07', 'ff8081816af4def0016af4e718010002', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e718460006', 0.01, NULL, '2019-05-27 00:09:07', 'ff8081816af4def0016af4e718010002', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e7a68b0008', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:09:43', 'ff8081816af4def0016af4e7a67c0007', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e7a6d0000b', 0.01, NULL, '2019-05-27 00:09:43', 'ff8081816af4def0016af4e7a67c0007', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e8cacd000d', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:10:58', 'ff8081816af4def0016af4e8cac4000c', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4e999e70010', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:11:51', 'ff8081816af4def0016af4e999de000f', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4eab0630014', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:13:02', 'ff8081816af4def0016af4eab05b0013', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4ebff6a0018', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:14:28', 'ff8081816af4def0016af4ebff600017', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4ed1e79001c', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:15:42', 'ff8081816af4def0016af4ed1e72001b', '17709201921', 1, 0, 1, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af4def0016af4edfd700020', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 00:16:39', 'ff8081816af4def0016af4edfd68001f', '17709201921', 1, 0, 0, b'1', '1', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816af5045e016af8a109fc0008', 0.01, NULL, '2019-05-27 17:31:05', 'ff8081816af5045e016af8a109e00007', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af9412e016af958caf80002', 0.01, NULL, '2019-05-27 20:51:47', 'ff8081816af9412e016af958cae90001', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af9412e016af95a04c00007', 0.01, NULL, '2019-05-27 20:53:07', 'ff8081816af9412e016af95a04a90006', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816af9412e016af9848b8c0009', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-27 21:39:34', 'ff8081816af9412e016af9848b7f0008', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afd9484016afd9539c80001', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 16:36:16', 'ff8081816afd9484016afd9539b30000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afd9484016afd9749ae0004', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 16:38:32', 'ff8081816afd9484016afd9749a10003', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afd9484016afd9bc16c0007', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 16:43:24', 'ff8081816afd9484016afd9bc1610006', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afd9484016afdae17bb000a', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 17:03:26', 'ff8081816afd9484016afdae17b30009', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afd9484016afdae86db000d', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 17:03:55', 'ff8081816afd9484016afdae86cb000c', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afdb1d0016afdb22f7f0001', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 17:07:54', 'ff8081816afdb1d0016afdb22f5a0000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afdb708016afdb7b1140001', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 17:13:55', 'ff8081816afdb708016afdb7b0fa0000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afdb708016afdb8615b0004', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 17:14:40', 'ff8081816afdb708016afdb861530003', '17709201921', 1, 0, 0, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe2adb016afe2ed2ee0001', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 19:24:03', 'ff8081816afe2adb016afe2ed2d70000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe2adb016afe2f60dd0004', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 19:24:39', 'ff8081816afe2adb016afe2f60d30003', '17709201921', 1, 0, 0, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe3df2016afe3e42d30001', 0.01, NULL, '2019-05-28 19:40:54', 'ff8081816afe3df2016afe3e42bb0000', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816afe3df2016afe401c640003', 0.01, NULL, '2019-05-28 19:42:56', 'ff8081816afe3df2016afe401c4f0002', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816afe3df2016afe40aac90006', 0.06, NULL, '2019-05-28 19:43:32', 'ff8081816afe3df2016afe40aabc0005', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afe849cdf0001', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 20:57:45', 'ff8081816afe78e7016afe849ccc0000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afe8534300004', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 20:58:24', 'ff8081816afe78e7016afe8534260003', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afe8577690008', 0.01, NULL, '2019-05-28 20:58:41', 'ff8081816afe78e7016afe8577500007', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afe872f0a000a', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-28 21:00:33', 'ff8081816afe78e7016afe872efe0009', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afecf09640013', 0.01, 'ff8081816afe78e7016afece47450011', '2019-05-28 22:19:02', 'ff8081816afe78e7016afecf09510012', '17804142688', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afecfa88e0016', 0.06, NULL, '2019-05-28 22:19:43', 'ff8081816afe78e7016afecfa87b0015', '17804142688', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816afe78e7016afed27bd00019', 0.00, 'ff8081816afe78e7016afece47450011', '2019-05-28 22:22:48', 'ff8081816afe78e7016afed27bca0018', '17804142688', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816b00e95a016b00f18a200002', 0.06, NULL, '2019-05-29 08:15:58', 'ff8081816b00e95a016b00f18a110001', '17379858863', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b00e95a016b00f3f04b0005', 0.01, 'ff8081816afe78e7016afebb456d000f', '2019-05-29 08:18:35', 'ff8081816b00e95a016b00f3f03d0004', '17379858863', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816b00e95a016b00f9caed000e', 0.01, NULL, '2019-05-29 08:24:59', 'ff8081816b00e95a016b00f9cadb000d', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b00e95a016b0102b2120012', 0.01, NULL, '2019-05-29 08:34:42', 'ff8081816b00e95a016b0102b2030011', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b00e95a016b010784120018', 0.06, NULL, '2019-05-29 08:39:58', 'ff8081816b00e95a016b010784020017', '17709201921', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b0110944b0001', 0.06, NULL, '2019-05-29 08:49:52', 'ff8081816b01101c016b0110940b0000', '17172220333', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b011192fe0003', 0.06, NULL, '2019-05-29 08:50:57', 'ff8081816b01101c016b011192f10002', '17172220333', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b01131bb00005', 0.06, NULL, '2019-05-29 08:52:38', 'ff8081816b01101c016b01131b8b0004', '17172220333', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b011499b80007', 0.06, NULL, '2019-05-29 08:54:16', 'ff8081816b01101c016b0114999f0006', '17172220333', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b0114cd4a0009', 0.06, NULL, '2019-05-29 08:54:29', 'ff8081816b01101c016b0114cd320008', '17172220333', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b01101c016b011872fa000c', 0.01, NULL, '2019-05-29 08:58:28', 'ff8081816b01101c016b011872ee000b', '17172220333', 9, 0, 0, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b013d95016b0224d2690001', 0.02, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-29 13:51:36', 'ff8081816b013d95016b0224d2510000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816b013d95016b022537890005', 0.06, NULL, '2019-05-29 13:52:02', 'ff8081816b013d95016b022537780004', '17709201921', 9, 0, 1, b'1', '4', '09201921');
INSERT INTO `user_preorder` VALUES ('ff8081816b022807016b0228953d0001', 0.00, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-29 13:55:43', 'ff8081816b022807016b022895250000', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816b022807016b035614c30005', 0.01, 'ff8081816ae8ebbb016ae9b10ff90000', '2019-05-29 19:25:02', 'ff8081816b022807016b035614c00004', '17709201921', 1, 0, 1, b'1', '2', '09201111');
INSERT INTO `user_preorder` VALUES ('ff8081816b022807016b042db7fa000b', 0.01, NULL, '2019-05-29 23:20:34', 'ff8081816b022807016b042db7e9000a', '17709201921', 9, 0, 1, b'1', '4', '09201921');

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
-- Records of user_refund
-- ----------------------------
INSERT INTO `user_refund` VALUES ('ff8081816ae86d9b016ae8a7a96f0003', 'ff8081816ae86d9b016ae8a733720000', '17709201921', '2019-05-24 15:04:23', '6ad003be6805bfe479e3f5e769d921e4', 2, b'1');
INSERT INTO `user_refund` VALUES ('ff8081816ae8b01f016ae8b856200003', 'ff8081816ae8b01f016ae8b816ee0000', '17709201921', '2019-05-24 15:22:36', 'e7bc7d8578a3d95ee07af91079406ec5', 4, b'1');
INSERT INTO `user_refund` VALUES ('ff8081816ae8ebbb016ae9b2c7bf0004', 'ff8081816ae8ebbb016ae9b244200001', '17709201921', '2019-05-24 19:56:09', 'c0187234ca7478f97f064e76573299d5', 2, b'1');
INSERT INTO `user_refund` VALUES ('ff8081816b00e95a016b00f5918d0007', 'ff8081816b00e95a016b00f3f03d0004', '17379858863', '2019-05-29 08:20:22', '3812983161a91c056c4d0b9911da73af', 1, b'1');

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
  `check_user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`card_id`) USING BTREE,
  INDEX `user_voucher_tyoe_abled`(`type_id`, `abled`) USING BTREE,
  INDEX `user_id_fk2`(`check_user_id`) USING BTREE,
  INDEX `user_voucher_type_id_fk`(`type_id`, `service_id`) USING BTREE,
  CONSTRAINT `user_id_fk2` FOREIGN KEY (`check_user_id`) REFERENCES `user_inf` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `user_voucher_tyoe_abled` FOREIGN KEY (`type_id`, `abled`) REFERENCES `user_voucher_type` (`type_id`, `abled`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_voucher_type_id_fk` FOREIGN KEY (`type_id`, `service_id`) REFERENCES `user_voucher_type` (`type_id`, `service_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_voucher
-- ----------------------------
INSERT INTO `user_voucher` VALUES ('144100', '123123', '快递代取劵', b'1', b'1', NULL, '17804142688', 1);
INSERT INTO `user_voucher` VALUES ('144101', '123123', '快递代取劵', b'1', b'1', NULL, '17709201921', 1);
INSERT INTO `user_voucher` VALUES ('144102', '124125', '订餐卷', b'1', b'0', NULL, NULL, 9);
INSERT INTO `user_voucher` VALUES ('144155', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('144156', '123123', '快递代取劵', b'1', b'1', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('144157', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('144158', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('144159', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('144166', '124125', '订餐卷', b'1', b'0', NULL, NULL, 9);
INSERT INTO `user_voucher` VALUES ('144167', '124125', '订餐卷', b'1', b'0', NULL, NULL, 9);
INSERT INTO `user_voucher` VALUES ('ff8081816af5045e016af50517670001', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('ff8081816af5045e016af505176f0002', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);
INSERT INTO `user_voucher` VALUES ('ff8081816af5045e016af50517770003', '123123', '快递代取劵', b'1', b'0', NULL, NULL, 1);

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

SET FOREIGN_KEY_CHECKS = 1;
