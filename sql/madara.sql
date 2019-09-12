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
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台菜单权限表';

-- ----------------------------
-- Records of mng_menu
-- ----------------------------
INSERT INTO `mng_menu` VALUES ('1', '1', '0', '系统管理', null, null, 'CATALOG', 'fa fa-cog', '0');
INSERT INTO `mng_menu` VALUES ('2', '2', '1', '用户管理', 'sys/user', null, 'MENU', 'fa fa-user', '1');
INSERT INTO `mng_menu` VALUES ('3', '3', '1', '角色管理', 'sys/role', null, 'MENU', 'fa fa-users', '2');
INSERT INTO `mng_menu` VALUES ('4', '20', '2', '查看', null, 'sys:user:list', 'BUTTON', null, '11');
INSERT INTO `mng_menu` VALUES ('5', '21', '2', '新增', null, 'sys:user:add', 'BUTTON', null, '12');
INSERT INTO `mng_menu` VALUES ('6', '22', '2', '修改', null, 'sys:user:update', 'BUTTON', null, '13');
INSERT INTO `mng_menu` VALUES ('7', '23', '2', '注销', null, 'sys:user:close', 'BUTTON', null, '14');
INSERT INTO `mng_menu` VALUES ('8', '30', '3', '查看', null, 'sys:role:list', 'BUTTON', null, '21');
INSERT INTO `mng_menu` VALUES ('9', '31', '3', '新增', null, 'sys:role:add', 'BUTTON', null, '22');
INSERT INTO `mng_menu` VALUES ('10', '32', '3', '修改', null, 'sys:role:update', 'BUTTON', null, '23');
INSERT INTO `mng_menu` VALUES ('11', '33', '3', '注销', null, 'sys:role:close', 'BUTTON', null, '24');
INSERT INTO `mng_menu` VALUES ('64', '24', '2', '解冻', null, 'sys:user:unfreeze', 'BUTTON', null, '15');
INSERT INTO `mng_menu` VALUES ('65', '25', '2', '重置密码', null, 'sys:user:resetPassword', 'BUTTON', null, '16');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台角色表';

-- ----------------------------
-- Records of mng_role
-- ----------------------------
INSERT INTO `mng_role` VALUES ('1', 'admin', '管理员', 'system', 'username', 'NORMAL', '', '系统初始化', '2018-04-23 15:13:37', '2018-05-02 09:41:26');

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
INSERT INTO `mng_role_menu` VALUES ('1808', '1', '1');
INSERT INTO `mng_role_menu` VALUES ('1809', '1', '2');
INSERT INTO `mng_role_menu` VALUES ('1810', '1', '4');
INSERT INTO `mng_role_menu` VALUES ('1811', '1', '5');
INSERT INTO `mng_role_menu` VALUES ('1812', '1', '6');
INSERT INTO `mng_role_menu` VALUES ('1813', '1', '7');
INSERT INTO `mng_role_menu` VALUES ('1814', '1', '64');
INSERT INTO `mng_role_menu` VALUES ('1815', '1', '65');
INSERT INTO `mng_role_menu` VALUES ('1816', '1', '3');
INSERT INTO `mng_role_menu` VALUES ('1817', '1', '8');
INSERT INTO `mng_role_menu` VALUES ('1818', '1', '9');
INSERT INTO `mng_role_menu` VALUES ('1819', '1', '10');
INSERT INTO `mng_role_menu` VALUES ('1820', '1', '11');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台用户表';

-- ----------------------------
-- Records of mng_user
-- ----------------------------
INSERT INTO `mng_user` VALUES ('1', 'admin', '超级管理员', '038bdaf98f2037b31f1e75b5b4c9b26e', 'NORMAL', '2018-05-11 16:43:27', '0', 'system', sysdate(), '系统初始化', sysdate(), sysdate());

-- ----------------------------
-- Table structure for mng_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mng_user_role`;
CREATE TABLE `mng_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台用户角色对应表';

-- ----------------------------
-- Records of mng_user_role
-- ----------------------------
INSERT INTO `mng_user_role` VALUES ('1', '1', '1');