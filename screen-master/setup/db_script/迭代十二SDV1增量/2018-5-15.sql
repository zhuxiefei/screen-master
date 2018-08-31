DELETE FROM admin_role_privilege_rela WHERE roleId = 1 AND privilegeId = 471;
DROP TABLE IF EXISTS `property_config`;
CREATE TABLE `property_config` (
  `confName` varchar(128) NOT NULL COMMENT '配置名称',
  `estateId` varchar(64) NOT NULL,
  `confValue` mediumtext NOT NULL COMMENT '配置值',
  `confType` varchar(128) DEFAULT NULL COMMENT '配置类型',
  `confDesc` varchar(512) DEFAULT NULL COMMENT '配置描述',
  PRIMARY KEY (`confName`,`estateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物业配置表';
INSERT INTO `property_config` VALUES ('overdueRemindDays', '1', '1', null, '超期提醒天数');
INSERT INTO `property_config` VALUES ('overdueRemindDays', '2', '1', null, '超期提醒天数');
INSERT INTO `property_config` VALUES ('paymentRemindDays', '1', '1', null, '支付提醒天数');
INSERT INTO `property_config` VALUES ('paymentRemindDays', '2', '1', null, '支付提醒天数');
INSERT INTO `property_config` VALUES ('propertyChargeStandard', '1', '', null, '物业收费标准');
INSERT INTO `property_config` VALUES ('propertyChargeStandard', '2', '', null, '物业收费标准');
INSERT INTO `property_config` VALUES ('repairChargeStandard', '1', '', null, '维修收费标准');
INSERT INTO `property_config` VALUES ('repairChargeStandard', '2', '', null, '维修收费标准');

-- 租赁表修改字段长度
alter table rent_info  modify column rentUnitPrice DOUBLE(10,2);
alter table rent_info  modify column propertyUnitPrice DOUBLE(10,2);
alter table rent_info  modify column tenant varchar(64);
alter table rent_info  modify column tenantPhone varchar(20);
alter table rent_info  modify column propertyPhone varchar(20);

INSERT INTO `admin_role_privilege_rela` ( `roleId`, `privilegeId`, `createTime`) VALUES ('2', '495', '2017-11-13 11:29:52');
INSERT INTO `admin_privilege` (`privilegeId`, `privilegeName`, `privilegeDesc`, `parentId`, `privilegeType`, `createTime`) VALUES ('495', 'lease-findAreaByHouseId', '获取房屋面积', '483', '3', '2017-06-13 09:27:10');

alter table house_office_house modify column buildingId BIGINT(64) DEFAULT NULL;
alter table house_office_house modify column unitId BIGINT(64) DEFAULT NULL;


ALTER TABLE `expense_bill`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '账单所属业主房屋ID';

	ALTER TABLE `house_house`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL;

	ALTER TABLE `house_member_auth`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '认证房屋ID' ;

	ALTER TABLE `house_owner_rela`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '所属房屋ID';

	ALTER TABLE `house_parking_space`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '房屋ID';

	ALTER TABLE `house_picture`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '所属房屋ID';

	ALTER TABLE `house_tenant`
	CHANGE COLUMN `houseId` `houseId` VARCHAR(64) NOT NULL COMMENT '所属房屋ID';

	alter table cleaning_check_record add column estateId VARCHAR(64) NOT NULL;

