-- ----------------------------
-- Table structure for mng_menu
-- ----------------------------
DROP TABLE IF EXISTS `mng_menu`;
CREATE TABLE `mng_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `MENU_CODE` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
  `PARENT_CODE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父菜单编码',
  `NAME` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单名',
  `URL` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应url',
  `PERMISSION` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单权限',
  `TYPE` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '菜单类型\r\nCATALOG-一级菜单\r\nMENU-二级菜单\r\nBUTTON-按钮',
  `ICON` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应图标',
  `ORDERING` bigint(20) NOT NULL COMMENT '菜单排序号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNQ_MENU_CODE` (`MENU_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台菜单权限表';

-- ----------------------------
-- Records of mng_menu
-- ----------------------------
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('1', '0', '系统管理', NULL, NULL, 'CATALOG', 'fa fa-cog', 0);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('2', '1', '用户管理', 'sys/user', NULL, 'MENU', 'fa fa-user', 1);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('3', '1', '角色管理', 'sys/role', NULL, 'MENU', 'fa fa-users', 2);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('20', '2', '查看', NULL, 'sys:user:list', 'BUTTON', NULL, 11);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('21', '2', '新增', NULL, 'sys:user:add', 'BUTTON', NULL, 12);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('22', '2', '修改', NULL, 'sys:user:update', 'BUTTON', NULL, 13);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('23', '2', '注销', NULL, 'sys:user:close', 'BUTTON', NULL, 14);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('30', '3', '查看', NULL, 'sys:role:list', 'BUTTON', NULL, 21);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('31', '3', '新增', NULL, 'sys:role:add', 'BUTTON', NULL, 22);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('32', '3', '修改', NULL, 'sys:role:update', 'BUTTON', NULL, 23);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('33', '3', '注销', NULL, 'sys:role:close', 'BUTTON', NULL, 24);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('4', '1', '系统参数', 'sys/param', NULL, 'MENU', 'fa fa-book', 3);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('24', '2', '解冻', NULL, 'sys:user:unfreeze', 'BUTTON', NULL, 15);
INSERT INTO `mng_menu` (`MENU_CODE`, `PARENT_CODE`, `NAME`, `URL`, `PERMISSION`, `TYPE`, `ICON`, `ORDERING`) VALUES ('25', '2', '重置密码', NULL, 'sys:user:resetPassword', 'BUTTON', NULL, 16);

-- ----------------------------
-- Table structure for mng_role
-- ----------------------------
DROP TABLE IF EXISTS `mng_role`;
CREATE TABLE `mng_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `ROLE_CODE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名',
  `CREATE_USER_CODE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人用户编码',
  `UPDATE_USER_CODE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人用户编码',
  `STATUS` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '状态 NORMAL:生效 CLOSED:注销',
  `ROLE_TYPE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色类型',
  `REMARK` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `GMT_CREATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `GMT_UPDATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台角色表';

-- ----------------------------
-- Records of mng_role
-- ----------------------------
INSERT INTO `mng_role` (`ROLE_CODE`, `NAME`, `CREATE_USER_CODE`, `UPDATE_USER_CODE`, `STATUS`, `ROLE_TYPE`, `REMARK`, `GMT_CREATE`, `GMT_UPDATE`) VALUES ('admin', '管理员', 'system', 'username', 'NORMAL', '', '系统初始化', '2018-4-23 15:13:37', '2018-5-2 09:41:26');

-- ----------------------------
-- Table structure for mng_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `mng_role_menu`;
CREATE TABLE `mng_role_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `MENU_ID` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台角色菜单权限表';

-- ----------------------------
-- Records of mng_role_menu
-- ----------------------------
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 1);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 2);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 4);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 5);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 6);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 7);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 64);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 65);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 3);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 8);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 9);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 10);
INSERT INTO `mng_role_menu` (`ROLE_ID`, `MENU_ID`) VALUES (1, 11);

-- ----------------------------
-- Table structure for mng_user
-- ----------------------------
DROP TABLE IF EXISTS `mng_user`;
CREATE TABLE `mng_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `USER_CODE` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `NAME` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户密码摘要',
  `STATUS` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '状态 NORMAL:生效 CLOSED:注销 FROZEN:锁定',
  `LAST_LOGIN_TIME` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `LOGIN_FAIL_NUM` smallint(6) DEFAULT NULL COMMENT '连续登录失败次数',
  `CREATE_USER_CODE` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编码',
  `CHANGE_PWD_TIME` timestamp NULL DEFAULT NULL COMMENT '上次修改密码时间',
  `REMARK` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `GMT_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `GMT_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台用户表';

-- ----------------------------
-- Records of mng_user
-- ----------------------------
INSERT INTO `mng_user` (`USER_CODE`, `NAME`, `PASSWORD`, `STATUS`, `LAST_LOGIN_TIME`, `LOGIN_FAIL_NUM`, `CREATE_USER_CODE`, `CHANGE_PWD_TIME`, `REMARK`, `GMT_CREATE`, `GMT_UPDATE`) VALUES ('admin', '超级管理员', '038bdaf98f2037b31f1e75b5b4c9b26e', 'NORMAL', '2019-9-10 17:28:46', 0, 'system', '2019-9-10 17:08:16', '系统初始化1', '2019-9-10 17:08:16', '2019-9-10 17:10:09');

-- ----------------------------
-- Table structure for mng_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mng_user_role`;
CREATE TABLE `mng_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台用户角色对应表';

-- ----------------------------
-- Records of mng_user_role
-- ----------------------------
INSERT INTO `mng_user_role` (`USER_ID`, `ROLE_ID`) VALUES (1, 1);