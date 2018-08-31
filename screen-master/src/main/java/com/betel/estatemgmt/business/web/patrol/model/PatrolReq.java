package com.betel.estatemgmt.business.web.patrol.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: PatrolReq <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 14:22 <br/>
 * @version: 1.0
 */
public class PatrolReq {
    /**
     * 设备是否正常
     */
    private String recordStatus;
    /**
     * 完成状态：1、待巡检  2、已巡检  3 已逾期
     */
    private String isPatrol;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 设备编号
     */
    private String equipmentNo;
    /**
     * 设备类型名称
     */
    private String equipmentType;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备类型
     */
    private String typeId;

    /**
     * 巡检记录id
     */
    private String recordId;
    /**
     * 巡检记录id集合
     */
    private String recordIds;
    /**
     * 设备集合
     */
    private String equipmentIds;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 巡检设备内容描述
     */
    private String equipmentDesc;
    /**
     * 巡检周期
     */
    private String checkCycle;

    /**
     * 巡检人员
     */
    private String employeeName;
    /**
     * 页码
     */
    private Integer curPage = 1;
    /**
     * 每一页条数
     */
    private Integer pageSize = 10;
    /**
     * 查询当前人id
     */
    private String employeeId;

    private String deadline;

    private String equipmentProducer;

    private String producerPhone;

    private String equipmentOperator;

    private String operatorPhone;

    private String keyWord;

    /**
     * 0未删除
     */
    private String isDelete;

    private String[] arrIds;
    /**
     * 1 未过期的巡检任务   2 已巡检    3 已过期的巡检任务
     */
    private String tab1;
    /**
     * 1 正常的，2 不正常 null全部
     */
    private String tab2;
    /**
     * 是否需要巡检
     */
    private String isCheck;

    /**
     * 设备生产日期
     *
     * @return
     */
    private String equipmentCreateTime;

    private String qualityPeriod;
    private String unit;

    /**
     * 设备维保记录id
     * @return
     */
    private String equipmentRepairRecordId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getEquipmentCreateTime() {
        return equipmentCreateTime;
    }

    public String getEquipmentRepairRecordId() {
        return equipmentRepairRecordId;
    }

    public void setEquipmentRepairRecordId(String equipmentRepairRecordId) {
        this.equipmentRepairRecordId = equipmentRepairRecordId;
    }

    public void setEquipmentCreateTime(String equipmentCreateTime) {
        this.equipmentCreateTime = equipmentCreateTime;
    }

    public String getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(String qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getTab1() {
        return tab1;
    }

    public void setTab1(String tab1) {
        this.tab1 = tab1;
    }

    public String getTab2() {
        return tab2;
    }

    public void setTab2(String tab2) {
        this.tab2 = tab2;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String[] getArrIds() {
        return arrIds;
    }

    public void setArrIds(String[] arrIds) {
        this.arrIds = arrIds;
    }


    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(String checkCycle) {
        this.checkCycle = checkCycle;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getEquipmentProducer() {
        return equipmentProducer;
    }

    public void setEquipmentProducer(String equipmentProducer) {
        this.equipmentProducer = equipmentProducer;
    }

    public String getProducerPhone() {
        return producerPhone;
    }

    public void setProducerPhone(String producerPhone) {
        this.producerPhone = producerPhone;
    }

    public String getEquipmentOperator() {
        return equipmentOperator;
    }

    public void setEquipmentOperator(String equipmentOperator) {
        this.equipmentOperator = equipmentOperator;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    public String getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(String equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIsPatrol() {
        return isPatrol;
    }

    public void setIsPatrol(String isPatrol) {
        this.isPatrol = isPatrol;
    }

}
