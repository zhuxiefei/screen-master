package com.betel.estatemgmt.business.web.security.model;


import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import com.betel.estatemgmt.common.model.security.SecuritySigninNumber;

import java.util.List;

/**
 * Created by zhangjian on 2018/2/12.
 */
public class SecurityResp {


    /**
     * 巡逻内容录入
     */
    private String contentId;
    private String contentNo;
    private String areaId;
    private String areaName;
    private String signinAddress;
    private String contentDesc;
    private String inspectionTime;
    private String createTime;
    private String updateTime;
    private String estateId;


    /**
     * 安保巡逻下的签到记录集合
     */
    private List<SecuritySigninNumber> signinNumRecordList;
    private List<SecuritySigninAddress> signinAddressList;

    private String recordId;
    private String employeeName;
    private String signinNum;
    private String finishTime;

    @Override
    public String toString() {
        return "SecurityResp{" +
                "contentId='" + contentId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", signinAddress='" + signinAddress + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", inspectionTime='" + inspectionTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", estateId='" + estateId + '\'' +
                ", signinNumRecordList=" + signinNumRecordList +
                ", signinAddressList=" + signinAddressList +
                ", recordId='" + recordId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", signinNum='" + signinNum + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", isPatrol='" + isPatrol + '\'' +
                '}';
    }

    /**
     * 巡逻状态：
     * 1 待巡逻
     * 2 已巡逻
     * 3 逾期未巡逻
     */
    private String isPatrol;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }
    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSigninNum() {
        return signinNum;
    }

    public void setSigninNum(String signinNum) {
        this.signinNum = signinNum;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<SecuritySigninAddress> getSigninAddressList() {
        return signinAddressList;
    }

    public void setSigninAddressList(List<SecuritySigninAddress> signinAddressList) {
        this.signinAddressList = signinAddressList;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<SecuritySigninNumber> getSigninNumRecordList() {
        return signinNumRecordList;
    }

    public void setSigninNumRecordList(List<SecuritySigninNumber> signinNumRecordList) {
        this.signinNumRecordList = signinNumRecordList;
    }

    public String getIsPatrol() {
        return isPatrol;
    }

    public void setIsPatrol(String isPatrol) {
        this.isPatrol = isPatrol;
    }
}
