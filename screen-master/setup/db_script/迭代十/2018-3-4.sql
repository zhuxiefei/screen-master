INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('436', 'securityArea-findAreaList', '查询安保巡逻区域列表', '410', '3', '2018-03-04 15:24:28');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('437', 'securityArea-findSignList', '查询安保巡逻区域下签到点列表', '410', '3', '2018-03-04 15:30:59');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('438', 'securityArea-addArea', '添加安保巡逻区域', '410', '2', '2018-03-04 15:32:22');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('439', 'securityArea-addSignin', '添加安保巡逻区域下签到地点', '410', '2', '2018-03-04 15:33:10');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('440', 'securityArea-updateArea', '编辑安保巡逻区域下签到地点', '410', '2', '2018-03-04 15:34:09');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('441', 'securityArea-updateSignin', '编辑安保巡逻区域下签到地点', '410', '2', '2018-03-04 15:34:51');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('442', 'securityArea-findArea', '查询安保巡逻区域', '410', '3', '2018-03-04 15:35:54');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('443', 'securityArea-findSignin', '查询安保巡逻区域下签到地点', '410', '3', '2018-03-04 15:36:33');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('444', 'securityArea-deleteArea', '删除安保巡逻区域下签到地点', '410', '2', '2018-03-04 15:37:16');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('445', 'securityArea-deleteSignin', '删除安保巡逻区域下签到地点', '410', '2', '2018-03-04 15:38:37');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('446', 'securityContent-findContentList', '查询安保巡逻内容列表', '411', '3', '2018-03-04 15:40:02');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('447', 'securityContent-findContent', '查询安保巡逻内容', '411', '3', '2018-03-04 15:40:50');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('448', 'securityContent-updateContent', '编辑安保巡逻内容', '411', '2', '2018-03-04 15:41:39');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('449', 'securityContent-addContent', '新增安保巡逻内容', '411', '2', '2018-03-04 15:42:10');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('450', 'securityContent-deleteContent', '删除安保巡逻内容', '411', '2', '2018-03-04 15:42:44');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('451', 'securityContent-findAllRecord', '查询内容模块下安保巡逻记录', '411', '3', '2018-03-04 15:43:40');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('452', 'securityContent-findRecordContent', '查询内容模块下安保巡逻签到记录', '411', '3', '2018-03-04 15:44:33');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('453', 'securityRecord-findRecordList', '查询安保巡逻记录', '412', '3', '2018-03-04 15:55:15');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('454', 'securityRecord-findRecord', '查询安保巡逻记录详情', '412', '3', '2018-03-04 15:57:10');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('455', 'securityRecord-exportList', '导出巡逻记录', '412', '3', '2018-03-04 15:57:36');


INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '436', '2018-03-04 16:02:00');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '437', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '438', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '439', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '440', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '441', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '442', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '443', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '444', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '445', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '446', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '447', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '448', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '449', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '450', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '451', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '452', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '453', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '454', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '455', '2017-11-13 11:29:52');

INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('456', 'propertyApp', '物业App', '0', '4', '2018-03-04 15:41:39');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('457', 'repairAssign', '报修指派', '456', '1', '2018-03-04 15:42:10');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('458', 'repairDeal', '报修处理', '456', '1', '2018-03-04 15:42:44');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('459', 'patrol', '设施巡检', '456', '1', '2018-03-04 15:43:40');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('460', 'task', '任务管理', '456', '1', '2018-03-04 15:44:33');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('461', 'daily', '工作日志', '456', '1', '2018-03-04 15:55:15');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('462', 'decoration', '装修申请', '456', '1', '2018-03-04 15:57:10');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('463', 'cleaning', '保洁管理', '456', '1', '2018-03-04 15:57:36');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('464', 'security', '安保巡逻', '456', '1', '2018-03-04 15:57:36');


INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '456', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '457', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '458', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '459', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '460', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '461', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '462', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '463', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '464', '2017-11-13 11:29:52');

INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('465', 'employeeAccount-resetPassword', '重置密码', 22, 2, '2017-05-15 15:32:43');

INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '465', '2017-11-13 11:29:52');

INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('466', 'maintenance-exportRepairRecords', '导出维保记录', 162, 2, '2017-05-15 15:32:43');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('467', 'equipment-findAllMaintenanceList', '设备管理维保记录查看', 170, 3, '2017-05-15 15:32:43');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('468', 'equipment-exportList', '设备管理维保记录导出', 170, 2, '2017-05-15 15:32:43');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('469', 'facilitiesPatrol-findAllRecordMaintenanceList', '巡检记录里维保记录查询列表', 108, 3, '2017-05-15 15:32:43');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('470', 'facilitiesPatrol-exportRecordList', '巡检记录里维保记录导出', 108, 2, '2017-05-15 15:32:43');

INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '466', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '467', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '468', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '469', '2017-11-13 11:29:52');
INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ( '1', '470', '2017-11-13 11:29:52');

ALTER TABLE admin_admin add COLUMN isEnableAccount tinyint NOT NULL default 1 COMMENT '是否启用账号：1为是、2为否';
DELETE FROM admin_privilege WHERE privilegeId IN (88,22,51,53,78,79,80,81,82,83,49,54,55,56,57,58,59,60,61);
DELETE FROM admin_role_privilege_rela WHERE privilegeId IN (53,78,79,80,81,82,83,49,54,55,56,57,58,59,60,61);
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('88', 'account', '人员账号管理', '107', '1', '2017-08-08 15:40:25');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('22', 'employeeAccount', '人员账号设置', '88', '1', '2017-11-13 17:40:55');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('51', 'role', '角色管理', '88', 1, '2017-05-15 15:32:43');

DELETE FROM admin_admin WHERE adminId = '1';
INSERT INTO `admin_admin` (`adminId`, `adminName`, `adminPassword`, `isInit`, `shiroKey`, `roleId`, `lastLoginTime`, `lastLoginIp`, `createTime`,`isEnableAccount`) VALUES
	('1', '超级管理员', '1C9F3894592E519D9EBBC1FE8BFA6DDA', 1, NULL, 1, '2017-05-23 10:17:05', '192.168.199.128', '2017-05-15 15:30:58',1);