/*
Navicat MySQL Data Transfer

Source Server         : localhost-db
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : taobao_renqi

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-09-11 21:22:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户主键表',
  `customer_state_id` int(11) NOT NULL COMMENT '客户总的状态ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `pass_word` varchar(50) NOT NULL COMMENT '登录密码',
  `token` varchar(255) NOT NULL COMMENT '用户token',
  `expire_time` varchar(20) NOT NULL COMMENT 'token过期时间',
  `level_id` int(11) NOT NULL DEFAULT '1' COMMENT '用户级别(1 普通会员,2 白银会员, 3 黄金会员, 4钻石会员)',
  `shop_id` bigint(20) NOT NULL COMMENT '商铺ID',
  `login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '0', '18589072284', '123456', 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiMSIsImlhdCI6MTUzNjU3MTE1NSwiaXNzIjoicmVucWkiLCJleHAiOjE1MzY1NzEyMTV9.rum2Y8marUzmpiYxlTv6oOc-rS3tJGseW-USt1-djOc', '1536571215448', '1', '1', '2018-09-10 17:19:15', '2018-09-08 19:15:48', '2018-09-10 17:19:15');

-- ----------------------------
-- Table structure for customer_level
-- ----------------------------
DROP TABLE IF EXISTS `customer_level`;
CREATE TABLE `customer_level` (
  `level_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员等级表',
  `level_name` varchar(25) NOT NULL COMMENT '级别名称',
  `level_desc` varchar(25) DEFAULT NULL COMMENT '会员级别说明( 1 普通会员,2 白银会员, 3 黄金会员, 4钻石会员)',
  `expiried_time` datetime NOT NULL COMMENT '会员过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员级别表';

-- ----------------------------
-- Records of customer_level
-- ----------------------------

-- ----------------------------
-- Table structure for customer_money
-- ----------------------------
DROP TABLE IF EXISTS `customer_money`;
CREATE TABLE `customer_money` (
  `amount_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户账户充值详细表',
  `customer_id` int(11) NOT NULL COMMENT '客户主键ID',
  `amount_money` varchar(255) NOT NULL COMMENT '充值金额',
  `recharge_state` int(2) NOT NULL COMMENT '充值状态',
  `level_id` int(11) NOT NULL COMMENT '会员级别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`amount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_money
-- ----------------------------

-- ----------------------------
-- Table structure for customer_shop
-- ----------------------------
DROP TABLE IF EXISTS `customer_shop`;
CREATE TABLE `customer_shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商铺信息',
  `shop_name` varchar(255) DEFAULT '' COMMENT '商铺名称',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `shop_no` varchar(100) DEFAULT '' COMMENT '商铺号或（商铺ID）',
  `credit_score` int(10) DEFAULT NULL COMMENT '信用分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户的商铺信息表';

-- ----------------------------
-- Records of customer_shop
-- ----------------------------

-- ----------------------------
-- Table structure for customer_state
-- ----------------------------
DROP TABLE IF EXISTS `customer_state`;
CREATE TABLE `customer_state` (
  `customer_state_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户流程控制',
  `total_task` int(11) NOT NULL DEFAULT '100' COMMENT '可发布的任务总数(各种类型流量的总和)',
  `total_money` double(20,2) NOT NULL COMMENT '用户充值的总金额',
  `is_recharge` int(2) NOT NULL DEFAULT '0' COMMENT '是否充值(0 未充值, 1 充值)',
  `expired_time` varchar(20) NOT NULL COMMENT '会员失效期',
  PRIMARY KEY (`customer_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_state
-- ----------------------------

-- ----------------------------
-- Table structure for customer_task
-- ----------------------------
DROP TABLE IF EXISTS `customer_task`;
CREATE TABLE `customer_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务主表',
  `task_type` varchar(25) NOT NULL COMMENT '任务类型(PC端,手机端)',
  `task_state` varchar(4) NOT NULL COMMENT '任务的状态(0 未执行，1 进行中 ， 2 已完成， 3 异常)',
  `good_id` int(11) NOT NULL COMMENT '宝贝的主键',
  `good_link_url` varchar(255) NOT NULL COMMENT '宝贝链接',
  `good_title` varchar(255) NOT NULL COMMENT '宝贝(商品)名称',
  `store_name` varchar(255) NOT NULL COMMENT '店铺旺旺名称',
  `total_visitor` int(11) NOT NULL COMMENT '总的访客数',
  `total_number` int(11) NOT NULL COMMENT '总的展现数',
  `task_time` varchar(255) NOT NULL COMMENT '任务投放时间',
  `task_begin_hour` varchar(2) NOT NULL COMMENT '任务执行开始时段',
  `task_end_hour` varchar(2) NOT NULL COMMENT '任务执行结束时段',
  `task_begin_miunte` varchar(20) NOT NULL COMMENT '执行任务起始分钟',
  `task_end_miunte` varchar(20) NOT NULL COMMENT '执行任务结束分钟',
  `task_search_scope` varchar(255) NOT NULL COMMENT '搜索范围',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_task
-- ----------------------------

-- ----------------------------
-- Table structure for task_good
-- ----------------------------
DROP TABLE IF EXISTS `task_good`;
CREATE TABLE `task_good` (
  `good_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品主键ID',
  `task_id` int(11) NOT NULL COMMENT '任务发布主键ID',
  `good_no` varchar(255) NOT NULL COMMENT '宝贝ID',
  `good_title` varchar(255) NOT NULL COMMENT '宝贝标题名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_good
-- ----------------------------

-- ----------------------------
-- Table structure for task_requirement
-- ----------------------------
DROP TABLE IF EXISTS `task_requirement`;
CREATE TABLE `task_requirement` (
  `require_rel_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `tempalte_id` int(11) NOT NULL,
  `template_type` varchar(50) NOT NULL COMMENT '任务发布需要的模板类型(PC端,手机端等等)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`require_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_requirement
-- ----------------------------

-- ----------------------------
-- Table structure for task_special
-- ----------------------------
DROP TABLE IF EXISTS `task_special`;
CREATE TABLE `task_special` (
  `task_special_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_search_plat` varchar(255) NOT NULL COMMENT '搜索平台',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`task_special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_special
-- ----------------------------

-- ----------------------------
-- Table structure for task_template
-- ----------------------------
DROP TABLE IF EXISTS `task_template`;
CREATE TABLE `task_template` (
  `template_id` int(11) NOT NULL,
  `task_begin_browse` varchar(20) NOT NULL COMMENT '浏览时长起始秒',
  `task_end_browse` varchar(20) NOT NULL COMMENT '浏览时长结束秒',
  `task_browse_second` varchar(255) NOT NULL COMMENT '浏览时长秒',
  `require_name` varchar(255) NOT NULL COMMENT '流量任务需求名称',
  `task_type` varchar(255) NOT NULL COMMENT '流量类型(手机端，PC端等等)',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_template
-- ----------------------------
INSERT INTO `task_template` VALUES ('1', '80', '120', '80', '浏览商品详情页', 'mobile');
INSERT INTO `task_template` VALUES ('2', '130', '170', '50', '货比三家', 'mobile');
INSERT INTO `task_template` VALUES ('3', '190', '230', '60', '浏览商品评价', 'mobile');
INSERT INTO `task_template` VALUES ('4', '260', '300', '70', '浏览关联商品', 'mobile');
INSERT INTO `task_template` VALUES ('5', '50', '80', '80', '浏览商品详情页', 'pc');
INSERT INTO `task_template` VALUES ('6', '100', '130', '50', '货比三家', 'pc');
INSERT INTO `task_template` VALUES ('7', '160', '190', '60', '浏览商品评价', 'pc');
INSERT INTO `task_template` VALUES ('8', '230', '260', '70', '浏览关联商品', 'pc');

-- ----------------------------
-- Table structure for task_word
-- ----------------------------
DROP TABLE IF EXISTS `task_word`;
CREATE TABLE `task_word` (
  `word_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务关键词表',
  `task_id` int(11) NOT NULL,
  `word_name` varchar(25) NOT NULL COMMENT '关键词名词',
  `task_visitor` int(11) NOT NULL COMMENT '关键词访客数',
  `show_number` int(11) NOT NULL COMMENT '关键词访问展现数',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_word
-- ----------------------------
