/*
 Navicat Premium Data Transfer

 Source Server         : 实验室服务器
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 10.176.24.51:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 16/04/2022 11:22:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品描述',
  `goods_price` decimal(10, 2) NULL COMMENT '商品价格',
  `goods_stock` int(11) NULL DEFAULT 0 COMMENT '商品库存,-1表示没有限制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES (1, 'IPHONE12', 'IPHONE 12 64GB', '/img/iphone12.png', 'IPHONE12 64GB', 6299.00, 100);
INSERT INTO `t_goods` VALUES (2, 'IPHONE12 PRO', 'IPHONE12 PRO 128GB', '/img/iphone12pro.png', 'IPHONE12 PRO 128GB', 9299.00, 100);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) NULL DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名字',
  `goods_count` int(20) NULL DEFAULT 0 COMMENT '商品数量',
  `goods_price` decimal(10, 2) NULL COMMENT '商品价格',
  `order_channel` tinyint(4) NULL DEFAULT 0 COMMENT '1 pc,2 android, 3 ios',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退货，5已完成',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (12, 18899059686, 1, 0, 'IPHONE12', 1, 629.00, 1, 0, '2022-02-26 19:38:51', NULL);
INSERT INTO `t_order` VALUES (13, 18899059686, 1, 0, 'IPHONE12', 1, 629.00, 1, 0, '2022-02-27 16:36:00', NULL);

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀家',
  `stock_count` int(10) NOT NULL COMMENT '库存数量',
  `start_date` datetime(0) NOT NULL COMMENT '秒杀开始时间',
  `end_date` datetime(0) NOT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES (1, 1, 629.00, 9, '2022-02-27 12:00:00', '2022-02-27 20:00:00');
INSERT INTO `t_seckill_goods` VALUES (2, 2, 929.00, 10, '2020-11-01 00:00:00', '2020-11-01 09:00:00');

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------
INSERT INTO `t_seckill_order` VALUES (4, 18899059686, 13, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户ID shoujihaoma',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MD5二次加密',
  `slat` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `head` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `register_date` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int(11) NULL DEFAULT 0 COMMENT '登录次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (18899059686, 'admin', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
