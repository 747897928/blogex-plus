/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : blogex

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 16/05/2022 21:27:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `snippet` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '摘要',
  `classify_id` int unsigned NOT NULL COMMENT '文章分类id',
  `classify_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名，冗余字段，用于加快检索效率',
  `review_img_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章预览图',
  `post_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '发表类型,原创(0),转载(1),翻译(2)',
  `content_type` tinyint unsigned NOT NULL COMMENT '文章内容类型HTML(0),MARKDOWN(1)',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `article_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态，0->发布,1->草稿，默认是发布',
  `comment_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否开启评论,开启评论并自动处理(0)不开启评论(1)',
  `view_count` int unsigned NOT NULL DEFAULT '0' COMMENT '阅览数',
  `support_count` int unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `sum_comment` int unsigned NOT NULL DEFAULT '0' COMMENT '评论数，冗余字段，用于加快检索效率',
  `priority` int unsigned NOT NULL DEFAULT '100' COMMENT '优先级用于排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` bigint unsigned NOT NULL DEFAULT '0' COMMENT '版本号，乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=401 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for t_article_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_map`;
CREATE TABLE `t_article_tag_map` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int unsigned NOT NULL COMMENT '文章id',
  `tag_id` int unsigned NOT NULL COMMENT '标签id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `version` bigint unsigned NOT NULL DEFAULT '0' COMMENT '版本号，乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_article_tag` (`article_id`,`tag_id`) USING BTREE COMMENT '文章id和标签id联合唯一'
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for t_blog_link
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_link`;
CREATE TABLE `t_blog_link` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `link_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '友联名',
  `link_href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '友联url',
  `link_avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '友联头像url',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for t_blog_music
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_music`;
CREATE TABLE `t_blog_music` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `music_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音乐名',
  `music_artist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专辑作者',
  `music_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音乐播放链接',
  `music_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专辑图片链接',
  `music_lrc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '歌词lrc',
  `priority` int unsigned NOT NULL DEFAULT '100' COMMENT '优先级用于排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for t_blog_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_setting`;
CREATE TABLE `t_blog_setting` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `web_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'web网站的域名',
  `mail_enable` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否开启邮件任务，0开启，1不开启',
  `blog_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '博客域名（入口前缀）用于大部分领域，比如拼接邮件回复链接',
  `blog_mail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '博主邮箱，用于接受备份文件的邮箱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_blog_setting
-- ----------------------------
BEGIN;
INSERT INTO `t_blog_setting` VALUES (1, 'http://localhost:20010', 0, 'http://localhost:20010/uploads/blogex-ui', 'xxxxx@qq.com', '2022-05-15 12:33:26', '2022-05-15 21:23:58');
COMMIT;

-- ----------------------------
-- Table structure for t_blogger_info
-- ----------------------------
DROP TABLE IF EXISTS `t_blogger_info`;
CREATE TABLE `t_blogger_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像url',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '个性签名',
  `github_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '博主github或者gitee的主页',
  `Record_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '(备案号)',
  `blogger_detail` text COLLATE utf8mb4_general_ci NOT NULL COMMENT '博客的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_blogger_info
-- ----------------------------
BEGIN;
INSERT INTO `t_blogger_info` VALUES (1, '樱满集', 'https://xxxx/example.jpg', '生活一场持续的搏斗目标是内心的安宁毫无畏惧的给予和接受爱的能力', 'https://gitee.com/', '@xxx|桂ICP备xxxx号', '一只人畜无害的博主', '2022-03-20 17:17:39', '2022-05-10 23:05:46');
COMMIT;

-- ----------------------------
-- Table structure for t_classify
-- ----------------------------
DROP TABLE IF EXISTS `t_classify`;
CREATE TABLE `t_classify` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `classify_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `version` bigint unsigned NOT NULL DEFAULT '0' COMMENT '版本号，乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_classify_name` (`classify_name`) COMMENT '分类名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int unsigned DEFAULT NULL COMMENT '文章id',
  `user_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `user_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户的邮箱',
  `user_avatar_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像url',
  `comment_content` text COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `parent_id` int unsigned DEFAULT NULL COMMENT '父评论id',
  `parent_tier_id` int unsigned DEFAULT NULL COMMENT '评论的父层级id(上层级的评论id，引入这个字段的目的是为了让层级最多为2)',
  `user_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ip',
  `user_os` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户os',
  `user_agent` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户浏览器标识',
  `browser_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户的浏览器名',
  `page_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1836 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for t_live2d
-- ----------------------------
DROP TABLE IF EXISTS `t_live2d`;
CREATE TABLE `t_live2d` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '模型id',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模型名',
  `model_json_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '模型json路径',
  `model_image_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '模型预览图片路径',
  `background_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型背景图片图片路径',
  `x` double NOT NULL DEFAULT '0' COMMENT 'x',
  `y` double NOT NULL DEFAULT '0' COMMENT 'y',
  `scale` double unsigned NOT NULL DEFAULT '1' COMMENT '模型缩放',
  `width` int unsigned NOT NULL DEFAULT '250' COMMENT '模型宽',
  `height` int unsigned NOT NULL DEFAULT '250' COMMENT '模型高',
  `anchorx` double NOT NULL DEFAULT '0' COMMENT '锚点，以画布中心下方为中心点x（单位：倍）',
  `anchory` double NOT NULL DEFAULT '0' COMMENT '锚点，以画布中心下方为中心点y（单位：倍）',
  `base_file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模型在文件系统的路径，用于下载和删除的时候找到这个路径',
  `show_mark` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否在前台展示的标记，0不展示，1展示，用于下架模型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `version` bigint unsigned NOT NULL DEFAULT '0' COMMENT '版本号，乐观锁',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_tag_name` (`tag_name`) COMMENT '标签名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
