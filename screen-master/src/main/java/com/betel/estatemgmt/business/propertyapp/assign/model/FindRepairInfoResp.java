package com.betel.estatemgmt.business.propertyapp.assign.model;

import com.betel.estatemgmt.common.model.repair.RepairHistory;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 维修单详情模型
 * </p>
 * ClassName: FindRepairInfoResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 16:18 <br/>
 * Version: 1.0 <br/>
 */
public class FindRepairInfoResp {

    /**
     * 报修联系人
     */
    String orderContact;

    /**
     * 联系电话
     */
    String contactPhone;

    /**
     * 房屋编号
     */
    String houseNum;

    /**
     * 栋
     */
    String buildingName;

    //
    String unitName;

    String orderDesc;

    int orderArea;

    String childName;

    String parentName;

    Date createTime;

    Date appointTime;

    int urgeCount;

    List<String> pictures;

    String assignPhoto;

    String assignDepartment;

    String assignName;

    String photo;

    String operatorId;

    String operatorDepartment;

    String operatorName;

    Date cancelTime;

    List<RepairHistory> records;

    String orderPic;

    String description;

    String assginDesc;

    private Integer serviceOnTime;

    private Integer serviceAttitude;

    private Integer serviceQuality;

    private String serviceDesc;

    public Integer getServiceOnTime() {
        return serviceOnTime;
    }

    public void setServiceOnTime(Integer serviceOnTime) {
        this.serviceOnTime = serviceOnTime;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Integer getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Integer serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(String orderContact) {
        this.orderContact = orderContact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(int orderArea) {
        this.orderArea = orderArea;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public int getUrgeCount() {
        return urgeCount;
    }

    public void setUrgeCount(int urgeCount) {
        this.urgeCount = urgeCount;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<RepairHistory> getRecords() {
        return records;
    }

    public void setRecords(List<RepairHistory> records) {
        this.records = records;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public String getAssignPhoto() {
        return assignPhoto;
    }

    public void setAssignPhoto(String assignPhoto) {
        this.assignPhoto = assignPhoto;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignDepartment() {
        return assignDepartment;
    }

    public void setAssignDepartment(String assignDepartment) {
        this.assignDepartment = assignDepartment;
    }

    public String getAssginDesc() {
        return assginDesc;
    }

    public void setAssginDesc(String assginDesc) {
        this.assginDesc = assginDesc;
    }

    @Override
    public String toString() {
        return "FindRepairInfoResp{" +
                "orderContact='" + orderContact + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", orderArea=" + orderArea +
                ", childName='" + childName + '\'' +
                ", parentName='" + parentName + '\'' +
                ", createTime=" + createTime +
                ", appointTime=" + appointTime +
                ", urgeCount=" + urgeCount +
                ", pictures=" + pictures +
                ", assignPhoto='" + assignPhoto + '\'' +
                ", assignDepartment='" + assignDepartment + '\'' +
                ", assignName='" + assignName + '\'' +
                ", photo='" + photo + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operatorDepartment='" + operatorDepartment + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", cancelTime=" + cancelTime +
                ", records=" + records +
                ", orderPic='" + orderPic + '\'' +
                ", description='" + description + '\'' +
                ", assginDesc='" + assginDesc + '\'' +
                ", serviceOnTime=" + serviceOnTime +
                ", serviceAttitude=" + serviceAttitude +
                ", serviceQuality=" + serviceQuality +
                ", serviceDesc='" + serviceDesc + '\'' +
                '}';
    }
}
