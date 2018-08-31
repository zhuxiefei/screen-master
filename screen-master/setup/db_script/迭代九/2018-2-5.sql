INSERT INTO `system_config` VALUES ('system.deliver.time', '2017-10-01', null, '系统交付时间');

INSERT INTO `equipment_type` VALUES ('1', '供水系统', '2018-02-04 13:15:33');
INSERT INTO `equipment_type` VALUES ('2', '排水系统', '2018-02-04 13:15:43');
INSERT INTO `equipment_type` VALUES ('3', '供暖系统', '2018-02-04 13:15:53');
INSERT INTO `equipment_type` VALUES ('4', '消防', '2018-02-04 13:16:04');
INSERT INTO `equipment_type` VALUES ('5', '楼控', '2018-02-04 13:16:15');
INSERT INTO `equipment_type` VALUES ('6', '监控', '2018-02-04 13:16:25');
INSERT INTO `equipment_type` VALUES ('7', '通风', '2018-02-04 13:16:35');
INSERT INTO `equipment_type` VALUES ('8', '电梯', '2018-02-04 13:16:44');
INSERT INTO `equipment_type` VALUES ('9', '空调', '2018-02-04 13:17:08');
INSERT INTO `equipment_type` VALUES ('10', '燃气供应', '2018-02-04 13:17:16');
INSERT INTO `equipment_type` VALUES ('11', '通信网络', '2018-02-04 13:17:27');

ALTER TABLE patrol_equipment MODIFY COLUMN repairNumber int(16) NOT NULL DEFAULT 0 COMMENT '维修次数';

ALTER TABLE repair_order MODIFY COLUMN orderPic VARCHAR(64) DEFAULT NULL COMMENT '维修单据照片ID';

