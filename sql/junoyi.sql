/*
 Navicat Premium Dump SQL

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80404 (8.4.4)
 Source Host           : localhost:3306
 Source Schema         : junoyi

 Target Server Type    : MySQL
 Target Server Version : 80404 (8.4.4)
 File Encoding         : 65001

 Date: 28/12/2025 17:37:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统部门权限表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID ( 0 表示顶级）',
  `name` varchar(100) DEFAULT NULL COMMENT '路由名称',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件路径',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单标题（支持 i18n key)',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` tinyint DEFAULT NULL COMMENT '菜单类型( 0目录 1菜单 2按钮)',
  `sort` int DEFAULT NULL COMMENT '排序号',
  `is_hide` tinyint DEFAULT '0' COMMENT '是否隐藏菜单（0否 1是）',
  `is_hide_tab` tinyint DEFAULT '0' COMMENT '是否隐藏标签页（0否 1是）',
  `keep_alive` tinyint DEFAULT '1' COMMENT '是否缓存（0 否 1是）',
  `is_iframe` tinyint DEFAULT '0' COMMENT '是否iframe：0=否, 1=是',
  `link` varchar(500) DEFAULT NULL COMMENT '外部链接地址',
  `is_full_page` tinyint DEFAULT '0' COMMENT '是否全屏页面：0=否, 1=是',
  `fixed_tab` int DEFAULT '0' COMMENT '是否固定标签页0=否, 1=是',
  `active_path` varchar(200) DEFAULT NULL COMMENT '激活菜单路径（详情页用）',
  `show_badge` tinyint DEFAULT '0' COMMENT '是否显示徽章',
  `show_text_badge` varchar(50) DEFAULT NULL COMMENT '文本徽章内容（如 New）',
  `status` tinyint DEFAULT '1' COMMENT '状态：0=禁用, 1=启用',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 0, 'Dashboard', '/dashboard', '/index/index', 'menus.dashboard.title', 'ri:pie-chart-line', 0, 1, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '仪表盘目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, 1, 'Console', 'console', '/dashboard/console', 'menus.dashboard.console', 'ri:home-smile-2-line', 1, 1, 0, 0, 0, 0, NULL, 0, 1, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '控制台');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, 1, 'Analysis', 'analysis', '/dashboard/analysis', 'menus.dashboard.analysis', 'ri:align-item-bottom-line', 1, 2, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '数据分析');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (4, 1, 'Ecommerce', 'ecommerce', '/dashboard/ecommerce', 'menus.dashboard.ecommerce', 'ri:bar-chart-box-line', 1, 3, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '电商数据');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (5, 0, 'System', '/system', '/index/index', 'menus.system.title', 'ri:settings-3-line', 0, 2, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '系统管理目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (6, 5, 'User', 'user', '/system/user', 'menus.system.user', 'ri:user-line', 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '用户管理');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (7, 5, 'Role', 'role', '/system/role', 'menus.system.role', 'ri:user-settings-line', 1, 2, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '角色管理');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (8, 5, 'UserCenter', 'user-center', '/system/user-center', 'menus.system.userCenter', 'ri:user-line', 1, 3, 1, 1, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '个人中心（隐藏）');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (9, 5, 'Menus', 'menu', '/system/menu', 'menus.system.menu', 'ri:menu-line', 1, 4, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '菜单管理');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (10, 9, NULL, NULL, NULL, '新增', NULL, 2, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, 'add', 1, NULL, NULL, NULL, NULL, '菜单新增按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (11, 9, NULL, NULL, NULL, '编辑', NULL, 2, 2, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, 'edit', 1, NULL, NULL, NULL, NULL, '菜单编辑按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (12, 9, NULL, NULL, NULL, '删除', NULL, 2, 3, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, 'delete', 1, NULL, NULL, NULL, NULL, '菜单删除按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (13, 5, 'Nested', 'nested', '', 'menus.system.nested', 'ri:menu-unfold-3-line', 0, 5, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (14, 13, 'NestedMenu1', 'menu1', '/system/nested/menu1', 'menus.system.menu1', 'ri:align-justify', 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单1');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (15, 13, 'NestedMenu2', 'menu2', '', 'menus.system.menu2', 'ri:align-justify', 0, 2, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单2目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (16, 15, 'NestedMenu2-1', 'menu2-1', '/system/nested/menu2', 'menus.system.menu21', 'ri:align-justify', 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单2-1');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (17, 13, 'NestedMenu3', 'menu3', '', 'menus.system.menu3', 'ri:align-justify', 0, 3, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单3目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (18, 17, 'NestedMenu3-1', 'menu3-1', '/system/nested/menu3', 'menus.system.menu31', NULL, 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单3-1');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (19, 17, 'NestedMenu3-2', 'menu3-2', '', 'menus.system.menu32', NULL, 0, 2, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单3-2目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (20, 19, 'NestedMenu3-2-1', 'menu3-2-1', '/system/nested/menu3/menu3-2', 'menus.system.menu321', NULL, 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '嵌套菜单3-2-1');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (21, 0, 'Article', '/article', '/index/index', 'menus.article.title', 'ri:book-2-line', 0, 3, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '文章管理目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (22, 21, 'ArticleList', 'article-list', '/article/list', 'menus.article.articleList', 'ri:article-line', 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '文章列表');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (23, 22, NULL, NULL, NULL, '新增', NULL, 2, 1, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'add', 1, NULL, NULL, NULL, NULL, '文章新增按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (24, 22, NULL, NULL, NULL, '编辑', NULL, 2, 2, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'edit', 1, NULL, NULL, NULL, NULL, '文章编辑按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (25, 21, 'ArticleDetail', 'detail/:id', '/article/detail', 'menus.article.articleDetail', NULL, 1, 2, 1, 0, 1, 0, NULL, 0, 0, '/article/article-list', 0, NULL, 1, NULL, NULL, NULL, NULL, '文章详情（隐藏）');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (26, 21, 'ArticleComment', 'comment', '/article/comment', 'menus.article.comment', 'ri:mail-line', 1, 3, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '评论管理');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (27, 21, 'ArticlePublish', 'publish', '/article/publish', 'menus.article.articlePublish', 'ri:telegram-2-line', 1, 4, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '发布文章');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (28, 27, NULL, NULL, NULL, '发布', NULL, 2, 1, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'add', 1, NULL, NULL, NULL, NULL, '文章发布按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (29, 0, 'Examples', '/examples', '/index/index', 'menus.examples.title', 'ri:sparkling-line', 0, 4, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '功能示例目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (30, 29, 'Permission', 'permission', '', 'menus.examples.permission.title', 'ri:fingerprint-line', 0, 1, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '权限管理目录');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (31, 30, 'PermissionSwitchRole', 'switch-role', '/examples/permission/switch-role', 'menus.examples.permission.switchRole', 'ri:contacts-line', 1, 1, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '切换角色');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (32, 30, 'PermissionButtonAuth', 'button-auth', '/examples/permission/button-auth', 'menus.examples.permission.buttonAuth', 'ri:mouse-line', 1, 2, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '按钮权限');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (33, 32, NULL, NULL, NULL, '新增', NULL, 2, 1, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'add', 1, NULL, NULL, NULL, NULL, '新增按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (34, 32, NULL, NULL, NULL, '编辑', NULL, 2, 2, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'edit', 1, NULL, NULL, NULL, NULL, '编辑按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (35, 32, NULL, NULL, NULL, '删除', NULL, 2, 3, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'delete', 1, NULL, NULL, NULL, NULL, '删除按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (36, 32, NULL, NULL, NULL, '导出', NULL, 2, 4, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'export', 1, NULL, NULL, NULL, NULL, '导出按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (37, 32, NULL, NULL, NULL, '查看', NULL, 2, 5, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'view', 1, NULL, NULL, NULL, NULL, '查看按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (38, 32, NULL, NULL, NULL, '发布', NULL, 2, 6, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'publish', 1, NULL, NULL, NULL, NULL, '发布按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (39, 32, NULL, NULL, NULL, '配置', NULL, 2, 7, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'config', 1, NULL, NULL, NULL, NULL, '配置按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (40, 32, NULL, NULL, NULL, '管理', NULL, 2, 8, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, 'manage', 1, NULL, NULL, NULL, NULL, '管理按钮');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (41, 30, 'PermissionPageVisibility', 'page-visibility', '/examples/permission/page-visibility', 'menus.examples.permission.pageVisibility', 'ri:user-3-line', 1, 3, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '页面可见性');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (42, 29, 'Tabs', 'tabs', '/examples/tabs', 'menus.examples.tabs', 'ri:price-tag-line', 1, 2, 0, 0, 0, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '标签页');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (43, 29, 'TablesBasic', 'tables/basic', '/examples/tables/basic', 'menus.examples.tablesBasic', 'ri:layout-grid-line', 1, 3, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '基础表格');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (44, 29, 'Tables', 'tables', '/examples/tables', 'menus.examples.tables', 'ri:table-3', 1, 4, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '表格');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (45, 29, 'Forms', 'forms', '/examples/forms', 'menus.examples.forms', 'ri:table-view', 1, 5, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '表单');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (46, 29, 'SearchBar', 'form/search-bar', '/examples/forms/search-bar', 'menus.examples.searchBar', 'ri:table-line', 1, 6, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '搜索栏');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (47, 29, 'TablesTree', 'tables/tree', '/examples/tables/tree', 'menus.examples.tablesTree', 'ri:layout-2-line', 1, 7, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '树形表格');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (48, 29, 'SocketChat', 'socket-chat', '/examples/socket-chat', 'menus.examples.socketChat', 'ri:shake-hands-line', 1, 8, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, 'New', 1, NULL, NULL, NULL, NULL, 'Socket聊天');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (49, 0, 'Document', '', '', 'menus.help.document', 'ri:bill-line', 1, 5, 0, 0, 1, 0, 'https://junoyi.eatfan.top', 0, 0, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, '文档外链');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `menu_type`, `sort`, `is_hide`, `is_hide_tab`, `keep_alive`, `is_iframe`, `link`, `is_full_page`, `fixed_tab`, `active_path`, `show_badge`, `show_text_badge`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (50, 0, 'ChangeLog', '/change/log', '/change/log', 'menus.plan.log', 'ri:gamepad-line', 1, 6, 0, 0, 1, 0, NULL, 0, 0, NULL, 0, 'v0.1.0', 1, NULL, NULL, NULL, NULL, '更新日志');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_auth`;
CREATE TABLE `sys_menu_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint DEFAULT NULL COMMENT '关联菜单ID',
  `title` varchar(50) DEFAULT NULL COMMENT '按钮名称',
  `auth_permission` varchar(200) DEFAULT NULL COMMENT '权限标识符',
  `sort` int DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单权限表';

-- ----------------------------
-- Records of sys_menu_auth
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_perm_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm_group`;
CREATE TABLE `sys_perm_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_code` varchar(50) DEFAULT NULL COMMENT '权限组编码',
  `group_name` int DEFAULT NULL COMMENT '权限组名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父权限组（支持继承）',
  `priority` int DEFAULT NULL COMMENT '优先级（数值越大优先级越高）',
  `description` varchar(500) DEFAULT NULL COMMENT '权限组描述',
  `status` int DEFAULT '1' COMMENT '状态（0停用，1启用）',
  `permissions` json DEFAULT NULL COMMENT '权限列表',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(5000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限组表';

-- ----------------------------
-- Records of sys_perm_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(30) DEFAULT NULL COMMENT '角色关键词',
  `sort` int DEFAULT NULL COMMENT '排序',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据显示范围',
  `status` int DEFAULT '1' COMMENT '状态（1正常，0禁用）',
  `del_flag` tinyint DEFAULT '0' COMMENT '软删除标识符（0正常，1删除）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `sort`, `data_scope`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '超级管理员', 'super_admin', 1, '1', 1, 0, 'system', '2025-12-05 08:25:15', 'system', '2025-12-05 08:25:23', '超级管理员');
INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `sort`, `data_scope`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '管理员', 'admin', 2, '2', 1, 0, 'super_admin', '2025-12-05 08:26:57', 'super_admin', '2025-12-05 08:27:08', '管理员');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '性别(0 未设定， 1男，2女）',
  `password` varchar(255) DEFAULT NULL COMMENT '密码（加密后）',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '加密盐',
  `status` int DEFAULT '1' COMMENT '状态（1正常，0停用）',
  `del_flag` tinyint DEFAULT '0' COMMENT '软删除标识符号（0未删除，1删除）',
  `pwd_update_time` datetime DEFAULT NULL COMMENT '密码修改时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `avatar`, `email`, `phonenumber`, `sex`, `password`, `salt`, `status`, `del_flag`, `pwd_update_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 1, 'super_admin', '超级管理员', NULL, 'exmple@junoyi.com', '18899887878', '1', 'm/ctuGNjUwrpOxdqrd2fQsfVN1Mnbu6EKwJWXN+P3W4=', '3dvSoCjGtCXZnSB+6ENWtQ==', 1, 0, NULL, 'system', '2025-12-05 08:13:00', 'system', '2025-12-05 08:13:17', '超级管理员');
INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `avatar`, `email`, `phonenumber`, `sex`, `password`, `salt`, `status`, `del_flag`, `pwd_update_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, 1, 'admin', '用户管理员', NULL, 'admin@junoyi.com', '18899887877', '1', 'm/ctuGNjUwrpOxdqrd2fQsfVN1Mnbu6EKwJWXN+P3W4=', '3dvSoCjGtCXZnSB+6ENWtQ==', 1, 0, NULL, 'super_admin', '2025-12-26 08:22:32', 'super_admin', '2025-12-26 08:22:43', '用户管理员');
INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `avatar`, `email`, `phonenumber`, `sex`, `password`, `salt`, `status`, `del_flag`, `pwd_update_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, 2, 'user1', '钧逸用户1', NULL, 'user1@junoyi.com', '18899887876', '1', 'm/ctuGNjUwrpOxdqrd2fQsfVN1Mnbu6EKwJWXN+P3W4=', '3dvSoCjGtCXZnSB+6ENWtQ==', 1, 0, NULL, 'admin', '2025-12-26 09:02:10', 'admin', '2025-12-26 09:02:15', '钧逸用户');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `group_id` bigint NOT NULL COMMENT '权限组ID',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间（支持临时授权）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_group` (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-权限组关联表';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_other_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_other_auth`;
CREATE TABLE `sys_user_other_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `auth_type` varchar(20) DEFAULT NULL COMMENT '登录类型',
  `auth_key` varchar(255) DEFAULT NULL COMMENT '平台唯一标识符',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户第三登录绑定表';

-- ----------------------------
-- Records of sys_user_other_auth
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_perm`;
CREATE TABLE `sys_user_perm` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `permission` varchar(200) NOT NULL COMMENT '权限节点',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_perm` (`user_id`,`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户独立权限表';

-- ----------------------------
-- Records of sys_user_perm
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_platform`;
CREATE TABLE `sys_user_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `platform_type` int DEFAULT NULL COMMENT '登录终端平台类型（0后台Web，1前台Web，2小程序，3APP，4桌面端）',
  `platform_uid` varchar(255) DEFAULT NULL COMMENT '平台唯一标识符',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `token` varchar(255) DEFAULT NULL COMMENT '登录使用到的token',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录终端信息表';

-- ----------------------------
-- Records of sys_user_platform
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_platform` (`id`, `user_id`, `platform_type`, `platform_uid`, `login_ip`, `login_time`, `token`) VALUES (1, 1, 0, 'admin_web', '127.0.0.1', '2025-12-18 21:19:35', 'fdafdasfdasfdafdsafdsa');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色关联id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色数据关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES (1, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
