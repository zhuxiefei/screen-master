package com.betel.estatemgmt.business.propertyapp.security.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: SecurityInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 15:48 <br/>
 * Version: 1.0 <br/>
 */
public class SecurityInfo {

    private String recordId;

    private String contentNo;

    private String areaName;

    private String signinAddress;

    private Date inspectionTime;

    private Date finishTime;

    private String contentDesc;

    private String employeeName;

    private String signinNum;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecurityInfo{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", contentNo='").append(contentNo).append('\'');
        sb.append(", areaName='").append(areaName).append('\'');
        sb.append(", signinAddress='").append(signinAddress).append('\'');
        sb.append(", inspectionTime='").append(inspectionTime).append('\'');
        sb.append(", finishTime='").append(finishTime).append('\'');
        sb.append(", contentDesc='").append(contentDesc).append('\'');
        sb.append(", employeeName='").append(employeeName).append('\'');
        sb.append(", signinNum='").append(signinNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
