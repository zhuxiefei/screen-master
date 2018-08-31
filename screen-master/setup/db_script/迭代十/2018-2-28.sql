INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('405', 'cleaning', '保洁管理', '320', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('406', 'cleaningArea', '保洁区域/类型', '405', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('407', 'cleaningContent', '保洁内容', '405', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('408', 'cleaningRecord', '保洁考核记录', '405', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('409', 'security', '安保巡逻', '320', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('410', 'securityArea', '巡逻区域', '409', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('411', 'securityContent', '巡逻内容', '409', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('412', 'securityRecord', '巡逻记录', '409', '1', '2017-08-08 15:40:25');

INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('413', 'cleaningArea-addArea', '添加区域', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('414', 'cleaningArea-deleteArea', '批量删除保洁区域', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('415', 'cleaningArea-addType', '添加类型', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('416', 'cleaningArea-deleteType', '批量删除保洁类型', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('417', 'cleaningArea-updateArea', '编辑保洁区域', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('418', 'cleaningArea-updateType', '编辑保洁类型', '406', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('419', 'cleaningArea-findAreaList', '查询保洁区域列表', '406', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('420', 'cleaningArea-findTypeList', '查询保洁类型列表', '406', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('421', 'cleaningArea-findArea', '查询保洁区域详情', '406', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('422', 'cleaningArea-findType', '查询保洁类型详情', '406', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('423', 'cleaningContent-addContent', '添加保洁内容', '407', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('424', 'cleaningContent-findAreas', '查询保洁区域列表', '407', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('425', 'cleaningContent-findTypes', '查询保洁类型列表', '407', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('426', 'cleaningContent-findAllContent', '分页搜索查询保洁内容列表', '407', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('427', 'cleaningContent-findContent', '查看保洁内容详情', '407', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('428', 'cleaningContent-updateContent', '编辑保洁内容', '407', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('429', 'cleaningContent-deleteContent', '批量删除保洁内容', '407', '2', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('430', 'cleaningContent-findAllContentRecords', '查看该保洁内容下的考核记录列表', '407', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('431', 'cleaningRecord-findCleaningAreas', '查询保洁区域列表', '408', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('432', 'cleaningRecord-findCleaningTypes', '查询保洁类型列表', '408', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('433', 'cleaningRecord-findAllRecords', '分页搜索查询考核记录列表', '408', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('434', 'cleaningRecord-findRecord', '查看考核记录详情', '408', '3', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('435', 'cleaningRecord-exportRecords', '导出考核记录', '408', '2', '2017-08-08 15:40:25');

INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '405', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '406', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '407', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '408', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '409', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '410', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '411', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '412', '2017-11-13 11:29:52');

INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '413', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '414', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '415', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '416', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '417', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '418', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '419', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '420', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '421', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '422', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '423', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '424', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '425', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '426', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '427', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '428', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '429', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '430', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '431', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '432', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '433', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '434', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela`(roleId,privilegeId,createTime) VALUES ('1', '435', '2017-11-13 11:29:52');

-- ----------------------------
-- Table structure for cleaning_area
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_area`;
CREATE TABLE `cleaning_area` (
  `areaId` varchar(64) NOT NULL COMMENT '区域ID',
  `areaName` varchar(64) NOT NULL COMMENT '区域名称',
  `areaDesc` varchar(512) DEFAULT NULL COMMENT '区域描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for cleaning_check_record
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_check_record`;
CREATE TABLE `cleaning_check_record` (
  `recordId` varchar(64) NOT NULL COMMENT '考核记录ID',
  `contentId` varchar(64) NOT NULL COMMENT '保洁内容Id',
  `cleaner` varchar(64) DEFAULT NULL COMMENT '保洁人员',
  `signRecord` varchar(64) DEFAULT NULL COMMENT '保洁签到记录',
  `isStandard` tinyint(1) NOT NULL COMMENT '是否达标：1为是\\2为否',
  `problemRecord` varchar(512) DEFAULT NULL COMMENT '问题记录',
  `examiner` varchar(64) NOT NULL COMMENT '考核人员',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for cleaning_check_record_picture
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_check_record_picture`;
CREATE TABLE `cleaning_check_record_picture` (
  `recordId` varchar(64) NOT NULL COMMENT '考核记录Id',
  `pictureId` varchar(64) NOT NULL COMMENT '图片Id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`recordId`,`pictureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for cleaning_content
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_content`;
CREATE TABLE `cleaning_content` (
  `contentId` varchar(64) NOT NULL COMMENT '保洁内容ID',
  `contentNo` varchar(64) NOT NULL COMMENT '保洁编号',
  `areaId` varchar(64) NOT NULL COMMENT '所属区域ID',
  `typeId` varchar(64) NOT NULL COMMENT '所属类型ID',
  `location` varchar(64) NOT NULL COMMENT '所在位置',
  `cycle` tinyint(1) NOT NULL COMMENT '保洁周期：1为每小时保洁、2为每半天保洁、3为每日保洁、4为每周保洁、5为月度保洁',
  `content` varchar(512) NOT NULL COMMENT '保洁内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for cleaning_type
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_type`;
CREATE TABLE `cleaning_type` (
  `typeId` varchar(64) NOT NULL COMMENT '保洁类型ID',
  `typeName` varchar(64) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(512) DEFAULT NULL COMMENT '类型描述',
  `areaId` varchar(64) NOT NULL COMMENT '所属区域ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
