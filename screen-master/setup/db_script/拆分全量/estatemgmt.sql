DROP DATABASE IF EXISTS estatemgmt;

CREATE DATABASE estatemgmt DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE estatemgmt;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_account
-- ----------------------------
DROP TABLE IF EXISTS `admin_account`;
CREATE TABLE `admin_account` (
  `acctName` varchar(128) NOT NULL COMMENT '账号',
  `acctType` tinyint(1) NOT NULL COMMENT '账号类型：1为手机，2为用户名',
  `adminId` varchar(64) NOT NULL COMMENT '账号所属管理员ID',
  `createTime` datetime NOT NULL COMMENT '账号创建时间',
  PRIMARY KEY (`acctName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号表';

-- ----------------------------
-- Table structure for admin_admin
-- ----------------------------
DROP TABLE IF EXISTS `admin_admin`;
CREATE TABLE `admin_admin` (
  `adminId` varchar(64) NOT NULL COMMENT 'UUID',
  `adminName` varchar(128) NOT NULL COMMENT '用户昵称',
  `adminPassword` varchar(128) NOT NULL COMMENT '密码',
  `isInit` tinyint(1) NOT NULL COMMENT '密码是否需要重新修改：\r\n            1：不需要\r\n            2：需要\r\n            密码是否需要重新修改：1不需要，2需要',
  `shiroKey` varchar(32) DEFAULT NULL COMMENT 'shiro鉴权标识（盐）',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `lastLoginIp` varchar(20) DEFAULT NULL COMMENT '最后登录IP',
  `createTime` datetime NOT NULL COMMENT '账号创建时间',
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ----------------------------
-- Table structure for admin_privilege
-- ----------------------------
DROP TABLE IF EXISTS `admin_privilege`;
CREATE TABLE `admin_privilege` (
  `privilegeId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `privilegeName` varchar(128) NOT NULL COMMENT '权限名称',
  `privilegeDesc` varchar(512) DEFAULT NULL COMMENT '权限描述',
  `parentId` bigint(64) DEFAULT NULL COMMENT '父权限ID',
  `privilegeType` tinyint(1) NOT NULL COMMENT '权限类型：1为菜单，2为按钮，3为接口',
  `createTime` datetime NOT NULL COMMENT '权限创建时间',
  PRIMARY KEY (`privilegeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `roleId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(128) NOT NULL COMMENT '角色名称',
  `roleDesc` varchar(512) DEFAULT NULL COMMENT '角色描述',
  `createTime` datetime NOT NULL COMMENT '角色创建时间',
  `estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for admin_role_privilege_rela
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_privilege_rela`;
CREATE TABLE `admin_role_privilege_rela` (
  `rpRela` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `roleId` bigint(64) NOT NULL COMMENT '角色ID',
  `privilegeId` bigint(64) NOT NULL COMMENT '权限ID',
  `createTime` datetime NOT NULL COMMENT '关系创建时间',
  PRIMARY KEY (`rpRela`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for decoration_apply_record
-- ----------------------------
DROP TABLE IF EXISTS `decoration_apply_record`;
CREATE TABLE `decoration_apply_record` (
	`recordId` VARCHAR(64) NOT NULL COMMENT '申请记录ID，uuid',
	`houseId` VARCHAR(64) NOT NULL COMMENT '房屋id',
	`decorationCompany` VARCHAR(64) NOT NULL COMMENT '装修公司',
	`startTime` DATETIME NOT NULL COMMENT '装修开始时间',
	`endTime` DATETIME NOT NULL COMMENT '装修结束时间',
	`decorationCycle` INT(16) NOT NULL COMMENT '装修周期',
	`decorationDesc` VARCHAR(512) NULL DEFAULT NULL COMMENT '装修备注',
	`status` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '1、申请中2、已同意3、已拒绝4、已取消',
	`reason` VARCHAR(512) NULL DEFAULT NULL COMMENT '拒绝理由',
	`description` VARCHAR(512) NULL DEFAULT NULL COMMENT '物业备注',
	`printStatus` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '装修许可证打印状态：1、未打印2、已打印',
	`createTime` DATETIME NOT NULL COMMENT '申请时间',
	`updateTime` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`cancelTime` DATETIME NULL DEFAULT NULL COMMENT '取消时间',
	`cancelReason` VARCHAR(512) NULL DEFAULT NULL COMMENT '取消理由',
	`operator` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作人',
	`isDelete` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '用户是否删除：0：未删除 1：删除',
	PRIMARY KEY (`recordId`)
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

-- ----------------------------
-- Table structure for equipment_repair_record
-- ----------------------------
DROP TABLE IF EXISTS `equipment_repair_record`;
CREATE TABLE `equipment_repair_record` (
  `recordId` varchar(64) NOT NULL COMMENT '记录ID，uuid',
  `equipmentId` varchar(64) NOT NULL COMMENT '设备ID',
  `repairTime` datetime DEFAULT NULL COMMENT '维修日期',
  `equipmentOperator` varchar(64) DEFAULT NULL COMMENT '维修厂商',
  `operatorPhone` varchar(32) DEFAULT NULL COMMENT '维修厂商电话',
  `repairDesc` varchar(512) DEFAULT NULL COMMENT '维修内容',
  `repairExpense` varchar(32) DEFAULT NULL COMMENT '维修费用',
  `qualityPeriod` varchar(32) DEFAULT NULL COMMENT '质保期（格式：数值,日期单位）',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for equipment_type
-- ----------------------------
DROP TABLE IF EXISTS `equipment_type`;
CREATE TABLE `equipment_type` (
  `typeId` varchar(64) NOT NULL COMMENT '设备类型ID，uuid',
  `typeName` varchar(64) NOT NULL COMMENT '设备类型名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for expense_bill
-- ----------------------------
DROP TABLE IF EXISTS `expense_bill`;
CREATE TABLE `expense_bill` (
  `billNo` varchar(128) NOT NULL COMMENT '账单编号',
  `itemId` bigint(64) NOT NULL COMMENT '收费项目ID',
  `houseId` varchar(64) NOT NULL COMMENT '账单所属业主房屋ID',
  `houseOwnerName` varchar(128) DEFAULT NULL COMMENT '户主名称',
  `houseOwnerPhone` varchar(32) DEFAULT NULL COMMENT '户主手机号',
  `billAmount` decimal(12,2) NOT NULL COMMENT '账单金额',
  `deadLine` datetime NOT NULL COMMENT '账单到期日',
  `startTime` datetime NOT NULL COMMENT '账单起始时间',
  `endTime` datetime NOT NULL COMMENT '账单结束时间',
  `billStatus` tinyint(1) NOT NULL COMMENT '账单状态：1为欠缴费（未缴费），2为已缴费',
  `flowNo` varchar(128) DEFAULT NULL COMMENT '缴费流水编号',
  `overdueFine` decimal(12,2) DEFAULT NULL COMMENT '滞纳金额',
  `isPrint` tinyint(1) NOT NULL COMMENT '是否打印票据：0为否，1为是',
  `createTime` datetime NOT NULL COMMENT '出账时间',
  `isDelete` tinyint(1) NOT NULL COMMENT '账单是否删除：1为正常，2为已删除',
  `itemType` tinyint(4) NOT NULL DEFAULT '0' COMMENT '收费项类型：1为物业费 2为公摊水电费 3为购买停车费 4为租赁停车费 5为能耗费',
  `urgeCount` int(3) NOT NULL DEFAULT '0' COMMENT '催单次数',
  `lastUrgeTime` datetime DEFAULT NULL COMMENT '最后催单时间',
  `demandBillStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '索取账单票据状态：1为未索取 2为已索取 3为已送达',
  `demandBillTime` datetime DEFAULT NULL COMMENT '索取账单票据时间（为空即为 未索取）',
  PRIMARY KEY (`billNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='费用账单';

-- ----------------------------
-- Table structure for expense_flow
-- ----------------------------
DROP TABLE IF EXISTS `expense_flow`;
CREATE TABLE `expense_flow` (
  `flowNo` varchar(128) NOT NULL COMMENT '缴费流水编号',
  `totalAmount` decimal(12,2) NOT NULL COMMENT '缴费总额',
  `chargeWay` tinyint(1) NOT NULL COMMENT '收费方式：1为现金，2为刷卡，3为支付宝，4为微信',
  `flowStatus` tinyint(1) NOT NULL COMMENT '流水状态：1为未支付，2为待支付，3为已支付',
  `billPayer` varchar(64) NOT NULL COMMENT '付款人ID',
  `createTime` datetime NOT NULL COMMENT '出账时间',
  `isOnline` tinyint(1) DEFAULT NULL COMMENT '0代表线上缴费  1代表线下缴费',
  `payTime` datetime DEFAULT NULL COMMENT '缴费时间',
  PRIMARY KEY (`flowNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费流水表';

-- ----------------------------
-- Table structure for expense_item
-- ----------------------------
DROP TABLE IF EXISTS `expense_item`;
CREATE TABLE `expense_item` (
  `itemId` bigint(64) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(128) NOT NULL COMMENT '收费项目名称',
  `itemType` tinyint(1) NOT NULL COMMENT '收费项类型：1为物业费，2为公摊水电费，3为购买停车费，4为租赁停车费，5为能耗费',
  `itemPrice` decimal(12,2) DEFAULT NULL COMMENT '单价',
  `itemCycle` int(10) NOT NULL COMMENT '收费周期，单位：自然月',
  `chargeType` tinyint(1) NOT NULL COMMENT '计费方式：1为建面计费',
  `chargeCycle` int(10) NOT NULL COMMENT '缴费周期，单位：天',
  `overdueFine` double(5,2) NOT NULL COMMENT '滞纳金率，单位：%',
  `startTime` datetime DEFAULT NULL COMMENT '开始收费时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间：创建时与创建时间相同',
  `billType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '出账方式:1为提前收费 2为使用后收费',
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项目';

-- ----------------------------
-- Table structure for expense_item_building_rela
-- ----------------------------
DROP TABLE IF EXISTS `expense_item_building_rela`;
CREATE TABLE `expense_item_building_rela` (
  `ibId` bigint(64) NOT NULL AUTO_INCREMENT,
  `itemId` bigint(64) NOT NULL COMMENT '收费项目ID',
  `buildingId` bigint(64) DEFAULT NULL COMMENT '楼宇ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ibId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项目与楼宇关系表';

-- ----------------------------
-- Table structure for expense_item_history
-- ----------------------------
DROP TABLE IF EXISTS `expense_item_history`;
CREATE TABLE `expense_item_history` (
  `historyId` bigint(64) NOT NULL AUTO_INCREMENT,
  `itemId` bigint(64) NOT NULL COMMENT '所属收费项ID',
  `adminName` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人',
  `historyDesc` varchar(512) NOT NULL COMMENT '修改内容描述',
  `createTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项目修改历史记录表';

-- ----------------------------
-- Table structure for house_auth_notice
-- ----------------------------
DROP TABLE IF EXISTS `house_auth_notice`;
CREATE TABLE `house_auth_notice` (
  `noticeId` bigint(64) NOT NULL AUTO_INCREMENT,
  `authId` bigint(64) NOT NULL COMMENT '该通知相关的认证ID',
  `noticeUserId` varchar(64) NOT NULL COMMENT '被通知人ID',
  `noticeContent` varchar(512) NOT NULL COMMENT '通知内容',
  `noticeStatus` tinyint(1) NOT NULL COMMENT '通知状态：1为未读，2为已读，3为已删除',
  `createTime` datetime NOT NULL COMMENT '通知产生时间',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_building
-- ----------------------------
DROP TABLE IF EXISTS `house_building`;
CREATE TABLE `house_building` (
  `buildingId` bigint(64) NOT NULL AUTO_INCREMENT,
  `buildingName` varchar(128) NOT NULL COMMENT '楼宇名称',
  `buildingDesc` varchar(512) DEFAULT NULL COMMENT '楼宇描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `displayOrder` int(8) DEFAULT NULL COMMENT '排序',
  `estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`buildingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼宇';

-- ----------------------------
-- Table structure for house_building_unit
-- ----------------------------
DROP TABLE IF EXISTS `house_building_unit`;
CREATE TABLE `house_building_unit` (
  `unitId` bigint(64) NOT NULL AUTO_INCREMENT,
  `unitName` varchar(128) NOT NULL COMMENT '单元名称',
  `buildingId` bigint(64) NOT NULL COMMENT '单元所属楼宇ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `displayOrder` int(8) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`unitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼宇单元';

-- ----------------------------
-- Table structure for house_house
-- ----------------------------
DROP TABLE IF EXISTS `house_house`;
CREATE TABLE `house_house` (
  `houseId` varchar(64) NOT NULL,
  `houseNum` varchar(10) NOT NULL COMMENT '房号',
  `houseDesc` varchar(512) DEFAULT NULL COMMENT '房屋描述',
  `buildingId` bigint(64) DEFAULT NULL COMMENT '房屋所属楼宇ID',
  `unitId` bigint(64) DEFAULT NULL COMMENT '所属单元ID',
  `typeId` bigint(64) DEFAULT NULL COMMENT '所属户型ID',
  `createTime` datetime NOT NULL COMMENT '房屋创建时间',
  `floorArea` double(10,2) NOT NULL COMMENT '建筑面积',
  `interFloorArea` double(10,2) NOT NULL COMMENT '套内面积',
  `deliverTime` datetime DEFAULT NULL COMMENT '交付时间',
  `houseStatus` tinyint(1) NOT NULL DEFAULT '2' COMMENT '房屋状态',
  `petStatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否养宠物：0为未养宠物（默认）1为养宠物',
  `displayOrder` int(8) DEFAULT NULL COMMENT '排序',
  `estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`houseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房屋';

-- ----------------------------
-- Table structure for house_material
-- ----------------------------
DROP TABLE IF EXISTS `house_material`;
CREATE TABLE `house_material` (
  `materialId` bigint(64) NOT NULL AUTO_INCREMENT,
  `materialName` varchar(128) NOT NULL COMMENT '建材名称',
  `materialPic` varchar(64) DEFAULT NULL COMMENT '建材图片ID',
  `materialBrand` varchar(128) DEFAULT NULL COMMENT '建材品牌',
  `materialModel` varchar(128) DEFAULT NULL COMMENT '建材型号',
  `materialSpecification` varchar(128) DEFAULT NULL COMMENT '建材规格',
  `materialOrigin` varchar(128) DEFAULT NULL COMMENT '建材产地',
  `materialProduceDate` datetime DEFAULT NULL COMMENT '建材生产日期',
  `materialWarrantyDate` datetime DEFAULT NULL COMMENT '建材质保期',
  `materialType` varchar(128) NOT NULL COMMENT '建材类型',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`materialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家装建材';

-- ----------------------------
-- Table structure for house_material_parm
-- ----------------------------
DROP TABLE IF EXISTS `house_material_parm`;
CREATE TABLE `house_material_parm` (
  `parmId` bigint(64) NOT NULL AUTO_INCREMENT,
  `parmName` varchar(128) NOT NULL COMMENT '参数名称',
  `parmContent` varchar(512) NOT NULL COMMENT '参数内容',
  `materialId` bigint(64) NOT NULL COMMENT '参数所属材料ID',
  PRIMARY KEY (`parmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='建材自定义参数';

-- ----------------------------
-- Table structure for house_material_type
-- ----------------------------
DROP TABLE IF EXISTS `house_material_type`;
CREATE TABLE `house_material_type` (
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(512) DEFAULT NULL COMMENT '类型描述',
  PRIMARY KEY (`typeName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='建材类型';

-- ----------------------------
-- Table structure for house_member
-- ----------------------------
DROP TABLE IF EXISTS `house_member`;
CREATE TABLE `house_member` (
  `memberId` varchar(64) NOT NULL COMMENT 'UUID',
  `userId` varchar(64) DEFAULT NULL COMMENT 'APP用户ID',
  `realName` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '手机号',
  `idNum` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `ethnic` varchar(32) DEFAULT NULL COMMENT '民族',
  `residency` varchar(64) DEFAULT NULL COMMENT '籍贯',
  `religion` tinyint(1) DEFAULT NULL COMMENT '宗教信仰:1为无（默认），2为佛教，3为基督教，4为伊斯兰教，5为其他',
  `memberStatus` tinyint(1) NOT NULL COMMENT '成员状态：1为待激活,2为已激活,3为已失效',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_member_auth
-- ----------------------------
DROP TABLE IF EXISTS `house_member_auth`;
CREATE TABLE `house_member_auth` (
  `authId` bigint(64) NOT NULL AUTO_INCREMENT,
  `applicantId` varchar(64) NOT NULL COMMENT '申请人ID',
  `houseId` varchar(64) NOT NULL COMMENT '认证房屋ID',
  `applicantName` varchar(128) NOT NULL COMMENT '申请人真实姓名',
  `phoneNum` varchar(32) NOT NULL COMMENT '申请人电话号码',
  `authStatus` tinyint(1) NOT NULL COMMENT '认证状态：1为正常，4为已撤销，5为已删除',
  `operatorId` varchar(64) DEFAULT NULL COMMENT '处理人ID：户主ID',
  `reviewStatus` tinyint(1) NOT NULL COMMENT '审核状态：1为待审核，2为核准，3为驳回',
  `reviewAdvice` varchar(512) DEFAULT NULL COMMENT '审核意见',
  `reviewTime` datetime DEFAULT NULL COMMENT '审核时间',
  `authTime` datetime NOT NULL COMMENT '申请认证时间',
  PRIMARY KEY (`authId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_member_rela
-- ----------------------------
DROP TABLE IF EXISTS `house_member_rela`;
CREATE TABLE `house_member_rela` (
  `memberId` varchar(64) NOT NULL COMMENT '成员ID',
  `houseId` varchar(64) NOT NULL COMMENT '所属房屋ID',
  `createTime` datetime NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`memberId`,`houseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_owner
-- ----------------------------
DROP TABLE IF EXISTS `house_owner`;
CREATE TABLE `house_owner` (
  `ownerId` varchar(64) NOT NULL COMMENT 'UUID',
  `userId` varchar(64) DEFAULT NULL COMMENT 'APP用户ID',
  `realName` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '手机号',
  `idNum` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `idFrontSidePic` bigint(64) DEFAULT NULL COMMENT '身份证正面照ID',
  `idBackSidePic` bigint(64) DEFAULT NULL COMMENT '身份证反面照ID',
  `realEstatePic` varchar(512) DEFAULT NULL COMMENT '不动产权证照片ID字符串，用,隔开',
  `ethnic` varchar(32) DEFAULT NULL COMMENT '民族',
  `residency` varchar(64) DEFAULT NULL COMMENT '籍贯',
  `religion` tinyint(1) DEFAULT NULL COMMENT '宗教信仰:1为无（默认），2为佛教，3为基督教，4为伊斯兰教，5为其他',
  `ownerStatus` tinyint(1) NOT NULL COMMENT '户主状态：1为待激活，2为已激活，3为已失效',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ownerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_owner_rela
-- ----------------------------
DROP TABLE IF EXISTS `house_owner_rela`;
CREATE TABLE `house_owner_rela` (
  `ownerId` varchar(64) NOT NULL COMMENT '户主ID',
  `houseId` varchar(64) NOT NULL COMMENT '所属房屋ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ownerId`,`houseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_parking_space
-- ----------------------------
DROP TABLE IF EXISTS `house_parking_space`;
CREATE TABLE `house_parking_space` (
  `spaceId` varchar(64) NOT NULL COMMENT 'UUID',
  `houseId` varchar(64) NOT NULL COMMENT '房屋ID',
  `spaceNum` varchar(64) NOT NULL COMMENT '车位编号',
  `spaceType` tinyint(1) NOT NULL COMMENT '车位类型：1为产权车位，2为租赁车位',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `licensePlate` varchar(16) NOT NULL COMMENT '车牌号',
  PRIMARY KEY (`spaceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房屋车位';

-- ----------------------------
-- Table structure for house_picture
-- ----------------------------
DROP TABLE IF EXISTS `house_picture`;
CREATE TABLE `house_picture` (
  `hpId` bigint(64) NOT NULL AUTO_INCREMENT,
  `houseId` varchar(64) NOT NULL COMMENT '所属房屋ID',
  `pictureId` varchar(64) DEFAULT NULL COMMENT '预览图片ID',
  `cadId` varchar(64) DEFAULT NULL COMMENT 'CAD图片ID',
  `pictureType` varchar(128) NOT NULL COMMENT '图片类型名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`hpId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_picture_share
-- ----------------------------
DROP TABLE IF EXISTS `house_picture_share`;
CREATE TABLE `house_picture_share` (
  `shareId` bigint(64) NOT NULL AUTO_INCREMENT,
  `shareUrl` varchar(64) NOT NULL COMMENT '分享url',
  `shareKey` varchar(128) NOT NULL COMMENT '分享key',
  `expireTime` datetime NOT NULL COMMENT '失效时间',
  `shareUser` varchar(64) NOT NULL COMMENT '分享者',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`shareId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房屋图纸分享表';

-- ----------------------------
-- Table structure for house_picture_type
-- ----------------------------
DROP TABLE IF EXISTS `house_picture_type`;
CREATE TABLE `house_picture_type` (
  `typeName` varchar(128) NOT NULL COMMENT '类型名称，类型包括：1.建筑图2.强电图3.弱电图4.动力煤气图5.给排水图6.通风空调图',
  `typeDesc` varchar(512) DEFAULT NULL COMMENT '类型描述',
  PRIMARY KEY (`typeName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_tenant
-- ----------------------------
DROP TABLE IF EXISTS `house_tenant`;
CREATE TABLE `house_tenant` (
  `tenantId` varchar(64) NOT NULL COMMENT 'UUID',
  `houseId` varchar(64) NOT NULL COMMENT '所属房屋ID',
  `tenantName` varchar(128) DEFAULT NULL COMMENT '租户姓名',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '租户手机号',
  `ethnic` varchar(32) DEFAULT NULL COMMENT '民族',
  `residency` varchar(64) DEFAULT NULL COMMENT '籍贯',
  `religion` tinyint(1) DEFAULT NULL COMMENT '宗教信仰:1为无（默认），2为佛教，3为基督教，4为伊斯兰教，5为其他',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`tenantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for house_type
-- ----------------------------
DROP TABLE IF EXISTS `house_type`;
CREATE TABLE `house_type` (
  `typeId` bigint(64) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '户型名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='户型';

-- ----------------------------
-- Table structure for house_type_function
-- ----------------------------
DROP TABLE IF EXISTS `house_type_function`;
CREATE TABLE `house_type_function` (
  `functionId` bigint(64) NOT NULL AUTO_INCREMENT,
  `functionName` varchar(128) NOT NULL COMMENT '功能区域名称',
  `typeId` bigint(64) NOT NULL COMMENT '功能区域所属户型ID',
  PRIMARY KEY (`functionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='户型功能区域';

-- ----------------------------
-- Table structure for house_type_material
-- ----------------------------
DROP TABLE IF EXISTS `house_type_material`;
CREATE TABLE `house_type_material` (
  `tmId` bigint(64) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(64) NOT NULL COMMENT '材料所属户型ID',
  `functionId` bigint(64) DEFAULT NULL COMMENT '材料所属功能区域ID',
  `materialId` bigint(64) NOT NULL COMMENT '材料ID',
  PRIMARY KEY (`tmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='户型建材';

-- ----------------------------
-- Table structure for patrol_equipment
-- ----------------------------
DROP TABLE IF EXISTS `patrol_equipment`;
CREATE TABLE `patrol_equipment` (
  `equipmentId` varchar(64) NOT NULL COMMENT 'UUID',
  `equipmentNo` varchar(64) NOT NULL COMMENT '设备编号',
  `equipmentType` varchar(64) NOT NULL COMMENT '设备类型，关联设备类型表',
  `equipmentName` varchar(128) NOT NULL COMMENT '设备名称',
  `equipmentLocation` varchar(128) NOT NULL COMMENT '设备位置',
  `equipmentQRCode` mediumtext COMMENT '设备二维码',
  `equipmentDesc` varchar(6000) DEFAULT NULL COMMENT '设备描述：可用于记录巡检内容',
  `isCheck` tinyint(1) DEFAULT NULL COMMENT '是否需要巡检:1代表需要 2代表不需要',
  `checkCycle` tinyint(1) DEFAULT NULL COMMENT '巡检周期：1、每日巡检2、每周巡检3、月度巡检4、季度巡检5、年度巡检',
  `equipmentCreateTime` datetime DEFAULT NULL COMMENT '设备生产日期',
  `qualityPeriod` varchar(32) DEFAULT NULL COMMENT '质保期',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `deadline` datetime NOT NULL COMMENT '保修截止日期',
  `equipmentProducer` varchar(64) NOT NULL COMMENT '设备生产厂商',
  `producerPhone` varchar(32) NOT NULL COMMENT '生产厂商电话',
  `equipmentOperator` varchar(64) NOT NULL COMMENT '设备维修厂商',
  `operatorPhone` varchar(32) NOT NULL COMMENT '维修厂商电话',
  `repairNumber` int(16) NOT NULL DEFAULT 0 COMMENT '维修次数',
  `unit` varchar(32) DEFAULT NULL COMMENT '质保期单位:1 天 2 个月 3 年',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`equipmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for patrol_record
-- ----------------------------
DROP TABLE IF EXISTS `patrol_record`;
CREATE TABLE `patrol_record` (
  `recordId` varchar(64) NOT NULL COMMENT 'UUID',
  `equipmentId` varchar(64) NOT NULL COMMENT '设备Id',
  `employeeId` varchar(64) DEFAULT NULL COMMENT '巡检员工ID',
  `employeeName` varchar(128) DEFAULT NULL COMMENT '巡检员工姓名',
  `recordStatus` tinyint(1) DEFAULT NULL COMMENT '巡检状态:1为正常,2为不正常',
  `recordDesc` varchar(512) DEFAULT NULL COMMENT '巡检记录描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '巡检时间',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0为未删除；1为app删除；2为web删除',
  `isPatrol` tinyint(1) DEFAULT '0' COMMENT '1、	待巡检  2、	已巡检',
  `serialNo` varchar(128) NOT NULL DEFAULT '' COMMENT '序列号',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for patrol_record_picture
-- ----------------------------
DROP TABLE IF EXISTS `patrol_record_picture`;
CREATE TABLE `patrol_record_picture` (
  `recordId` varchar(64) NOT NULL COMMENT '巡检记录ID',
  `pictureId` varchar(64) NOT NULL COMMENT '图片ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`recordId`,`pictureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for property_config
-- ----------------------------
DROP TABLE IF EXISTS `property_config`;
CREATE TABLE `property_config` (
  `confName` varchar(128) NOT NULL COMMENT '配置名称',
  `estateId` varchar(64) NOT NULL,
  `confValue` mediumtext NOT NULL COMMENT '配置值',
  `confType` varchar(128) DEFAULT NULL COMMENT '配置类型',
  `confDesc` varchar(512) DEFAULT NULL COMMENT '配置描述',
  PRIMARY KEY (`confName`,`estateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物业配置表';

-- ----------------------------
-- Table structure for property_doc
-- ----------------------------
DROP TABLE IF EXISTS `property_doc`;
CREATE TABLE `property_doc` (
  `docId` varchar(64) NOT NULL COMMENT 'UUID',
  `docTitle` varchar(128) NOT NULL COMMENT '资料标题',
  `docContent` text NOT NULL COMMENT '资料内容 ',
  `typeId` varchar(64) NOT NULL COMMENT '资料类型ID',
  `attachment` varchar(64) DEFAULT NULL COMMENT '附件文件ID',
  `createUser` varchar(64) NOT NULL COMMENT '创建人ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`docId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for property_doc_type
-- ----------------------------
DROP TABLE IF EXISTS `property_doc_type`;
CREATE TABLE `property_doc_type` (
  `typeId` varchar(64) NOT NULL COMMENT 'UUID',
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(512) DEFAULT NULL COMMENT '类型描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for property_hotline
-- ----------------------------
DROP TABLE IF EXISTS `property_hotline`;
CREATE TABLE `property_hotline` (
  `hotlineId` varchar(64) NOT NULL COMMENT 'UUID',
  `hotlineName` varchar(128) NOT NULL COMMENT '热线名称',
  `hotline` varchar(32) NOT NULL COMMENT '热线电话',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`hotlineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for repair_history
-- ----------------------------
DROP TABLE IF EXISTS `repair_history`;
CREATE TABLE `repair_history` (
  `historyId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
  `orderNo` varchar(128) NOT NULL COMMENT '维修单ID',
  `operatorId` varchar(64) NOT NULL COMMENT '操作人ID',
  `operatorType` tinyint(1) NOT NULL COMMENT '操作人标识：1为web管理员,2为app管理员',
  `historyType` tinyint(1) NOT NULL COMMENT '操作记录类型：1为新增维修单  2为取消维修单  3为指派维修单  4为完成维修单',
  `operatorDepartment` varchar(64) DEFAULT NULL COMMENT '操作人部门名称',
  `operatorName` varchar(64) NOT NULL COMMENT '操作人姓名',
  `operatorHead` varchar(128) DEFAULT NULL COMMENT '操作人头像',
  `receiver` varchar(64) DEFAULT NULL COMMENT '被指派人',
  `receiverPhone` varchar(64) DEFAULT NULL COMMENT '被指派人联系电话',
  `receiverHead` varchar(128) DEFAULT NULL COMMENT '被指派人头像',
  `receiverDepartment` varchar(64) DEFAULT NULL COMMENT '被指派人部门名称',
  `receiverName` varchar(64) DEFAULT NULL COMMENT '被指派人姓名',
  `description` varchar(512) DEFAULT NULL COMMENT '备注',
  `historyDesc` varchar(512) NOT NULL COMMENT '操作内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for repair_order
-- ----------------------------
DROP TABLE IF EXISTS `repair_order`;
CREATE TABLE `repair_order` (
  `orderNo` varchar(128) NOT NULL COMMENT '维修单号',
  `userId` varchar(64) DEFAULT NULL COMMENT '报修人ID',
  `orderContact` varchar(128) NOT NULL COMMENT '报修人姓名',
  `contactPhone` varchar(32) NOT NULL COMMENT '报修人联系电话',
  `orderArea` tinyint(1) NOT NULL COMMENT '微信区域：1为个人，2为公共区域',
  `houseId` varchar(64) DEFAULT NULL COMMENT '个人报修房屋的ID',
  `orderType` bigint(64) NOT NULL COMMENT '维修类型ID',
  `orderDesc` varchar(512) DEFAULT NULL COMMENT '维修单描述',
  `orderStatus` tinyint(1) NOT NULL COMMENT '维修单状态：1为待维修，2为已接单，3为已完成',
  `operatorId` varchar(64) DEFAULT NULL COMMENT '维修员ID',
  `orderPic` VARCHAR(64) DEFAULT NULL COMMENT '维修单据照片ID',
  `appointTime` datetime DEFAULT NULL COMMENT '预约上门维修时间',
  `acceptTime` datetime DEFAULT NULL COMMENT '接单时间',
  `finishTime` datetime DEFAULT NULL COMMENT '维修完成时间',
  `createTime` datetime NOT NULL COMMENT '报修时间',
  `urgeCount` int(3) NOT NULL DEFAULT '0' COMMENT '催单次数',
  `lastUrgeTime` datetime DEFAULT NULL COMMENT '最后催单时间',
  `cancelTime` datetime DEFAULT NULL COMMENT '报修单取消时间',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0为未删除；1为业主app删除；2为物管app删除',
  `description` varchar(512) DEFAULT NULL COMMENT '维修完成备注',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修单';

-- ----------------------------
-- Table structure for repair_order_picture
-- ----------------------------
DROP TABLE IF EXISTS `repair_order_picture`;
CREATE TABLE `repair_order_picture` (
  `opId` bigint(64) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(128) NOT NULL COMMENT '所属维修单ID',
  `pictureId` varchar(64) NOT NULL COMMENT '图片ID',
  `createTime` datetime NOT NULL COMMENT '维修单图片创建时间',
  PRIMARY KEY (`opId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修单图片';

-- ----------------------------
-- Table structure for repair_order_reply
-- ----------------------------
DROP TABLE IF EXISTS `repair_order_reply`;
CREATE TABLE `repair_order_reply` (
  `replyId` bigint(64) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(128) NOT NULL COMMENT '所属维修单ID',
  `serviceOnTime` int(1) NOT NULL COMMENT '上门服务准时：星级，范围1-5，整数',
  `serviceAttitude` int(1) NOT NULL COMMENT '服务态度：星级，范围1-5，整数',
  `serviceQuality` int(1) NOT NULL COMMENT '服务质量：星级，范围1-5，整数',
  `serviceDesc` varchar(512) DEFAULT NULL COMMENT '服务评价',
  `createTime` datetime NOT NULL COMMENT '评价时间',
  PRIMARY KEY (`replyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修单评价';

-- ----------------------------
-- Table structure for repair_order_type
-- ----------------------------
DROP TABLE IF EXISTS `repair_order_type`;
CREATE TABLE `repair_order_type` (
  `typeId` bigint(64) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(64) DEFAULT NULL COMMENT '父级分类ID，空则为一级分类',
  `typeType` tinyint(1) NOT NULL COMMENT '维修单类型的类型：1为个人，2为公共区域',
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(512) DEFAULT NULL COMMENT '类型描述',
  `estateType` TINYINT(1) NULL DEFAULT NULL COMMENT '楼盘类型：1：小区 2：写字楼',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修单类型';

-- ----------------------------
-- Table structure for system_ad
-- ----------------------------
DROP TABLE IF EXISTS `system_ad`;
CREATE TABLE `system_ad` (
  `adId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `authorId` varchar(64) NOT NULL COMMENT '广告创建人ID',
  `adTitle` varchar(120) NOT NULL COMMENT '广告标题',
  `adContent` mediumtext NOT NULL COMMENT '广告内容',
  `createTime` datetime NOT NULL COMMENT '广告创建时间',
  `lastModifyId` varchar(64) DEFAULT NULL COMMENT '最后修改人ID',
  `updateTime` datetime DEFAULT NULL COMMENT '广告修改时间',
  PRIMARY KEY (`adId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统广告';

-- ----------------------------
-- Table structure for system_ad_picture
-- ----------------------------
DROP TABLE IF EXISTS `system_ad_picture`;
CREATE TABLE `system_ad_picture` (
  `apId` bigint(64) NOT NULL AUTO_INCREMENT,
  `adId` bigint(64) NOT NULL COMMENT '广告ID',
  `pictureId` varchar(64) NOT NULL COMMENT '图片ID',
  `createTime` datetime NOT NULL COMMENT '广告图片创建时间',
  PRIMARY KEY (`apId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统广告图片表';

-- ----------------------------
-- Table structure for system_announce
-- ----------------------------
DROP TABLE IF EXISTS `system_announce`;
CREATE TABLE `system_announce` (
  `announceId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `authorId` varchar(64) NOT NULL COMMENT '公告创建人ID',
  `announceTitle` varchar(120) NOT NULL COMMENT '公告标题',
  `announceContent` text NOT NULL COMMENT '公告内容',
  `announceStatus` tinyint(1) NOT NULL COMMENT '公告状态：1为未发送，2为已发送，3为已删除',
  `createTime` datetime NOT NULL COMMENT '公告创建时间',
  `lastModifyId` varchar(64) DEFAULT NULL COMMENT '最后修改人ID',
  `updateTime` datetime DEFAULT NULL COMMENT '公告修改时间',
  `isTop` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否置顶：1是，0否',
  `topTime` datetime DEFAULT NULL COMMENT '置顶时间（用于置顶条目的排序）',
  PRIMARY KEY (`announceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告';

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `confName` varchar(128) NOT NULL COMMENT '配置名称',
  `confValue` varchar(512) NOT NULL COMMENT '配置值',
  `confType` varchar(128) DEFAULT NULL COMMENT '配置类型',
  `confDesc` varchar(512) DEFAULT NULL COMMENT '配置描述',
  PRIMARY KEY (`confName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

-- ----------------------------
-- Table structure for system_file
-- ----------------------------
DROP TABLE IF EXISTS `system_file`;
CREATE TABLE `system_file` (
  `fileId` varchar(64) NOT NULL COMMENT 'UUID',
  `fileName` varchar(128) NOT NULL COMMENT '原始文件名称',
  `fileUrl` varchar(512) NOT NULL COMMENT '文件存储路径，备注：全路径',
  `createTime` datetime NOT NULL COMMENT '文件创建时间',
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统文件';

-- ----------------------------
-- Table structure for system_picture
-- ----------------------------
DROP TABLE IF EXISTS `system_picture`;
CREATE TABLE `system_picture` (
  `pictureId` varchar(64) NOT NULL,
  `pictureName` varchar(128) NOT NULL COMMENT '图片名称',
  `pictureUrl` varchar(512) NOT NULL COMMENT '图片存储路径',
  `createTime` datetime NOT NULL COMMENT '图片添加时间',
  PRIMARY KEY (`pictureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片表';

-- ----------------------------
-- Table structure for work_task
-- ----------------------------
DROP TABLE IF EXISTS `work_task`;
CREATE TABLE `work_task` (
  `taskId` varchar(64) NOT NULL COMMENT 'UUID',
  `taskNo` varchar(64) NOT NULL COMMENT '任务编号',
  `taskType` varchar(64) NOT NULL COMMENT '任务类型ID',
  `taskDesc` text NOT NULL COMMENT '任务描述',
  `createUser` varchar(64) NOT NULL COMMENT '发起人ID',
  `createUserName` varchar(128) NOT NULL COMMENT '发起人姓名',
  `lastModifyId` varchar(64) NOT NULL COMMENT '最后修改人ID',
  `lastModifyName` varchar(128) NOT NULL COMMENT '最后修改人姓名',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `isDelete` tinyint(1) NOT NULL COMMENT '0为未删除  1为已删除',
  `createUserPhone` varchar(32) DEFAULT NULL COMMENT '发起人手机号',
  `lastModifyPhone` varchar(32) DEFAULT NULL COMMENT '最后修改人手机号',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for work_task_history
-- ----------------------------
DROP TABLE IF EXISTS `work_task_history`;
CREATE TABLE `work_task_history` (
  `historyId` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
  `taskId` varchar(64) NOT NULL COMMENT '任务ID',
  `operatorId` varchar(64) NOT NULL COMMENT '操作人ID',
  `operatorType` tinyint(1) NOT NULL COMMENT '操作人标识：1为web管理员  2为app管理员',
  `historyType` tinyint(1) NOT NULL COMMENT '操作记录类型：1为新增任务  2为删除任务  3为编辑任务',
  `historyDesc` text NOT NULL COMMENT '操作内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for work_task_record
-- ----------------------------
DROP TABLE IF EXISTS `work_task_record`;
CREATE TABLE `work_task_record` (
  `recordId` varchar(64) NOT NULL COMMENT 'UUID',
  `taskId` varchar(64) NOT NULL COMMENT '任务id',
  `departmentId` varchar(64) NOT NULL COMMENT '部门ID',
  `depId` varchar(64) NOT NULL DEFAULT '' COMMENT '规则ID',
  `executor` varchar(64) NOT NULL COMMENT '执行人ID',
  `executorName` varchar(128) NOT NULL COMMENT '执行人姓名',
  `executorPhone` varchar(32) NOT NULL COMMENT '执行人手机号',
  `refuseTime` datetime DEFAULT NULL COMMENT '拒绝时间',
  `reason` varchar(512) DEFAULT NULL COMMENT '拒绝理由',
  `acceptTime` datetime DEFAULT NULL COMMENT '接受时间',
  `finishTime` datetime DEFAULT NULL COMMENT '完成时间',
  `taskStatus` tinyint(1) NOT NULL COMMENT '任务状态：1为待接受；2为进行中；3为已完成；4为超时未完成',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `executorDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0为未删除；1为已删除',
  `createUserDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0为未删除；1为已删除',
  `closeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for work_task_record_picture
-- ----------------------------
DROP TABLE IF EXISTS `work_task_record_picture`;
CREATE TABLE `work_task_record_picture` (
  `recordId` varchar(64) NOT NULL COMMENT '任务记录id',
  `pictureId` varchar(64) NOT NULL COMMENT '图片ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`recordId`,`pictureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for work_task_type
-- ----------------------------
DROP TABLE IF EXISTS `work_task_type`;
CREATE TABLE `work_task_type` (
  `typeId` varchar(64) NOT NULL COMMENT 'UUID',
  `typeName` varchar(128) NOT NULL COMMENT '任务类型名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*==============================================================*/
/* Table: user_feedback                                         */
/*==============================================================*/
create table user_feedback
(
   feedbackId           bigint(64) not null auto_increment comment '反馈ID',
   authorId             varchar(64) not null comment '反馈提交人ID',
   authorName           varchar(64) not null comment '反馈提交人名字',
   feedbackContent      text CHARACTER SET utf8mb4 not null comment '反馈内容',
   feedbackType         tinyint(1) not null comment '反馈类型：1为意见，2为问题',
   feedbackStatus       tinyint(1) not null comment '反馈状态：1为未读，2为已读',
   clientModel          varchar(128) not null comment '客户端型号',
   clientVersion        varchar(32) not null comment '客户端版本',
   appType              tinyint(1) not null comment 'app类型：1为业主APP，2为物业APP',
   appVersion           varchar(32) not null comment 'APP版本',
   createTime           datetime not null comment '反馈提交时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
   primary key (feedbackId)
);

alter table user_feedback comment '用户意见反馈';

/*==============================================================*/
/* Table: system_notice                                         */
/*==============================================================*/
create table system_notice
(
   noticeId             bigint(64) not null auto_increment comment '通知ID',
   noticeUserId         varchar(64) not null comment '接收通知人ID',
   noticeContent        varchar(512) not null comment '通知内容',
   noticeStatus         tinyint(1) not null comment '通知状态：1为未读，2为已读，3为已删除',
   noticeType           tinyint(1) not null comment '通知类型：1为物品交易留言，2为认证通知...',
   createTime           datetime not null comment '通知产生时间',
   primary key (noticeId)
);

alter table system_notice comment '系统通知';

-- ----------------------------
-- Table structure for cleaning_area
-- ----------------------------
DROP TABLE IF EXISTS `cleaning_area`;
CREATE TABLE `cleaning_area` (
  `areaId` varchar(64) NOT NULL COMMENT '区域ID',
  `areaName` varchar(64) NOT NULL COMMENT '区域名称',
  `areaDesc` varchar(512) DEFAULT NULL COMMENT '区域描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
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
  `estateId` VARCHAR(64) NOT NULL COMMENT '楼盘id',
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
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
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

-- ----------------------------
-- Table structure for security_area
-- ----------------------------
DROP TABLE IF EXISTS `security_area`;
CREATE TABLE `security_area` (
  `areaId` varchar(64) NOT NULL COMMENT '区域id',
  `areaName` varchar(128) NOT NULL COMMENT '区域名称',
  `areaDesc` varchar(512) DEFAULT NULL COMMENT '区域描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_content
-- ----------------------------
DROP TABLE IF EXISTS `security_content`;
CREATE TABLE `security_content` (
  `contentId` varchar(64) NOT NULL,
  `contentNo` varchar(128) NOT NULL COMMENT '巡逻内容编号:XL+日期戳',
  `areaId` varchar(64) NOT NULL COMMENT '区域id',
  `inspectionTime` varchar(512) NOT NULL COMMENT '定时发布时间：多个，隔开',
  `contentDesc` varchar(512) DEFAULT NULL COMMENT '巡逻内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_content_area_signin
-- ----------------------------
DROP TABLE IF EXISTS `security_content_signin`;
CREATE TABLE `security_content_signin` (
  `contSignId` varchar(64) NOT NULL,
  `contentId` varchar(64) NOT NULL COMMENT '巡逻内容id',
  `signinId` varchar(64) NOT NULL COMMENT '签到地点id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`contSignId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_record
-- ----------------------------
DROP TABLE IF EXISTS `security_record`;
CREATE TABLE `security_record` (
  `recordId` varchar(64) NOT NULL,
  `contentId` varchar(64) NOT NULL COMMENT '内容id',
  `employeeId` varchar(64) DEFAULT NULL COMMENT '员工id',
  `employeeName` varchar(128) DEFAULT NULL COMMENT '员工名称',
  `isPatrol` tinyint(1) NOT NULL COMMENT '1、待巡逻  2、已巡逻',
  `signinNum` int(4) DEFAULT '0' COMMENT '签到次数',
  `finishTime` datetime DEFAULT NULL COMMENT '安保巡逻完成时间',
  `createTime` datetime NOT NULL COMMENT '安保巡逻发布时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
	`estateId` VARCHAR(64) NULL DEFAULT NULL COMMENT '楼盘id',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_signin_address
-- ----------------------------
DROP TABLE IF EXISTS `security_signin_address`;
CREATE TABLE `security_signin_address` (
  `signinId` varchar(64) NOT NULL,
  `signinAddress` varchar(128) NOT NULL COMMENT '签到地址名称',
  `signinDesc` varchar(512) DEFAULT NULL COMMENT '签到地址描述',
  `areaId` varchar(64) NOT NULL COMMENT '区域id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`signinId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_signin_number
-- ----------------------------
DROP TABLE IF EXISTS `security_signin_number`;
CREATE TABLE `security_signin_number` (
  `numId` varchar(255) NOT NULL,
  `longitude` double(10,6) NOT NULL COMMENT '经度，小数最大6',
  `latitude` double(10,6) NOT NULL COMMENT '纬度，小数最大6位',
  `address` varchar(128) NOT NULL COMMENT '签到地址',
  `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
  `recordId` varchar(64) NOT NULL COMMENT '巡逻记录id',
  `createTime` datetime NOT NULL COMMENT '签到时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `signinEmpId` varchar(64) DEFAULT NULL COMMENT '签到人id',
  `signinEmpName` varchar(128) DEFAULT NULL COMMENT '签到人名称',
  PRIMARY KEY (`numId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `admin_estate_rela`;
CREATE TABLE `admin_estate_rela` (
	`adminId` VARCHAR(64) NOT NULL COMMENT '管理员ID，UUID',
	`estateId` VARCHAR(64) NOT NULL COMMENT '楼盘ID',
	`roleId` VARCHAR(64) NOT NULL COMMENT '角色ID',
	`isEnableAccount` TINYINT(1) NOT NULL COMMENT '是否启用账号登录：1为启用0为未启用',
	`createTime` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`adminId`, `estateId`)
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `estate_estate`;
CREATE TABLE `estate_estate` (
  `estateId` varchar(64) NOT NULL COMMENT '楼盘ID',
  `estateName` varchar(64) NOT NULL COMMENT '楼盘名称',
  `cityId` varchar(64) NOT NULL COMMENT '所在市Id',
  `estateType` tinyint(1) NOT NULL COMMENT '楼盘标识：1为小区楼盘2为商用楼盘',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`estateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `estate_city`;
CREATE TABLE `estate_city` (
	`cityId` VARCHAR(64) NOT NULL COMMENT '城市ID',
	`cityName` VARCHAR(64) NOT NULL COMMENT '城市名称',
	`createTime` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`cityId`)
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `rent_info`;
CREATE TABLE `rent_info` (
	`rentId` VARCHAR(64) NOT NULL COMMENT '租赁信息Id',
	`companyName` VARCHAR(64) NOT NULL COMMENT '租赁公司名称',
	`tenant` VARCHAR(64) NOT NULL COMMENT '租客对接人',
	`tenantPhone` VARCHAR(20) NOT NULL COMMENT '租客电话',
	`houseId` VARCHAR(64) NOT NULL COMMENT '房屋编号',
	`acreage` DOUBLE(10,0) NOT NULL COMMENT '租赁面积',
	`rentUnitPrice` DOUBLE(10,2) NOT NULL COMMENT '租赁单价',
	`signTime` DATETIME NOT NULL COMMENT '合同签订时间',
	`startTime` DATETIME NOT NULL COMMENT '合同计租时间',
	`rentCycle` INT(4) NOT NULL COMMENT '合同周期',
	`endTime` DATETIME NOT NULL COMMENT '合同结束时间',
	`propertyCompany` VARCHAR(64) NOT NULL COMMENT '物业公司',
	`propertyUnitPrice` DOUBLE(10,2) NOT NULL COMMENT '物业费单价',
	`propertyPhone` VARCHAR(20) NOT NULL COMMENT '物业联系电话',
	`isUpload` TINYINT(2) NOT NULL COMMENT '是否上传电子合同：0代表未上传 1代表已上传',
	`fileId` VARCHAR(64) NULL DEFAULT NULL COMMENT '电子合同文件ID',
	`createTime` DATETIME NOT NULL COMMENT '创建时间',
	`rentNo` VARCHAR(64) NOT NULL COMMENT '编号',
	PRIMARY KEY (`rentId`)
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `house_office_house`;
CREATE TABLE `house_office_house` (
	`houseId` VARCHAR(64) NOT NULL COMMENT '房屋id，uuid',
	`houseNum` VARCHAR(64) NOT NULL COMMENT '房号',
	`floorArea` DOUBLE(10,2) NULL DEFAULT NULL COMMENT '房屋建筑面积（预留字段）',
	`interFloorArea` DOUBLE(10,2) NOT NULL COMMENT '房屋套内面积',
	`deliverTime` DATETIME NULL DEFAULT NULL COMMENT '房屋交付时间（预留字段）',
	`buildingId` BIGINT(64) DEFAULT NULL COMMENT '房屋所属楼宇id',
	`unitId` BIGINT(64) DEFAULT NULL COMMENT '房屋所属楼座id',
	`houseStatus` TINYINT(1) NOT NULL COMMENT '房屋状态：1为招商中，2为空置中，3为出租中',
	`displayOrder` INT(8) NULL DEFAULT NULL COMMENT '排序字段',
	`estateId` VARCHAR(64) NOT NULL COMMENT '楼盘id',
	`createTime` DATETIME NOT NULL COMMENT '房屋创建时间',
	PRIMARY KEY (`houseId`)
)
COMMENT='房屋表（写字楼版）'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `estate_start_page`;
CREATE TABLE `estate_start_page` (
	`id` VARCHAR(64) NOT NULL,
	`name` VARCHAR(64) NOT NULL COMMENT '描述',
	`sort` INT(4) NOT NULL COMMENT '排序',
	`url` VARCHAR(64) NOT NULL COMMENT '图片地址',
	`appType` TINYINT(1) NOT NULL COMMENT '1：住宅用户app 2物业app 3 写字楼用户app',
	`createTime` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='app启动页的广告'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;


/**
创建存储过程，修改任务状态
 */
DROP PROCEDURE IF EXISTS `UPDATE_TASK_RECORD_CLOSETIME`;
CREATE PROCEDURE UPDATE_TASK_RECORD_CLOSETIME()
BEGIN
    UPDATE work_task_record SET taskStatus=4 WHERE taskStatus!=3 AND closeTime < NOW();
END;
/**
创建定时器，执行存储过程
 */
create event if not exists eventJob
on schedule every 1 second
on completion PRESERVE
do CALL UPDATE_TASK_RECORD_CLOSETIME();
/**
开启定时器
 */
SET GLOBAL event_scheduler = 1;
/**
开启事件
 */
ALTER EVENT eventJob ON  COMPLETION PRESERVE ENABLE;