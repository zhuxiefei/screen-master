-- ----------------------------
-- Table structure for security_area
-- ----------------------------
DROP TABLE IF EXISTS `security_area`;
CREATE TABLE `security_area` (
  `areaId` varchar(64) NOT NULL COMMENT '区域id',
  `areaName` varchar(128) NOT NULL COMMENT '区域名称',
  `areaDesc` varchar(512) DEFAULT NULL,
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_content
-- ----------------------------
DROP TABLE IF EXISTS `security_content`;
CREATE TABLE `security_content` (
  `contentId` varchar(64) NOT NULL,
  `contentNo` varchar(128) NOT NULL COMMENT '巡逻内容编号:XL+日期戳',
  `areaId` varchar(64) NOT NULL,
  `inspectionTime` varchar(512) NOT NULL COMMENT '定时发布时间：多个，隔开',
  `contentDesc` varchar(512) DEFAULT NULL COMMENT '巡逻内容',
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
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
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`contSignAreaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for security_record
-- ----------------------------
DROP TABLE IF EXISTS `security_record`;
CREATE TABLE `security_record` (
  `recordId` varchar(64) NOT NULL,
  `contentId` varchar(64) NOT NULL,
  `employeeId` varchar(64) DEFAULT NULL,
  `employeeName` varchar(128) DEFAULT NULL,
  `isPatrol` tinyint(1) NOT NULL COMMENT '1、待巡逻  2、已巡逻',
  `signinNum` int(4) DEFAULT '0',
  `finishTime` datetime DEFAULT NULL COMMENT '安保巡逻完成时间',
  `createTime` datetime NOT NULL COMMENT '安保巡逻发布时间',
  `updateTime` datetime NOT NULL,
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
  `areaId` varchar(64) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
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